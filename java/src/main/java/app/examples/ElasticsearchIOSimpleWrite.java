package app.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import app.pipelines.connectors.ElasticsearchIORepository;
import app.pipelines.elements.Quote;
import app.pipelines.elements.StaticElements;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElasticsearchIOSimpleWrite {

    private static final String[] addresses = new String[] {"http://localhost:9200"} ;
    private static final String index = "quote" ;
    private static final String type = "_doc" ;

    final Logger logger = LogManager.getLogger(
            ElasticsearchIOSimpleWrite.class) ;

    private static class StringToJsonFn extends SimpleFunction<String, String> {
        final Logger logger = LogManager.getLogger(
                StringToJsonFn.class
        );

        @Override
        public String apply(String line) {
            ObjectMapper objectMapper = new ObjectMapper() ;
            Quote quote = new Quote(line) ;
            String jsonString = null ;

            try {
                jsonString = objectMapper.writeValueAsString(quote);
            } catch (Exception e) {
                this.logger.error("Something wrong.") ;
            }
            return jsonString ;
        }
    }

    public static void build(org.apache.beam.sdk.Pipeline p) {
        PCollection<String> pCol =  p.apply(
                "ToPcollection",
                Create.of(StaticElements.LINES)).setCoder(StringUtf8Coder.of()
        ).apply(
                MapElements.via(new StringToJsonFn())
        );

        ElasticsearchIORepository.write(
                pCol,
                addresses,
                index,
                type);
    }

    public static void main(String[] args)  {
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options) ;
        build(pipeline) ;
        pipeline.run().waitUntilFinish() ;
    }

}
