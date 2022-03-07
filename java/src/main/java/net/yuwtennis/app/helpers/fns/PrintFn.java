package net.yuwtennis.app.helpers.fns;

import org.apache.beam.sdk.transforms.SimpleFunction;

public class PrintFn extends SimpleFunction<String, String> {

    @Override
    public String apply(String line) {
        System.out.println(line);

        return line;
    }
}
