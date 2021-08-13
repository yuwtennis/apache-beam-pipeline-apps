package net.yuwtennis.app.entities;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class PipelineDomainEntity {

    /**
     * Returns Pipeline instance.
     * https://beam.apache.org/documentation/programming-guide/
     *
     * @return Pipeline instance
     */
    public Pipeline create() {
        PipelineOptions options = PipelineOptionsFactory.create();

        return Pipeline.create(options);
    }
}
