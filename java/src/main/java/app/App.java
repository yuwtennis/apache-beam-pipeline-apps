package app;

import app.examples.ElasticsearchIOSimpleRead;
import app.examples.ElasticsearchIOSimpleWrite;
import app.examples.Examples;
import app.examples.HelloWorld;
import app.examples.HttpResponseRequest;
import app.examples.MongoIOSimpleQuery;
import app.examples.MongoIOSimpleRead;
import app.examples.MongoIOSimpleWrite;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation;

import java.util.HashMap;

public class App {
    public interface MyOptions extends PipelineOptions {
        @Description("Get Example class")
        @Validation.Required
        String getExampleClass();
        void setExampleClass(String value);
    }

    public static void main(String[] args) {
        HashMap<String, Examples> builderMap = loadClasses();
        Dispatcher dispatcher = new Dispatcher(builderMap);
        String exampleClassName = System.getenv("EXAMPLE_CLASS");

        MyOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(MyOptions.class);

        Pipeline pipeline = Pipeline.create(options) ;
        Examples builder = dispatcher.dispatch(
                options.getExampleClass().isEmpty() ? exampleClassName : options.getExampleClass());

        builder.build(pipeline);
        pipeline.run().waitUntilFinish() ;
    }

    private static HashMap<String, Examples> loadClasses() {
        HashMap<String, Examples> builders = new HashMap<>();
        builders.put(HelloWorld.class.getName(),
                new HelloWorld());
        builders.put(ElasticsearchIOSimpleRead.class.getName(),
                new ElasticsearchIOSimpleRead());
        builders.put(ElasticsearchIOSimpleWrite.class.getName(),
                new ElasticsearchIOSimpleWrite());
        builders.put(HttpResponseRequest.class.getName(),
                new HttpResponseRequest());
        builders.put(MongoIOSimpleQuery.class.getName(),
                new MongoIOSimpleQuery());
        builders.put(MongoIOSimpleRead.class.getName(),
                new MongoIOSimpleRead());
        builders.put(MongoIOSimpleWrite.class.getName(),
                new MongoIOSimpleWrite());

        return builders;
    }
}
