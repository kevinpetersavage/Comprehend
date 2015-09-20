package org.comprehend.capture;

public interface ThirdParameterCapturer<T, U> {
    U thirdParameter(T... param);
}
