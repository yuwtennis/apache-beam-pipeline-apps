package net.yuwtennis.app.pipelines;

import org.apache.beam.sdk.Pipeline;

public interface PipelineService {
    Pipeline build(Pipeline p);
}
