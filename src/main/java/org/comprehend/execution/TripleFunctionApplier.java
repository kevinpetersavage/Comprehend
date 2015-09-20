package org.comprehend.execution;

import org.comprehend.functions.TripleFunction;

import java.util.List;

public class TripleFunctionApplier<P1, P2, P3, S> implements FunctionApplier<S> {
    private TripleFunction<P1, P2, P3, S> function;

    public TripleFunctionApplier(TripleFunction<P1, P2, P3, S> function) {
        this.function = function;
    }

    @Override
    public S apply(List<Object> args) {
        return function.apply((P1)args.get(0), (P2)args.get(1), (P3)args.get(2));
    }
}
