package org.comprehend;

import static java.util.Arrays.asList;
import static org.comprehend.Comprehension.comprehend;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

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
	
	/** this was originally a problem **/
	@Test @SuppressWarnings("unchecked")
	public void canCallFromDifferentThreads() throws Exception {
		List<Thread> threads = new ArrayList<Thread>();
		final List<Set<Double>> comprehended = new ArrayList<Set<Double>>();
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(){
				@Override
				public void run() {
					comprehended.add(comprehend(multiply(x, y, z), x.in(1.,2.), y.in(1.,2.,3.), z.in(1.,2.,3.,4.)));
				}
			};
			threads.add(thread);
			thread.start();
		}

		for (Thread thread: threads) {
			thread.join();
		}

		assertEquals(threads.size(), comprehended.size());
		for (Set<Double> set : comprehended) {
			assertEquals(set(1.,2.,3.,4.,6.,8.,9.,12.,16.,18.,24.), set);
		}
	}	
	
	private <T> Set<T> set(T... values) {
		return new HashSet<T>(asList(values));
	}
}