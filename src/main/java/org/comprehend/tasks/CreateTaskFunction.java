package org.comprehend.tasks;

import com.google.common.base.Function;
import org.comprehend.Parameter;
import org.comprehend.ParameterSetter;

import java.util.List;

public class CreateTaskFunction<T> implements Function<Long, ExecuteFunctionTask<T>>{
    private final ParameterSetter[] params;
    private final List<Integer> sizes;
    private final Parameter function;

    public CreateTaskFunction(ParameterSetter[] params, List<Integer> sizes, Parameter function) {
        this.params = params;
        this.sizes = sizes;
        this.function = function;
    }

    @Override
    public ExecuteFunctionTask<T> apply(Long index) {
        return new ExecuteFunctionTask(params, sizes, function, index);
    }
}
