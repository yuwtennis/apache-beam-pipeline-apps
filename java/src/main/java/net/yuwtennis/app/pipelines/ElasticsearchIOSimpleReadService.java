package net.yuwtennis.app.pipelines;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.yuwtennis.app.helpers.fns.PrintFn;
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

public class ElasticsearchIOSimpleReadService implements PipelineService {

    private String[] addresses;
    private String index ;
    private String type ;
    private String query ;
    final Logger logger = LogManager.getLogger(
            ElasticsearchIOSimpleReadService.class) ;

    public ElasticsearchIOSimpleReadService() {
        this.addresses = new String[] {"http://localhost:9200"};
        this.index = "quote" ;
        this.type = "doc" ;
        this.query = "{\"query\": {\"match_all\": {}}}";
    }

    public Pipeline build(Pipeline p) {
        PCollection<String> pCol = ElasticsearchIORepository.read(
                p,
                this.addresses,
                this.index,
                this.type,
                this.query) ;
        pCol.apply(MapElements.via(new PrintFn())) ;

        return p;
    }
}
