package app.examples;

import app.helpers.fns.PrintFn;
import app.helpers.transforms.TypeTransferHelper;
import app.pipelines.elements.SimpleMongoDocument;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.mongodb.FindQuery;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static app.pipelines.connectors.MongoIORepository.ReadWithCustomQuery;

public class MongoIOSimpleQuery {
    private static final String uri = String.format("mongodb://%s:%s@%s",
            System.getenv("MONGO_USER"),
            System.getenv("MONGO_PASSWORD"),
            System.getenv("MONGO_HOST"));;
    private static final String database = System.getenv("MONGO_DB");
    private static final String collection = System.getenv("MONGO_COLLECTION") ;
    final Logger logger = LogManager.getLogger(
            MongoIOSimpleRead.class) ;

    /***
     *
     * @param p Pipeline instance
     */
    public static void build(org.apache.beam.sdk.Pipeline p) {

        // BSON document as filter
        // https://github.com/apache/beam/blob/v2.36.0/sdks/java/io/mongodb/src/main/java/org/apache/beam/sdk/io/mongodb/FindQuery.java#L79
        String filter = "{}" ;

        // Will be passed to Projection
        // https://github.com/apache/beam/blob/v2.36.0/sdks/java/io/mongodb/src/main/java/org/apache/beam/sdk/io/mongodb/FindQuery.java#L109
        List<String> projections = new ArrayList<String>() ;
        projections.add("sentence");

        FindQuery findQuery = FindQuery
                .create()
                .withFilters(TypeTransferHelper.strToBSONDocument(filter))
                .withLimit(10)
                .withProjection(projections) ;

        // From org.bson.Document to String
        PCollection<Document> r_docs = ReadWithCustomQuery(
                p,
                uri,
                database,
                collection,
                findQuery);

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

    public static void main(String[] args)  {
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options) ;
        build(pipeline) ;
        pipeline.run().waitUntilFinish() ;
    }

}
