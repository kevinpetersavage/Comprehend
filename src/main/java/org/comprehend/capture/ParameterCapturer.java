package org.comprehend.capture;

import com.google.common.collect.Lists;
import org.comprehend.execution.Action;

import java.util.List;

public class ParameterCapturer<P, R> implements FirstParameterCapturer<P, R>, SecondParameterCapturer<P,R>, ThirdParameterCapturer<P,R>, FourthParameterCapture<P,R>{
    private final Action<R> nextAction;
    private final List<List> paramsSoFar;

    public ParameterCapturer(Action<R> nextAction, List<List> paramsSoFar) {
        this.nextAction = nextAction;
        this.paramsSoFar = paramsSoFar;
    }

    @Override
    public R firstParameter(P... param) {
        return captureAndNextAction(param);
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
    public R fourthParameter(P... param) {
        return captureAndNextAction(param);
    }

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
