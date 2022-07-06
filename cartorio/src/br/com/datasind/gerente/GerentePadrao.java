
package br.com.datasind.gerente;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.datasind.aop.WriteMethod;
import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.aplicacao.ConfiguracaoDataSind;
import br.com.datasind.cadastro.CadastroException;
import br.com.datasind.cadastro.CadastroPadrao;
import br.com.datasind.cadastro.CadastroPagamento;
import br.com.datasind.cadastro.ContextoCadastroBasico;
import br.com.datasind.cadastro.QueryConfig;
import br.com.datasind.ciclovida.CicloVida;
import br.com.datasind.ciclovida.FabricaCicloVida;
import br.com.datasind.conversao.Conversor;
import br.com.datasind.conversao.FabricaConversor;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.EntidadePadrao;
import br.com.datasind.entidade.Pagamento;
import br.com.datasind.inicializador.FabricaInicializador;
import br.com.datasind.inicializador.Inicializador;
import br.com.datasind.preparacao.ContextoPreparadorBasico;
import br.com.datasind.preparacao.FabricaPreparador;
import br.com.datasind.preparacao.Preparador;
import br.com.datasind.preparacao.PreparadorException;
import br.com.datasind.validacao.FabricaValidador;
import br.com.datasind.validacao.ValidacaoException;
import br.com.datasind.validacao.Validador;

@SuppressWarnings("rawtypes")
public class GerentePadrao implements GerenteModulo{

   private static Logger LOGGER=Logger.getLogger(GerentePadrao.class);

   private ContextoGerenteCadastro contexto;

   public ContextoGerenteCadastro getContexto() {
	  return contexto;
   }

   public void setContexto(ContextoGerenteCadastro contexto) {
	  this.contexto=contexto;
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#incluirEntidade(java.lang.Object)
    */
   @WriteMethod
   public EntidadePadrao incluirEntidade(EntidadePadrao entidade) throws ApplicationException, ValidacaoException {

	  converte(entidade);
	  valida(entidade);
	  executaCiclo(entidade , INCLUIR);

	  FabricaPreparador fabricaPreparador=FabricaPreparador.getInstancia();
	  Preparador preparador=fabricaPreparador.getPreparador(entidade);
	  preparador.setContextoPreparador(new ContextoPreparadorBasico(getContexto().getFabricaGerente()));

	  try {
		 preparador.prepara(entidade);

	  } catch (PreparadorException e) {
		 throw new ApplicationException(e);
	  }

	  CadastroPadrao cadastroPadrao=getCadastro(entidade);
	  try {
		 cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
		 entidade=cadastroPadrao.incluirEntidade(entidade);
		 return entidade;

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @WriteMethod
   public EntidadePadrao incluirEntidadeSemValidacao(EntidadePadrao entidade) throws ApplicationException, ValidacaoException {

	  converte(entidade);
	  // valida(entidade);
	  executaCiclo(entidade , INCLUIR);

	  FabricaPreparador fabricaPreparador=FabricaPreparador.getInstancia();
	  Preparador preparador=fabricaPreparador.getPreparador(entidade);
	  preparador.setContextoPreparador(new ContextoPreparadorBasico(getContexto().getFabricaGerente()));

	  try {
		 preparador.prepara(entidade);

	  } catch (PreparadorException e) {
		 throw new ApplicationException(e);
	  }

	  CadastroPadrao cadastroPadrao=getCadastro(entidade);
	  try {
		 cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
		 entidade=cadastroPadrao.incluirEntidade(entidade);
		 return entidade;

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @WriteMethod
   public EntidadePadrao incluirEntidadeSemValidacaoSemPreparacao(EntidadePadrao entidade) throws ApplicationException, ValidacaoException {

	  converte(entidade);
	  // valida(entidade);
	  executaCiclo(entidade , INCLUIR);

	  CadastroPadrao cadastroPadrao=getCadastro(entidade);
	  try {
		 cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
		 entidade=cadastroPadrao.incluirEntidade(entidade);
		 return entidade;

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public CadastroPadrao getCadastro(Object object) {
	  if(object == null) {
		 return null;
	  }
	  String nome=object.getClass().getName();
	  int indice=nome.lastIndexOf('.');

	  String nomeCadastro=ConfiguracaoDataSind.getInstancia().get("dataSind.cadastro.pacote") + ".Cadastro" + nome.substring(indice + 1);

	  CadastroPadrao cadastro;
	  try {
		 cadastro=(CadastroPadrao) Class.forName(nomeCadastro).newInstance();
		 return cadastro;

	  } catch (InstantiationException e) {
		 e.printStackTrace();

	  } catch (IllegalAccessException e) {
		 e.printStackTrace();

	  } catch (ClassNotFoundException e) {
		 LOGGER.debug("Cadastro nao encontrado para " + object);
	  }

	  return new CadastroPadrao();
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#excluirEntidade(java.lang.Object)
    */
   @WriteMethod
   public EntidadePadrao excluirEntidade(EntidadePadrao entidade) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  executaCiclo(entidade , EXCLUIR);

	  try {
		 cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
		 entidade=cadastroPadrao.excluirEntidade(entidade);

		 return entidade;

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#atualizarEntidade(java.lang.Object)
    */
   @WriteMethod
   public EntidadePadrao atualizarEntidade(EntidadePadrao entidade) throws ApplicationException, ValidacaoException {

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();

	  converte(entidade);
	  valida(entidade);
	  executaCiclo(entidade , ALTERAR);

	  try {
		 cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
		 entidade=cadastroPadrao.atualizarEntidade(entidade);

		 return entidade;

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#atualizarEntidade(java.lang.Object)
    */
   @WriteMethod
   public EntidadePadrao atualizarEntidadeComit(EntidadePadrao entidade) throws ApplicationException, ValidacaoException {

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();

	  converte(entidade);
	  valida(entidade);
	  executaCiclo(entidade , ALTERAR);

	  try {
		 cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
		 entidade=cadastroPadrao.atualizarEntidadeEComit(entidade);

		 return entidade;

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public List<Pagamento> obterListaPagamentosByCartorioPeriodo(Cartorio cartorio, String mes, String ano) throws ApplicationException {
	  CadastroPagamento cadastroPagamento=new CadastroPagamento();
	  cadastroPagamento.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroPagamento.findByCartorioPeriodo(cartorio, mes, ano);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public List<Pagamento> obterListaPagamentosByCartorioPeriodoPagoNPago(Cartorio cartorio, String mes, String ano, Boolean condicao) throws ApplicationException {
	  CadastroPagamento cadastroPagamento=new CadastroPagamento();
	  cadastroPagamento.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroPagamento.findByCartorioPeriodoPagoNPago(cartorio, mes, ano, condicao);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @WriteMethod
   public Object incluirOuAtualizarEntidade(Object entidade) throws ApplicationException, ValidacaoException {

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();

	  converte(entidade);
	  valida(entidade);

	  FabricaPreparador fabricaPreparador=FabricaPreparador.getInstancia();
	  Preparador preparador=fabricaPreparador.getPreparador(entidade);
	  preparador.setContextoPreparador(new ContextoPreparadorBasico(getContexto().getFabricaGerente()));

	  try {
		 preparador.prepara(entidade);

	  } catch (PreparadorException e) {
		 throw new ApplicationException(e);
	  }
	  if(((EntidadePadrao) entidade).entidadeTransitente()) {
		 executaCiclo(entidade , INCLUIR);

	  } else {
		 executaCiclo(entidade , ALTERAR);
	  }

	  try {
		 cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

		 entidade=cadastroPadrao.incluirOuAtualizarEntidade(entidade);
		 return entidade;

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * Metodo utilitario que recebe uma lista e insere uma entidade caso ela nao exista, e caso exista, atualiza seus
    * valores na fonte de dados.
    */

   @WriteMethod
   public Object incluirOuAtualizarListaEntidade(List listEntidade) throws ApplicationException, ValidacaoException {

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  for(Iterator iter=listEntidade.iterator(); iter.hasNext();) {
		 Object entidade=(Object) iter.next();

		 converte(entidade);
		 valida(entidade);

		 FabricaPreparador fabricaPreparador=FabricaPreparador.getInstancia();
		 Preparador preparador=fabricaPreparador.getPreparador(entidade);
		 preparador.setContextoPreparador(new ContextoPreparadorBasico(getContexto().getFabricaGerente()));

		 try {
			preparador.prepara(entidade);

		 } catch (PreparadorException e) {
			throw new ApplicationException(e);
		 }

		 if(((EntidadePadrao) entidade).entidadeTransitente()) {
			executaCiclo(entidade , INCLUIR);
		 } else {
			executaCiclo(entidade , ALTERAR);
		 }

		 try {
			cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
			entidade=cadastroPadrao.incluirOuAtualizarEntidade(entidade);

		 } catch (CadastroException e) {
			throw new ApplicationException(e);
		 }
	  }
	  return listEntidade;
   }

   @WriteMethod
   public Object incluirOuAtualizarListaEntidadeSemValidacao(List listEntidade) throws ApplicationException, ValidacaoException {

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  for(Iterator iter=listEntidade.iterator(); iter.hasNext();) {
		 Object entidade=(Object) iter.next();

		 converte(entidade);

		 FabricaPreparador fabricaPreparador=FabricaPreparador.getInstancia();
		 Preparador preparador=fabricaPreparador.getPreparador(entidade);
		 preparador.setContextoPreparador(new ContextoPreparadorBasico(getContexto().getFabricaGerente()));

		 try {
			preparador.prepara(entidade);

		 } catch (PreparadorException e) {
			throw new ApplicationException(e);
		 }

		 if(((EntidadePadrao) entidade).entidadeTransitente()) {
			executaCiclo(entidade , INCLUIR);
		 } else {
			executaCiclo(entidade , ALTERAR);
		 }

		 try {
			cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
			entidade=cadastroPadrao.incluirOuAtualizarEntidade(entidade);

		 } catch (CadastroException e) {
			throw new ApplicationException(e);
		 }
	  }
	  return listEntidade;
   }

   /**
    * Metodo atualizar entidades para objetos compostos por uma colecao de objetos ( List) - exemplo o objeto permissao
    * de acesso do cadastro permissao de acesso controler.
    * 
    * @see br.com.datasind.gerente.GerenteCadastr#atualizarEntidades(java.util.List)
    */
   @SuppressWarnings("unchecked")
   @WriteMethod
   public List atualizarEntidades(List list) throws ValidacaoException, ApplicationException {

	  if(list.size() < 1) {
		 return list;
	  }

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();

	  FabricaConversor fabricaConversor=FabricaConversor.getInstancia();
	  Conversor conversor=null;

	  FabricaValidador fabricaValidador=getContexto().getFabricaValidador();
	  Validador validador=null;

	  int j=0;
	  for(Iterator i=list.iterator(); i.hasNext();) {
		 validador=fabricaValidador.getValidador(list.get(j));
		 conversor=fabricaConversor.getConversor(list.get(j));
		 j++;

		 Object object=(Object) i.next();
		 conversor.converter(object);
		 validador.validar(object);
		 if(((EntidadePadrao) object).entidadeTransitente()) {
			executaCiclo(object , INCLUIR);
		 }
	  }

	  try {
		 cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
		 for(int i=0; i < list.size(); i++) {
			Object element=list.get(i);
			list.set(i , cadastroPadrao.incluirOuAtualizarEntidade(element));
		 }
		 return list;

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#obterListaEntidades(java.lang.Object)
    */
   public List obterListaEntidades(Object query) throws ApplicationException {
	  return obterListaEntidades(query.getClass() , query);
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#obterListaEntidades(java.lang.Object)
    */
   public List obterListaEntidades(Class clazz, Object query) throws ApplicationException {
	  converte(query);
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();

	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroPadrao.findByQuery(clazz , query);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#obterListaEntidades(java.lang.Object)
    */
   public List obterListaEntidades(Class clazz, Object query, QueryConfig config) throws ApplicationException {
	  converte(query);
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();

	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroPadrao.findByQuery(clazz , query , config);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#obterEntidade(java.lang.Object)
    */
   public Object obterEntidade(Object query) throws ApplicationException {
	  return obterEntidade(query.getClass() , query);
   }

   public List obterTodos(Class<? extends EntidadePadrao> clazz) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();

	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroPadrao.findAll(clazz);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#obterEntidade(java.lang.Object)
    */
   public Object obterEntidade(Class clazz, Object query) throws ApplicationException {
	  converte(query);

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroPadrao.findUniqueResultByQuery(clazz , query);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#obterListaEntidadesSelect(java.lang.Object, java.lang.Object)
    */
   public List obterListaEntidadesSelect(Object query, Object select) throws ApplicationException {
	  converte(query);

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroPadrao.findByQuery(query , select);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see
    */
   public Object obterEntidade(Object object, Integer id) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroPadrao.findById(object , id);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see
    */
   public EntidadePadrao obterEntidadePelaClasse(Class classObject, Integer id) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroPadrao.findById(classObject , id);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);

	  }
   }

   public void atualizarSessao(Object objeto) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 cadastroPadrao.atualizarSessaoAtual(objeto);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @deprecated
    */
   public void inicializarObjeto(Object object) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 cadastroPadrao.inicializarProxy(object);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public void liberarSessao(Object objeto) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 cadastroPadrao.liberarSessao(objeto);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#converte(java.lang.Object)
    */
   public Object converte(Object entidade) {
	  FabricaConversor fabricaConversor=FabricaConversor.getInstancia();
	  Conversor conversor=fabricaConversor.getConversor(entidade);

	  conversor.converter(entidade);

	  return entidade;
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#converte(java.lang.Object)
    */
   public void inicializa(String uso, Object dados) {
	  FabricaInicializador fabricaInicializador=FabricaInicializador.getInstancia();
	  Inicializador inicializador=fabricaInicializador.getInicializador(uso);
	  inicializador.inicializar(dados);
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#converte(java.lang.Object)
    */
   public void inicializaComValidacao(String uso, Object dados) {
	  FabricaInicializador fabricaInicializador=FabricaInicializador.getInstancia();
	  Inicializador inicializador=fabricaInicializador.getInicializador(uso);

	  inicializador.inicializar(dados);
   }

   /**
    * @see br.com.datasind.gerente.GerenteCadastr#valida(java.lang.Object)
    */
   public Object valida(Object entidade) throws ValidacaoException {
	  FabricaValidador fabricaValidador=getContexto().getFabricaValidador();
	  Validador validador=fabricaValidador.getValidador(entidade);

	  validador.validar(entidade);

	  return entidade;
   }

   public void desacoplarEntidade(EntidadePadrao object) throws ApplicationException {

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 cadastroPadrao.makeDetached(object);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public void desacoplarListaEntidade(List<EntidadePadrao> objects) throws ApplicationException {

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 for(EntidadePadrao entidadePadrao : objects) {
			cadastroPadrao.makeDetached(entidadePadrao);
		 }

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   protected void lock(Object object, int modo) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 cadastroPadrao.lock(object , modo);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   protected void lockEntity(Class<? extends EntidadePadrao> clazz) throws ApplicationException {
	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 cadastroPadrao.lockEntity(clazz);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public Map obterNumeroPorTabelaTipoAndInstituicao(String tabela, Integer tipo, Integer instituicao) throws ApplicationException {

	  CadastroPadrao cadastroPadrao=new CadastroPadrao();
	  cadastroPadrao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroPadrao.findByNumeroSequencia(tabela , tipo , instituicao);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public static final int INCLUIR=3;
   public static final int ALTERAR=1;
   public static final int EXCLUIR=2;

   public void executaCiclo(Object entidade, int fase) {
	  CicloVida cicloVida=FabricaCicloVida.getInstancia().getCicloVida(entidade);
	  if(cicloVida == null) {
		 return;
	  }

	  switch (fase) {
		 case INCLUIR:
			cicloVida.incluir(entidade);
			break;

		 case ALTERAR:
			cicloVida.alterar(entidade);
			break;

		 case EXCLUIR:
			cicloVida.excluir(entidade);
			break;
	  }
   }
}
