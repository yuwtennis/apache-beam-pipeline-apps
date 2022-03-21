package net.yuwtennis.app.entities;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.bson.Document;
import java.util.HashMap;
import java.util.Map;

// https://beam.apache.org/documentation/programming-guide/#annotating-custom-type-default-coder
@DefaultCoder(AvroCoder.class)
public class SimpleEntity {
    public String sentence ;

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
        return new SimpleEntity((String)doc.get("sentence"));
    }
}
