package org.comprehend.capture;

public interface FirstParameterCapturer<T, U>{
    public U firstParameter(T... param);
}
