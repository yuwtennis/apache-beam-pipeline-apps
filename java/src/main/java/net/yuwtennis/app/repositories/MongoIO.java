package net.yuwtennis.app.repositories;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.mongodb.MongoDbIO;
import org.apache.beam.sdk.values.PCollection;
import org.bson.Document;

public class MongoIO {

    public static void Write(
            PCollection<Document> docs,
            String uri,
            String database,
            String collection) {
        docs.apply(
                MongoDbIO.write()
                        .withUri((uri))
                        .withDatabase(database)
                        .withCollection(collection));
    }

    public static PCollection<Document> Read(
            Pipeline p,
            String uri,
            String database,
            String collection) {
        PCollection<Document> docs = p.apply(
                MongoDbIO.read()
                        .withUri((uri))
                        .withDatabase(database)
                        .withCollection(collection));

        return docs;
    }
}
