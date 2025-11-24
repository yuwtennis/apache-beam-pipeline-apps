package app.examples;

import app.helpers.fns.PrintFn;
import app.pipelines.elements.SimpleMongoDocument;
import app.pipelines.elements.StaticElements;
import app.pipelines.values.envs.EnvVars;
import app.pipelines.values.envs.MongoDbEnvVars;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.bson.Document;

import static app.pipelines.connectors.MongoIORepository.Read;

public class MongoIOSimpleRead implements Examples {

    final Logger logger = LoggerFactory.getLogger(
            MongoIOSimpleRead.class) ;

    /***
     *
     * @param p Pipeline instance
     */
    public void build(Pipeline p) {
        EnvVars<MongoDbEnvVars.MongoDb> envVars = new MongoDbEnvVars();
        MongoDbEnvVars.MongoDb mongoDbVars = envVars.loadEnv();

        PCollection<SimpleMongoDocument> pcol = p.apply(
                Create.of(StaticElements.LINES)).setCoder(StringUtf8Coder.of()
        ).apply(
                MapElements
                        .into(TypeDescriptor.of(SimpleMongoDocument.class))
                        .via(SimpleMongoDocument::new)
        );

        // From org.bson.Document to String
        PCollection<Document> r_docs = Read(
                p,
                String.format("mongodb://%s:%s@%s",
                        mongoDbVars.username(), mongoDbVars.password(), mongoDbVars.host()),
                mongoDbVars.dbName(),
                mongoDbVars.collectionName());

        r_docs.apply(
                MapElements
                        .into(TypeDescriptor.of(SimpleMongoDocument.class))
                        .via(SimpleMongoDocument::fromDocument)
        ).apply(
                MapElements
                        .into(TypeDescriptors.strings())
                        .via((SimpleMongoDocument entity) -> entity.sentence)
        ).apply(MapElements.via(new PrintFn()));
    }
}
