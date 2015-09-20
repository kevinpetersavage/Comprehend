package org.comprehend.execution;

import com.google.common.collect.Lists;
import org.comprehend.functions.QuadFunction;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuadFunctionApplierTest {
    @Test
    public void shouldApplyFunction(){
        QuadFunction<Integer, Integer, Integer, Integer, String> function = mock(QuadFunction.class);
        when(function.apply(1,2,3,4)).thenReturn("result");

        Object result = new QuadFunctionApplier<>(function).apply(Lists.newArrayList(1, 2, 3, 4));

        assertThat(result).isEqualTo("result");
    }
}