package net.yuwtennis.app.pipelines;

import org.apache.beam.sdk.Pipeline;

public interface PipelineService {
    void run(Pipeline p);
}
