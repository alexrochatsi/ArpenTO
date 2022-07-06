package br.com.datasind.transacao;

import br.com.datasind.aplicacao.ApplicationException;

public interface Transacao {

	public void commit() throws ApplicationException ;

	public void rollback() throws ApplicationException;
}