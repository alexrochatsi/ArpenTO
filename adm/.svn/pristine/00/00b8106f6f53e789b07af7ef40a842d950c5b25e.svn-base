package br.com.datasind.cadastro;

import br.com.datasind.gerente.FabricaGerentePadrao;
import br.com.datasind.transacao.Transacao;

public class ContextoCadastroBasico implements ContextoCadastro {
	private Transacao transacao;

	private FabricaGerentePadrao fabricaGerente;

	/**
	 * 
	 */
	public ContextoCadastroBasico(Transacao transacao,
			FabricaGerentePadrao fabricaGerente) {
		this.transacao = transacao;
		this.fabricaGerente = fabricaGerente;
	}

	public ContextoCadastroBasico(FabricaGerentePadrao fabricaGerente) {
		this.fabricaGerente = fabricaGerente;
	}

	/**
	 * 
	 */
	public Transacao getTransacaoAtual() {
		return transacao;
	}

	/**
	 * 
	 */
	public FabricaGerentePadrao getFabricaGerente() {
		return fabricaGerente;
	}
}
