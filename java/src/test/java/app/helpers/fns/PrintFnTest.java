package app.helpers.fns;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PrintFnTest {

    private static String data ;

    @BeforeAll
    static void setUp() {
        data = "SOMEDATA";
    }

    @Test
    public void apply() {
        PrintFn object = new PrintFn() ;

        String line = object.apply(data);

        assertEquals(data, line);
    }
}
