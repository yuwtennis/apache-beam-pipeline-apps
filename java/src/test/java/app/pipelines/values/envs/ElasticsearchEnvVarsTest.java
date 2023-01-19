package app.pipelines.values.envs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test based on AutoValue usage
// See https://github.com/google/auto/blob/main/value/userguide/index.md#usage
public class ElasticsearchEnvVarsTest {
    @Test
    public void loadEnvValuesTest() {
        EnvVars<ElasticsearchEnvVars.Elasticsearch> envVars = new ElasticsearchEnvVars();
        ElasticsearchEnvVars.Elasticsearch esVars = envVars.loadEnv();
        String[] hosts = esVars.hosts().toArray(new String[0]);

        assertEquals("192.168.1.1:9200", hosts[0]);
        assertEquals("192.168.1.2:9200", hosts[1]);
        assertEquals("my_index", esVars.indexName());
        assertEquals("_doc", esVars.mappingType());
        assertEquals("my_username", esVars.username());
        assertEquals("my_password", esVars.password());
    }

    @Test
    public void loadEnvEqualityTest() {
        EnvVars<ElasticsearchEnvVars.Elasticsearch> envVars = new ElasticsearchEnvVars();
        ElasticsearchEnvVars.Elasticsearch esVars1 = envVars.loadEnv();
        ElasticsearchEnvVars.Elasticsearch esVars2 = envVars.loadEnv();

        assertTrue(esVars1.equals(esVars2));
    }
}
