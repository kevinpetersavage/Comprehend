package org.comprehend.capture;

import java.util.List;

public class ParameterCaptureAction<P, R> implements NextAction<ParameterCapturer<P, R>> {
    private final NextAction<R> nextAction;

    public ParameterCaptureAction(NextAction<R> nextAction) {
        this.nextAction = nextAction;
    }

    @Override
    public ParameterCapturer<P,R> perform(List<List> paramsSoFar) {
        return new MultipleParameterCapturer<>(nextAction, paramsSoFar);
    }
}
