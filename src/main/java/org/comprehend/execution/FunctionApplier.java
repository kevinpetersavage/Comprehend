package org.comprehend.execution;

import java.util.List;

public interface FunctionApplier<S> {
    S apply(List<Object> args);
}
