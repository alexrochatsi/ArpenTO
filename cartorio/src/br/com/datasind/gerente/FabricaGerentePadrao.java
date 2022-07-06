package br.com.datasind.gerente;



public class FabricaGerentePadrao {
	
	private GerenteTransacao gerenteTransacao;

	public GerenteTransacao getGerenteTransacao() {
		if (gerenteTransacao == null) {
			gerenteTransacao = new GerenteTransacaoImpl();
		}
		return gerenteTransacao;
	}
}
