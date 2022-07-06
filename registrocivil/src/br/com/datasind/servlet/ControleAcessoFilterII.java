
package br.com.datasind.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.aplicacao.SessionSystemAttibutes;
import br.com.datasind.aplicacao.informacao.RegistroRequisicao;
import br.com.datasind.aplicacao.informacao.ServicoInformacao;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.gerente.FabricaGerente;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.gerente.UsuarioSiteThread;
import br.com.datasind.transacao.hibernate.FabricaTransacaoHibernate;
import br.com.datasind.view.jsf.HttpServletAdapter;

public class ControleAcessoFilterII implements Filter{
   private static Logger LOGGER=Logger.getLogger(ControleAcessoFilterII.class);

   private static Collection<FreeResourceRule> freeResourceRules;

   static {
	  addFreeResourceRule(new FreeResourceRule() {
		 public boolean isFreeResource(HttpServletRequest request) {
			if(request.getRequestURI().indexOf("/listagens/") > -1) {

			   LOGGER.debug("Recurso " + request.getRequestURI() + " e uma listagem de entidade " + "e esta sendo liberado. ");

			   return true;
			}
			return false;
		 }

		 @Override
		 public String toString() {
			return "FreeResourceRule Listagens.";
		 }
	  });

	  addFreeResourceRule(new FreeResourceRule() {
		 public boolean isFreeResource(HttpServletRequest request) {
			if(request.getRequestURI().endsWith(LOGIN_PAGE)) {
			   return true;
			}
			return false;
		 }

		 public String toString() {
			return "FreeResourceRule LOGIN_PAGE.";
		 }
	  });

	  addFreeResourceRule(new FreeResourceRule() {
		 public boolean isFreeResource(HttpServletRequest request) {
			if(request.getRequestURI().endsWith(EXT_INDEX)) {
			   return true;
			}
			return false;
		 }

		 public String toString() {
			return "FreeResourceRule EXT_INDEX.";
		 }
	  });

	  addFreeResourceRule(new FreeResourceRule() {
		 public boolean isFreeResource(HttpServletRequest request) {
			if(request.getRequestURI().indexOf("/test/") > -1) {

			   LOGGER.debug("Recurso " + request.getRequestURI() + " e uma pagina de testes. Liberado.");

			   return true;
			}
			return false;
		 }

		 public String toString() {
			return "FreeResourceRule test.";
		 }
	  });

	  addFreeResourceRule(new FreeResourceRule() {
		 public boolean isFreeResource(HttpServletRequest request) {
			if(request.getRequestURI().indexOf("javax.faces.resource/") > -1) {

			   LOGGER.debug("Recurso " + request.getRequestURI() + " e um faces. Acesso Liberado.");

			   return true;
			}
			return false;
		 }

		 public String toString() {
			return "FreeResourceRule javax.";
		 }
	  });

   }

   private static final String LOGIN_PAGE="login.jsf";

   private static final String EXT_INDEX="/ext/index.jsf";

   private final String PORTAL_PAGE="/portal/portal.jsf";
   
   private final String PEDIDO_PAGE="cad/certidaoPedido.jsf";
   
   private final String PEDIDOS_PAGE="cad/pedidosCetidoes.jsf";
   
   private final String MATRICULA_PAGE="cad/geraMatriculaCertidao.jsf";

   private final String SENHA_PAGE="portal/alterarSenha.jsf";

   private final String PAGE_EXTENSION=".jsf";

   private FilterConfig filterConfig;

   public void init(FilterConfig config) throws ServletException {
	  this.filterConfig=config;
	  LOGGER.debug("Inicializando " + getClass().getName());
   }

   @SuppressWarnings("rawtypes")
   public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

	  HttpServletRequest request=(HttpServletRequest) req;
	  Recurso recurso=new Recurso(request);

	  HttpServletResponse response=(HttpServletResponse) resp;
	  HttpServletAdapter adapter=new HttpServletAdapter(request, response);
	  resp.setCharacterEncoding("UTF-8");
	  req.setCharacterEncoding("UTF-8");
	  if( !recurso.matchExtension(PAGE_EXTENSION)) {
		 chain.doFilter(req , resp);
		 return;
	  }

	  Map<String, Object> param=new HashMap<String, Object>();
	  param.put("modulo" , recurso.modulo);
	  param.put("url" , request.getRequestURI());
	  param.put("data" , new Date());

	  ServicoInformacao.registra(new RegistroRequisicao() , param);

	  for(Iterator iter=getFreeResourceRules().iterator(); iter.hasNext();) {
		 FreeResourceRule element=(FreeResourceRule) iter.next();
		 if(element.isFreeResource(request)) {
			chain.doFilter(req , resp);
			return;
		 }
	  }

	  if(request.getSession(false) == null) {
		 redirect(adapter , LOGIN_PAGE);
		 return;
	  }

	  // Para uso de componentes da aplicacao;
	  request.getSession().setAttribute("modulo" , recurso.modulo);

	  if(recurso.modulo == null) {
		 LOGGER.debug("Recurso fora de qualquer modulo.");

		 if(LOGIN_PAGE.equals(recurso.nomeInterno)) {
			LOGGER.debug("Requisicao feita pela pagina" + " de login. Continuando.");

			chain.doFilter(req , resp);
			return;

		 } else {
			LOGGER.debug("Requisicao feita pelo recurso " + recurso + ". Verificando usuario logado.");

			UsuarioSite usuarioSite=getUsuarioSiteLogado(request);
			if(usuarioSite == null) {
			   LOGGER.debug("Usuario nao esta logado." + " Redirecionando para pagina de login");

			   redirect(adapter , LOGIN_PAGE);
			   return;
			}
			UsuarioSiteThread usuarioSiteThread=UsuarioSiteThread.getInstance();
			usuarioSiteThread.set(usuarioSite);

			LOGGER.debug("Usuario logado. Continuando.");

			chain.doFilter(req , resp);
			return;
		 }
	  }

	  UsuarioSite usuarioSite=getUsuarioSiteLogado(request);

	  LOGGER.debug("Verificando se usuario esta logado");
	  if(usuarioSite == null) {
		 LOGGER.info("Usuario nao esta logado. " + "Redirecionando para a pagina de login.");
		 if(recurso.modulo.equals("ext")) {
			redirect(adapter , EXT_INDEX);
		 } else {

			if(isAjax(request) && !request.isRequestedSessionIdValid()) {
			   response.getWriter().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>" , "../" + LOGIN_PAGE);
			} else {
			   redirect(adapter , LOGIN_PAGE);
			}

		 }

		 return;
	  }

	  UsuarioSiteThread usuarioSiteThread=UsuarioSiteThread.getInstance();
	  usuarioSiteThread.set(usuarioSite);

	  PermissaoAcesso permissao=getPermissaoSessao(adapter);

	  if(recurso.modulo != null) {
		 String verifPermissao=recurso.modulo + "/index.jsf";
		 if(recurso.nomeInterno.equals(verifPermissao)) {
			permissao=null;
		 }

	  }

	  if(permissao != null) {
		 // Demorou muito!
		 Modulo modulo=permissao.getPerfil().getModulo();
		 setModuloSessao(adapter , modulo);

		 /********************************************/

		 if(recurso.nomeInterno.equals(permissao.getPerfil().getModulo().getNomeInterno())) {

			LOGGER.debug("Recurso requisitado pelo index e " + "a pagina principal da funcionalidade " + modulo);

			chain.doFilter(req , resp);
			return;
		 }
		 
		 if(recurso.nomeInterno.equals(permissao.getPerfil().getModulo().getNomeInterno())) {

			LOGGER.debug("Recurso requisitado pelo index e " + "a pagina principal da funcionalidade " + modulo);

			chain.doFilter(req , resp);
			return;
		 }
/*
		 for(Iterator iter=permissao.getFuncionalidade().getSubFuncionalidades().iterator(); iter.hasNext();) {
			FuncionalidadePagina pagina=(FuncionalidadePagina) iter.next();
			if(recurso.nomeInterno.equals(pagina.getNomeInterno())) {
			   LOGGER.debug("Recurso encontrado nas " + "paginas da funcionalidade.");

			   chain.doFilter(req , resp);
			   return;
			}
		 }
*/
		 LOGGER.debug("Recurso " + recurso + " nao encontrado. " + "Iniciando limpeza dos objetos na sessao.");

		 LOGGER.debug("Limpeza finalizada");
		 permissao=null;

		 LOGGER.debug("Finalizando sessao do hibernate");
		 FabricaTransacaoHibernate.sessaoFinalizada();
	  }

	  if(permissao == null) {
		 LOGGER.debug("Procurando permissao na " + "base de dados para o recurso" + recurso);

		 Modulo modulo=null;
		 try {
			FabricaGerente fabricaGerente=getFabricaGerente(request);
			if(fabricaGerente == null) {
			   // TODO ver porque acontece o nullpointer quando vc tenta
			   // acessar uma pagina diretamente sem estar logado
			   forward(adapter , LOGIN_PAGE);
			   return;
			}

			GerenteControleAcesso gerente=fabricaGerente.getGerenteControleAcesso();

			modulo=gerente.obterModuloPeloNomeInterno(recurso.modulo);
			setModuloSessao(adapter , modulo);
			if(modulo == null) {
			   if(recurso.nomeInterno.equals(SENHA_PAGE)) {
				  LOGGER.debug("Alterar Senha");
				  forward(adapter , SENHA_PAGE);
				  return;
			   }
			   
			   if(recurso.nomeInterno.equals(PEDIDO_PAGE)) {
				  LOGGER.debug("Novo pedido");
				  forward(adapter , PEDIDO_PAGE);
				  return;
			   }
			   
			   if(recurso.nomeInterno.equals(MATRICULA_PAGE)) {
				  LOGGER.debug("Gerar Matrícula");
				  forward(adapter , MATRICULA_PAGE);
				  return;
			   }
			   
			   if(recurso.nomeInterno.equals(PEDIDOS_PAGE)) {
				  LOGGER.debug("Pedidos de certidões");
				  forward(adapter , PEDIDOS_PAGE);
				  return;
			   }
			   
			   LOGGER.debug("O modulo do recurso " + recurso + " nao foi encontrado.");

			   forward(adapter , PORTAL_PAGE);
			   return;
			}

			if(recurso.nomeInterno.equals(modulo.getNomeInterno())) {
			   LOGGER.debug("Requisicao feita pelo indice do modulo " + modulo + ".");

			   chain.doFilter(req , resp);
			   return;
			}

			PermissaoAcesso permissaoEncontrada=gerente.obterPermissoesAcessoPorUsuarioEModulo(usuarioSite , modulo);

			if(permissaoEncontrada == null) {
			   LOGGER.debug("Usuario nao esta autorizado " + "a acessar a funcionalidade no modulo " + modulo);

			   forward(adapter , modulo.getNomeInterno());
			   return;
			}

			if(permissao == null) {
			   // FabricaTransacaoHibernate.sessaoFinalizada();
			}

			setPermissaoSessao(adapter , permissaoEncontrada);

			chain.doFilter(req , resp);
			return;

		 } catch (ApplicationException e) {
			LOGGER.error("Problemas procurando pela " + "permissao do usuario. " + "Redirecionando requisicao." , e);

			if(modulo != null) {
			   forward(adapter , modulo.getNomeInterno());

			} else {
			   forward(adapter , PORTAL_PAGE);
			}
			return;
		 }
	  }

   }

   public void destroy() {
	  LOGGER.debug("Finalizando " + getClass().getName());
   }

   private class Recurso{
	  public static final char PATH_SEPARATOR='/';

	  public String modulo;

	  public String nomeInterno;

	  public Recurso(HttpServletRequest request) {

		 nomeInterno=request.getRequestURI().replaceAll(request.getContextPath() + PATH_SEPARATOR , "");

		 int index=nomeInterno.indexOf(PATH_SEPARATOR);
		 if(index > -1) {
			modulo=nomeInterno.substring(0 , index);
		 }
	  }

	  public String toString() {
		 return "[nomeInterno=" + nomeInterno + ", modulo=" + modulo + "]";
	  }

	  public boolean matchExtension(String extension) {
		 if(nomeInterno.endsWith(extension)) {
			return true;
		 }
		 return false;
	  }

   }

   private UsuarioSite getUsuarioSiteLogado(HttpServletRequest request) {
	  HttpSession httpSession=request.getSession();
	  return (UsuarioSite) httpSession.getAttribute(SessionSystemAttibutes.SESSION_PARAMETER_USUARIO_SITE_SESSAO);
   }

   public FabricaGerente getFabricaGerente(HttpServletRequest request) {
	  return (FabricaGerente) WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean(FabricaGerente.NOME);
   }

   public PermissaoAcesso getPermissaoSessao(HttpServletAdapter adapter) {
	  return (PermissaoAcesso) adapter.getSessionAttribute(HttpServletAdapter.SESSION_PARAMETER_PERMISSAO_ACESSO_ATUAL);
   }

   public void setPermissaoSessao(HttpServletAdapter adapter, PermissaoAcesso permissao) {
	  adapter.setSessionAttribute(HttpServletAdapter.SESSION_PARAMETER_PERMISSAO_ACESSO_ATUAL , permissao);
   }

   public void forward(HttpServletAdapter adapter, String target) throws ServletException, IOException {

	  if(target.indexOf(Recurso.PATH_SEPARATOR) < 0) {
		 target=Recurso.PATH_SEPARATOR + target;
	  }

	  if( !target.startsWith("" + Recurso.PATH_SEPARATOR)) {
		 target=Recurso.PATH_SEPARATOR + target;
	  }

	  adapter.dispatcherRequestForward(target);
	  return;
   }

   public void redirect(HttpServletAdapter adapter, String target) throws IOException {

	  if(target.indexOf(Recurso.PATH_SEPARATOR) < 0) {
		 target=Recurso.PATH_SEPARATOR + target;
	  }

	  if( !target.startsWith("" + Recurso.PATH_SEPARATOR)) {
		 target=Recurso.PATH_SEPARATOR + target;
	  }
	  adapter.sendRedirect(target);
   }

   /**
    * @param adapter
    * @param acesso
    * @deprecated usar {@link #setModuloSessao(HttpServletAdapter, Modulo)} ao inves desse.
    */
   @Deprecated
   public void setModuloSessao(HttpServletAdapter adapter, PermissaoAcesso acesso) {
	  setModuloSessao(adapter , acesso.getPerfil().getModulo());
   }

   public void setModuloSessao(HttpServletAdapter adapter, Modulo modulo) {
	  adapter.setSessionAttribute(SessionSystemAttibutes.SESSION_PARAMETER_MODULO_ATUAL_SESSAO , modulo);
   }

   public static Collection<FreeResourceRule> getFreeResourceRules() {
	  if(freeResourceRules == null) {
		 freeResourceRules=new ArrayList<FreeResourceRule>();
	  }
	  return freeResourceRules;
   }

   public static void addFreeResourceRule(FreeResourceRule rule) {
	  LOGGER.debug("Adicionando FreeResourceRule " + rule + " ao ControleAcessoFilterII");
	  getFreeResourceRules().add(rule);
   }

   private boolean isAjax(HttpServletRequest request) {
	  return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
   }

}
