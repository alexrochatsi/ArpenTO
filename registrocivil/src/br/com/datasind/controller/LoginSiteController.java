
package br.com.datasind.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.gerente.EmailInativoException;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.gerente.SenhaInvalidaException;
import br.com.datasind.gerente.UsuarioInativoException;
import br.com.datasind.gerente.UsuarioNaoEncontradoException;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="loginSite")
@SessionScoped
public class LoginSiteController extends CadastroControler{
   /**
    * 
    */
   private static final long serialVersionUID=3770067184721142046L;

   private UsuarioSiteHolder usuarioSiteH;

   public UsuarioSiteHolder getUsuarioSite() {
	  if(usuarioSiteH == null) {
		 usuarioSiteH=new UsuarioSiteHolder();
	  }
	  return usuarioSiteH;
   }

   public void loadModel() {
	  
   }

   public LoginSiteController() {
	  super();
   }

   public String loginAction() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 if(getUsuarioSite().getEmail() == null || getUsuarioSite().getSenha() == null) {
			mensageiro(ERROR , "Preencha todos os campos!");
			return CURRENT_PAGE;
		 }

		 LOGGER.debug("Verificando senha do usuario");
		 UsuarioSite usuarioSiteT=gerenteControleAcesso.verificarUsuarioSite(getUsuarioSite());

		 setUsuarioSiteSessao(usuarioSiteT);
		 if( !getUsuarioSiteSessao().entidadeTransitente()) {
			gerenteControleAcesso.inicializa("inicializadorLoginSite" , getUsuarioSiteSessao());
		 }

	  } catch (ValidacaoException e) {
		 mensageiro(WARNING , e.getMessage());
		 LOGGER.error(e);
		 return CURRENT_PAGE;

	  } catch (EmailInativoException e) {
		 mensageiro(WARNING , "Email inativo, verifique sua caixa de entrada.");
		 LOGGER.error(e);
		 return CURRENT_PAGE;

	  } catch (UsuarioNaoEncontradoException e) {
		 mensageiro(ERROR , "Login ou Senha incorretos.");
		 return CURRENT_PAGE;

	  } catch (UsuarioInativoException e) {
		 mensageiro(ERROR , "Usuario inativo.");
		 return CURRENT_PAGE;

	  } catch (SenhaInvalidaException e) {
		 mensageiro(ERROR , "Login ou Senha incorretos.");
		 return CURRENT_PAGE;
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Problemas ao efetuar o login.");
		 e.printStackTrace();
		 return CURRENT_PAGE;

	  }
	  return "portal";
   }

   public String logout() {
	  try {
		 FacesContext fc=FacesContext.getCurrentInstance();
		 Flash flash=fc.getExternalContext().getFlash();
		 flash.setKeepMessages(true);
		 flash.setRedirect(true);
		 ExternalContext ec=fc.getExternalContext();
		 setUsuarioSiteSessao(null);
		 mensageiro(ERROR , "Sessão encerrada.");

		 ec.redirect(ec.getRequestContextPath() + "/login.jsf");
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
	  return "portal";
   }

   public class UsuarioSiteHolder extends UsuarioSite{
	  private static final long serialVersionUID=1L;

	  private String email;

	  @Override
	  public String getEmail() {
		 return email;
	  }

	  public void setEmail(String email) {
		 this.email=email;
	  }

   }

}
