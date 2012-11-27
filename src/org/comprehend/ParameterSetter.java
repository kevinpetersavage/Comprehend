package org.comprehend;

public class ParameterSetter<T> {
	private final T[] items;
	private final Parameter<T> parameter;

	public ParameterSetter(Parameter<T> parameter, T[] items) {
		this.parameter = parameter;
		this.items = items;
	}
	
	int size() {
		return items.length;
	}
	
	public void setParameter(long index){
		parameter.setValue(items[(int)index]);
	}
}
