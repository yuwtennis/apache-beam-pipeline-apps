package net.yuwtennis.app.pipelines;

import net.yuwtennis.app.entities.SimpleEntity;
import net.yuwtennis.app.helpers.fns.PrintFn;
import net.yuwtennis.app.schemas.StaticSchema;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.bson.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.yuwtennis.app.repositories.MongoIO.Read;
import static net.yuwtennis.app.repositories.MongoIO.Write;

public class MongoIOSimpleRWQueryService implements PipelineService {

    public String uri ;
    public String database ;
    public String collection ;
    final Logger logger = LogManager.getLogger(
            MongoIOSimpleRWQueryService.class) ;

    public MongoIOSimpleRWQueryService() {
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

        PCollection<SimpleEntity> pcol = p.apply(
                Create.of(StaticSchema.LINES)).setCoder(StringUtf8Coder.of()
        ).apply(
                MapElements
                        .into(TypeDescriptor.of(SimpleEntity.class))
                        .via(SimpleEntity::new)
        );

        // From String to org.bson.Document
        PCollection<Document> w_docs =  pcol.apply(
                MapElements
                        .into(TypeDescriptor.of(Document.class))
                        .via(SimpleEntity::toDocument));

        Write(
                w_docs,
                this.uri,
                this.database,
                this.collection);

        // From org.bson.Document to String
        PCollection<Document> r_docs = Read(
                p,
                this.uri,
                this.database,
                this.collection);

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
