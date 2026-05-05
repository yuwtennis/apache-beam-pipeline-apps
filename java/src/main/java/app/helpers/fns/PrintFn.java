package app.helpers.fns;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintFn extends SimpleFunction<String, String> {
    private final Logger logger = LoggerFactory.getLogger(PrintFn.class);

    @Override
    public String apply(String line) {
        logger.info(line);

        return line;
    }
}
