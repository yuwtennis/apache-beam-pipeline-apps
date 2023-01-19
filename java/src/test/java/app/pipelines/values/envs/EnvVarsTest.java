package app.pipelines.values.envs;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EnvVarsTest {
    @Test
    public void filterEnvVarsTest() {
        Map envVars = EnvVars.filterEnvVars("ES_");

        assertEquals("192.168.1.1:9200,192.168.1.2:9200", envVars.get("ES_HOSTS"));
        assertEquals("my_index", envVars.get("ES_INDEX"));
        assertEquals("_doc", envVars.get("ES_TYPE"));
        assertEquals("my_username", envVars.get("ES_USERNAME"));
        assertEquals("my_password", envVars.get("ES_PASSWORD"));
        assertNull(envVars.get("SOME_KEY"));
    }
}
