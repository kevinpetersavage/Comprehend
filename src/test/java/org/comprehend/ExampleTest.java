package org.comprehend;

import org.fest.assertions.Assertions;
import org.fest.assertions.CollectionAssert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.comprehend.Comprehension.comprehend;

/**
 * mainly intended as documentation but also used for TDDing*
 */
public class ExampleTest {

    @Test
    public void basicExample() throws Exception {
        assertThat(
                comprehend((Double x, Double y) -> x * x + y * y).firstParameter(1., 2., 3.).secondParameter(1., 2., 3.)
        ).isEqualTo(
                set(2., 5., 8., 10., 13., 18.)
        );
    }


    @Test
    public void stringConcatExample() throws Exception {
        assertThat(
                comprehend((String s, String t) -> s + t).firstParameter("A", "B").secondParameter("c", "d")
        ).isEqualTo(
                set("Ac", "Ad", "Bc", "Bd")
        );
    }

    @Test
    public void multiplyExample() throws Exception {
        assertThat(
                comprehend((Integer x, Integer y, Integer z) -> x * y * z)
                        .firstParameter(1, 2)
                        .secondParameter(1, 2, 3)
                        .thirdParameter(1, 2, 3, 4)
        ).isEqualTo(
                set(1, 2, 3, 4, 6, 8, 9, 12, 16, 18, 24)
        );
    }

    @Test
    public void fourParametersExample() throws Exception {
        assertThat(
                comprehend((Integer a, Integer b, Integer c, Integer d) -> a + b + c + d)
                        .firstParameter(1, 2)
                        .secondParameter(1, 2, 3)
                        .thirdParameter(1, 2, 3, 4)
                        .fourthParameter(1)
        ).isEqualTo(
                set(4, 5, 6, 7, 8, 9, 10)
        );
    }

    @Test
    public void concurrentExample() throws Exception {
        double start = System.currentTimeMillis();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        comprehend(delayForMilliseconds(1000)).firstParameter(1., 2., 3.).secondParameter(1., 2., 3.)
                .parallel().count();

        double duration = System.currentTimeMillis() - start;

        double roughExpectedDuration = 9000. / numberOfThreads;

        double tolerance = 1000.;
        Assertions.assertThat(roughExpectedDuration).isGreaterThan(duration - tolerance).isLessThan(duration + tolerance);
    }

    @SafeVarargs
    private final <T> Set<T> set(T... values) {
        return new HashSet<>(asList(values));
    }

    private BiFunction<Double, Double, Double> delayForMilliseconds(int milliseconds) {
        return (x, y) -> {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0.;
        };
    }

    private <T> CollectionAssert assertThat(Stream<T> stream) {
        return Assertions.assertThat(stream.collect(Collectors.<T>toSet()));
    }
}