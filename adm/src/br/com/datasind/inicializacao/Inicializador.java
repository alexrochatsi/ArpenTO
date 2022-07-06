package br.com.datasind.inicializacao;

/**
 * Esta interface deve ser implementada pelas classes responsaveis por
 * inicializar os objetos necessarios a algum caso de uso antes da execucao do
 * mesmo.
 * 
 * 
 */
public interface Inicializador {

	/**
	 * Este metodo e chamado para fazer a inicializacao. O parametro e Object
	 * para suportar a inicializacao de mesmo objetos que nao estao no mesmo
	 * grafo. Assim e possivel por exemplo, passar como paramentro um array com
	 * os objetos a serem inicializados.
	 * 
	 * @param object
	 */
	public void inicializar(Object object);
}
