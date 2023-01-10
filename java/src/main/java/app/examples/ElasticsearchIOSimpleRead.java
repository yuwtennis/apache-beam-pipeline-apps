package app.examples;

import app.helpers.fns.PrintFn;
import app.pipelines.connectors.ElasticsearchIORepository;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElasticsearchIOSimpleRead {

    private static final String[] addresses = new String[] {"http://localhost:9200"};
    private static final String index = "quote" ;
    private static final String type = "doc" ;
    private static final String query = "{\"query\": {\"match_all\": {}}}" ;

    final Logger logger = LogManager.getLogger(
            ElasticsearchIOSimpleRead.class) ;

    public static void build(Pipeline p) {
        PCollection<String> pCol = ElasticsearchIORepository.read(
                p,
                addresses,
                index,
                type,
                query,
                true) ;

        pCol.apply(MapElements.via(new PrintFn())) ;
    }

    public static void main(String[] args)  {
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options) ;
        build(pipeline) ;
        pipeline.run().waitUntilFinish() ;
    }

}
