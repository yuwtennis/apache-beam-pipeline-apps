package app.helpers.fns;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrintFnTest {

    private String data ;

    @Before
    public void setUp() {
        data = "SOMEDATA";
    }

    @Test
    public void apply() {
        PrintFn object = new PrintFn() ;

        String line = object.apply(data);

        assertEquals(data, line);
    }
}
