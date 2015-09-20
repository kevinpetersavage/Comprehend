package org.comprehend.capture;

public interface ThirdParameterCapturer<T, U> extends ParameterCapturer<T, U> {
    U thirdParameter(T... param);
}
