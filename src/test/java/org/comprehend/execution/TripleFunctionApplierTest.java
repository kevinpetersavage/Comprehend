package org.comprehend.execution;

import com.google.common.collect.Lists;
import org.comprehend.TripleFunction;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripleFunctionApplierTest {
    @Test
    public void shouldApplyFunction(){
        TripleFunction<Integer, Integer, Integer, String> function = mock(TripleFunction.class);
        when(function.apply(1,2,3)).thenReturn("result");

        Object result = new TripleFunctionApplier<>(function).apply(Lists.newArrayList(1, 2, 3));

        assertThat(result).isEqualTo("result");
    }
}