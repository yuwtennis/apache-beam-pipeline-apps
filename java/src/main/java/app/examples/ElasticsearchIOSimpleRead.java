package app.examples;

import app.helpers.fns.PrintFn;
import app.pipelines.connectors.ElasticsearchIORepository;
import app.pipelines.values.envs.ElasticsearchEnvVars;
import app.pipelines.values.envs.EnvVars;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.elasticsearch.ElasticsearchIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElasticsearchIOSimpleRead {

    final Logger logger = LogManager.getLogger(
            ElasticsearchIOSimpleRead.class) ;

    public static void build(Pipeline p) {
        String query = "{\"query\": {\"match_all\": {}}}" ;
        Boolean enableMetadata = true ;

        EnvVars<ElasticsearchEnvVars.Elasticsearch> envVars = new ElasticsearchEnvVars() ;
        ElasticsearchEnvVars.Elasticsearch esVars = envVars.loadEnv();

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

        PCollection<String> pCol = ElasticsearchIORepository.read(
                p,
                query,
                enableMetadata,
                con) ;

        pCol.apply(MapElements.via(new PrintFn())) ;
    }

    public static void main(String[] args)  {
        PipelineOptions options = PipelineOptionsFactory.fromArgs().withValidation().create();
        Pipeline pipeline = Pipeline.create(options) ;
        build(pipeline) ;
        pipeline.run().waitUntilFinish() ;
    }

}
