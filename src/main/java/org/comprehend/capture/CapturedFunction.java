package org.comprehend.capture;

import com.google.common.collect.Lists;

import java.util.List;

public class CapturedFunction<P, R> implements FirstParameterCapturer<P, R> {
    private NextAction<R> nextAction;

    public CapturedFunction(NextAction<R> nextAction) {
        this.nextAction = nextAction;
    }

    public R firstParameter(P... param){
        List<P> paramAsList = Lists.newArrayList(param);
        List<List> paramList = Lists.newArrayList();
        paramList.add(paramAsList);

        return nextAction.perform(paramList);
    }
}
