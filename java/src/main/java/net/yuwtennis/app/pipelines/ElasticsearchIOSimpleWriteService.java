package net.yuwtennis.app.pipelines;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.yuwtennis.app.pipelines.PipelineService;
import net.yuwtennis.app.pipelines.connectors.ElasticsearchIORepository;
import net.yuwtennis.app.pipelines.elements.Quote;
import net.yuwtennis.app.pipelines.elements.StaticElements;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElasticsearchIOSimpleWriteService implements PipelineService {

    private String[] addresses;
    private String index ;
    private String type ;
    final Logger logger = LogManager.getLogger(
            ElasticsearchIOSimpleWriteService.class) ;

    public ElasticsearchIOSimpleWriteService() {
        this.addresses = new String[] {"http://localhost:9200"};
        this.index = "quote" ;
        this.type = "doc" ;
    }

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

    public Pipeline build(Pipeline p) {
        PCollection<String> pCol =  p.apply(
                "ToPcollection",
                Create.of(StaticElements.LINES)).setCoder(StringUtf8Coder.of()
        ).apply(
                MapElements.via(new StringToJsonFn())
        );

        ElasticsearchIORepository.Write(
                pCol,
                this.addresses,
                this.index,
                this.type);

        return p;
    }
}
