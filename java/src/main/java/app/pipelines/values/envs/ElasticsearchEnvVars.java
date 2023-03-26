package app.pipelines.values.envs;

import avro.shaded.com.google.common.collect.ImmutableList;
import com.google.auto.value.AutoValue;

import java.util.Map;

public class ElasticsearchEnvVars extends EnvVars<ElasticsearchEnvVars.Elasticsearch> {

    private static final String PREFIX_NAME = "ES_";
    private static final String ES_HOSTS = "ES_HOSTS";
    private static final String ES_INDEX = "ES_INDEX";
    private static final String ES_TYPE = "ES_TYPE";
    private static final String ES_USERNAME = "ES_USERNAME";
    private static final String ES_PASSWORD = "ES_PASSWORD";
    private static final String ES_TRUST_SELF_SIGNED_CERTS = "ES_TRUST_SELF_SIGNED_CERTS";
    private static final String ES_KEY_STORE_PATH = "ES_KEY_STORE_PATH";
    private static final String ES_KEY_STORE_PASSWORD = "ES_KEY_STORE_PASSWORD";

    @AutoValue
    public abstract static class Elasticsearch {

        public static Elasticsearch create(
                String[] hosts,
                String indexName,
                String mappingType,
                String username,
                String password,
                Boolean trustSelfSignedCerts,
                String keyStorePath,
                String keyStorePassword
                ) {
            return new AutoValue_ElasticsearchEnvVars_Elasticsearch(
                    ImmutableList.copyOf(hosts),
                    indexName,
                    mappingType,
                    username,
                    password,
                    trustSelfSignedCerts,
                    keyStorePath,
                    keyStorePassword);
        }

        public abstract ImmutableList<String> hosts();

        public abstract String indexName();

        public abstract String mappingType();

        public abstract String username();

        public abstract String password();

        public abstract Boolean trustSelfSignedCerts();

        public abstract String keyStorePath();

        public abstract String keyStorePassword();
    }

    /***
     * loadEnv extracts Elasticsearch related environment variables and return as object
     * @return Value object of Elasticsearch
     */
    public Elasticsearch loadEnv() {
        Map<String, String> envMap = filterEnvVars(PREFIX_NAME);

        return Elasticsearch.create(
                envMap.get(ES_HOSTS).split(","),
                envMap.get(ES_INDEX),
                envMap.get(ES_TYPE),
                envMap.get(ES_USERNAME),
                envMap.get(ES_PASSWORD),
                Boolean.valueOf(envMap.get(ES_TRUST_SELF_SIGNED_CERTS)),
                envMap.get(ES_KEY_STORE_PATH),
                envMap.get(ES_KEY_STORE_PASSWORD)
        );
    }
}
