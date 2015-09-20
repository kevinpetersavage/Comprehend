package org.comprehend.capture;

import com.google.common.collect.Lists;

import java.util.List;

public class MultipleParameterCapturer<P, R> implements SecondParameterCapturer<P,R>, ThirdParameterCapture<P,R>{
    private final NextAction<R> nextAction;
    private final List<List> paramsSoFar;

    public MultipleParameterCapturer(NextAction<R> nextAction, List<List> paramsSoFar) {
        this.nextAction = nextAction;
        this.paramsSoFar = paramsSoFar;
    }

    @Override
    public R secondParameter(P... param) {
        return captureAndNextAction(param);
    }

    @Override
    public R thirdParameter(P... param) {
        return captureAndNextAction(param);
    }

    @Override
    public R captureAndNextAction(P[] param) {
        return nextAction.perform(capture(param));
    }

    private List<List> capture(P[] param) {
        List<List> parameters = Lists.newArrayList();
        parameters.addAll(paramsSoFar);
        parameters.add(Lists.newArrayList(param));
        return parameters;
    }
}
