package org.comprehend;

import static org.comprehend.Comprehension.comprehend;
import static org.comprehend.Parameters.x;

import org.junit.Test;

public class ExceptionHandlingTest {
	@Test(expected=RuntimeException.class)
	public void throwsExceptionOnFunctionError() {
		Parameter<Double> throwsException = new Parameter<Double>(){
			@Override
			public Double evaluate() {
				double d = (Double)null; // plausible kind of null pointer condition
				return d;
			}
		};
		comprehend(throwsException , x.in(1.,2.,3.));
	}
}
