package net.yuwtennis.app.pipelines.connectors;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.elasticsearch.ElasticsearchIO;
import org.apache.beam.sdk.values.PCollection;

public class ElasticsearchIORepository {

    public static void Write (PCollection<String> docs,
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

    public static PCollection<String> Read (
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

    private static ElasticsearchIO.ConnectionConfiguration create(
            String[] addresses,
            String index,
            String type) {
        ElasticsearchIO.ConnectionConfiguration con = ElasticsearchIO.ConnectionConfiguration.create(
                addresses,
                index,
                type);

        return con ;
    }
}
