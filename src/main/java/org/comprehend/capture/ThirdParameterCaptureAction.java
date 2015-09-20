package org.comprehend.capture;

import java.util.List;

public class ThirdParameterCaptureAction<P, R> implements NextAction<ThirdParameterCapture<P, R>> {
    private final NextAction<R> nextAction;

    public ThirdParameterCaptureAction(NextAction<R> nextAction) {
        this.nextAction = nextAction;
    }

    @Override
    public ThirdParameterCapture<P, R> perform(List<List> paramsSoFar) {
        return new MultipleParameterCapturer<>(nextAction, paramsSoFar);
    }
}
