package net.yuwtennis.app.service.pipeline;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.mongodb.MongoDbIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

import java.util.Arrays;
import java.util.List;

public class MongoReadQueryService {

    static class PrintFn extends SimpleFunction<String, String>{
        @Override
        public String apply(String line) {
            System.out.println(line);

            return line;
        }
    }

    public static void run(Pipeline p) {
        // https://beam.apache.org/documentation/programming-guide/#creating-pcollection-in-memory
        final List<String> LINES = Arrays.asList(
                "To be, or not to be: that is the question: ",
                "Whether 'tis nobler in the mind to suffer ",
                "The slings and arrows of outrageous fortune, ",
                "Or to take arms against a sea of troubles, ") ;

        PCollection pcol = p.apply(Create.of(LINES)).setCoder(StringUtf8Coder.of());

        pcol = Write(pcol);
        pcol = Read(pcol);
        pcol.apply(MapElements.via(new PrintFn()));
    }

    private static PCollection Write(PCollection pcol) {
        pcol.apply(
                MongoDbIO.write()
                        .withUri(("mongodb://localhost:27017"))
                        .withDatabase("literature")
                        .withCollection("quotes"));
        return pcol ;

    }

    private static PCollection Read(PCollection pcol) {
        pcol.apply(
                MongoDbIO.read()
                        .withUri(("mongodb://localhost:27017"))
                        .withDatabase("literature")
                        .withCollection("quotes"));

        return pcol;
    }
}
