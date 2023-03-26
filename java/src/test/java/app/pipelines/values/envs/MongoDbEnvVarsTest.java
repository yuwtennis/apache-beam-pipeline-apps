package app.pipelines.values.envs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoDbEnvVarsTest {
    @Test
    public void loadEnvValuesTest() {
        EnvVars<MongoDbEnvVars.MongoDb> envVars = new MongoDbEnvVars();
        MongoDbEnvVars.MongoDb mongoVars = envVars.loadEnv();

        assertEquals("192.168.1.1", mongoVars.host());
        assertEquals("my_db", mongoVars.dbName());
        assertEquals("my_collection", mongoVars.collectionName());
        assertEquals("my_username", mongoVars.username());
        assertEquals("my_password", mongoVars.password());
    }

    @Test
    public void loadEnvEqualityTest() {
        EnvVars<MongoDbEnvVars.MongoDb> envVars = new MongoDbEnvVars();
        MongoDbEnvVars.MongoDb mongoDbVars1 = envVars.loadEnv();
        MongoDbEnvVars.MongoDb mongoDbVars2 = envVars.loadEnv();

        assertTrue(mongoDbVars1.equals(mongoDbVars2));
    }
}
