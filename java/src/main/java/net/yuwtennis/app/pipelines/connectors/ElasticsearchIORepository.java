package net.yuwtennis.app.pipelines.connectors;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.elasticsearch.ElasticsearchIO;
import org.apache.beam.sdk.values.PCollection;

public class ElasticsearchIORepository {

    /***
     * Write uses bulk api to index documents to elasticsearch
     * @param docs
     * @param addresses
     * @param index
     * @param type
     */
    public static void write (PCollection<String> docs,
                              String[] addresses,
                              String index,
                              String type) {

        ElasticsearchIO.ConnectionConfiguration con = createConnection(addresses, index, type) ;

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
     * @param addresses
     * @param index
     * @param type
     * @param query
     * @return
     */
    public static PCollection<String> read (
            Pipeline p,
            String[] addresses,
            String index,
            String type,
            String query,
            Boolean enableMetadata) {
        ElasticsearchIO.ConnectionConfiguration con = createConnection(addresses, index, type) ;

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

    /***
     * createConnection creates connection object for accessing elasticsearch
     * @param addresses
     * @param index
     * @param type
     * @return
     */
    private static ElasticsearchIO.ConnectionConfiguration createConnection(
            String[] addresses,
            String index,
            String type) {
        ElasticsearchIO.ConnectionConfiguration con = ElasticsearchIO.ConnectionConfiguration.create(
                addresses,
                index,
                type)
                .withUsername(System.getenv("ES_USER"))
                .withPassword(System.getenv("ES_PASSWORD"));

        return con ;
    }
}
