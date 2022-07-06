package br.com.datasind.preparacao;

public abstract class PreparadorBase implements Preparador {
	private ContextoPreparador contextoPreparador;

	public ContextoPreparador getContextoPreparador() {
		return contextoPreparador;
	}

	public void setContextoPreparador(ContextoPreparador contextoPreparador) {
		this.contextoPreparador = contextoPreparador;
	}
}