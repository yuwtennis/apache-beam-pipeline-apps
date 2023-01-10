package app.helpers.transforms;

import org.bson.BsonDocument;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TypeTransferHelperTest {
    private String data ;

    @Before
    public void setUp() {
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
