package org.comprehend.capture;

public interface ParameterCapturer<P, R> {
    R captureAndNextAction(P[] param) ;
}
