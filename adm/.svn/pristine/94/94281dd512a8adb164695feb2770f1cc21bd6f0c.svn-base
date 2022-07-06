
package br.com.datasind.controller.cad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Documento;
import br.com.datasind.entidade.Genitor;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.ImpressoSeguranca;
import br.com.datasind.entidade.Local;
import br.com.datasind.entidade.LocalNascimentoEnum;
import br.com.datasind.entidade.Ocupacao;
import br.com.datasind.entidade.OrgaoEmissor;
import br.com.datasind.entidade.Pais;
import br.com.datasind.entidade.RegistroNascimento;
import br.com.datasind.entidade.SexoEnum;
import br.com.datasind.entidade.TipoDocumentoEnum;
import br.com.datasind.entidade.TipoImpressoSegurancaEnum;
import br.com.datasind.entidade.UF;
import br.com.datasind.entidade.ViaEnum;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.util.Utilitario;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="registroNascimento")
@ViewScoped
public class RegistroNascimentoController extends CadastroControler{

   private static final long serialVersionUID=2171403870690807881L;

   private RegistroNascimento registroNascimento=new RegistroNascimento();
   private RegistroNascimento registroNascimentoSelecionado=new RegistroNascimento();
   private List<SexoEnum> sexos=Arrays.asList(SexoEnum.values());
   private List<ViaEnum> vias=Arrays.asList(ViaEnum.values());
   private List<TipoDocumentoEnum> tiposDocumento=Arrays.asList(TipoDocumentoEnum.values());
   private List<TipoImpressoSegurancaEnum> impressosSeguranca=Arrays.asList(TipoImpressoSegurancaEnum.values());
   private List<LocalNascimentoEnum> tiposLocaisNascimento=Arrays.asList(LocalNascimentoEnum.values());
   private List<OrgaoEmissor> orgaoEmissores=null;
   private List<UF> ufs=new ArrayList<>();
   private List<Local> locais=new ArrayList<>();
   private List<RegistroNascimento> registrosNascimento=new ArrayList<>();
   private List<IbgeUF> municipios=new ArrayList<IbgeUF>();
   private UF ufSelecionada;
   private UF ufSelecionadaGenitor;
   private UF ufSelecionadaGenitora;
   private UF ufSelecionadaCasalGenitor;
   private UF ufSelecionadaCasalGenitora;
   private List<Pais> paisesIBGE=new ArrayList<>();
   private List<Ocupacao> ocupacoes=null;
   private Boolean morarJunto=true;
   private Boolean localInexistente=false;
   private boolean skip;
   private Boolean disableCidade=true;
   private Boolean disableCidadeGenitor=true;
   private Boolean disableCidadeGenitora=true;
   private Boolean disableCidadeCasalGenitor=true;
   private Boolean disableCidadeCasalGenitora=true;
   private Boolean disableNovoMunicipioGenitor=true;
   private Boolean disableNovoMunicipioGenitora=true;
   private Boolean disableSelectNaturalidadeGenitor=false;
   private Boolean disableSelectNaturalidadeGenitora=false;
   private Boolean disableDNV1=false;
   private Boolean disableDNV2=false;
   private Boolean disableLocal1=true;
   private Boolean disableLocal2=true;
   private String preMatricula;
   private String dvMatricula;
   private String dvDNV;
   private Boolean matriculaInvalida=true;
   private String pathRelatorio="";

   public RegistroNascimentoController() {
	  registroNascimento.setGenitora(new Genitor());
	  registroNascimento.setGenitor(new Genitor());
	  registroNascimento.setImpressoSeguranca(new ImpressoSeguranca());
	  registroNascimento.getGenitora().setDocumento(new Documento());
	  registroNascimento.getGenitor().setDocumento(new Documento());
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 RegistroNascimento rntemp=new RegistroNascimento();
		 rntemp=getRegistroNascimento();
		 rntemp.setNome(getRegistroNascimento().getNome().toUpperCase());
		 rntemp.getGenitor().setPai(getRegistroNascimento().getGenitor().getPai().toUpperCase());
		 rntemp.getGenitor().setMae(getRegistroNascimento().getGenitor().getMae().toUpperCase());
		 rntemp.getGenitora().setPai(getRegistroNascimento().getGenitora().getPai().toUpperCase());
		 rntemp.getGenitora().setMae(getRegistroNascimento().getGenitora().getMae().toUpperCase());
		 rntemp.setDataCadastroSistema(new Date());
		 if(morarJunto == true) {
			rntemp.getGenitor().setEndereco(rntemp.getGenitora().getEndereco());
		 }
		 rntemp.setMatricula(getPreMatricula().concat(getDvMatricula()));
		 rntemp.setDnv(getRegistroNascimento().getDnv().concat("-" + getDvDNV()));
		 rntemp.getImpressoSeguranca().setDataUtilizacao(new Date());
		 // rntemp.setUsuarioCartorio(getUsuarioCartorioSessao());
		 rntemp.getImpressoSeguranca().setNumeroSerie(rntemp.getImpressoSeguranca().getNumeroSerie().toUpperCase());
		 rntemp.getImpressoSeguranca().setSeloDigital(rntemp.getImpressoSeguranca().getSeloDigital().toUpperCase());
		 gerenteControleAcesso.incluirEntidade(rntemp);
		 // limpar();
		 context.execute("PF('dlgAcao').show();");

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

   public void carregaRegistro() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  registroNascimento=getRegistroNascimentoSelecionado();
	  inicializar(registroNascimento);
	  context.update("formLista");
   }

   public void onRowSelect(SelectEvent event) {
	  setRegistroNascimento((RegistroNascimento) event.getObject());
	  mensageiro(WARNING , "Registro Nascimento " + ((RegistroNascimento) event.getObject()).getId());
   }

   public void limpar() {
	  registroNascimento=new RegistroNascimento();
	  registroNascimento.setGenitora(new Genitor());
	  registroNascimento.setGenitor(new Genitor());
	  registroNascimento.setImpressoSeguranca(new ImpressoSeguranca());
	  registroNascimento.getGenitora().setDocumento(new Documento());
	  registroNascimento.getGenitor().setDocumento(new Documento());
	  registroNascimento.getGenitora().setOcupacao(new Ocupacao());
	  registroNascimento.getGenitor().setOcupacao(new Ocupacao());
   }

   public String acaoImprimir(ActionEvent event) {
	  /*
	   * GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso(); GerenteArquivos
	   * gerenteArquivos=getFabricaGerente().getGerenteArquivos(); ExternalContext
	   * ec=FacesContext.getCurrentInstance().getExternalContext(); RequestContext
	   * context=RequestContext.getCurrentInstance(); Arquivo arquivo=null;
	   * 
	   * try { // inicializar(registroNascimento);
	   * 
	   * UsuarioCartorio usuarioCartorio=new UsuarioCartorio();
	   * 
	   * usuarioCartorio=gerenteControleAcesso.obterUsuarioCartorioPorId(getUsuarioCartorioSessao()); Map
	   * parametrosPDF=new HashMap(); String matriculatmp=getRegistroNascimento().getMatricula(); String livro="",
	   * folha="", termo=""; Calendar cal=Calendar.getInstance();
	   * cal.setTime(getRegistroNascimento().getDataNascimento());
	   * //gerenteControleAcesso.inicializa("inicializadorRegistroNascimento" , getRegistroNascimento());
	   * 
	   * Integer dia=cal.get(Calendar.DAY_OF_MONTH); Integer mes=cal.get(Calendar.MONTH) + 1; Integer
	   * ano=cal.get(Calendar.YEAR);
	   * 
	   * livro=matriculatmp.substring(15 , 20); folha=matriculatmp.substring(20 , 23); termo=matriculatmp.substring(23 ,
	   * 30);
	   * 
	   * String endereco=usuarioCartorio.getCartorio().getEndereco() + "," +
	   * usuarioCartorio.getCartorio().getMunicipio() + "/" + usuarioCartorio.getCartorio().getUf() + "," +
	   * usuarioCartorio.getCartorio().getBairro() + "- CEP: " + usuarioCartorio .getCartorio().getCep();
	   * 
	   * String contato="Fone: " + usuarioCartorio.getCartorio().getFone() + "- Email: " +
	   * usuarioCartorio.getCartorio().getEmail();
	   * 
	   * arquivo=gerenteArquivos.abrirArquivoPorRealPath("certidao1.jasper" , getContext());
	   * parametrosPDF.put("matricula" , matriculatmp); parametrosPDF.put("livro" , livro); parametrosPDF.put("folha" ,
	   * folha); parametrosPDF.put("termo" , termo); parametrosPDF.put("nomeLivro" , "LIVRO A");
	   * parametrosPDF.put("tipoCertidao" , "CERTIDÃO DE NASCIMENTO"); parametrosPDF.put("seloDigital" ,
	   * getRegistroNascimento().getImpressoSeguranca().getSeloDigital()); parametrosPDF.put("cidade" ,
	   * usuarioCartorio.getCartorio().getMunicipio()); parametrosPDF.put("uf" , usuarioCartorio.getCartorio().getUf());
	   * parametrosPDF.put("razao" , usuarioCartorio.getCartorio().getRazao()); parametrosPDF.put("nome1" ,
	   * getRegistroNascimento().getNome()); parametrosPDF.put("dataExtenso" ,
	   * getRegistroNascimento().getDataNascimentoExtenso()); parametrosPDF.put("dia" , dia.toString());
	   * parametrosPDF.put("mes" , mes.toString()); parametrosPDF.put("ano" , ano.toString());
	   * if(registroNascimento.getHoraNascimentoIgnorada() == true) parametrosPDF.put("hora" , "HORA IGNORADA"); else
	   * parametrosPDF.put("hora" , getRegistroNascimento().getHoraNascimento() + ":" +
	   * getRegistroNascimento().getMinutoNascimento()); //parametrosPDF.put("municipioNascimento" ,
	   * getRegistroNascimento().getMunicipioUF().getNomeMunicipio() + " /" +
	   * getRegistroNascimento().getMunicipioUF().getUf()); parametrosPDF.put("nomeCartorio" ,
	   * usuarioCartorio.getCartorio().getNome()); parametrosPDF.put("endereco" , endereco); parametrosPDF.put("contato"
	   * , contato); parametrosPDF.put("sexo" , getRegistroNascimento().getSexo().getLabel()); //
	   * parametrosPDF.put("municipioRegistro" , //
	   * getRegistroNascimento().getLocalNascimento().getEndereco().getMunicipioUF().getNomeMunicipio()+" / " // +
	   * getRegistroNascimento().getLocalNascimento().getEndereco().getMunicipioUF().getUf()); //
	   * parametrosPDF.put("localNascimento" , getRegistroNascimento().getLocalNascimento().getDescricao()+", "+ //
	   * getRegistroNascimento().getLocalNascimento().getEndereco().getLogradouro() + ", " + //
	   * getRegistroNascimento().getLocalNascimento().getEndereco().getNumero()); parametrosPDF.put("genitor" ,
	   * getRegistroNascimento().getGenitor().getNome()); parametrosPDF.put("genitora" ,
	   * getRegistroNascimento().getGenitora().getNome()); parametrosPDF.put("avo1Paterno" ,
	   * getRegistroNascimento().getGenitor().getPai()); parametrosPDF.put("avo2Paterno" ,
	   * getRegistroNascimento().getGenitor().getMae()); parametrosPDF.put("avo1Materno" ,
	   * getRegistroNascimento().getGenitora().getPai()); parametrosPDF.put("avo2Materno" ,
	   * getRegistroNascimento().getGenitora().getMae()); if(registroNascimento.getPossuiGemeos() == null ||
	   * registroNascimento.getPossuiGemeos() == false) { parametrosPDF.put("gemeo" , "NÃO");
	   * parametrosPDF.put("dadosGemeo" , "-------------------------------------------"); }
	   * parametrosPDF.put("dataRegistroExtenso" ,
	   * Utilitario.dataPorExtenso(getRegistroNascimento().getDataRegistroNascimento()).toUpperCase());
	   * parametrosPDF.put("dnv" , getRegistroNascimento().getDnv()); parametrosPDF.put("observacao" ,
	   * getRegistroNascimento().getObservacao());
	   * 
	   * setPathRelatorio(ReportHelper.gerarPDFSemDataSource(arquivo , parametrosPDF));
	   * ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()); context.update("formPDF");
	   * context.execute("PF('dlgImp').show();"); ec.redirect(ec.getRequestContextPath() + "/"+getPathRelatorio()); }
	   * catch (ApplicationException e) { e.getStackTrace(); mensageiro(ERROR , "Erro ao gerar relatório"); return ""; }
	   * catch (ArquivoNaoEncontradoException e) { e.printStackTrace(); mensageiro(ERROR , "Erro ao gerar relatório");
	   * return ""; } catch (ValidacaoException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace();
	   * }
	   */

	  return CURRENT_PAGE;
   }

   public void redirecionaHome() {
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
	  try {
		 ec.redirect(ec.getRequestContextPath() + "/portal/portal.jsf");
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
   }

   public void redirecionaNovoCadastro() {
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
	  try {
		 ec.redirect(ec.getRequestContextPath() + "/cad/registroNascimento.jsf");
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
   }

   public void validaMatricula() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  String retorno=Utilitario.matriculaDV(getPreMatricula());

	  if(retorno.equals(getDvMatricula())) {
		 mensageiro(INFO , "Matrícula Válida");
		 setMatriculaInvalida(false);
		 context.update("formCadastro:dadosCertidaoPanel");
	  } else {
		 setMatriculaInvalida(true);
		 context.update("formCadastro:dadosCertidaoPanel");
		 mensageiro(ERROR , "Matrícula Inválida");
	  }
   }

   public void validaDNV() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  String retorno=Utilitario.dnvDV(getRegistroNascimento().getDnv());

	  if(retorno.equals(getDvDNV())) {
		 mensageiro(INFO , "DNV é Válida");
		 setDisableDNV1(true);
		 setDisableDNV2(true);
		 setDisableLocal1(false);
		 context.update("formCadastro:localNascimentoPanel");
	  } else {
		 setDisableDNV1(false);
		 setDisableDNV2(false);
		 setDisableLocal1(true);
		 context.update("formCadastro:localNascimentoPanel");
		 mensageiro(ERROR , "Matrícula Inválida");
	  }
   }

   public void DNVInexistente() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  if(registroNascimento.getDnvInexistente() == true) {
		 setDisableDNV1(true);
		 setDisableLocal1(false);
		 context.update("formCadastro:localNascimentoPanel");
	  } else {
		 setDisableDNV1(false);
		 setDisableLocal1(true);
		 context.update("formCadastro:localNascimentoPanel");
	  }
   }

   public List<RegistroNascimento> getMeusRegistrosNascimentos() {
	  /*if(registrosNascimento.isEmpty()) {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

		 registrosNascimento=gerenteControleAcesso.obterRegistrosNascimentoUsuarioCartorio(getUsuarioCartorioSessao());
	  }*/
	  return registrosNascimento;
   }

   public void localNaoListado() {
	  if(localInexistente == true) {
		 setDisableLocal2(false);
	  } else {
		 setDisableLocal2(true);
	  }
   }

   public List<SexoEnum> getSexos() {
	  return sexos;
   }

   public void setSexos(List<SexoEnum> sexos) {
	  this.sexos=sexos;
   }

   public List<TipoImpressoSegurancaEnum> getImpressosSeguranca() {
	  return impressosSeguranca;
   }

   public void setImpressososSeguranca(List<TipoImpressoSegurancaEnum> impressosSeguranca) {
	  this.impressosSeguranca=impressosSeguranca;
   }

   public List<ViaEnum> getVias() {
	  return vias;
   }

   public void setVias(List<ViaEnum> vias) {
	  this.vias=vias;
   }

   public List<LocalNascimentoEnum> getTiposLocaisNascimento() {
	  return tiposLocaisNascimento;
   }

   public void setTiposLocaisNascimento(List<LocalNascimentoEnum> tiposLocaisNascimento) {
	  this.tiposLocaisNascimento=tiposLocaisNascimento;
   }

   public List<Ocupacao> getOcupacoes() {
	  if(ocupacoes == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			ocupacoes=new ArrayList<Ocupacao>();
			ocupacoes=gerenteControleAcesso.obterOcupacoes();
		 } catch (ApplicationException e) {
			e.printStackTrace();
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

   public List<Local> getLocais() {
	  if(locais.isEmpty()) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			setLocais(gerenteControleAcesso.obterLocais());
		 } catch (ApplicationException e) {
			e.printStackTrace();
		 }
	  }
	  return locais;
   }

   public void setLocais(List<Local> locais) {
	  this.locais=locais;
   }

   public void setUfs(List<UF> ufs) {
	  this.ufs=ufs;
   }

   public RegistroNascimento getRegistroNascimento() {
	  return registroNascimento;
   }

   public void setRegistroNascimento(RegistroNascimento registroNascimento) {
	  this.registroNascimento=registroNascimento;
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

   public Boolean getMorarJunto() {
	  return morarJunto;
   }

   public void setMorarJunto(Boolean morarJunto) {
	  this.morarJunto=morarJunto;
	  System.out.println("O VALOR É: " + getMorarJunto());
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

   public List<IbgeUF> completeMunicipiosIBGEGenitor(String cidade) {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 municipios=gerenteControleAcesso.obterMunicipiosPorNomeUF(cidade , getUfSelecionadaGenitor());
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
	  return municipios;
   }

   public List<IbgeUF> completeMunicipiosIBGEGenitora(String cidade) {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 municipios=gerenteControleAcesso.obterMunicipiosPorNomeUF(cidade , getUfSelecionadaGenitora());
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
	  return municipios;
   }

   public List<IbgeUF> completeMunicipiosIBGECasalGenitora(String cidade) {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 municipios=gerenteControleAcesso.obterMunicipiosPorNomeUF(cidade , getUfSelecionadaCasalGenitora());
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
	  return municipios;
   }

   public List<IbgeUF> completeMunicipiosIBGECasalGenitor(String cidade) {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 municipios=gerenteControleAcesso.obterMunicipiosPorNomeUF(cidade , getUfSelecionadaCasalGenitor());
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

   public void atualizaDataExtenso() {
	  String dataExtenso;
	  dataExtenso=Utilitario.dataPorExtenso(getRegistroNascimento().getDataNascimento()).toUpperCase();
	  getRegistroNascimento().setDataNascimentoExtenso(dataExtenso.replace("UM MIL E" , "MIL"));
   }

   public void validaHora() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  if(getRegistroNascimento().getHoraNascimento() == null || getRegistroNascimento().getHoraNascimento().equals("") || getRegistroNascimento().getHoraNascimento().equals("__")) {
		 mensageiro(ERROR , "A hora é requerida.");
		 return;
	  } else {
		 Integer valor=Integer.parseInt(getRegistroNascimento().getHoraNascimento());
		 if(valor < 0 || valor > 23) {
			getRegistroNascimento().setHoraNascimento("");
			context.update("formCadastro:hora");
			mensageiro(ERROR , "Valor inválido, informe um valor entre 00 e 23.");
			return;
		 }
	  }
   }

   public void validaMinuto() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  if(getRegistroNascimento().getMinutoNascimento() == null || getRegistroNascimento().getMinutoNascimento().equals("") || getRegistroNascimento().getMinutoNascimento().equals("__")) {
		 mensageiro(ERROR , "O minuto é requerido.");
		 return;
	  } else {
		 Integer valor=Integer.parseInt(getRegistroNascimento().getMinutoNascimento());
		 if(valor < 0 || valor >= 60) {
			getRegistroNascimento().setMinutoNascimento("");
			context.update("formCadastro:minuto");
			mensageiro(ERROR , "Valor inválido, informe um valor entre 0 e 59.");
			return;
		 }
	  }
   }

   public void habilitaListCidades() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidade(false);
	  context.update("formCadastro:municipioNascimento");
   }

   public void habilitaListCidadesGenitor() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidadeGenitor(false);
	  context.update("formCadastro:naturalidadeGenitor");
   }

   public void habilitaListCidadesGenitora() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidadeGenitora(false);
	  context.update("formCadastro:naturalidadeGenitora");
   }

   public void habilitaListCidadesCasalGenitora() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidadeCasalGenitora(false);
	  context.update("formCadastro:municipioCasalGenitora");
   }

   public void habilitaListCidadesCasalGenitor() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidadeCasalGenitor(false);
	  context.update("formCadastro:municipioCasalGenitor");
   }

   public void novoMunicipioGenitora() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidadeGenitora(true);
	  setDisableNovoMunicipioGenitora(false);
	  setDisableSelectNaturalidadeGenitora(true);
	  context.update("formCadastro:naturalidadeGenitora");
	  context.update("formCadastro:s_ufGenitora");
	  context.update("formCadastro:novaNaturalidadeGenitora");
   }

   public void novoMunicipioGenitor() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidadeGenitor(true);
	  setDisableNovoMunicipioGenitor(false);
	  setDisableSelectNaturalidadeGenitor(true);
	  context.update("formCadastro:naturalidadeGenitor");
	  context.update("formCadastro:s_ufGenitor");
	  context.update("formCadastro:novaNaturalidadeGenitor");
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

   public UF getUfSelecionadaGenitor() {
	  return ufSelecionadaGenitor;
   }

   public void setUfSelecionadaGenitor(UF ufSelecionadaGenitor) {
	  this.ufSelecionadaGenitor=ufSelecionadaGenitor;
   }

   public UF getUfSelecionadaGenitora() {
	  return ufSelecionadaGenitora;
   }

   public void setUfSelecionadaGenitora(UF ufSelecionadaGenitora) {
	  this.ufSelecionadaGenitora=ufSelecionadaGenitora;
   }

   public List<IbgeUF> getMunicipios() {
	  return municipios;
   }

   public void setMunicipios(List<IbgeUF> municipios) {
	  this.municipios=municipios;
   }

   public Boolean getDisableCidadeGenitor() {
	  return disableCidadeGenitor;
   }

   public void setDisableCidadeGenitor(Boolean disableCidadeGenitor) {
	  this.disableCidadeGenitor=disableCidadeGenitor;
   }

   public Boolean getDisableCidadeGenitora() {
	  return disableCidadeGenitora;
   }

   public void setDisableCidadeGenitora(Boolean disableCidadeGenitora) {
	  this.disableCidadeGenitora=disableCidadeGenitora;
   }

   public Boolean getLocalInexistente() {
	  return localInexistente;
   }

   public void setLocalInexistente(Boolean localInexistente) {
	  this.localInexistente=localInexistente;
   }

   public Boolean getDisableNovoMunicipioGenitor() {
	  return disableNovoMunicipioGenitor;
   }

   public void setDisableNovoMunicipioGenitor(Boolean disableNovoMunicipioGenitor) {
	  this.disableNovoMunicipioGenitor=disableNovoMunicipioGenitor;
   }

   public Boolean getDisableNovoMunicipioGenitora() {
	  return disableNovoMunicipioGenitora;
   }

   public void setDisableNovoMunicipioGenitora(Boolean disableNovoMunicipioGenitora) {
	  this.disableNovoMunicipioGenitora=disableNovoMunicipioGenitora;
   }

   public Boolean getDisableSelectNaturalidadeGenitor() {
	  return disableSelectNaturalidadeGenitor;
   }

   public void setDisableSelectNaturalidadeGenitor(Boolean disableSelectNaturalidadeGenitor) {
	  this.disableSelectNaturalidadeGenitor=disableSelectNaturalidadeGenitor;
   }

   public Boolean getDisableSelectNaturalidadeGenitora() {
	  return disableSelectNaturalidadeGenitora;
   }

   public void setDisableSelectNaturalidadeGenitora(Boolean disableSelectNaturalidadeGenitora) {
	  this.disableSelectNaturalidadeGenitora=disableSelectNaturalidadeGenitora;
   }

   public Boolean getDisableCidadeCasalGenitor() {
	  return disableCidadeCasalGenitor;
   }

   public void setDisableCidadeCasalGenitor(Boolean disableCidadeCasalGenitor) {
	  this.disableCidadeCasalGenitor=disableCidadeCasalGenitor;
   }

   public Boolean getDisableCidadeCasalGenitora() {
	  return disableCidadeCasalGenitora;
   }

   public void setDisableCidadeCasalGenitora(Boolean disableCidadeCasalGenitora) {
	  this.disableCidadeCasalGenitora=disableCidadeCasalGenitora;
   }

   public UF getUfSelecionadaCasalGenitor() {
	  return ufSelecionadaCasalGenitor;
   }

   public void setUfSelecionadaCasalGenitor(UF ufSelecionadaCasalGenitor) {
	  this.ufSelecionadaCasalGenitor=ufSelecionadaCasalGenitor;
   }

   public UF getUfSelecionadaCasalGenitora() {
	  return ufSelecionadaCasalGenitora;
   }

   public void setUfSelecionadaCasalGenitora(UF ufSelecionadaCasalGenitora) {
	  this.ufSelecionadaCasalGenitora=ufSelecionadaCasalGenitora;
   }

   public String getPreMatricula() {
	  return preMatricula;
   }

   public void setPreMatricula(String preMatricula) {
	  this.preMatricula=preMatricula;
   }

   public String getDvMatricula() {
	  return dvMatricula;
   }

   public void setDvMatricula(String dvMatricula) {
	  this.dvMatricula=dvMatricula;
   }

   public Boolean getMatriculaInvalida() {
	  return matriculaInvalida;
   }

   public void setMatriculaInvalida(Boolean matriculaInvalida) {
	  this.matriculaInvalida=matriculaInvalida;
   }

   public String getDvDNV() {
	  return dvDNV;
   }

   public void setDvDNV(String dvDNV) {
	  this.dvDNV=dvDNV;
   }

   public Boolean getDisableDNV1() {
	  return disableDNV1;
   }

   public void setDisableDNV1(Boolean disableDNV1) {
	  this.disableDNV1=disableDNV1;
   }

   public Boolean getDisableDNV2() {
	  return disableDNV2;
   }

   public void setDisableDNV2(Boolean disableDNV2) {
	  this.disableDNV2=disableDNV2;
   }

   public Boolean getDisableLocal1() {
	  return disableLocal1;
   }

   public void setDisableLocal1(Boolean disableLocal1) {
	  this.disableLocal1=disableLocal1;
   }

   public Boolean getDisableLocal2() {
	  return disableLocal2;
   }

   public void setDisableLocal2(Boolean disableLocal2) {
	  this.disableLocal2=disableLocal2;
   }

   public String getPathRelatorio() {
	  return pathRelatorio;
   }

   public void setPathRelatorio(String pathRelatorio) {
	  this.pathRelatorio=pathRelatorio;
   }

   public List<RegistroNascimento> getRegistrosNascimento() {
	  return registrosNascimento;
   }

   public void setRegistrosNascimento(List<RegistroNascimento> registrosNascimento) {
	  this.registrosNascimento=registrosNascimento;
   }

   public RegistroNascimento getRegistroNascimentoSelecionado() {
	  return registroNascimentoSelecionado;
   }

   public void setRegistroNascimentoSelecionado(RegistroNascimento registroNascimentoSelecionado) {
	  this.registroNascimentoSelecionado=registroNascimentoSelecionado;
   }
}
