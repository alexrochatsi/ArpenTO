package br.com.datasind.gerente;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.transacao.FabricaTransacao;
import br.com.datasind.transacao.Transacao;

public class GerenteTransacaoImpl extends GerentePadrao implements
		GerenteTransacao {

	private FabricaTransacao fabricaTransacao;

	@SuppressWarnings("unused")
	private String userId;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setFabricaTransacao(FabricaTransacao fabricaTransacao) {
		this.fabricaTransacao = fabricaTransacao;
	}

	public FabricaTransacao getFabricaTransacao() {
		return fabricaTransacao;
	}

	public Transacao criarTransacao() throws ApplicationException {
		return getFabricaTransacao().criarTransacao();
	}

	public Object getSessaoAtual() {
		return getFabricaTransacao().getSessaoAtual();
	}
}
