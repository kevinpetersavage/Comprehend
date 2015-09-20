package org.comprehend.execution;

import org.comprehend.functions.QuadFunction;

import java.util.List;

public class QuadFunctionApplier<P1, P2, P3, P4, S> implements FunctionApplier<S> {
    private QuadFunction<P1, P2, P3, P4, S> function;

    public QuadFunctionApplier(QuadFunction<P1, P2, P3, P4, S> function) {
        this.function = function;
    }

    @Override
    public S apply(List<Object> args) {
        return function.apply((P1)args.get(0), (P2)args.get(1), (P3)args.get(2), (P4)args.get(3));
    }
}
