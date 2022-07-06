package br.com.datasind.preparacao;

public class PreparadorPadrao implements Preparador {

	/**
	 * Existe a tabela SGI.SEQUENCEANUAL no banco, onde deve ser cadastrado o
	 * nome da tabela que vai gerar o numero sequencia, seu tipo caso exista
	 * dois tipos diferentes (ex. processo) e tambem para qual empresa
	 */

	public PreparadorPadrao() {
		super();
	}

	public Object prepara(Object object) throws PreparadorException {
		return object;
	}

	public void setContextoPreparador(ContextoPreparador contexto) {

	}

}
