package net.yuwtennis.app.pipelines;

import net.yuwtennis.app.helpers.fns.PrintFn;
import net.yuwtennis.app.helpers.transforms.TypeTransferHelper;
import net.yuwtennis.app.pipelines.elements.SimpleEntity;
import net.yuwtennis.app.pipelines.elements.StaticElements;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.mongodb.FindQuery;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static net.yuwtennis.app.pipelines.connectors.MongoIORepository.ReadWithCustomQuery;

public class MongoIOSimpleQueryService implements PipelineService{
    public String uri ;
    public String database ;
    public String collection ;
    final Logger logger = LogManager.getLogger(
            MongoIOSimpleReadService.class) ;

    public MongoIOSimpleQueryService() {
        // FIXME Default system env for now
        this.uri = String.format("mongodb://%s:%s@%s",
                System.getenv("MONGO_USER"),
                System.getenv("MONGO_PASSWORD"),
                System.getenv("MONGO_HOST"));

        this.database =  System.getenv("MONGO_DB") ;
        this.collection =  System.getenv("MONGO_COLLECTION") ;

        this.logger.info("uri: {} , db: {} , collection: {}",
                this.uri , this.database, this.collection) ;
    }

    /***
     *
     * @param p Pipeline instance
     */
    public void run(Pipeline p) {
        this.logger.info("Running pipeline...") ;

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
                this.uri,
                this.database,
                this.collection,
                findQuery);

        r_docs.apply(
                MapElements
                        .into(TypeDescriptor.of(SimpleEntity.class))
                        .via(SimpleEntity::fromDocument)
        ).apply(
                MapElements
                        .into(TypeDescriptors.strings())
                        .via((SimpleEntity entity) -> entity.sentence)
        ).apply(MapElements.via(new PrintFn()));

        p.run().waitUntilFinish() ;
    }
}
