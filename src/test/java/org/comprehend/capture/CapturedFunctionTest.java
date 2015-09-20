package org.comprehend.capture;

import org.comprehend.execution.ComprehensionGenerator;
import org.comprehend.execution.FunctionApplier;
import org.junit.Test;

import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CapturedFunctionTest {
    @Test
    public void shouldCaptureFunctionAndTwoParameters() {
        NextAction<Stream<String>> action = mock(NextAction.class);
        SecondParameterCaptureAction<String, Stream<String>> secondParam = new SecondParameterCaptureAction<>(action);
        new CapturedFunction<>(secondParam).firstParameter(5, 6).secondParameter("a", "b");

        verify(action).perform(newArrayList(newArrayList(5, 6), newArrayList("a", "b")));
    }

    @Test
    public void shouldCaptureFunctionAndThreeParameters() {
        NextAction<Stream<String>> action = mock(NextAction.class);
        ThirdParameterCaptureAction<Double, Stream<String>> thirdParam = new ThirdParameterCaptureAction<>(action);
        SecondParameterCaptureAction<String, ThirdParameterCapture<Double, Stream<String>>> secondParam =
                new SecondParameterCaptureAction<>(thirdParam);
        new CapturedFunction<>(secondParam)
                .firstParameter(5, 6).secondParameter("a", "b").thirdParameter(5., 6.);

        verify(action).perform(newArrayList(newArrayList(5, 6), newArrayList("a", "b"), newArrayList(5., 6.)));
    }

}