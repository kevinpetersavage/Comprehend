package org.comprehend.tasks;

import org.comprehend.Parameter;
import org.comprehend.ParameterSetter;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ExecuteFunctionTaskTest {
    @Test
    public void testCall() throws Exception {
        ParameterSetter param1 = mock(ParameterSetter.class);
        ParameterSetter param2 = mock(ParameterSetter.class);
        ParameterSetter[] params = new ParameterSetter[]{param1, param2};

        Parameter<String> function = mock(Parameter.class);
        String expected = "expected";
        when(function.evaluate()).thenReturn(expected);

        ExecuteFunctionTask<String> task = new ExecuteFunctionTask<>(params, newArrayList(2, 3), function, 5);

        String result = task.call();
        verify(param1).setParameter(1);
        verify(param2).setParameter(2);
        assertThat(result).isEqualTo(expected);
    }
}
