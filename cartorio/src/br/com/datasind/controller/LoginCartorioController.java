
package br.com.datasind.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.certisign.CertificadoBean;
import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.gerente.SenhaInvalidaException;
import br.com.datasind.gerente.UsuarioInativoException;
import br.com.datasind.gerente.UsuarioNaoEncontradoException;
import br.com.datasind.util.Utilitaria;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="loginCartorio")
@SessionScoped
public class LoginCartorioController extends CadastroControler{
   /**
    * 
    */
   private static final long serialVersionUID=3770067184721142046L;
   private UsuarioCartorioHolder usuarioCartorioH;
   private UsuarioCartorio usuarioCartorioTemp;

   public UsuarioCartorioHolder getUsuarioCartorio() {
	  if(usuarioCartorioH == null) {
		 usuarioCartorioH=new UsuarioCartorioHolder();
	  }
	  return usuarioCartorioH;
   }

   public LoginCartorioController() {
	  super();
   }

   @PostConstruct
   public String verificaMensagensSession() {
	  if(getMsgLoginSessao() != null && !getMsgLoginSessao().equals("")) {
		 mensageiroFull(ERROR , getMsgLoginSessao());
		 System.out.println("######  " + getMsgLoginSessao());
		 setMsgLoginSessao("");
		 return "";
	  } else {
		 System.out.println("######  " + getMsgLoginSessao());
		 return "";
	  }
   }

   public String validacaoCertificado() {
	  FacesContext fc=FacesContext.getCurrentInstance();
	  ExternalContext ec=fc.getExternalContext();

	  try {
		 if(getUsuarioCartorioSessao().getId() == null) {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			CertificadoBean certificado=(CertificadoBean) getCertificadoSessao();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			if(certificado.getCpf() != null) {
			   Date validadeCertificado=sdf.parse(certificado.getValidade().replace("-" , "/"));
			   String cpfFormatado=Utilitaria.formataCpf(certificado.getCpf());
			   usuarioCartorioTemp=gerenteControleAcesso.obterUsuarioCartorioPorCpfCert(cpfFormatado);
			   if(usuarioCartorioTemp.getId() != null) {
				  if(usuarioCartorioTemp.getFlagAtivo() == false) {
					 setMsgLoginSessao("O usuário não esta ativo!");
					 ec.redirect(ec.getRequestContextPath() + "/login.jsf");
				  } else {
					 if(usuarioCartorioTemp.getValidadeCertificado() == null) {
						usuarioCartorioTemp.setValidadeCertificado(validadeCertificado);
						gerenteControleAcesso.atualizarEntidade(usuarioCartorioTemp);
					 }

					 if( !certificado.getValidade().equals(usuarioCartorioTemp.getValidadeCertificado()) && validadeCertificado.after(usuarioCartorioTemp.getValidadeCertificado())) {
						usuarioCartorioTemp.setValidadeCertificado(validadeCertificado);
						gerenteControleAcesso.atualizarEntidade(usuarioCartorioTemp);
					 }

					 setUsuarioCartorioSessao(usuarioCartorioTemp);
					 if( !getUsuarioCartorioSessao().entidadeTransitente()) {
						gerenteControleAcesso.inicializa("inicializadorLoginCartorio" , getUsuarioCartorioSessao());
					 }
					 if(new Date().after(usuarioCartorioTemp.getValidadeCertificado())) {
						setMsgLoginSessao("Certificado Expirado!");
						ec.redirect(ec.getRequestContextPath() + "/login.jsf");
					 } else {
						FacesContext.getCurrentInstance().getExternalContext().redirect(ec.getRequestContextPath() + "/portal/portal.jsf");
					 }
				  }
			   } else {
				  setMsgLoginSessao("Usuário não cadastrado!");
				  ec.redirect(ec.getRequestContextPath() + "/login.jsf");
			   }
			} else {
			   setMsgLoginSessao("Erro ao capturar dados do Certificado!");
			   ec.redirect(ec.getRequestContextPath() + "/login.jsf");
			}
		 }
	  } catch (IOException | ApplicationException | ValidacaoException | ParseException e) {
		 e.printStackTrace();
	  }
	  return "portal";
   }

   public String loginAction() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 if(getUsuarioCartorio().getNomeLogin().isEmpty() || getUsuarioCartorio().getSenha().isEmpty()) {
			if(getUsuarioCartorio().getNomeLogin().isEmpty()) {
			   mensageiro(ERROR , "Informe o nome de LOGIN!");
			}
			if(getUsuarioCartorio().getSenha().isEmpty()) {
			   mensageiro(ERROR , "Informe uma SENHA!");
			}
			return CURRENT_PAGE;
		 }
		 // Verifica se o usuario esta cadastrado e ativo, depois se a senha
		 // esta correta
		 // e se o usuario ja possui outra sessao aberta.

		 LOGGER.debug("Verificando senha do usuario");
		 UsuarioCartorio usuarioCartorioT=gerenteControleAcesso.verificarUsuarioCartorio(getUsuarioCartorio());

		 setUsuarioCartorioSessao(usuarioCartorioT);
		 if( !getUsuarioCartorioSessao().entidadeTransitente()) {
			gerenteControleAcesso.inicializa("inicializadorLoginCartorio" , getUsuarioCartorioSessao());
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
		 ExternalContext ec=fc.getExternalContext();
		 setUsuarioCartorioSessao(null);
		 setCertificadoBeanSessao(null);
		 HttpSession session=(HttpSession) ec.getSession(false);
		 session.invalidate();
		 mensageiroFull(ERROR , "Sessão encerrada.");

		 ec.redirect(ec.getRequestContextPath() + "/login.jsf");
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
	  return "portal";
   }

   public class UsuarioCartorioHolder extends UsuarioCartorio{
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
