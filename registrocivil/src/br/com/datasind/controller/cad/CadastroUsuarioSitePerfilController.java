
package br.com.datasind.controller.cad;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Endereco;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.UF;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.entidade.UsuarioSitePerfil;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.util.Utilitaria;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="usuarioSitePerfil")
@ViewScoped
public class CadastroUsuarioSitePerfilController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;
   private UsuarioSitePerfil usuarioSitePerfil=new UsuarioSitePerfil();
   private List<UsuarioSitePerfil> listaUsuariosSitePerfil;
   private UsuarioSitePerfil usuarioSitePerfilSelecionado;
   private UF ufSelecionada;
   private Boolean disableCidade=true;
   private List<IbgeUF> municipios=new ArrayList<IbgeUF>();
   private List<UF> ufs=new ArrayList<>();
   private Boolean disableUpdatePerfil=true;
   private Boolean renderizaForm = false;

   public CadastroUsuarioSitePerfilController() {
	  usuarioSitePerfil.setEndereco(new Endereco());
   }

   public void loadModel() {
	  if(getUsuarioSiteSessao().getUsuarioSitePerfil() == null) {
		 getUsuarioSitePerfil().setNome((getUsuarioSiteSessao().getNome()));
		 RequestContext context=RequestContext.getCurrentInstance();
		 context.execute("PF('dlg2').show();");
	  }
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gCAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 String cpfTratado=getUsuarioSitePerfil().getCpf().replaceAll("[.-]" , "");
		 if( !Utilitaria.checaCPF(cpfTratado)) {
			mensageiro(ERROR , "O CPF informado é inválido.");
			return;
		 }
		 gCAcesso.incluirEntidade(getUsuarioSitePerfil());

		 atualizaUsuarioSite();

		 mensageiro(INFO , "Perfil cadastrado com sucesso!");
		 context.execute("PF('dlg2').hide();");
		 acaoLimpar();
		 setUsuarioSitePerfil(null);
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel cadastrar o perfil do usuario.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(ERROR , e.getMessage());
		 LOGGER.equals(e);
		 return;
	  }
   }

   public void atualizaUsuarioSite() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 UsuarioSite usuarioSite=getUsuarioSiteSessao();
		 usuarioSite.setPerfilAtivo(true);
		 usuarioSite.setUsuarioSitePerfil(getUsuarioSitePerfil());
		 gerenteControleAcesso.atualizarEntidade(usuarioSite);
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }

   }

   public void preparaUpdatePerfil() {
	  setUsuarioSitePerfil(getUsuarioSiteSessao().getUsuarioSitePerfil());
	  RequestContext context=RequestContext.getCurrentInstance();
	  setRenderizaForm(true);
	  context.update("dlgUpdPerfil");
	  setDisableUpdatePerfil(false);
	  context.update("formPerfil2");
	  context.execute("PF('dlgUpdPerfil').show();");
   }

   public void acaoAlterar(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  String cpfTratado=getUsuarioSitePerfil().getCpf().replaceAll("[.-]" , "");
	  if( !Utilitaria.checaCPF(cpfTratado)) {
		 mensageiro(ERROR , "O CPF informado é inválido.");
		 return;
	  }
	  try {
		 gerenteControleAcesso.atualizarEntidade(getUsuarioSitePerfil());
		 context.execute("PF('dlgUpdPerfil').hide();");
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

	  mensageiro(INFO , "Perfil alterado com sucesso.");
   }

   public void habilitaListCidades() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidade(false);
	  context.update("formPerfil:municipio");
	  context.update("formPerfil2:municipio");
   }

   public void acaoExcluir(ActionEvent event) {

	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

	  try {
		 gerenteControleAcesso.excluirEntidade(getUsuarioSitePerfil());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel excluir usuario.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Usuario excluido com sucesso.");
   }

   public void acaoLimpar() {

	  setUsuarioSitePerfil(null);
	  setListaUsuariosSitePerfil(null);
   }

   public void onRowSelect(SelectEvent event) {

	  setUsuarioSitePerfil((UsuarioSitePerfil) event.getObject());
	  mensageiro(WARNING , "Usuario " + ((UsuarioSitePerfil) event.getObject()).getNome());

   }

   public void setUsuarioSitePerfil(UsuarioSitePerfil usuarioSitePerfil) {
	  this.usuarioSitePerfil=usuarioSitePerfil;
   }

   public List<UsuarioSitePerfil> getListaUsuariosSitePerfil() {
	  if(listaUsuariosSitePerfil == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			listaUsuariosSitePerfil=new ArrayList<UsuarioSitePerfil>();

			listaUsuariosSitePerfil=gerenteControleAcesso.obterListaUsuariosSitePerfil();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Usuarios");
		 }
	  }
	  return listaUsuariosSitePerfil;
   }

   public List<IbgeUF> completeMunicipiosIBGE(String cidade) {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 setMunicipios(gerenteControleAcesso.obterMunicipiosPorNomeUF(cidade , getUfSelecionada()));
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
	  return getMunicipios();
   }

   public void setListaUsuariosSitePerfil(List<UsuarioSitePerfil> listaUsuariosSitePerfil) {
	  this.listaUsuariosSitePerfil=listaUsuariosSitePerfil;
   }

   public UsuarioSitePerfil getUsuarioSitePerfilSelecionado() {
	  if(usuarioSitePerfilSelecionado == null) {
		 usuarioSitePerfilSelecionado=new UsuarioSitePerfil();
	  }
	  return usuarioSitePerfilSelecionado;
   }

   public void setUsuarioSitePerfilSelecionado(UsuarioSitePerfil usuarioSitePerfilSelecionado) {
	  this.usuarioSitePerfilSelecionado=usuarioSitePerfilSelecionado;
   }

   public UsuarioSitePerfil getUsuarioSitePerfil() {
	  return usuarioSitePerfil;
   }

   public List<UF> getUfs() {
	  if(ufs.isEmpty()) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			setUfs(gerenteControleAcesso.obterUFs());
		 } catch (ApplicationException e) {
			e.printStackTrace();
		 }
	  }
	  return ufs;
   }

   public void setUfs(List<UF> ufs) {
	  this.ufs=ufs;
   }

   public UF getUfSelecionada() {
	  return ufSelecionada;
   }

   public void setUfSelecionada(UF ufSelecionada) {
	  this.ufSelecionada=ufSelecionada;
   }

   public Boolean getDisableCidade() {
	  return disableCidade;
   }

   public void setDisableCidade(Boolean disableCidade) {
	  this.disableCidade=disableCidade;
   }

   public List<IbgeUF> getMunicipios() {
	  return municipios;
   }

   public void setMunicipios(List<IbgeUF> municipios) {
	  this.municipios=municipios;
   }

   public Boolean getDisableUpdatePerfil() {
      return disableUpdatePerfil;
   }

   public void setDisableUpdatePerfil(Boolean disableUpdatePerfil) {
      this.disableUpdatePerfil=disableUpdatePerfil;
   }

   public Boolean getRenderizaForm() {
      return renderizaForm;
   }

   public void setRenderizaForm(Boolean renderizaForm) {
      this.renderizaForm=renderizaForm;
   }
}
