package org.comprehend;

import org.comprehend.capture.*;
import org.comprehend.execution.BiFunctionApplier;
import org.comprehend.execution.ComprehensionGenerator;
import org.comprehend.execution.TripleFunctionApplier;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Comprehension {
    static public <P1,P2,P3,S> CapturedFunction<P1, SecondParameterCapturer<P2, ThirdParameterCapture<P3, Stream<S>>>> comprehend(TripleFunction<P1, P2, P3, S> function) {
        return new CapturedFunction<>(
                new SecondParameterCaptureAction<>(
                        new ThirdParameterCaptureAction<>(
                            new ComprehensionGenerator<>(
                                    new TripleFunctionApplier<>(function)
                            )
                        )
                )
        );
    }

    static public <P1,P2,S> CapturedFunction<P1,SecondParameterCapturer<P2, Stream<S>>> comprehend(BiFunction<P1,P2,S> function) {
        return new CapturedFunction<>(
                new SecondParameterCaptureAction<>(
                        new ComprehensionGenerator<>(new BiFunctionApplier<>(function))
                )
        );
    }
}