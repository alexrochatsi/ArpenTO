package br.com.datasind.gerente;

import java.util.List;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.QueryConfig;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.EntidadePadrao;
import br.com.datasind.entidade.Pagamento;
import br.com.datasind.validacao.ValidacaoException;

@SuppressWarnings("rawtypes")
public interface GerenteModulo extends Gerente {
	public abstract void inicializa(String uso, Object dados);

	public abstract Object valida(Object entidade) throws ValidacaoException;

	public abstract Object converte(Object entidade) throws ValidacaoException;

	public abstract Object incluirEntidade(EntidadePadrao entidade) throws ApplicationException, ValidacaoException;

	public abstract Object incluirEntidadeSemValidacao(EntidadePadrao entidade) throws ApplicationException, ValidacaoException;

	public abstract Object incluirEntidadeSemValidacaoSemPreparacao(EntidadePadrao entidade) throws ApplicationException, ValidacaoException;

	public abstract Object atualizarEntidade(EntidadePadrao entidade) throws ApplicationException, ValidacaoException;
	
	public List<Pagamento> obterListaPagamentosByCartorioPeriodo(Cartorio cartorio, String mes, String ano) throws ApplicationException;
	
	public List<Pagamento> obterListaPagamentosByCartorioPeriodoPagoNPago(Cartorio cartorio, String mes, String ano,Boolean condicao) throws ApplicationException;

	public abstract Object excluirEntidade(EntidadePadrao entidade) throws ApplicationException;

	public abstract List obterListaEntidades(Object query) throws ApplicationException;

	public List obterListaEntidades(Class clazz, Object query) throws ApplicationException;

	public List obterListaEntidades(Class clazz, Object query, QueryConfig config) throws ApplicationException;

	/**
	 * <p>
	 * Este metodo tem a mesma semantica do metodo obterListaEntidades no
	 * sentido de selecionar com base nas propriedades setadas no objeto query,
	 * e adiciona a possibilidade de se selecionar apenas as propriedades de
	 * interesse de acordo com as propriedades setadas no objeto select.
	 * </p>
	 * 
	 * <p>
	 * e importante notar que os objetos retornados por este metodo nao podem
	 * ser utilizados em contextos transacionais como os objetos retornados pelo
	 * obterListaEntidades.
	 * </p>
	 * 
	 * 
	 * @param query
	 *            - Especifica os parametros de pesquisa
	 * @param select
	 *            - Especifica quais sao os atributos que devem ser selecionados
	 * 
	 * @return Retorna uma colecao de objetos da mesma classe (ou subclasses) do
	 *         query com somente os atributos especificados no select
	 *         preenchidos
	 * 
	 * @throws ApplicationException
	 */
	public List obterListaEntidadesSelect(Object query, Object select) throws ApplicationException;

	public EntidadePadrao obterEntidadePelaClasse(Class classObject, Integer id) throws ApplicationException;

	public Object obterEntidade(Object object, Integer id) throws ApplicationException;

	public List atualizarEntidades(List list) throws ValidacaoException, ApplicationException;

	/**
	 * Este metodo tem a mesma semantica do obterListaEntidades com relacao ao
	 * query no entanto lanca um ApplicationException caso mais de uma entidade
	 * seja encontrada e retorna null caso nehuma seja encontrada
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationException
	 */
	public Object obterEntidade(Object query) throws ApplicationException;

	public void atualizarSessao(Object objeto) throws ApplicationException;

	public void inicializarObjeto(Object object) throws ApplicationException;

	public void desacoplarEntidade(EntidadePadrao object) throws ApplicationException;

	/**
	 * Desacopla lista de entidade padrão
	 * 
	 * @author OsmarJunior
	 * @param objects
	 * @throws ApplicationException
	 * @since 25 de fev de 2016
	 *
	 */
	public void desacoplarListaEntidade(List<EntidadePadrao> objects) throws ApplicationException;

	/**
	 * Metodo retorna todos os objeto da classe passada como parametro. Cuidado
	 * com este metodo.
	 * 
	 * @param clazz
	 * @return
	 * @throws ApplicationException
	 */
	public List obterTodos(Class<? extends EntidadePadrao> clazz) throws ApplicationException;

	/**
	 * Metodo que inclui a entidade caso ela nao esteja persistida no banco ou
	 * faz apenas update nos atributos que foram alterado.
	 */
	public Object incluirOuAtualizarEntidade(Object entidade) throws ApplicationException, ValidacaoException;

	/**
	 * Metodo que passa uma lista e inclui a entidade caso ela nao esteja
	 * persistida no banco ou faz apenas update nos atributos que foram
	 * alterado.
	 */
	public Object incluirOuAtualizarListaEntidade(List listEntidade) throws ApplicationException, ValidacaoException;

	/**
	 * Metodo que passa uma lista e inclui a entidade caso ela nao esteja
	 * persistida no banco ou faz apenas update nos atributos que foram alterado
	 * sem validacao.
	 */

	public Object incluirOuAtualizarListaEntidadeSemValidacao(List listEntidade) throws ApplicationException, ValidacaoException;

}
