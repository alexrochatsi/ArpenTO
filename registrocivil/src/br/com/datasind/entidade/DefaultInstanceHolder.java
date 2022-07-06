package br.com.datasind.entidade;



public class DefaultInstanceHolder implements InstanceHolder {
	private Object instance;

	public DefaultInstanceHolder(Object instance) {
		super();
		this.instance = instance;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

}