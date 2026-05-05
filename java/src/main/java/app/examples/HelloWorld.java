package app.examples;

import app.helpers.fns.PrintFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;

public class HelloWorld implements Examples{

    public void build(Pipeline p) {
        PCollection<String> strings = p.apply(Create.of("Hello World!"));
        strings.apply(MapElements.via(new PrintFn()));
    }
}
