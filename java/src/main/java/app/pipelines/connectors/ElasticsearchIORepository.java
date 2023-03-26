package app.pipelines.connectors;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.elasticsearch.ElasticsearchIO;
import org.apache.beam.sdk.values.PCollection;

public class ElasticsearchIORepository {

    /***
     * Write uses bulk api to index documents to elasticsearch. This will be the terminal of a pipeline.
     * @param docs
     * @param con
     */
    public static void write (PCollection<String> docs,
                              ElasticsearchIO.ConnectionConfiguration con) {
        docs.apply(
                "ToElasticsearch",
                ElasticsearchIO
                        .write()
                        .withConnectionConfiguration(con)
                        .withMaxBatchSize(100)
        );
    }

    /***
     * Read queries document from elasticsearch using specified query
     * @param p
     * @param query
     * @param enableMetadata
     * @param con
     * @return
     */
    public static PCollection<String> read (
            Pipeline p,
            String query,
            Boolean enableMetadata,
            ElasticsearchIO.ConnectionConfiguration con) {
        ElasticsearchIO.Read rd = ElasticsearchIO.read()
                .withConnectionConfiguration(con)
                .withQuery(query) ;
        ReadOptBuilder.setOptMetadata(rd, enableMetadata);

        return p.apply(rd);
    }

    public static class ReadOptBuilder {

        /***
         * setOptMetadata sets MetaData option to Read class object
         * @param rd
         * @param isMetadataEnabled
         */
        public static void setOptMetadata(ElasticsearchIO.Read rd, Boolean isMetadataEnabled) {
            if(isMetadataEnabled) {
                rd.withMetadata();
            }
        }
    }
}
