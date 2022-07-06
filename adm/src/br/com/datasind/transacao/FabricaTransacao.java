package br.com.datasind.transacao;

import br.com.datasind.aplicacao.ApplicationException;

public abstract class FabricaTransacao {
	public abstract Transacao criarTransacao() throws ApplicationException;

	public abstract Object getSessaoAtual();
	
	public abstract void setContexto(ContextoFabricaTransacao contexto);
}
