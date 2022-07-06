package br.com.datasind.transacao;



public interface ListenableTransaction extends Transacao {
	public void addOuvinteEstadosTransacao(OuvinteEstadoTransacao ouvinte);
}
