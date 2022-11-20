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

        ElasticsearchIO.ConnectionConfiguration con = create(addresses, index, type) ;

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
            String query) {
        ElasticsearchIO.ConnectionConfiguration con = create(addresses, index, type) ;

        PCollection<String> docs =  p.apply(
                ElasticsearchIO.read()
                        .withConnectionConfiguration(con)
                        .withQuery(query)
        ) ;

        return docs ;
    }

    /***
     * create creates connection object for accessing elasticsearch
     * @param addresses
     * @param index
     * @param type
     * @return
     */
    private static ElasticsearchIO.ConnectionConfiguration create(
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
