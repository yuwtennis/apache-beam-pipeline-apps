package app;

import app.examples.Examples;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, Examples> builderMap = new HashMap<>();

    public Dispatcher(Map<String, Examples> builderMap) {
        this.builderMap = builderMap;
    }

    /***
     *
     * @param pipelineName
     * @return
     */
    public Examples dispatch(String pipelineName) {
        return builderMap.get(pipelineName) ;
    }
}
