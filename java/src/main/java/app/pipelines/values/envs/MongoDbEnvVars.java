package app.pipelines.values.envs;

import com.google.auto.value.AutoValue;
import java.util.Map;

public class MongoDbEnvVars extends EnvVars<MongoDbEnvVars.MongoDb> {
    private static final String PREFIX_NAME = "MONGO_";
    private static final String MONGO_HOST = "MONGO_HOST";
    private static final String MONGO_DB = "MONGO_DB";
    private static final String MONGO_COLLECTION = "MONGO_COLLECTION";
    private static final String MONGO_USERNAME = "MONGO_USERNAME";
    private static final String MONGO_PASSWORD = "MONGO_PASSWORD";

    @AutoValue
    public abstract static class MongoDb {
        static MongoDb create(
                String host, String dbName, String collectionName, String username, String password) {
            return new AutoValue_MongoDbEnvVars_MongoDb(
                    host, dbName, collectionName, username, password);
        }

        public abstract String host();

        public abstract String dbName();

        public abstract String collectionName();

        public abstract String username();

        public abstract String password();
    }

    /***
     * loadEnv extracts MongoDb related environment variables and return as object
     * @return
     */
    public MongoDb loadEnv() {
        Map<String, String> envMap = filterEnvVars(PREFIX_NAME) ;

        return MongoDb.create(
                envMap.get(MONGO_HOST),
                envMap.get(MONGO_DB),
                envMap.get(MONGO_COLLECTION),
                envMap.get(MONGO_USERNAME),
                envMap.get(MONGO_PASSWORD)
        );
    }
}