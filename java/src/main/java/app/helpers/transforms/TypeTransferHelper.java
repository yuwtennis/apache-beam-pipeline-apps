package app.helpers.transforms;

import org.bson.BsonDocument;

public class TypeTransferHelper {
    /**
     *
     * @param json as STRING
     * @return BSON document
     */
    public static BsonDocument strToBSONDocument(String json) {
        return BsonDocument.parse(json) ;
    }
}
