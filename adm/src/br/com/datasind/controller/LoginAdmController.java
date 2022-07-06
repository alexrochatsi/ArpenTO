
package br.com.datasind.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.UsuarioAdministrador;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.gerente.SenhaInvalidaException;
import br.com.datasind.gerente.UsuarioInativoException;
import br.com.datasind.gerente.UsuarioNaoEncontradoException;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="loginAdministrador")
@SessionScoped
public class LoginAdmController extends CadastroControler{
   /**
    * 
    */
   private static final long serialVersionUID=3770067184721142046L;

   private UsuarioAdministradorHolder usuarioAdministradorH;

   public UsuarioAdministradorHolder getUsuarioAdministrador() {
	  if(usuarioAdministradorH == null) {
		 usuarioAdministradorH=new UsuarioAdministradorHolder();
	  }
	  return usuarioAdministradorH;
   }

   public LoginAdmController() {
	  super();
	  // atualiza();
   }

   public String loginAction() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 if(getUsuarioAdministrador().getNomeLogin() == null || getUsuarioAdministrador().getSenha() == null) {
			mensageiro(ERROR , "Preencha todos os campos!");
			return CURRENT_PAGE;
		 }
		// Verifica se o usuario esta cadastrado e ativo, depois se a senha
				 // esta correta
				 // e se o usuario ja possui outra sessao aberta.
				 
		 LOGGER.debug("Verificando senha do usuario");
		 UsuarioAdministrador usuarioAdministradorT=gerenteControleAcesso.verificarUsuarioAdministrador(getUsuarioAdministrador());

		 setUsuarioAdministradorSessao(usuarioAdministradorT);
		 if( !getUsuarioAdministradorSessao().entidadeTransitente()) {
			gerenteControleAcesso.inicializa("inicializadorLoginAdministrador" , getUsuarioAdministradorSessao());
		 }

	  } catch (ValidacaoException e) {
		 mensageiro(WARNING , e.getMessage());
		 LOGGER.error(e);
		 return CURRENT_PAGE;

	  } catch (UsuarioNaoEncontradoException e) {
		 mensageiro(ERROR , "Usuario ou senha inválidos.");
		 return CURRENT_PAGE;

	  } catch (UsuarioInativoException e) {
		 mensageiro(ERROR , "Usuario inativo.");
		 return CURRENT_PAGE;

	  } catch (SenhaInvalidaException e) {
		 mensageiro(ERROR , "Senha incorreta.");
		 return CURRENT_PAGE;
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Problemas ao verificar usuario.");
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
		 ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
		 setUsuarioAdministradorSessao(null);
		 mensageiro(ERROR , "Sessão encerrada.");

		 ec.redirect(ec.getRequestContextPath() + "/login.jsf");
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
	  return "portal";
   }

   public class UsuarioAdministradorHolder extends UsuarioAdministrador{
	  private static final long serialVersionUID=1L;

	  private String nomeLogin;

	  @Override
	  public String getNomeLogin() {
		 return nomeLogin;
	  }

	  public void setNomeLogin(String nomeLogin) {
		 this.nomeLogin=nomeLogin;
	  }

   }

}
