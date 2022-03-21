package net.yuwtennis.app.repositories;

import org.apache.beam.sdk.io.mongodb.MongoDbIO;
import org.apache.beam.sdk.values.PCollection;

public class MongoIO {

    public static PCollection Write(PCollection pcol, String uri, String database, String collection) {
        pcol.apply(
                MongoDbIO.write()
                        .withUri((uri))
                        .withDatabase(database)
                        .withCollection(collection));
        return pcol ;
    }

    public static PCollection Read(PCollection pcol, String uri, String database, String collection) {
        pcol.apply(
                MongoDbIO.read()
                        .withUri((uri))
                        .withDatabase(database)
                        .withCollection(collection));

        return pcol;
    }
}
