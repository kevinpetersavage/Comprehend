package org.comprehend.capture;

public interface SecondParameterCapturer<P, R>{
    R secondParameter(P... param);
}
