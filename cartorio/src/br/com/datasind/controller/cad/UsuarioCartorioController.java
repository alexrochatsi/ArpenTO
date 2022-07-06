
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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.conversao.util.Codificador;
import br.com.datasind.dataModel.UsuarioCartorioDataModel;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.util.Utilitaria;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="cadastroUsuarioCartorio")
@ViewScoped
public class UsuarioCartorioController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;
   private UsuarioCartorio usuarioCartorio;
   private List<UsuarioCartorio> listaUsuariosCartorio;
   private UsuarioCartorio usuarioCartorioSelecionado;
   private UsuarioCartorioDataModel usuarioCartorioDM;
   private List<Cartorio> cartorios=null;
   private String UID;
   private Integer tipoRequisicao;// 1 - Ativar email, 2 - Solicitar nova senha
   private String validaEmail;
   private Boolean renderizaCartoriosLogin=false;

   public void loadModel() {
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
	  if(getUID() != null && getTipoRequisicao() == 1) {
		 validaContaEmail(getUID());
	  }
	  if(getUID() != null && getTipoRequisicao() == 2) {
		 try {
			ec.redirect(ec.getRequestContextPath() + "/test/usuarioCartorioSenha.jsf?uid=" + getUID());
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	  }
   }

   public void solicitaNovaSenha(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  UsuarioCartorio usutemp=new UsuarioCartorio();

	  try {
		 if( !getUsuarioCartorio().getEmail().equals(getValidaEmail())) {
			mensageiro(WARNING , "Os e-mails não são iguais! Tente novamente.");
			return;
		 }
		 usutemp=gerenteControleAcesso.obterUsuarioCartorioPorEmail(getUsuarioCartorio().getEmail());
		 if((usutemp == null)) {
			mensageiro(WARNING , "Email não encontrado!");
			return;
		 } else {
			String url="http://localhost:8080/cartorio/test/usuarioCartorio.jsf";
			String mensagem="<html><h2>Redefinição de senha solicitada.</h2><br/>" + "Foi solicitado a redefinição de senha em nossa base, caso "
			   + "você não tenha solicitado, desconsidere essa mensagem.<br/><br/>" + "<h3><a href =\"" + url + "?t=2&uid=" + usutemp.getUID()
			   + "\">Click aqui para redefinir sua senha.</a></h3><br/><br/>" + "Att;<br/>TECNOTINS</html>";
			String destino=usutemp.getEmail();
			String titulo="ARPEN-TO: Redefinição de Senha.";
			acaoLimpar();
			gerenteControleAcesso.enviarEmailStatico(titulo , mensagem , destino);
			mensageiro(INFO , "Solicitação encaminhada!");
			return;
		 }
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }

   }

   public void validaContaEmail(String uid) {

	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  UsuarioCartorio usutemp=new UsuarioCartorio();
	  try {
		 FacesContext fc=FacesContext.getCurrentInstance();
		 Flash flash=fc.getExternalContext().getFlash();
		 flash.setKeepMessages(true);
		 flash.setRedirect(true);
		 ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
		 usutemp=gerenteControleAcesso.obterUsuarioCartorioPorUID(uid);

		 if((usutemp.getNome().length() > 0)) {
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

   public String renderizarCartorios(ActionEvent event) {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setRenderizaCartoriosLogin(true);
	  context.execute("PF('dlgCadastro').show()");
	  context.update("formCadastro");
	  return CURRENT_PAGE;
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {

		 if((gerenteControleAcesso.obterUsuariosCartorioPorLogin(getUsuarioCartorio().getNomeLogin()).size() > 0)) {
			mensageiro(WARNING , "Esse usuário já existe, tente novamente.");
			return;
		 }
		 String cpf=getUsuarioCartorio().getCpf().replaceAll("[./,-]" , "");
		 if( !Utilitaria.checaCPF(cpf)) {
			mensageiro(ERROR , "O CPF informado não é válido.");
			return;
		 }

		 getUsuarioCartorio().setSenha(Codificador.codificaSenhaMD5(getUsuarioCartorio().getSenha()));
		 getUsuarioCartorio().setDataCadastro(new Date());
		 getUsuarioCartorio().setFlagAtivo(false);
		 getUsuarioCartorio().setUID(Codificador.geraUID(20));// gera id especifica do usuario tamanho 20
		 gerenteControleAcesso.incluirEntidade(getUsuarioCartorio());

		 mensageiro(INFO , "Usuario " + getUsuarioCartorio().getNome() + " cadastrado com sucesso! Aguarde a aprovação do Administrador.");
		 context.execute("PF('dlgNovoUsuario').hide()");
		 acaoLimpar();
		 setUsuarioCartorio(null);
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
		 UsuarioCartorio usutemp=new UsuarioCartorio();
		 usutemp=(gerenteControleAcesso.obterUsuarioCartorioPorId(usuarioCartorio));
		 getUsuarioCartorio().setSenha(usutemp.getSenha());
		 getUsuarioCartorio().setDataUltimoLogin(usutemp.getDataUltimoLogin());

		 gerenteControleAcesso.atualizarEntidade(getUsuarioCartorio());
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

   public void validaUsuarioCartorio() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();

	  try {
		 if((gerenteControleAcesso.obterUsuariosCartorioPorLogin(getUsuarioCartorio().getNomeLogin()).size() > 0)) {
			mensageiro(WARNING , "Nome de usuário já existe, tente novamente.");
			getUsuarioCartorio().setNomeLogin("");
			context.update("dialogCadastroUsuarioCartorio");
			context.execute("PF('dlg1').show();");
			return;
		 } else {
			mensageiro(INFO , "O nome de usuário pode ser usado.");
		 }
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }
   }

   public void salvarNovaSenha(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 FacesContext fc=FacesContext.getCurrentInstance();
		 Flash flash=fc.getExternalContext().getFlash();
		 flash.setKeepMessages(true);
		 flash.setRedirect(true);

		 if(getUID() == null) {
			setUID(getUsuarioCartorioSessao().getUID());
		 }

		 ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
		 UsuarioCartorio usutemp=new UsuarioCartorio();
		 usutemp=(gerenteControleAcesso.obterUsuarioCartorioPorUID(getUID()));
		 usutemp.setSenha(Codificador.codificaSenhaMD5(getUsuarioCartorio().getSenha()));
		 setUsuarioCartorio(usutemp);

		 gerenteControleAcesso.atualizarEntidade(usutemp);

		 String url="http://localhost:8080/cartorio/login.jsf";
		 String mensagem="<html><h2>Senha alterada com sucesso!</h2><h3><a href =\"" + url + "\">Click aqui para acessar o sistema.</a></h3><br/><br/>" + "Att;<br/>TECNOTINS</html>";
		 String destino=getUsuarioCartorio().getEmail();
		 String titulo="ARPEN-TO: Senha alterada.";

		 gerenteControleAcesso.enviarEmailStatico(titulo , mensagem , destino);

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
		 gerenteControleAcesso.excluirEntidade(getUsuarioCartorio());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel excluir usuario.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Usuario excluido com sucesso.");
   }

   public void acaoLimpar() {

	  setUsuarioCartorio(null);
	  setListaUsuariosCartorio(null);
	  setUsuarioCartorioDM(null);
	  setValidaEmail(null);
   }

   public void onRowSelect(SelectEvent event) {

	  setUsuarioCartorio((UsuarioCartorio) event.getObject());
	  mensageiro(WARNING , "Usuario " + ((UsuarioCartorio) event.getObject()).getNome());

   }

   public UsuarioCartorio getUsuarioCartorio() {
	  if(usuarioCartorio == null) {
		 usuarioCartorio=new UsuarioCartorio();
		 usuarioCartorio.setFlagAtivo(Boolean.TRUE);
	  }
	  return usuarioCartorio;
   }

   public void setUsuarioCartorio(UsuarioCartorio usuarioCartorio) {
	  this.usuarioCartorio=usuarioCartorio;
   }

   public List<UsuarioCartorio> getListaUsuariosCartorio() {
	  if(listaUsuariosCartorio == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			listaUsuariosCartorio=new ArrayList<UsuarioCartorio>();

			listaUsuariosCartorio=gerenteControleAcesso.obterListaUsuariosCartorio();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Usuarios");
		 }
	  }
	  return listaUsuariosCartorio;
   }

   public void setListaUsuariosCartorio(List<UsuarioCartorio> listaUsuariosCartorio) {
	  this.listaUsuariosCartorio=listaUsuariosCartorio;
   }

   public UsuarioCartorio getUsuarioCartorioSelecionado() {
	  if(usuarioCartorioSelecionado == null) {
		 usuarioCartorioSelecionado=new UsuarioCartorio();
	  }
	  return usuarioCartorioSelecionado;
   }

   public void setusuarioCartorioSelecionado(UsuarioCartorio usuarioCartorioSelecionado) {
	  this.usuarioCartorioSelecionado=usuarioCartorioSelecionado;
   }

   public UsuarioCartorioDataModel getUsuarioCartorioDM() {
	  if(usuarioCartorioDM == null) {
		 usuarioCartorioDM=new UsuarioCartorioDataModel(getListaUsuariosCartorio());
	  }
	  return usuarioCartorioDM;
   }

   public void setUsuarioCartorioDM(UsuarioCartorioDataModel usuarioCartorioDM) {
	  this.usuarioCartorioDM=usuarioCartorioDM;
   }

   public void adicionar(ActionEvent event) {
	  System.out.println(usuarioCartorio.getNome());
   }

   public List<Cartorio> getCartorios() {
	  if(cartorios == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			cartorios=new ArrayList<Cartorio>();
			cartorios=gerenteControleAcesso.obterCartorios();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Cartórios");
		 }
	  }
	  return cartorios;
   }

   public void setCartorios(List<Cartorio> cartorios) {
	  this.cartorios=cartorios;
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

   public Boolean getRenderizaCartoriosLogin() {
	  return renderizaCartoriosLogin;
   }

   public void setRenderizaCartoriosLogin(Boolean renderizaCartoriosLogin) {
	  this.renderizaCartoriosLogin=renderizaCartoriosLogin;
   }
}
