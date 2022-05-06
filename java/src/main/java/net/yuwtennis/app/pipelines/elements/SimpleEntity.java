package net.yuwtennis.app.pipelines.elements;

import net.yuwtennis.app.AppClient;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import java.util.HashMap;
import java.util.Map;

// https://beam.apache.org/documentation/programming-guide/#annotating-custom-type-default-coder
@DefaultCoder(AvroCoder.class)
public class SimpleEntity {
    private final static Logger logger = LogManager.getLogger(
            SimpleEntity.class) ;

    public String sentence ;

    // no-argument constructor
    public SimpleEntity() {}

    public SimpleEntity(String line) {
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
    public static SimpleEntity fromDocument(Document doc) {
        SimpleEntity.logger.info("Queried document: {}", doc.toJson());

        return new SimpleEntity((String)doc.get("sentence"));
    }
}
