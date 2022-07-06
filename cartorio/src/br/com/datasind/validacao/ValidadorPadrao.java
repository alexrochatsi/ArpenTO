package br.com.datasind.validacao;


public abstract class ValidadorPadrao implements Validador {
	private ContextoValidador contexto;

	public ContextoValidador getContexto() {
		return contexto;
	}

	public void setContexto(ContextoValidador contexto) {
		this.contexto = contexto;
	}

	/**
	 * 
	 */
	public ValidadorPadrao() {
		super();
	}

}
