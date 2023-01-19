package app.pipelines.elements;

import java.util.Arrays;
import java.util.List;

/**
 * A value object class.
 */
public class StaticElements {

    // https://beam.apache.org/documentation/programming-guide/#creating-pcollection-in-memory
    public static final List<String> LINES = Arrays.asList(
            "To be, or not to be: that is the question: ",
            "Whether 'tis nobler in the mind to suffer ",
            "The slings and arrows of outrageous fortune, ",
            "Or to take arms against a sea of troubles, ") ;
}
