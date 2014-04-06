package org.comprehend;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.comprehend.Comprehension.comprehend;
import static org.comprehend.Comprehension.comprehendInParallel;
import static org.comprehend.Functions.*;
import static org.comprehend.Parameters.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 * mainly intended as documentation but also used for TDDing*
 */
public class ExampleTest {

    @Test
    public void basicExample() throws Exception {
        assertThat(
                comprehend(sum(square(x), square(y)), x.in(1., 2., 3.), y.in(1., 2., 3.))
        ).isEqualTo(
                set(2., 5., 8., 10., 13., 18.)
        );
    }

    @Test
    public void stringConcat() throws Exception {
        assertThat(
                comprehend(concatenate(s, t), s.in("A", "B"), t.in("c", "d"))
        ).isEqualTo(
                set("Ac", "Ad", "Bc", "Bd")
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void multiplyExample() throws Exception {
        assertThat(
                comprehend(multiply(x, y, z), x.in(1., 2.), y.in(1., 2., 3.), z.in(1., 2., 3., 4.))
        ).isEqualTo(
                set(1., 2., 3., 4., 6., 8., 9., 12., 16., 18., 24.)
        );
    }

    @Test
    public void concurrentExample() throws Exception {
        // we would expect the below to take ~ 1000ms * 3 * 3 = 9000ms sequentially
        double start = System.currentTimeMillis();
        int numberOfThreads = 9;
        comprehendInParallel(numberOfThreads, new DelayForMilliseconds(1000), x.in(1., 2., 3.), y.in(1., 2., 3.));
        double duration = System.currentTimeMillis() - start;

        // but because we ran in parallel...
        double roughExpectedDuration = 9000. / 9.;

        double tolerance = 100.;
        assertThat(roughExpectedDuration).isGreaterThan(duration - tolerance).isLessThan(duration + tolerance);
    }

    @SafeVarargs
    private final <T> Set<T> set(T... values) {
        return new HashSet<>(asList(values));
    }

    private final class DelayForMilliseconds extends Parameter<Double> {
        private final int milliseconds;

        public DelayForMilliseconds(int milliseconds) {
            this.milliseconds = milliseconds;
        }

        public Double evaluate() {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0.;
        }
    }
}