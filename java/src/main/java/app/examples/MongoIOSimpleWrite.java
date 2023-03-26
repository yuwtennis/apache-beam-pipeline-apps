package app.examples;

import app.pipelines.elements.SimpleMongoDocument;
import app.pipelines.elements.StaticElements;
import app.pipelines.values.envs.EnvVars;
import app.pipelines.values.envs.MongoDbEnvVars;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import static app.pipelines.connectors.MongoIORepository.Write;

public class MongoIOSimpleWrite {

    final Logger logger = LogManager.getLogger(
            MongoIOSimpleWrite.class) ;

    /***
     *
     * @param p Pipeline instance
     */
    public static void build(org.apache.beam.sdk.Pipeline p) {
        EnvVars<MongoDbEnvVars.MongoDb> envVars = new MongoDbEnvVars();
        MongoDbEnvVars.MongoDb mongoDbVars = envVars.loadEnv();

        PCollection<SimpleMongoDocument> pcol = p.apply(
                Create.of(StaticElements.LINES)).setCoder(StringUtf8Coder.of()
        ).apply(
                MapElements
                        .into(TypeDescriptor.of(SimpleMongoDocument.class))
                        .via(SimpleMongoDocument::new)
        );

        // From String to org.bson.Document
        PCollection<Document> w_docs =  pcol.apply(
                MapElements
                        .into(TypeDescriptor.of(Document.class))
                        .via(SimpleMongoDocument::toDocument));

        Write(
                w_docs,
                String.format("mongodb://%s:%s@%s",
                        mongoDbVars.username(), mongoDbVars.password(), mongoDbVars.host()),
                mongoDbVars.dbName(),
                mongoDbVars.collectionName());
    }

    public static void main(String[] args)  {
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options) ;
        build(pipeline) ;
        pipeline.run().waitUntilFinish() ;
    }

}
