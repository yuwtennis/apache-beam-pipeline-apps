package app.helpers.transforms;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bson.BsonDocument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Test for TypeTransferHelper. */
public class TypeTransferHelperTest {

  private static String data;

  @BeforeAll
  static void setUp() {
    // https://www.mongodb.com/docs/manual/reference/mongodb-extended-json/#example
    // Use canonical format
    data = "{\"a\":\"hello\"}";
  }

  @Test
  public void strToBsonDocumentTest() {
    BsonDocument bdoc = TypeTransferHelper.strToBSONDocument(data);

    assertNotNull(bdoc);
  }
}
