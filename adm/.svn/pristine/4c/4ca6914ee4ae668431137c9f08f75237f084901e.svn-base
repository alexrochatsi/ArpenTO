package br.com.datasind.controller;

/**
 * 
 * @author OsmarJunior
 * @since 19/08/2011
 */
public abstract class ControladorListagem extends CadastroControler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8914201284246164684L;

	private Object query;

	private Boolean fecharListagem;

	private OuvinteSelecao ouvinteSelecao;

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ControladorListagem() {
		super();
		getSessionScope().put(getNome(), this);
	}

	public Boolean getFecharListagem() {
		return fecharListagem;
	}

	public void setFecharListagem(Boolean fecharListagem) {
		this.fecharListagem = fecharListagem;
	}

	public OuvinteSelecao getOuvinteSelecao() {
		return ouvinteSelecao;
	}

	public void setOuvinteSelecao(OuvinteSelecao ouvinteSelecao) {
		this.ouvinteSelecao = ouvinteSelecao;
	}

	public Object getQuery() {
		return query;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

	public abstract String getNome();
}

