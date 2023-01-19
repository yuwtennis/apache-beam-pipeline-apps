package app.pipelines.values.envs;

import java.util.HashMap;
import java.util.Map;

public abstract class EnvVars<T> {

    public abstract T loadEnv() ;

    /***
     * filterEnvVars will get environment variables and filter keys with specified prefix
     * @param prefixName
     * @return Map of filtered environment variables
     */
    protected static Map<String, String> filterEnvVars(String prefixName) {
        Map<String, String> envMap = System.getenv();
        HashMap<String, String> filteredEnvMap = new HashMap<String, String>();

        for(String key : envMap.keySet()) {
            if(key.startsWith(prefixName)) {
                filteredEnvMap.put(key, envMap.get(key));
            }
        }

        return filteredEnvMap;
    }
}
