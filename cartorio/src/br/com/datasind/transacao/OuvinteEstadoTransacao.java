package br.com.datasind.transacao;



public interface OuvinteEstadoTransacao {
	public void transacaoConcluida(Transacao transacao);

	public void transacaoAbortada(Transacao transacao);
}
