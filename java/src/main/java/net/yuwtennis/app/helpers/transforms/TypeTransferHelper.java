package net.yuwtennis.app.helpers.transforms;

import org.bson.BsonDocument;

public class TypeTransferHelper {
    public static BsonDocument strToBSONDocument(String json) {
        return BsonDocument.parse(json) ;
    }
}
