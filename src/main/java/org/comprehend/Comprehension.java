package org.comprehend;

import com.google.common.collect.*;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.comprehend.tasks.CreateTaskFunction;
import org.comprehend.tasks.ExecuteFunctionTask;
import org.comprehend.tasks.SubmitToServiceFunction;
import org.comprehend.exception.ComprehendException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.google.common.collect.Iterables.transform;

public class Comprehension {
	static public <T> Set<T> comprehendInParallel(Parameter<T> function, ParameterSetter... params) throws ComprehendException {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		return comprehendInParallel(availableProcessors == 1 ? 1 : availableProcessors-1, function, params);
	}
	
	static public <T> Set<T> comprehend(Parameter<T> function, ParameterSetter... params) throws ComprehendException {
		return comprehendInParallel(1, function, params);
	}

	static public <T> Set<T> comprehendInParallel(final int numberOfThreads, final Parameter<T> function, final ParameterSetter... params) throws ComprehendException {
		final List<Integer> sizes = sizes(params);
		final long combinations = combinations(sizes);

        final ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(numberOfThreads));

        ContiguousSet<Long> indexes = createLazySet(0L, combinations);
        Iterable<ExecuteFunctionTask<T>> tasks = transform(indexes, new CreateTaskFunction<T>(params, sizes, function));
        Iterable<ListenableFuture<T>> futures = transform(tasks, new SubmitToServiceFunction<T>(service));
        ListenableFuture<List<T>> resultFuture = Futures.allAsList(futures);

        try {
            return Sets.newHashSet(resultFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new ComprehendException(e);
        }
	}

    private static ContiguousSet<Long> createLazySet(long from, long to) {
        return ContiguousSet.create(Range.closedOpen(from, to), DiscreteDomain.longs());
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

	private static List<Integer> sizes(ParameterSetter... params) {
		List<Integer> sizes = new ArrayList<>();
		for (ParameterSetter param: params) {
			int size = param.size();
			sizes.add(size);
		}
		return sizes; 
	}
}