package net.yuwtennis.app.pipelines;

import net.yuwtennis.app.pipelines.elements.SimpleEntity;
import net.yuwtennis.app.pipelines.elements.StaticElements;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.bson.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.yuwtennis.app.pipelines.connectors.MongoIORepository.Write;

public class MongoIOSimpleWriteService implements PipelineService {

    public String uri ;
    public String database ;
    public String collection ;
    final Logger logger = LogManager.getLogger(
            MongoIOSimpleWriteService.class) ;

    public MongoIOSimpleWriteService() {
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
                Create.of(StaticElements.LINES)).setCoder(StringUtf8Coder.of()
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

        p.run().waitUntilFinish() ;
    }
}
