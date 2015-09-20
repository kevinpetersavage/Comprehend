package org.comprehend.capture;

public interface ThirdParameterCapture<T, U> extends ParameterCapturer<T, U> {
    public U thirdParameter(T... param);
}
