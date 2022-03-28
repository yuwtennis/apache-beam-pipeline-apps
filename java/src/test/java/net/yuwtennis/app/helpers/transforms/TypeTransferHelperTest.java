package net.yuwtennis.app.helpers.transforms;

import org.bson.BsonDocument;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TypeTransferHelperTest {
    private String data ;

    @Before
    public void setUp() {
        String data = "{sentence: 'SOMEVALUE'}";
    }

    @Test
    public void strToBSONDocumentTest() {
        BsonDocument bdoc = TypeTransferHelper.strToBSONDocument(data) ;

        assertNotNull(bdoc);
    }
}
