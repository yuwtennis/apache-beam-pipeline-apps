package app;

import app.examples.Examples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Dispatcher {
    private final Logger logger = LoggerFactory.getLogger(
            Dispatcher.class) ;

    private final Map<String, Examples> builderMap;

    public Dispatcher(Map<String, Examples> builderMap) {
        this.builderMap = builderMap;
    }

    /***
     *
     * @param builderName
     * @return
     */
    public Examples dispatch(String builderName) throws RuntimeException{
        Examples builder =  builderMap.get(builderName);

        if (builder == null) {
            throw new RuntimeException("No builder found in builder map " + builderName);
        }

        return builder;
    }
}
