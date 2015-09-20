package org.comprehend.capture;

import java.util.List;

public class SecondParameterCaptureAction<P, R> implements NextAction<SecondParameterCapturer<P, R>> {
    private final NextAction<R> nextAction;

    public SecondParameterCaptureAction(NextAction<R> nextAction) {
        this.nextAction = nextAction;
    }

    @Override
    public SecondParameterCapturer<P, R> perform(List<List> paramsSoFar) {
        return new MultipleParameterCapturer<>(nextAction, paramsSoFar);
    }
}
