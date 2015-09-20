package org.comprehend.execution;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.function.BiFunction;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BiFunctionApplierTest {
    @Test
    public void shouldApplyFunction(){
        BiFunction<Integer, Integer, String> function = mock(BiFunction.class);
        when(function.apply(1,2)).thenReturn("result");

        Object result = new BiFunctionApplier<>(function).apply(Lists.newArrayList(1, 2));

        assertThat(result).isEqualTo("result");
    }
}