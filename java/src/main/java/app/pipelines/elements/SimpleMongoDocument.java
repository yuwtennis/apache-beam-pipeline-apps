package app.pipelines.elements;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

// https://beam.apache.org/documentation/programming-guide/#annotating-custom-type-default-coder
@DefaultCoder(AvroCoder.class)
public class SimpleMongoDocument {
    private final static Logger logger = LoggerFactory.getLogger(
            SimpleMongoDocument.class) ;

    public String sentence ;

    // no-argument constructor
    public SimpleMongoDocument() {}

    public SimpleMongoDocument(String line) {
        this.sentence = line ;
    }

    /***
     *
     * @return Mongo document
     */
    public Document toDocument() {

        Map<String, Object> map = new HashMap<String, Object>() ;
        map.put("sentence", this.sentence);

        return new Document(map);
    }

    /***
     *
     * @param doc Mongo document
     * @return Instance of SimpleEntity
     */
    public static SimpleMongoDocument fromDocument(Document doc) {
        SimpleMongoDocument.logger.info("Queried document: {}", doc.toJson());

        return new SimpleMongoDocument((String)doc.get("sentence"));
    }
}
