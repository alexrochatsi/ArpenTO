
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

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.conversao.util.Codificador;
import br.com.datasind.dataModel.UsuarioAdministradorDataModel;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.UsuarioAdministrador;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="usuarioAdministrador")
@ViewScoped
public class UsuarioAdministradorController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;
   private UsuarioAdministrador usuarioAdministrador;
   private List<UsuarioAdministrador> listaUsuariosAdministrador;
   private UsuarioAdministrador usuarioAdministradorSelecionado;
   private UsuarioAdministradorDataModel usuarioAdministradorDM;
   private List<Cartorio> cartorios=null;
   private String UID;
   private Integer tipoRequisicao;// 1 - Ativar email, 2 - Solicitar nova senha
   private String validaEmail;

   public void loadModel() {
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
	  if(getUID() != null && getTipoRequisicao() == 2) {
		 try {
			ec.redirect(ec.getRequestContextPath() + "/test/usuarioAdministradorSenha.jsf?uid=" + getUID());
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	  }
   }

   public void acaoAlterar(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 UsuarioAdministrador usutemp=new UsuarioAdministrador();
		 usutemp=(gerenteControleAcesso.obterUsuarioAdministradorPorId(usuarioAdministrador));
		 getUsuarioAdministrador().setSenha(usutemp.getSenha());
		 getUsuarioAdministrador().setDataUltimoLogin(usutemp.getDataUltimoLogin());

		 gerenteControleAcesso.atualizarEntidade(getUsuarioAdministrador());

		 context.execute("PF('dlgStatus').hide()");
		 context.update("formCadastro");
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

   public void acaoExcluir(ActionEvent event) {

	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

	  try {
		 gerenteControleAcesso.excluirEntidade(getUsuarioAdministrador());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel excluir usuario.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Usuario excluido com sucesso.");
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {

		 if((gerenteControleAcesso.obterUsuariosAdministradorPorLogin(getUsuarioAdministrador().getNomeLogin()).size() > 0)) {
			mensageiro(WARNING , "Esse usu??rio j?? existe, tente novamente.");
			return;
		 }

		 getUsuarioAdministrador().setSenha(Codificador.codificaSenhaMD5("tecnotins"));
		 getUsuarioAdministrador().setDataCadastro(new Date());
		 getUsuarioAdministrador().setFlagAtivo(false);
		 getUsuarioAdministrador().setUID(Codificador.geraUID(20));// gera id especifica do usuario tamanho 20
		 gerenteControleAcesso.incluirEntidade(getUsuarioAdministrador());

		 mensageiro(INFO , "Usuario " + getUsuarioAdministrador().getNome() + " cadastrado com sucesso! Aguarde a aprova????o do Administrador.");
		 context.execute("PF('dlgNovoUsuario').hide()");
		 acaoLimpar();
		 setUsuarioAdministrador(null);
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

   public void validaUsuarioAdministrador() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();

	  try {
		 if((gerenteControleAcesso.obterUsuariosAdministradorPorLogin(getUsuarioAdministrador().getNomeLogin()).size() > 0)) {
			mensageiro(WARNING , "Nome de usu??rio j?? existe, tente novamente.");
			getUsuarioAdministrador().setNomeLogin("");
			context.update("formCadastroUsuario");
			return;
		 } else {
			mensageiro(INFO , "O nome de usu??rio pode ser usado.");
		 }
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }
   }

   public void solicitaNovaSenha(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  UsuarioAdministrador usutemp=new UsuarioAdministrador();
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();

	  try {

		 usutemp=gerenteControleAcesso.obterUsuarioAdministradorPorNomeLogin(getUsuarioAdministrador().getNomeLogin());
		 if((usutemp == null)) {
			mensageiro(WARNING , "Usu??rio n??o encontrado!");
			return;
		 } else {
			ec.redirect(ec.getRequestContextPath() + "/test/usuarioAdministrador.jsf?t=2&uid=" + usutemp.getUID());
			acaoLimpar();
			return;
		 }
	  } catch (ApplicationException | ValidacaoException | IOException e) {
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
			setUID(getUsuarioAdministradorSessao().getUID());
		 }

		 ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
		 UsuarioAdministrador usutemp=new UsuarioAdministrador();
		 usutemp=(gerenteControleAcesso.obterUsuarioAdministradorPorUID(getUID()));
		 usutemp.setSenha(Codificador.codificaSenhaMD5(getUsuarioAdministrador().getSenha()));
		 setUsuarioAdministrador(usutemp);

		 gerenteControleAcesso.atualizarEntidade(usutemp);

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

   public void acaoLimpar() {
	  setUsuarioAdministrador(null);
	  setusuarioAdministradorSelecionado(null);
	  setListaUsuariosAdministrador(null);
	  setUsuarioAdministradorDM(null);
	  setValidaEmail(null);
   }

   public UsuarioAdministrador getUsuarioAdministrador() {
	  if(usuarioAdministrador == null) {
		 usuarioAdministrador=new UsuarioAdministrador();
	  }
	  return usuarioAdministrador;
   }

   public void carregaUsuarioAdministrador() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  usuarioAdministrador=getUsuarioAdministradorSelecionado();
	  context.update("formStatusUsuario");
   }

   public void setUsuarioAdministrador(UsuarioAdministrador usuarioAdministrador) {
	  this.usuarioAdministrador=usuarioAdministrador;
   }

   public List<UsuarioAdministrador> getListaUsuariosAdministrador() {
	  if(listaUsuariosAdministrador == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			listaUsuariosAdministrador=new ArrayList<UsuarioAdministrador>();

			listaUsuariosAdministrador=gerenteControleAcesso.obterListaUsuariosAdministrador();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Usuarios");
		 }
	  }
	  return listaUsuariosAdministrador;
   }

   public void setListaUsuariosAdministrador(List<UsuarioAdministrador> listaUsuariosAdministrador) {
	  this.listaUsuariosAdministrador=listaUsuariosAdministrador;
   }

   public UsuarioAdministrador getUsuarioAdministradorSelecionado() {
	  if(usuarioAdministradorSelecionado == null) {
		 usuarioAdministradorSelecionado=new UsuarioAdministrador();
	  }
	  return usuarioAdministradorSelecionado;
   }

   public void setusuarioAdministradorSelecionado(UsuarioAdministrador usuarioAdministradorSelecionado) {
	  this.usuarioAdministradorSelecionado=usuarioAdministradorSelecionado;
   }

   public UsuarioAdministradorDataModel getUsuarioAdministradorDM() {
	  if(usuarioAdministradorDM == null) {
		 usuarioAdministradorDM=new UsuarioAdministradorDataModel(getListaUsuariosAdministrador());
	  }
	  return usuarioAdministradorDM;
   }

   public void setUsuarioAdministradorDM(UsuarioAdministradorDataModel usuarioAdministradorDM) {
	  this.usuarioAdministradorDM=usuarioAdministradorDM;
   }

   public void adicionar(ActionEvent event) {
	  System.out.println(usuarioAdministrador.getNome());
   }

   public List<Cartorio> getCartorios() {
	  if(cartorios == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			cartorios=new ArrayList<Cartorio>();
			cartorios=gerenteControleAcesso.obterCartorios();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Cart??rios");
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

}
