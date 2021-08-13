package net.yuwtennis.app.service.pipeline;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.io.elasticsearch.ElasticsearchIO;

public class LocalFsToElasticsearchService implements PipelineBase {

    // This is the file name which will read data from.
    public static final String fromFileName = "/tmp/sample.log";

    // This is the list of elasticsearch host:port connecting to.
    public static final String[] toEsHost =
            new String[]{"http://localhost:9200"};

    // This is the index name of elasticsearch
    public static final String toEsIndex = "my-index";

    /**
     * Simply run the pipeline.
     *
     * @param p Pipeline instance
     */
    public void run(Pipeline p) {
        // This will be the 'Read Transform'
        PCollection records = p.apply(
                "LocalFilesystem",
                TextIO.read().from(this.fromFileName)
        );

        // Prepare connection instance
        ElasticsearchIO.ConnectionConfiguration esConn =
                ElasticsearchIO.ConnectionConfiguration.create(
                       this.toEsHost, this.toEsIndex);

        // This will be the 'Write Transform' using elasticsearch build API
        records.apply(
                ElasticsearchIO.bulkIO().withConnectionConfiguration(esConn));
    }
}
