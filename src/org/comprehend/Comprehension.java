package org.comprehend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Comprehension {
	static public <T> Set<T> comprehend(Parameter<T> function, @SuppressWarnings("rawtypes") ParameterSetter... params) {
		List<Integer> sizes = sizes(params);
		long combinations = combinations(sizes);

		Set<T> results = new HashSet<T>();
		for (long i = 0; i<combinations; i++){
			long combinationsSoFar = 1;
			for (int j = 0; j < params.length; j++) {
				long size = sizes.get(j);
				params[j].setParameter((i/combinationsSoFar)%size);
				combinationsSoFar *= size;
			}
			results.add(function.evaluate());
		}
		return results;
	}

	private static long combinations(List<Integer> sizes) {
		long combinations = 1;
		for (Integer size : sizes) {
			combinations *= size;
		}
		return combinations;
	}

	@SuppressWarnings("rawtypes")
	private static List<Integer> sizes(ParameterSetter... params) {
		List<Integer> sizes = new ArrayList<Integer>();
		for (ParameterSetter param: params) {
			int size = param.size();
			sizes.add(size);
		}
		return sizes; 
	}
}
