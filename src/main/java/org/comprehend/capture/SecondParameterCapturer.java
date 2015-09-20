package org.comprehend.capture;

public interface SecondParameterCapturer<P, R> extends ParameterCapturer<P, R>{
    R secondParameter(P... param);
}
