package app.pipelines.connectors;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.mongodb.FindQuery;
import org.apache.beam.sdk.io.mongodb.MongoDbIO;
import org.apache.beam.sdk.values.PCollection;
import org.bson.Document;

public class MongoIORepository {

    /***
     * Store elements into MongoDb
     *
     * @param docs Mongo documents
     * @param uri Mongo connection uri
     * @param database Mongo database name
     * @param collection Mongo collection name
     */
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

    /***
     * Reconstitute elements from MongoDb
     *
     * @param p Pipeline instance
     * @param uri Mongo uri
     * @param database Mongo database name
     * @param collection Mongo collection name
     * @return A mongo document
     */
    public static PCollection<Document> Read(
            Pipeline p,
            String uri,
            String database,
            String collection) {

        return p.apply(
                MongoDbIO.read()
                        .withUri((uri))
                        .withDatabase(database)
                        .withCollection(collection));
    }

    public static PCollection<Document> ReadWithCustomQuery(
            Pipeline p,
            String uri,
            String database,
            String collection,
            FindQuery queryBuilder) {

        return p.apply(
                MongoDbIO.read()
                        .withUri((uri))
                        .withDatabase(database)
                        .withCollection(collection)
                        .withQueryFn(queryBuilder));
    }
}
