package org.comprehend;

import org.comprehend.exception.ComprehendException;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.comprehend.Comprehension.comprehend;
import static org.comprehend.Comprehension.comprehendInParallel;
import static org.comprehend.Functions.multiply;
import static org.comprehend.Parameters.*;
import static org.fest.assertions.Assertions.assertThat;

public class ConcurrentTest {
	@Test @SuppressWarnings("unchecked")
	public void parallelMultiThreadedExample() throws Exception {
		List<Parameter<Double>> params = new ArrayList<>();
		List<ParameterSetter<Double>> setters = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Parameter<Double> parameter = new Parameter<>();
			params.add(parameter);
			setters.add(parameter.in(1.,2.,3.,4.,5.));
		}
		
		double start = System.currentTimeMillis();
		Set<Double> comprehendedLinear = comprehend(multiply(params.toArray(new Parameter[0])), setters.toArray(new ParameterSetter[0]));
		System.out.println("done");

		double afterLinear = System.currentTimeMillis();
		Set<Double> comprehendedParallel = comprehendInParallel(multiply(params.toArray(new Parameter[0])), setters.toArray(new ParameterSetter[0]));
        System.out.println("done");

        double afterParallel = System.currentTimeMillis();

		assertThat(comprehendedLinear).isEqualTo(comprehendedParallel);
		double linearTime = afterLinear - start;
		double concurrentTime = afterParallel - afterLinear;
        assertThat(linearTime).isGreaterThan(concurrentTime);
        assertThat(linearTime/concurrentTime).isGreaterThan(2);
	}

	/** this was originally a problem **/
	@Test @SuppressWarnings("unchecked")
	public void canCallFromDifferentThreads() throws Throwable {
		List<Thread> threads = new ArrayList<>();
		final List<Set<Double>> comprehended = Collections.synchronizedList(new ArrayList<Set<Double>>());
		final List<Throwable> error = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(){
				@Override
				public void run() {
                    try {
                        comprehended.add(comprehend(multiply(x, y, z), x.in(1.,2.), y.in(1.,2.,3.), z.in(1.,2.,3.,4.)));
                    } catch (ComprehendException e) {
                        throw new RuntimeException(e);
                    }
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
        if (!error.isEmpty()){
			throw error.get(0);
		}

		assertThat(threads).hasSize(comprehended.size());
		for (Set<Double> set : comprehended) {
			assertThat(set(1.,2.,3.,4.,6.,8.,9.,12.,16.,18.,24.)).isEqualTo(set);
		}
	}

	@SafeVarargs
    private final <T> Set<T> set(T... values) {
		return new HashSet<>(asList(values));
	}
}
