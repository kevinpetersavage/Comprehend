package org.comprehend;

import org.junit.Test;

import static org.comprehend.Comprehension.comprehend;

public class ExceptionHandlingTest {
    @Test(expected = NullPointerException.class)
    public void throwsExceptionOnFunctionError() {
        comprehend((Double x, Double y) -> {
            Double bigD = null;
            double d = bigD; // plausible kind of null pointer condition
            return d;
        }).firstParameter(1.).secondParameter(2.).count();
    }
}
