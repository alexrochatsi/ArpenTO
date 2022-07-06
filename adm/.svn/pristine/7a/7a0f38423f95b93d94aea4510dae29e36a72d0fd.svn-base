
package br.com.datasind.controller.cad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Documento;
import br.com.datasind.entidade.Genitor;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.Ocupacao;
import br.com.datasind.entidade.OrgaoEmissor;
import br.com.datasind.entidade.Pais;
import br.com.datasind.entidade.TipoDocumentoEnum;
import br.com.datasind.entidade.UF;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="genitor")
@ViewScoped
public class GenitorController extends CadastroControler{

   private static final long serialVersionUID=2171403870690807881L;

   private Genitor genitor =new Genitor();
   private List<TipoDocumentoEnum> tiposDocumento=Arrays.asList(TipoDocumentoEnum.values());
   private List<OrgaoEmissor> orgaoEmissores=null;
   private List<UF> ufs=new ArrayList<>();
   private List<IbgeUF> municipios=new ArrayList<IbgeUF>();
   private UF ufSelecionada;
   private List<Pais> paisesIBGE=new ArrayList<>();
   private List<Ocupacao> ocupacoes= null;
   private boolean skip;
   private Boolean disableCidade=true;
   private Boolean disableNovoMunicipio=true;
   private Boolean disableSelectNaturalidade=false;
   private Ocupacao ocupacaoSelecionada;

   public GenitorController() {
	  getGenitor().setDocumento(new Documento());
	  getGenitor().setOcupacao(new Ocupacao());
   }
   
   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 Genitor gntemp=new Genitor();
		 gntemp=getGenitor();
		 gntemp.setOcupacao(ocupacaoSelecionada);
		 gerenteControleAcesso.incluirEntidade(gntemp);
		 limpar();

		 mensageiro(INFO , "Ristro cadastrado com sucesso!");
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel cadastrar o registro.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(ERROR , e.getMessage());
		 LOGGER.equals(e);
		 return;
	  }
   }
   
   public void limpar() {
	  genitor = new Genitor();
	  genitor.setDocumento(new Documento());
   }

   @SuppressWarnings("unchecked")
   public List<Ocupacao> getOcupacoes() {
	  if(ocupacoes == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			ocupacoes=new ArrayList<Ocupacao>();
			ocupacoes=gerenteControleAcesso.obterTodos(Ocupacao.class);
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Ocupacoes");
		 }
	  }
	  return ocupacoes;
   }

   public void setOcupacoes(List<Ocupacao> ocupacoes) {
	  this.ocupacoes=ocupacoes;
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

   public Genitor getGenitor() {
	  return genitor;
   }

   public void setGenitor(Genitor genitor) {
	  this.genitor=genitor;
   }

   public String onFlowProcess(FlowEvent event) {
	  RequestContext request=RequestContext.getCurrentInstance();
	  if(skip) {
		 skip=false; // reset in case user goes back
		 request.update("formCadastro:growl");
		 return "confirm";
	  } else {
		 request.update("formCadastro:growl");
		 return event.getNewStep();
	  }
   }

   public boolean isSkip() {
	  return skip;
   }

   public void setSkip(boolean skip) {
	  this.skip=skip;
   }

   public List<IbgeUF> completeMunicipiosIBGE(String cidade) {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 municipios=gerenteControleAcesso.obterMunicipiosPorNomeUF(cidade , getUfSelecionada());
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
	  return municipios;
   }

   public List<Pais> paisesIBGE() {
	  if(paisesIBGE.isEmpty()) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			paisesIBGE=gerenteControleAcesso.obterPaises();
		 } catch (ApplicationException e) {
			e.printStackTrace();
		 }
	  }
	  return paisesIBGE;
   }

   public List<Pais> completePaisesIBGE(String pais) {
	  List<Pais> resultado=new ArrayList<Pais>();
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 resultado=gerenteControleAcesso.obterPaisPorNomeCidade(pais);
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
	  return resultado;
   }

   public List<TipoDocumentoEnum> getTiposDocumento() {
	  return tiposDocumento;
   }

   public void setTiposDocumento(List<TipoDocumentoEnum> tiposDocumento) {
	  this.tiposDocumento=tiposDocumento;
   }

   public List<OrgaoEmissor> getOrgaoEmissores() {
	  if(orgaoEmissores == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			orgaoEmissores=new ArrayList<OrgaoEmissor>();
			orgaoEmissores=gerenteControleAcesso.obterOrgaoEmissores();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Orgaos Emissores");
		 }
	  }
	  return orgaoEmissores;
   }

   public void setOrgaoEmissores(List<OrgaoEmissor> orgaoEmissores) {
	  this.orgaoEmissores=orgaoEmissores;
   }

   public void habilitaListCidades() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidade(false);
	  context.update("formCadastro:naturalidade");
   }

   public void novoMunicipio() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidade(true);
	  setDisableNovoMunicipio(false);
	  setDisableSelectNaturalidade(true);
	  context.update("formCadastro:naturalidade");
	  context.update("formCadastro:s_uf");
	  context.update("formCadastro:novaNaturalidade");
   }


   public Boolean getDisableCidade() {
	  return disableCidade;
   }

   public void setDisableCidade(Boolean disableCidade) {
	  this.disableCidade=disableCidade;
   }

   public UF getUfSelecionada() {
	  return ufSelecionada;
   }

   public void setUfSelecionada(UF ufSelecionada) {
	  this.ufSelecionada=ufSelecionada;
   }
   
   public List<IbgeUF> getMunicipios() {
	  return municipios;
   }

   public void setMunicipios(List<IbgeUF> municipios) {
	  this.municipios=municipios;
   }

   public Boolean getDisableNovoMunicipio() {
	  return disableNovoMunicipio;
   }

   public void setDisableNovoMunicipio(Boolean disableNovoMunicipio) {
	  this.disableNovoMunicipio=disableNovoMunicipio;
   }
   
   public Boolean getDisableSelectNaturalidade() {
	  return disableSelectNaturalidade;
   }

   public void setDisableSelectNaturalidade(Boolean disableSelectNaturalidadeGenitor) {
	  this.disableSelectNaturalidade=disableSelectNaturalidadeGenitor;
   }

   public Ocupacao getOcupacaoSelecionada() {
	  if(ocupacaoSelecionada == null){
		 ocupacaoSelecionada = new Ocupacao();
	  }
      return ocupacaoSelecionada;
   }

   public void setOcupacaoSelecionada(Ocupacao ocupacaoSelecionada) {
      this.ocupacaoSelecionada=ocupacaoSelecionada;
   }

}
