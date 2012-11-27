package org.comprehend;

import static java.util.Arrays.asList;
import static org.comprehend.Comprehension.comprehend;
import static org.comprehend.Comprehension.comprehendInParallel;
import static org.comprehend.Functions.concatinate;
import static org.comprehend.Functions.multiply;
import static org.comprehend.Functions.square;
import static org.comprehend.Functions.sum;
import static org.comprehend.Parameters.s;
import static org.comprehend.Parameters.t;
import static org.comprehend.Parameters.x;
import static org.comprehend.Parameters.y;
import static org.comprehend.Parameters.z;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/** mainly intended as documentation but also used for TDDing**/
public class ExampleTests {

	@Test @SuppressWarnings("unchecked")
	public void basicExample() throws Exception {
		assertEquals(
			set(2., 5., 8., 10., 13., 18.), 
			comprehend(sum(square(x), square(y)), x.in(1.,2.,3.), y.in(1.,2.,3.))
		);
	}
	
	@Test
	public void stringConcat() throws Exception {
		assertEquals(
			set("Ac","Ad","Bc","Bd"), 
			comprehend(concatinate(s, t), s.in("A","B"), t.in("c","d"))
		);
	}

	@Test @SuppressWarnings("unchecked")
	public void multiplyExample() throws Exception {
		assertEquals(
			set(1.,2.,3.,4.,6.,8.,9.,12.,16.,18.,24.), 
			comprehend(multiply(x, y, z), x.in(1.,2.), y.in(1.,2.,3.), z.in(1.,2.,3.,4.))
		);
	}	
	
	@Test
	public void concurrentExample() throws Exception {
		// we would expect the below to take ~ 1000ms * 3 * 3 = 9000ms sequentially
		double start = System.currentTimeMillis();
		int numberOfThreads = 9;
		comprehendInParallel(numberOfThreads, new DelayForMilliseconds(1000), x.in(1.,2.,3.), y.in(1.,2.,3.));
		double duration = System.currentTimeMillis()-start;
		
		// but because we ran in parallel and sleeping actually doesn't use a core up...
		double roughExpectedDuration = 9000./9.; 
		
		assertEquals(roughExpectedDuration, duration, 100.);
	}	
	
	private <T> Set<T> set(T... values) {
		return new HashSet<T>(asList(values));
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