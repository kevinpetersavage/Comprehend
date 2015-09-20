package org.comprehend.execution;

import java.util.List;
import java.util.function.BiFunction;

public class BiFunctionApplier<T, U, S> implements FunctionApplier<S> {
    private BiFunction<T, U, S> function;

    public BiFunctionApplier(BiFunction<T, U, S> function) {
        this.function = function;
    }

    @Override
    public S apply(List<Object> args) {
        return function.apply((T)args.get(0), (U)args.get(1));
    }
}
