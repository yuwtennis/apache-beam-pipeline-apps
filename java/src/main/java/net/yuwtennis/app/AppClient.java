package net.yuwtennis.app;

import net.yuwtennis.app.pipelines.PipelineService;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class AppClient {

    public static void main( String[] args ) {
        Pipeline p = create_pipeline();

        try {
            PipelineService object = (PipelineService)Class
                                .forName(System.getenv("PIPELINE_CLASS"))
                                .getDeclaredConstructor()
                                .newInstance();

            object.run(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Factory method
     * @return Pipeline
     */
    public static Pipeline create_pipeline() {
        PipelineOptions options = PipelineOptionsFactory.create();
        return Pipeline.create(options);
    }
}
