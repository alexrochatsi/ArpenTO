
package br.com.datasind.controller;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import br.com.certisign.CertificadoBean;
import br.com.datasind.aplicacao.Configuracao;
import br.com.datasind.aplicacao.SessionSystemAttibutes;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.relatorio.Relatorio;
import br.com.datasind.relatorio.RelatorioBuilder;
import br.com.datasind.servlet.RelatorioServlet;
import br.com.evoluti.jsf.componente.messages.Message;
import br.com.evoluti.jsf.componente.messages.MessageImpl;
import br.com.evoluti.jsf.componente.messages.MessageSeverity;
import br.com.evoluti.jsf.componente.messages.UIMessages;

@SuppressWarnings("rawtypes")
public abstract class AbstractControler implements Serializable{

   /**
    * 
    */
   private static final long serialVersionUID= -4781595463988876036L;

   private Configuracao configuracao;

   private UsuarioCartorio usuarioCartorioSessao;

   private CertificadoBean certificadoBeanSessao;
   
   private String msgLoginSessao;

   private Modulo moduloAtual;

   private PermissaoAcesso permissoesAcessoAtual;

   public static Logger LOGGER=Logger.getLogger(AbstractControler.class);

   protected static final String CURRENT_PAGE="";

   public static final String ERROR_MESSAGE="ERROR";

   public static final String WARNING_MESSAGE="WARN";

   public static final String INFO_MESSAGE="INFO";

   public static final Severity ERROR=FacesMessage.SEVERITY_ERROR;

   public static final Severity WARNING=FacesMessage.SEVERITY_WARN;

   public static final Severity INFO=FacesMessage.SEVERITY_INFO;

   protected transient FacesContext facesContext;

   protected transient ServletContext servletContext;

   protected transient Map requestScope;

   protected transient Map sessionScope;

   protected transient Map applicationScope;

   protected transient Map requestParam;

   private List messages;

   /**
    * @generated
    */
   public AbstractControler() {

   }

   /**
    * @generated
    */
   protected void gotoPage(String pageName) {
	  if(pageName != null) {
		 FacesContext context=FacesContext.getCurrentInstance();
		 UIViewRoot newView=context.getApplication().getViewHandler().createView(context , pageName);
		 context.setViewRoot(newView);
	  }
   }

   /**
    * <p>
    * Return the {@link UIComponent}(if any) with the specified <code>id</code> , searching recursively starting at the
    * specified <code>base</code>, and examining the base component itself, followed by examining all the base
    * component's facets and children. Unlike findComponent method of {@link UIComponentBase}, which skips recursive
    * scan each time it finds a {@link NamingContainer}, this method examines all components, regardless of their
    * namespace (assuming IDs are unique).
    * 
    * @param base
    *           Base {@link UIComponent}from which to search
    * @param id
    *           Component identifier to be matched
    */
   public static UIComponent findComponent(UIComponent base, String id) {

	  // Is the "base" component itself the match we are looking for?
	  if(id.equals(base.getId())) {
		 return base;
	  }

	  // Search through our facets and children
	  UIComponent kid=null;
	  UIComponent result=null;
	  Iterator kids=base.getFacetsAndChildren();
	  while (kids.hasNext() && (result == null)) {
		 kid=(UIComponent) kids.next();
		 if(id.equals(kid.getId())) {
			result=kid;
			break;
		 }
		 result=findComponent(kid , id);
		 if(result != null) {
			break;
		 }
	  }
	  return result;
   }

   public static UIComponent findComponentInRoot(String id) {
	  UIComponent ret=null;

	  FacesContext context=FacesContext.getCurrentInstance();
	  if(context != null) {
		 UIComponent root=context.getViewRoot();
		 ret=findComponent(root , id);
	  }

	  return ret;
   }

   /**
    * @generated Place an Object on the tree's attribute map
    * 
    * @param key
    * @param value
    */

   protected void putTreeAttribute(String key, Object value) {
	  FacesContext.getCurrentInstance().getViewRoot().getAttributes().put(key , value);
   }

   /**
    * @generated Retrieve an Object from the tree's attribute map
    * @param key
    * @return
    */
   protected Object getTreeAttribute(String key) {
	  return FacesContext.getCurrentInstance().getViewRoot().getAttributes().get(key);
   }

   /**
    * @generated Return the result of the resolved expression
    * 
    * @param expression
    * @return
    */
   @SuppressWarnings("deprecation")
   protected Object resolveExpression(String expression) {
	  Object value=null;
	  if((expression.indexOf("#{") != -1) && (expression.indexOf("#{") < expression.indexOf('}'))) {
		 FacesContext context=FacesContext.getCurrentInstance();
		 value=context.getApplication().createValueBinding(expression).getValue(context);
	  } else {
		 value=expression;
	  }
	  return value;
   }

   /**
    * @generated Resolve all parameters passed in via the argNames/argValues array pair, and add them to the provided
    *            paramMap. If a parameter can not be resolved, then it will attempt to be retrieved from a cachemap
    *            stored using the cacheMapKey
    * 
    * @param paramMap
    * @param argNames
    * @param argValues
    * @param cacheMapKey
    */
   @SuppressWarnings({"unchecked"})
   protected void resolveParams(Map paramMap, String[] argNames, String[] argValues, String cacheMapKey) {

	  Object rawCache=getTreeAttribute(cacheMapKey);
	  Map cache=Collections.EMPTY_MAP;
	  if(rawCache instanceof Map) {
		 cache=(Map) rawCache;
	  }
	  for(int i=0; i < argNames.length; i++) {
		 Object result=resolveExpression(argValues[i]);
		 if(result == null) {
			result=cache.get(argNames[i]);
		 }
		 paramMap.put(argNames[i] , result);
	  }
	  putTreeAttribute(cacheMapKey , paramMap);
   }

   /**
    * @generated Returns a full system path for a file path given relative to the web project
    */
   protected static String getRealPath(String relPath) {
	  String path=relPath;
	  try {
		 URL url=FacesContext.getCurrentInstance().getExternalContext().getResource(relPath);
		 if(url != null) {
			path=url.getPath();
		 }
	  } catch (MalformedURLException e) {
		 e.printStackTrace();
	  }
	  return path;
   }

   /**
    * Lista de menssagens de log
    * 
    * @return
    */
   @SuppressWarnings({"unchecked"})
   public List getLogMessages() {
	  if(messages == null) {
		 messages=(List) getSessionScope().get("logMessages");
		 if(messages == null) {
			messages=new ArrayList();
			getSessionScope().put("logMessages" , messages);
		 }
	  }
	  return messages;
   }

   /**
    * 
    */
   protected void logException(Throwable throwable) {
	  StringWriter stringWriter=new StringWriter();
	  PrintWriter printWriter=new PrintWriter(stringWriter);
	  throwable.printStackTrace(printWriter);
	  log(stringWriter.toString());
   }

   /**
    * @generated
    */
   @SuppressWarnings("unchecked")
   protected void log(String message) {
	  getLogMessages().add(0 , message);
	  System.out.println(message);
   }

   /**
    * @param id
    * @param message
    */
   protected void addMessage(String id, String message) {
	  addSessionMessage(id , message);
	  /*
	   * FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(getSeverity(id), message, null));
	   */
   }

   protected void mensageiro(Severity severity, String message) {
	  FacesMessage msg=new FacesMessage(severity, message, "Message");
	  FacesContext.getCurrentInstance().addMessage(null , msg);
   }

   protected void mensageiroFull(Severity severity, String message) {
	  FacesMessage msg=new FacesMessage(severity, message, "Message");
	  FacesContext fc=FacesContext.getCurrentInstance();

	  ExternalContext ec=fc.getExternalContext();
	  Flash flash=ec.getFlash();
	  flash.setKeepMessages(true);
	  fc.addMessage(null , msg);
   }

   protected void mensageiroListagem(Severity severity, String message) {
	  FacesMessage msg=new FacesMessage(severity, "", message);
	  FacesContext.getCurrentInstance().addMessage("forms" , msg);
   }

   /**
    * @param id
    * @param message
    */
   @SuppressWarnings({"unchecked"})
   protected void addSessionMessage(String id, String message) {
	  Map session=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	  List list=null;
	  list=(List<Message>) session.get(UIMessages.MESSAGES_ID);
	  if(list == null) {
		 list=new ArrayList<Message>();
		 session.put(UIMessages.MESSAGES_ID , list);
	  }
	  MessageImpl messageObj=new MessageImpl();
	  messageObj.setMessage(message);
	  messageObj.setSeverity(MessageSeverity.valueOf(id));

	  list.add(messageObj);
   }

   private Severity getSeverity(String id) {
	  Severity severity=null;
	  if(ERROR_MESSAGE.equals(id)) {
		 severity=FacesMessage.SEVERITY_ERROR;
	  }

	  if(INFO_MESSAGE.equals(id)) {
		 severity=FacesMessage.SEVERITY_INFO;
	  }

	  if(WARNING_MESSAGE.equals(id)) {
		 severity=FacesMessage.SEVERITY_WARN;
	  }
	  return severity;
   }

   /**
    * @param id
    * @param message
    */
   protected void addMessage(String id, Exception exception, String message) {
	  StringWriter stringWriter=new StringWriter();

	  PrintWriter printWriter=new PrintWriter(stringWriter);
	  exception.printStackTrace(printWriter);

	  log(message + ": " + stringWriter.getBuffer().toString());

	  FacesContext.getCurrentInstance().addMessage(id , new FacesMessage(getSeverity(id), message, stringWriter.getBuffer().toString()));
   }

   /**
    * @return
    */
   @SuppressWarnings("deprecation")
   public Map getApplicationScope() {
	  if(applicationScope == null) {
		 FacesContext context=FacesContext.getCurrentInstance();
		 applicationScope=(Map) context.getApplication().createValueBinding("#{applicationScope}").getValue(context);
	  }
	  return applicationScope;
   }

   /**
    * @return
    */
   @SuppressWarnings("deprecation")
   public Map getRequestParam() {
	  if(requestParam == null) {
		 FacesContext context=FacesContext.getCurrentInstance();
		 requestParam=(Map) context.getApplication().createValueBinding("#{param}").getValue(context);
	  }
	  return requestParam;
   }

   /**
    * @return
    */
   @SuppressWarnings("deprecation")
   public Map getRequestScope() {
	  FacesContext context=FacesContext.getCurrentInstance();
	  if(context != null) {
		 requestScope=(Map) context.getApplication().createValueBinding("#{requestScope}").getValue(context);
	  }
	  return requestScope;
   }

   /**
    * @return
    */
   @SuppressWarnings("deprecation")
   public Map getSessionScope() {
	  FacesContext context=FacesContext.getCurrentInstance();
	  if(context != null) {
		 sessionScope=(Map) context.getApplication().createValueBinding("#{sessionScope}").getValue(context);
	  }
	  return sessionScope;
   }

   public void setSessionScope(Map sessionScope) {
	  this.sessionScope=sessionScope;
   }

   /**
    * @param usuarioCartorioSessao
    */
   @SuppressWarnings("unchecked")
   public void setUsuarioCartorioSessao(UsuarioCartorio usuarioCartorio) {
	  this.usuarioCartorioSessao=usuarioCartorio;
	  if(usuarioCartorio == null) {
		 getSessionScope().remove("usuarioCartorioSessao");

	  } else {
		 getSessionScope().put("usuarioCartorioSessao" , usuarioCartorioSessao);

	  }
   }

   /**
    * @return
    */
   public UsuarioCartorio getUsuarioCartorioSessao() {
	  if(usuarioCartorioSessao == null || usuarioCartorioSessao.getId() == null) {
		 usuarioCartorioSessao=(UsuarioCartorio) getSessionScope().get(SessionSystemAttibutes.SESSION_PARAMETER_USUARIO_CARTORIO_SESSAO);

		 if(usuarioCartorioSessao == null) {
			UsuarioCartorio usuarioCartorio=new UsuarioCartorio();
			setUsuarioCartorioSessao(usuarioCartorio);
		 }
	  }
	  return usuarioCartorioSessao;
   }
   
   /**
    * @param usuarioCartorioSessao
    */
   @SuppressWarnings("unchecked")
   public void setMsgLoginSessao(String msgLoginSessao) {
	  this.msgLoginSessao=msgLoginSessao;
	  if(msgLoginSessao == null) {
		 getSessionScope().remove("msgLoginSessao");

	  } else {
		 getSessionScope().put("msgLoginSessao" , msgLoginSessao);

	  }
   }

   /**
    * @return
    */
   public String getMsgLoginSessao() {
	  if(msgLoginSessao == null) {
		 msgLoginSessao= (String) getSessionScope().get(SessionSystemAttibutes.MSG_LOGIN_SESSAO);

		 if(msgLoginSessao == null) {
			String setMsgLoginSessao= "";
			setMsgLoginSessao(setMsgLoginSessao);
		 }
	  }
	  return msgLoginSessao;
   }

   /**
    * @param certificadoBeanSessao
    */
   @SuppressWarnings("unchecked")
   public void setCertificadoBeanSessao(CertificadoBean certificadoBean) {
	  this.certificadoBeanSessao=certificadoBean;
	  if(certificadoBean == null) {
		 getSessionScope().remove("certificadoBeanSessao");

	  } else {
		 getSessionScope().put("certificadoBeanSessao" , certificadoBeanSessao);

	  }
   }

   /**
    * @return
    */
   public CertificadoBean getCertificadoSessao() {
	  if(certificadoBeanSessao == null || certificadoBeanSessao.getNome() == null) {
		 certificadoBeanSessao=(CertificadoBean) getSessionScope().get(SessionSystemAttibutes.SESSION_PARAMETER_CERTIFICADO_BEAN_SESSAO);

		 if(certificadoBeanSessao == null) {
			CertificadoBean certificadoBean=new CertificadoBean();
			setCertificadoBeanSessao(certificadoBean);
		 }
	  }
	  return certificadoBeanSessao;
   }

   public Modulo getModuloAtual() {
	  if(moduloAtual == null) {
		 moduloAtual=(Modulo) getSessionScope().get(SessionSystemAttibutes.SESSION_PARAMETER_MODULO_ATUAL_SESSAO);

		 if(moduloAtual == null) {
			moduloAtual=new Modulo();
		 }
	  }
	  return moduloAtual;
   }

   public PermissaoAcesso getPermissaoAcessoAtual() {
	  if(permissoesAcessoAtual == null) {
		 permissoesAcessoAtual=(PermissaoAcesso) getSessionScope().get(SessionSystemAttibutes.SESSION_PARAMETER_PERMISSAO_ACESSO_ATUAL);

		 if(permissoesAcessoAtual == null) {
			permissoesAcessoAtual=new PermissaoAcesso();
		 }
	  }
	  return permissoesAcessoAtual;
   }

   public Configuracao getConfiguracao() {
	  if(configuracao == null) {
		 configuracao=Configuracao.getInstancia();
	  }
	  return configuracao;
   }

   public ServletContext getServletContext() {
	  if(servletContext == null) {
		 servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	  }
	  return servletContext;
   }

   public void setServletContext(ServletContext servletContext) {
	  this.servletContext=servletContext;
   }

   /**
    * 
    * @return
    * 
    */
   @SuppressWarnings("unchecked")
   public void abrirRelatorio(String nome, Relatorio relatorio) {

	  RelatorioBuilder relatorioBuilder=RelatorioBuilder.getInstancia();
	  relatorioBuilder.printTo(relatorio , true);

	  getServletContext().setAttribute(RelatorioServlet.ATTRIBUTE_RELATORIO , relatorio);

	  getSessionScope().put(RelatorioServlet.ATTRIBUTE_RELATORIO , relatorio);

	  getApplicationScope().put(nome , Boolean.TRUE);
   }

}
