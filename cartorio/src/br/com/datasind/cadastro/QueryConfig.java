package br.com.datasind.cadastro;

public class QueryConfig {

	public static final int MODO_ORDENACAO_ID = 1;

	public static final int MODO_ORDENACAO_PRIMEIRA_STRING = 2;

	private int modoOrdenacao = MODO_ORDENACAO_PRIMEIRA_STRING;

	private Object orderObject;

	public int getModoOrdenacao() {
		return modoOrdenacao;
	}

	public void setModoOrdenacao(int modoOrdenacao) {
		this.modoOrdenacao = modoOrdenacao;
	}

	public Object getOrderObject() {
		return orderObject;
	}

	public void setOrderObject(Object orderObject) {
		this.orderObject = orderObject;
	}

}

