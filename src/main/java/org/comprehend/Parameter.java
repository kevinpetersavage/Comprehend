package org.comprehend;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Parameter<T>{
	private Map<Thread, T> valueMap = new ConcurrentHashMap<>();

	public Parameter() {}

	@SafeVarargs
    public final ParameterSetter<T> in(T... items) {
		return new ParameterSetter<>(this, items);
	}

	void setValue(T value){
		this.valueMap.put(Thread.currentThread(), value);
	}

	public T evaluate(){
		return valueMap.get(Thread.currentThread());
	}
}
