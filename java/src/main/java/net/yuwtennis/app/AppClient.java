package net.yuwtennis.app;

import net.yuwtennis.app.pipelines.MongoIOSimpleRWQueryService;
import net.yuwtennis.app.pipelines.PipelineService;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AppClient {
    private final static Logger logger = LogManager.getLogger(
            AppClient.class) ;

    public static void main( String[] args ) {
        Pipeline p = create_pipeline();
        String pipelineClass = System.getenv("PIPELINE_CLASS");

        AppClient.logger.info("Loading {}", pipelineClass);

        try {
            PipelineService object = (PipelineService)Class
                                .forName(pipelineClass)
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
