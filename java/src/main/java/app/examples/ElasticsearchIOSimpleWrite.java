package app.examples;

import app.pipelines.values.envs.ElasticsearchEnvVars;
import app.pipelines.values.envs.EnvVars;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.pipelines.connectors.ElasticsearchIORepository;
import app.pipelines.elements.Quote;
import app.pipelines.elements.StaticElements;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.elasticsearch.ElasticsearchIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElasticsearchIOSimpleWrite {

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
        EnvVars<ElasticsearchEnvVars.Elasticsearch> envVars = new ElasticsearchEnvVars() ;
        ElasticsearchEnvVars.Elasticsearch esVars = envVars.loadEnv();

        PCollection<String> pCol =  p.apply(
                "ToPcollection",
                Create.of(StaticElements.LINES)).setCoder(StringUtf8Coder.of()
        ).apply(
                MapElements.via(new StringToJsonFn())
        );

        // Use TLS connection as default. Be close to real world configuration.
        // Keystore needs to be configured in advance. See TUTORIAL.md for example configuration.
        ElasticsearchIO.ConnectionConfiguration con = ElasticsearchIO.ConnectionConfiguration.create(
                        esVars.hosts().toArray(new String[0]),
                        esVars.indexName(),
                        esVars.mappingType())
                .withUsername(esVars.username())
                .withPassword(esVars.password())
                .withTrustSelfSignedCerts(esVars.trustSelfSignedCerts())
                .withKeystorePath(esVars.keyStorePath())
                .withKeystorePassword(esVars.keyStorePassword());

        ElasticsearchIORepository.write(
                pCol,
                con
        );
    }

    public static void main(String[] args)  {
        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().create();
        Pipeline pipeline = Pipeline.create(options) ;
        build(pipeline) ;
        pipeline.run().waitUntilFinish() ;
    }
}
