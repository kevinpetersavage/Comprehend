package org.comprehend;

import static java.util.Arrays.asList;
import static org.comprehend.Comprehension.comprehend;
import static org.comprehend.Comprehension.comprehendInParallel;
import static org.comprehend.Functions.multiply;
import static org.comprehend.Parameters.x;
import static org.comprehend.Parameters.y;
import static org.comprehend.Parameters.z;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/** Originally the code would have done the wrong thing if you used the same parameters from multiple threads. 
 *  This is now fixed and it also gave an option to make the comprehend also run in parallel so I also did this.
**/
public class ConcurrentTest {
	@Test @SuppressWarnings("unchecked")
	public void parallelMultiThreadedExample() throws Exception {
		List<Parameter<Double>> params = new ArrayList<Parameter<Double>>();
		List<ParameterSetter<Double>> setters = new ArrayList<ParameterSetter<Double>>();
		for (int i = 0; i < 5; i++) {
			Parameter<Double> parameter = new Parameter<Double>();
			params.add(parameter);
			setters.add(parameter.in(1.,2.,3.,4.,5.));
		}
		
		double start = System.currentTimeMillis();
		Set<Double> comprehendedLinear = comprehend(multiply(params.toArray(new Parameter[0])), setters.toArray(new ParameterSetter[0]));
		
		double afterLinear = System.currentTimeMillis();
		Set<Double> comprehendedParallel = comprehendInParallel(multiply(params.toArray(new Parameter[0])), setters.toArray(new ParameterSetter[0]));
		
		double afterParallel = System.currentTimeMillis();

		assertEquals("should produce same result", comprehendedLinear,comprehendedParallel);
		double linearTime = afterLinear - start;
		double concurrentTime = afterParallel - afterLinear;
		assertTrue("should be faster but " + linearTime + ">" + concurrentTime, linearTime > concurrentTime);
		assertTrue("should be way faster but linearTime/concurrentTime = " + linearTime/concurrentTime, linearTime/concurrentTime > 2);
	}	
	
	/** this was originally a problem **/
	@Test @SuppressWarnings("unchecked")
	public void canCallFromDifferentThreads() throws Throwable {
		List<Thread> threads = new ArrayList<Thread>();
		final List<Set<Double>> comprehended = Collections.synchronizedList(new ArrayList<Set<Double>>());
		final List<Throwable> error = new ArrayList<Throwable>();
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(){
				@Override
				public void run() {
					comprehended.add(comprehend(multiply(x, y, z), x.in(1.,2.), y.in(1.,2.,3.), z.in(1.,2.,3.,4.)));
				}
			};
			threads.add(thread);
			thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				public void uncaughtException(Thread t, Throwable e) {
					error.add(e);
				}
			});
			thread.start();
		}

		for (Thread thread: threads) {
			thread.join();
		}
		for (Throwable throwable : error) {
			throw throwable;
		}

		assertEquals(threads.size(), comprehended.size());
		for (Set<Double> set : comprehended) {
			assertEquals(set(1.,2.,3.,4.,6.,8.,9.,12.,16.,18.,24.), set);
		}
	}
	
	private <T> Set<T> set(T... values) {
		return new HashSet<T>(asList(values));
	}
}
