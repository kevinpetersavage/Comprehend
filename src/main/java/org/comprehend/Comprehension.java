package org.comprehend;

import com.google.common.collect.Lists;
import org.comprehend.capture.*;
import org.comprehend.execution.BiFunctionApplier;
import org.comprehend.execution.ComprehensionGenerator;
import org.comprehend.execution.QuadFunctionApplier;
import org.comprehend.execution.TripleFunctionApplier;
import org.comprehend.functions.QuadFunction;
import org.comprehend.functions.TripleFunction;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Comprehension {

    static public <P1, P2, S> FirstParameterCapturer<P1, SecondParameterCapturer<P2, Stream<S>>> comprehend(BiFunction<P1, P2, S> function) {
        return new ParameterCapturer<>(
                (List<List> paramsSoFar) -> new ParameterCapturer<>(
                        new ComprehensionGenerator<>(
                                new BiFunctionApplier<>(function)
                        ),
                        paramsSoFar),
                Lists.newArrayList()
        );
    }

    static public <P1, P2, P3, S> FirstParameterCapturer<P1, SecondParameterCapturer<P2, ThirdParameterCapturer<P3, Stream<S>>>> comprehend(TripleFunction<P1, P2, P3, S> function) {
        return new ParameterCapturer<>(
                (List<List> paramsSoFar1) -> new ParameterCapturer<>(
                        (List<List> paramsSoFar2) -> new ParameterCapturer<>(
                                new ComprehensionGenerator<>(
                                        new TripleFunctionApplier<>(function)
                                ),
                                paramsSoFar2
                        ),
                        paramsSoFar1),
                Lists.newArrayList()
        );
    }

    static public <P1, P2, P3, P4, S> FirstParameterCapturer<P1, SecondParameterCapturer<P2, ThirdParameterCapturer<P3, FourthParameterCapture<P4, Stream<S>>>>> comprehend(QuadFunction<P1, P2, P3, P4, S> function) {
        return new ParameterCapturer<>(
                (List<List> paramsSoFar1) -> new ParameterCapturer<>(
                        (List<List> paramsSoFar2) -> new ParameterCapturer<>(
                                (List<List> paramsSoFar3) -> new ParameterCapturer<>(
                                        new ComprehensionGenerator<>(
                                                new QuadFunctionApplier<>(function)
                                        ),
                                        paramsSoFar3
                                ),
                                paramsSoFar2
                        ),
                        paramsSoFar1),
                Lists.newArrayList()
        );
    }
}