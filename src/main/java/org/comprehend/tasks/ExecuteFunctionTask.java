package org.comprehend.tasks;

import org.comprehend.Parameter;
import org.comprehend.ParameterSetter;

import java.util.List;
import java.util.concurrent.Callable;

public class ExecuteFunctionTask<T> implements Callable<T> {
    private final ParameterSetter[] params;
    private final List<Integer> sizes;
    private final Parameter<T> function;
    private final long index;

    public ExecuteFunctionTask(ParameterSetter[] params, List<Integer> sizes, Parameter<T> function, long index) {
        this.params = params;
        this.sizes = sizes;
        this.function = function;
        this.index = index;
    }

    @Override
    public T call() throws Exception {
        long combinationsSoFar = 1;
        for (int j = 0; j < params.length; j++) {
            long size = sizes.get(j);
            params[j].setParameter((index / combinationsSoFar) % size);
            combinationsSoFar *= size;
        }
        return function.evaluate();
    }
}
