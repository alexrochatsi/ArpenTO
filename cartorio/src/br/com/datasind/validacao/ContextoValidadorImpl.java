package br.com.datasind.validacao;

import br.com.datasind.gerente.FabricaGerentePadrao;



public class ContextoValidadorImpl implements ContextoValidador{
	private FabricaValidador fabricaValidador;
	
	private FabricaGerentePadrao fabricaGerente;

	public FabricaGerentePadrao getFabricaGerente() {
		return fabricaGerente;
	}

	public void setFabricaGerente(FabricaGerentePadrao fabricaGerente) {
		this.fabricaGerente = fabricaGerente;
	}

	public FabricaValidador getFabricaValidador() {
		return fabricaValidador;
	}

	public void setFabricaValidador(FabricaValidador fabricaValidador) {
		this.fabricaValidador = fabricaValidador;
	}
	
	
}
