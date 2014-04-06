package org.comprehend.tasks;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubmitToServiceFunctionTest {
    @Test
    public void testApply() throws Exception {
        ListeningExecutorService mockService = mock(ListeningExecutorService.class);

        Callable<String> input = mock(Callable.class);
        ListenableFuture<String> expectedOutput = mock(ListenableFuture.class);
        when(mockService.submit(input)).thenReturn(expectedOutput);

        assertThat(new SubmitToServiceFunction<String>(mockService).apply(input)).isEqualTo(expectedOutput);
    }
}
