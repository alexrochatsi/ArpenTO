package br.com.datasind.validacao;



public interface Validador {
	public void setContexto(ContextoValidador contexto);

	public void validar(Object object) throws ValidacaoException;
}
