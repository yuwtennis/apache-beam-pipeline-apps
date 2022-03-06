package net.yuwtennis.app;

import net.yuwtennis.app.service.pipeline.MongoReadQueryService;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;

public class AppClient {

    public static void main( String[] args ) {
        Pipeline p = create_pipeline();

        // FIXME Should be double dispatch
    }

    /**
     *
     * @return Pipeline
     */
    public static Pipeline create_pipeline() {
        PipelineOptions options = PipelineOptionsFactory.create();
        return Pipeline.create();
    }
}
