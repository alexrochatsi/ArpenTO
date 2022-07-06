package br.com.datasind.validacao;

import br.com.datasind.gerente.FabricaGerentePadrao;



public interface ContextoValidador {
	public FabricaGerentePadrao getFabricaGerente();
	
	public FabricaValidador getFabricaValidador();
}

