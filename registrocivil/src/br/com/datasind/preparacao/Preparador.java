package br.com.datasind.preparacao;

public interface Preparador {
	/**
	 * Chamado para realizar o processo de preparacao
	 * 
	 * @param object
	 * @return
	 * @throws PreparadorException
	 */
	public Object prepara(Object object) throws PreparadorException;

	public void setContextoPreparador(ContextoPreparador contexto);
}
