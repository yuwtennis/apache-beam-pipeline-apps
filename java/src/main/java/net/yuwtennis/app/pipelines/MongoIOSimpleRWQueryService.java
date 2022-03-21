package net.yuwtennis.app.pipelines;

import net.yuwtennis.app.helpers.fns.PrintFn;
import net.yuwtennis.app.schemas.StaticSchema;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

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

    public void run(Pipeline p) {

        PCollection<String> pcol = p.apply(
                Create.of(StaticSchema.LINES)).setCoder(StringUtf8Coder.of());

        // From String to org.bson.Document
        PCollection<Document> w_docs =  pcol.apply(MapElements.via(
                new SimpleFunction<String, Document>() {
                    public Document apply(String line) {
                        Map<String, Object> map = new HashMap<String, Object>() ;
                        map.put("sentence", line);

                        return new Document(map) ;
                    }
                }
        ));

        Write(w_docs, uri, database, collection);

        // From org.bson.Document to String
        PCollection<Document> r_docs = Read(p, uri, database, collection);

        r_docs.apply(MapElements.via(
                new SimpleFunction<Document, String>() {
                    public String apply(Document doc) {
                      return (String)doc.get("sentence") ;
                    }
        })).apply(MapElements.via(new PrintFn()));
    }
}
