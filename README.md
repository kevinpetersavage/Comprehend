Comprehend
==========

Attempt at a syntax for list comprehensions in java. Allows you to write things like:

	comprehend((Double x, Double y) -> x * x + y * y)
		.firstParameter(1., 2., 3.).secondParameter(1., 2., 3.)

See org.comprehend.ExampleTests for (a few) more examples.

Adding more parameters
======================

Because Java 8 functions need to implement an interface to be passed to other code, comprehend needs to know about interfaces for different numbers of function parameters. It is easy to add extra parameter support by doing the following:

* Create a single method interface in org.comprehend.function with the number of parameters you require.
* Create a parameter capturer interface in org.comprehend.capture to name your new parameters.
* Implement your capturer in ParameterCapturer using exactly the same single line as the existing ones.
* Create a new method in Comprehension to allow passing your new function interface.