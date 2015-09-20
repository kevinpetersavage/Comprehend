package org.comprehend.capture;

import com.google.common.collect.Lists;
import org.comprehend.execution.Action;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ParameterCapturerTest {
    @Test
    public void shouldCaptureFunctionAndTwoParameters() {
        Action<Stream<String>> action = mock(Action.class);
        Action<SecondParameterCapturer<String, Stream<String>>> secondParam =
                (paramsSoFar) -> new ParameterCapturer<>(action, paramsSoFar);
        new ParameterCapturer<>(secondParam, Lists.newArrayList()).firstParameter(5, 6).secondParameter("a", "b");

        verify(action).perform(newArrayList(newArrayList(5, 6), newArrayList("a", "b")));
    }

    @Test
    public void shouldCaptureFunctionAndThreeParameters() {
        Action<Stream<String>> action = mock(Action.class);
        Action<ThirdParameterCapturer<Double, Stream<String>>> thirdParam =
                (paramsSoFar) -> new ParameterCapturer<>(action, paramsSoFar);
        Action<SecondParameterCapturer<String, ThirdParameterCapturer<Double, Stream<String>>>> secondParam =
                (paramsSoFar) -> new ParameterCapturer<>(thirdParam, paramsSoFar);
        new ParameterCapturer<>(secondParam, Lists.newArrayList())
                .firstParameter(5, 6).secondParameter("a", "b").thirdParameter(5., 6.);

        verify(action).perform(newArrayList(newArrayList(5, 6), newArrayList("a", "b"), newArrayList(5., 6.)));
    }

}