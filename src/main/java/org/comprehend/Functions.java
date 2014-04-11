package org.comprehend;

public class Functions {
    static Parameter<Double> square(final Parameter<Double> x) {
        return power(x, constant(2));
    }

    static Parameter<Double> power(final Parameter<Double> x, final Parameter<Double> e) {
        return new Parameter<Double>() {
            @Override
            public Double evaluate() {
                return Math.pow(x.evaluate(), e.evaluate());
            }
        };
    }

    static Parameter<Double> constant(final double value) {
        return new Parameter<Double>() {
            @Override
            public Double evaluate() {
                return value;
            }
        };
    }

    @SafeVarargs
    static Parameter<Double> sum(final Parameter<Double>... params) {
        return new Parameter<Double>() {
            @Override
            public Double evaluate() {
                double sum = 0;
                for (Parameter<Double> parameter : params) {
                    sum += parameter.evaluate();
                }
                return sum;
            }
        };
    }

    static Parameter<String> concatenate(final Parameter<String> s, final Parameter<String> t) {
        return new Parameter<String>() {
            @Override
            public String evaluate() {
                return s.evaluate() + t.evaluate();
            }
        };
    }

    @SafeVarargs
    static Parameter<Double> multiply(final Parameter<Double>... params) {
        return new Parameter<Double>() {
            @Override
            public Double evaluate() {
                double multiple = 1.;
                for (Parameter<Double> parameter : params) {
                    multiple *= parameter.evaluate();
                }
                return multiple;
            }
        };
    }

}
