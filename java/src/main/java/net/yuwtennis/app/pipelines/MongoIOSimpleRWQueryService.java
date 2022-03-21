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

import static net.yuwtennis.app.repositories.MongoIO.Read;
import static net.yuwtennis.app.repositories.MongoIO.Write;

public class MongoIOSimpleRWQueryService implements PipelineService {

    public String uri ;
    public String database ;
    public String collection ;

    public MongoIOSimpleRWQueryService() {
        // FIXME Default system env for now
        this.uri = String.format("mongodb://%s:%s@%s",
                System.getenv("MONGO_USER"),
                System.getenv("MONGO_PASSWORD"),
                System.getenv("MONGO_HOST"));

        this.database =  System.getenv("MONGO_DB") ;
        this.collection =  System.getenv("MONGO_COLLECTION") ;
    }

    /***
     *
     * @param p Pipeline instance
     */
    public void run(Pipeline p) {

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

        Write(w_docs, uri, database, collection);

        // From org.bson.Document to String
        PCollection<Document> r_docs = Read(p, uri, database, collection);

        r_docs.apply(
                MapElements
                        .into(TypeDescriptor.of(SimpleEntity.class))
                        .via(SimpleEntity::fromDocument)
        ).apply(
                MapElements
                        .into(TypeDescriptors.strings())
                        .via((SimpleEntity entity) -> entity.sentence)
        ).apply(MapElements.via(new PrintFn()));
    }
}
