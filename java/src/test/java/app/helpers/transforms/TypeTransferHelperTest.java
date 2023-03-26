package app.helpers.transforms;

import org.bson.BsonDocument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TypeTransferHelperTest {
    private static String data ;

    @BeforeAll
    static void setUp() {
        // https://www.mongodb.com/docs/manual/reference/mongodb-extended-json/#example
        // Use canonical format
        data = "{\"a\":\"hello\"}";
    }

    @Test
    public void strToBSONDocumentTest() {
        BsonDocument bdoc = TypeTransferHelper.strToBSONDocument(data) ;

        assertNotNull(bdoc);
    }
}
