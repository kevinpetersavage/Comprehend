package org.comprehend;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Parameter<T>{
	private Map<Thread, T> valueMap = new ConcurrentHashMap<Thread, T>();

	public Parameter() {}

	public ParameterSetter<T> in(T... items) {
		return new ParameterSetter<T>(this, items);
	}
	
	void setValue(T value){
		this.valueMap.put(Thread.currentThread(), value);
	}
	
	public T evaluate(){
		return valueMap.get(Thread.currentThread());
	}
}
