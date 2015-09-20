package org.comprehend.execution;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComprehensionGeneratorTest {
    @Test
    public void shouldProduceStreamOfFunctionForParams(){
        FunctionApplier<String> functionApplier = mock(FunctionApplier.class);

        ArrayList<List> params = Lists.newArrayList(
                Lists.newArrayList(1,2),
                Lists.newArrayList(5,500)
        );

        when(functionApplier.apply(Lists.newArrayList(1, 5))).thenReturn("1,5");
        when(functionApplier.apply(Lists.newArrayList(2, 5))).thenReturn("2,5");
        when(functionApplier.apply(Lists.newArrayList(1, 500))).thenReturn("1,500");
        when(functionApplier.apply(Lists.newArrayList(2, 500))).thenReturn("2,500");

        Set<String> result = new ComprehensionGenerator<>(functionApplier).perform(params).collect(Collectors.toSet());

        assertThat(result).isEqualTo(Sets.newHashSet("1,5","2,5","1,500","2,500"));
    }

}