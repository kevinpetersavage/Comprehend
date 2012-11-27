package org.comprehend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Comprehension {
	static public <T> Set<T> comprehendInParallel(Parameter<T> function, @SuppressWarnings("rawtypes") ParameterSetter... params) {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		return comprehendInParallel(availableProcessors == 1 ? 1 : availableProcessors-1, function, params);
	}
	
	static public <T> Set<T> comprehend(Parameter<T> function, @SuppressWarnings("rawtypes") ParameterSetter... params) {
		return comprehendInParallel(1, function, params);
	}

	static public <T> Set<T> comprehendInParallel(final int numberOfThreads, final Parameter<T> function, @SuppressWarnings("rawtypes") final ParameterSetter... params) {
		final List<Integer> sizes = sizes(params);
		final long combinations = combinations(sizes);

		final Set<T> results = Collections.synchronizedSet(new HashSet<T>());
		
		List<Thread> threads = new ArrayList<Thread>();
		final Queue<Long> queue = new ConcurrentLinkedQueue<Long>();
		for (int i = 0; i < numberOfThreads; i++) {
			sendMessageIfValid(queue, i, combinations);
			threads.add(new Thread(){
				@Override
				public void run() {
					Long i = null;
					while ((i = queue.poll()) != null){
						sendMessageIfValid(queue, i + numberOfThreads, combinations);
						
						long combinationsSoFar = 1;
						for (int j = 0; j < params.length; j++) {
							long size = sizes.get(j);
							params[j].setParameter((i/combinationsSoFar)%size);
							combinationsSoFar *= size;
						}
						results.add(function.evaluate());
					}
				}
			});
		}
		runAndJoin(threads);
		return results;
	}

	private static void runAndJoin(List<Thread> threads) {
		final List<Throwable> errors = Collections.synchronizedList(new ArrayList<Throwable>());
		for (Thread thread: threads) {
			thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					errors.add(e);
				}
			});
			thread.start();
		}
		for (Thread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		for (Throwable throwable : errors) {
			throw new RuntimeException(throwable);
		}
	}
	
	private static void sendMessageIfValid(Queue<Long> queue, long i, long combinations) {
		if (i < combinations){
			queue.add(i);
		}
	}

	private static long combinations(List<Integer> sizes) {
		BigInteger combinations = BigInteger.ONE;
		for (Integer size : sizes) {
			combinations = combinations.multiply(new BigInteger(size.toString()));
		}
		if (combinations.compareTo(new BigInteger(String.valueOf(Long.MAX_VALUE))) >=0) {
			throw new UnsupportedOperationException("to many combinations: " + combinations);
		}
		return combinations.longValue();
	}

	@SuppressWarnings("rawtypes")
	private static List<Integer> sizes(ParameterSetter... params) {
		List<Integer> sizes = new ArrayList<Integer>();
		for (ParameterSetter param: params) {
			int size = param.size();
			sizes.add(size);
		}
		return sizes; 
	}
}
