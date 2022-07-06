
package br.com.datasind.controller.cad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.conversao.util.Codificador;
import br.com.datasind.dataModel.UsuarioSiteDataModel;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="cadastroUsuarioSite")
@ViewScoped
public class CadastroUsuarioSiteController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;
   private UsuarioSite usuarioSite;
   private List<UsuarioSite> listaUsuariosSite;
   private UsuarioSite usuarioSiteSelecionado;
   private UsuarioSiteDataModel usuarioSiteDM;
   private String UID;
   private Integer tipoRequisicao;// 1 - Ativar email, 2 - Solicitar nova senha
   private String validaEmail;

   public void loadModel() {
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 if(getUID() == null && getTipoRequisicao() == null) {

			ec.redirect(ec.getRequestContextPath() + "/login.jsf");

		 } else if(getUID() != null && getTipoRequisicao() == null) {
			context.execute("PF('dlg2').show();");
		 } else if(getUID() != null && getTipoRequisicao() == 1) {
			validaContaEmail(getUID());
		 } else if(getUID() != null && getTipoRequisicao() == 2) {
			ec.redirect(ec.getRequestContextPath() + "/test/usuarioSiteSenha.jsf?uid=" + getUID());
		 }
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
   }

   public void solicitaNovaSenha(ActionEvent event) {
	  GerenteControleAcesso gCAcesso=getFabricaGerente().getGerenteControleAcesso();
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
	  RequestContext context=RequestContext.getCurrentInstance();
	  HttpServletRequest request=(HttpServletRequest) ec.getRequest();
	  UsuarioSite usutemp=new UsuarioSite();

	  try {
		 if( !getUsuarioSite().getEmail().equals(getValidaEmail())) {
			mensageiro(WARNING , "Os e-mails não são iguais! Tente novamente.");
			return;
		 }
		 usutemp=gCAcesso.obterUsuarioSitePorEmail(getUsuarioSite().getEmail());
		 if((usutemp == null)) {
			mensageiro(WARNING , "Email não encontrado!");
			return;
		 } else {
			String url=request.getRequestURL().toString().replace("/login.jsf" , "") + "/test/usuarioSite.jsf";
			String mensagem="<html><h2>Redefinição de senha solicitada.</h2><br/>" + "Foi solicitado a redefinição de senha em nosso site, caso "
			   + "você não tenha solicitado, desconsidere essa mensagem.<br/><br/>" + "<h3><a href =\"" + url + "?t=2&uid=" + usutemp.getUID()
			   + "\">Click aqui para redefinir sua senha.</a></h3><br/><br/>" + gCAcesso.obterMensagemAvisoEmail() + "</html>";
			String destino=usutemp.getEmail();
			String titulo="ARPENTO: Redefinição de Senha.";
			acaoLimpar();
			gCAcesso.enviarEmailStatico(titulo , mensagem , destino);
			context.execute("PF('dlg2').hide();");
			mensageiro(INFO , "Solicitação encaminhada!");
			return;
		 }
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }

   }

   public void validaContaEmail(String uid) {

	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  UsuarioSite usutemp=new UsuarioSite();
	  try {
		 FacesContext fc=FacesContext.getCurrentInstance();
		 Flash flash=fc.getExternalContext().getFlash();
		 flash.setKeepMessages(true);
		 flash.setRedirect(true);
		 ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
		 usutemp=gerenteControleAcesso.obterUsuarioSitePorUID(uid);

		 if((usutemp.getNome().length() > 0)) {
			usutemp.setValidaEmail(true);
			gerenteControleAcesso.atualizarEntidade(usutemp);
			fc.addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO, "Sua conta foi ativada!", null));
			ec.redirect(ec.getRequestContextPath() + "/login.jsf");
		 } else {
			fc.addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao validar a sua conta!", null));
			ec.redirect(ec.getRequestContextPath() + "/login.jsf");
		 }
	  } catch (ApplicationException | ValidacaoException | IOException e) {
		 e.printStackTrace();
	  }
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gCAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {

		 if( !getUsuarioSite().getEmail().equals(getValidaEmail())) {
			mensageiro(WARNING , "Os e-mails não são iguais! Tente novamente.");
			return;
		 }

		 if((gCAcesso.obterUsuariosSitePorEmail(getUsuarioSite().getEmail()).size() > 0)) {
			mensageiro(WARNING , "Esse e-mail já foi cadastrado.");
			return;
		 }

		 getUsuarioSite().setSenha(Codificador.codificaSenhaMD5(getUsuarioSite().getSenha()));
		 getUsuarioSite().setValidaEmail(true);
		 getUsuarioSite().setPerfilAtivo(true);
		 getUsuarioSite().setDataCadastro(new Date());
		 getUsuarioSite().setUID(Codificador.geraUID(20));// gera id especifica do usuario tamanho 20
		 gCAcesso.incluirEntidade(getUsuarioSite());

		 mensageiro(INFO , "Usuario " + getUsuarioSite().getNome() + " cadastrado com sucesso!");
		 context.execute("PF('dlg1').hide();");
		 acaoLimpar();
		 setUsuarioSite(null);
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel cadastrar o usuario.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(ERROR , e.getMessage());
		 LOGGER.equals(e);
		 return;
	  }
   }

   public void acaoAlterar(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

	  try {
		 UsuarioSite usutemp=new UsuarioSite();
		 usutemp=(gerenteControleAcesso.obterUsuarioSitePorId(usuarioSite));
		 getUsuarioSite().setSenha(usutemp.getSenha());
		 getUsuarioSite().setDataUltimoLogin(usutemp.getDataUltimoLogin());

		 gerenteControleAcesso.atualizarEntidade(getUsuarioSite());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel atualizar o cadastro do usuario.");
		 LOGGER.error(e);
		 acaoLimpar();
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(WARNING , e.getMessage().toString());
		 LOGGER.error(e);
		 acaoLimpar();
		 return;
	  }

	  mensageiro(INFO , "Usuario alterado com sucesso.");
   }

   public void salvarNovaSenha(ActionEvent event) {
	  GerenteControleAcesso gCAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 FacesContext fc=FacesContext.getCurrentInstance();
		 Flash flash=fc.getExternalContext().getFlash();
		 flash.setKeepMessages(true);
		 flash.setRedirect(true);

		 if(getUID() == null) {
			setUID(getUsuarioSiteSessao().getUID());
		 }

		 ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
		 HttpServletRequest request=(HttpServletRequest) ec.getRequest();
		 UsuarioSite usutemp=new UsuarioSite();
		 usutemp=(gCAcesso.obterUsuarioSitePorUID(getUID()));
		 usutemp.setSenha(Codificador.codificaSenhaMD5(getUsuarioSite().getSenha()));
		 setUsuarioSite(usutemp);

		 gCAcesso.atualizarEntidade(usutemp);

		 String url=request.getRequestURL().toString().replace("/login.jsf" , "") + "/login.jsf";
		 String mensagem="<html><h2>Senha alterada com sucesso!</h2><h3><a href =\"" + url + "\">Click aqui para acessar o sistema.</a></h3><br/><br/>" + gCAcesso.obterMensagemAvisoEmail()
			+ "</html>";
		 String destino=getUsuarioSite().getEmail();
		 String titulo="ARPENTO: Senha alterada.";

		 gCAcesso.enviarEmailStatico(titulo , mensagem , destino);

		 fc.addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha alterada com sucesso!", null));
		 acaoLimpar();
		 ec.redirect(ec.getRequestContextPath() + "/login.jsf");
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel atualizar a senha do usuario.");
		 LOGGER.error(e);
		 acaoLimpar();
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(WARNING , e.getMessage().toString());
		 LOGGER.error(e);
		 acaoLimpar();
		 return;
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
   }

   public void acaoExcluir(ActionEvent event) {

	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

	  try {
		 gerenteControleAcesso.excluirEntidade(getUsuarioSite());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel excluir usuario.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Usuario excluido com sucesso.");
   }

   public void acaoLimpar() {

	  setUsuarioSite(null);
	  setListaUsuariosSite(null);
	  setUsuarioSiteDM(null);
	  setValidaEmail(null);
   }

   public void onRowSelect(SelectEvent event) {

	  setUsuarioSite((UsuarioSite) event.getObject());
	  mensageiro(WARNING , "Usuario " + ((UsuarioSite) event.getObject()).getNome());

   }

   public UsuarioSite getUsuarioSite() {
	  if(usuarioSite == null) {
		 usuarioSite=new UsuarioSite();
		 usuarioSite.setFlagAtivo(Boolean.TRUE);
	  }
	  return usuarioSite;
   }

   public void setUsuarioSite(UsuarioSite usuarioSite) {
	  this.usuarioSite=usuarioSite;
   }

   public List<UsuarioSite> getListaUsuariosSite() {
	  if(listaUsuariosSite == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			listaUsuariosSite=new ArrayList<UsuarioSite>();

			listaUsuariosSite=gerenteControleAcesso.obterListaUsuariosSite();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Usuarios");
		 }
	  }
	  return listaUsuariosSite;
   }

   public void setListaUsuariosSite(List<UsuarioSite> listaUsuariosSite) {
	  this.listaUsuariosSite=listaUsuariosSite;
   }

   public UsuarioSite getUsuarioSiteSelecionado() {
	  if(usuarioSiteSelecionado == null) {
		 usuarioSiteSelecionado=new UsuarioSite();
	  }
	  return usuarioSiteSelecionado;
   }

   public void setusuarioSiteSelecionado(UsuarioSite usuarioSiteSelecionado) {
	  this.usuarioSiteSelecionado=usuarioSiteSelecionado;
   }

   public UsuarioSiteDataModel getUsuarioSiteDM() {
	  if(usuarioSiteDM == null) {
		 usuarioSiteDM=new UsuarioSiteDataModel(getListaUsuariosSite());
	  }
	  return usuarioSiteDM;
   }

   public void setUsuarioSiteDM(UsuarioSiteDataModel usuarioSiteDM) {
	  this.usuarioSiteDM=usuarioSiteDM;
   }

   public void adicionar(ActionEvent event) {
	  System.out.println(usuarioSite.getNome());
   }

   public String getUID() {
	  return UID;
   }

   public void setUID(String UID) {
	  this.UID=UID;
   }

   public Integer getTipoRequisicao() {
	  return tipoRequisicao;
   }

   public void setTipoRequisicao(Integer tipoRequisicao) {
	  this.tipoRequisicao=tipoRequisicao;
   }

   public String getValidaEmail() {
	  return validaEmail;
   }

   public void setValidaEmail(String validaEmail) {
	  this.validaEmail=validaEmail;
   }

}
