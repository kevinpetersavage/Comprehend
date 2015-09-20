package org.comprehend.execution;

import com.google.common.collect.Lists;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ComprehensionGenerator<S> implements NextAction<Stream<S>> {
    private FunctionApplier<S> functionApplier;

    public ComprehensionGenerator(FunctionApplier<S> functionApplier) {
        this.functionApplier = functionApplier;
    }

    @Override
    public Stream<S> perform(List<List> params) {
        return LongStream.range(0L, combinations(params))
                .mapToObj((index) -> findParameters(params, index))
                .map(functionApplier::apply);
    }

    private List<Object> findParameters(List<List> params, long index) {
        List<Object> paramsToApply = Lists.newArrayList();

        long combinationsSoFar = 1;
        for (List param : params) {
            long size = param.size();
            paramsToApply.add(param.get((int) ((index / combinationsSoFar) % size)));
            combinationsSoFar *= size;
        }
        return paramsToApply;
    }

    private static long combinations(List<List> params) {
        BigInteger combinations = BigInteger.ONE;
        for (List param : params) {
            combinations = combinations.multiply(new BigInteger(String.valueOf(param.size())));
        }
        if (combinations.compareTo(new BigInteger(String.valueOf(Long.MAX_VALUE))) >= 0) {
            throw new UnsupportedOperationException("to many combinations: " + combinations);
        }
        return combinations.longValue();
    }
}