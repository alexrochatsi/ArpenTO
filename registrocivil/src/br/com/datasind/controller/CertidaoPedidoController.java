
package br.com.datasind.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.FinderException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.Correios;
import br.com.datasind.entidade.CustoPedido;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.UF;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.gerente.GerenteBoleto;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.relatorio.TempFileNameHelper;
import br.com.datasind.util.Utilitario;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="certidaoPedido")
@ViewScoped
public class CertidaoPedidoController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;
   private CertidaoPedido certidaoPedido=new CertidaoPedido();
   private CertidaoPedido certidaoPedido2;
   private CustoPedido custoPedidoSelecionado;
   private List<Correios> correios;
   private Correios correio;
   private List<CertidaoPedido> listaCertidaoPedido;
   private CertidaoPedido certidaoPedidoSelecionado;
   private List<Cartorio> cartorios=new ArrayList<>();
   private List<CustoPedido> custosPedido;
   private List<UF> ufs=new ArrayList<>();
   private UF ufSelecionada;
   private List<IbgeUF> municipios=new ArrayList<IbgeUF>();
   private Boolean disableCidade=true;
   private Boolean renderizaCasamento=false;
   private Boolean renderizaNascimento=true;
   private Boolean renderizaObitoNatimorto=false;
   private Boolean renderizaBoleto=false;
   private StreamedContent file, file2;
   private String nomePdf="";
   private Boolean usaEndereco=true;
   private Boolean disableEndereco=false;
   private Boolean disablePeriodoFinal=true;
   private BigDecimal valor=new BigDecimal(0);
   private String preMatricula;
   private String dvMatricula;
   private Boolean possuiMatricula=true;
   private Date periodoInicial;
   private Date periodoFinal;
   private Boolean renderizarMatricula=false;

   @PostConstruct
   public void init() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 custoPedidoSelecionado=gerenteControleAcesso.obterCustoPedidoPorId(2);
		 certidaoPedido.setFormaEntrega(1);
		 correio=gerenteControleAcesso.obterCorreiosPorId(3);
		 verificaTipoEndereco();
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 CertidaoPedido cptemp=new CertidaoPedido();
		 cptemp=getCertidaoPedido();
		 if(getPreMatricula() != null || getDvMatricula() != null) {
			setPreMatricula(getCertidaoPedido().getMatricula().substring(0 , 30));
			setDvMatricula(getCertidaoPedido().getMatricula().substring(30 , 32));
			if(getPossuiMatricula() == true) {
			   String retorno=Utilitario.matriculaDV(getPreMatricula());
			   if( !retorno.equals(getDvMatricula())) {
				  mensageiro(ERROR , "Matrícula Inválida");
				  return;
			   }
			} else {
			   if( !cptemp.getTermo().equals("")) {
				  cptemp.setTermo(Utilitario.validaTermoPedido(cptemp.getTermo()));
			   }
			}
		 }
		 if(cptemp.getCorreios() == null && cptemp.getFormaEntrega() != 1) {
			cptemp.setCorreios(correio);
			cptemp.setEnderecoEntrega(new br.com.datasind.entidade.Endereco());
		 }

		 if(cptemp.getCorreios() == null) {
			mensageiro(ERROR , "A 'FORMA DE ENVIO' deve ser especificada.");
			return;
		 }
		 cptemp.setUsuarioSite(getUsuarioSiteSessao());
		 cptemp.setCustoPedido(getCustoPedidoSelecionado());
		 cptemp.setDataPedido(new Date());
		 cptemp.setBoletoPago(false);
		 cptemp.setBoletoGerado(false);
		 cptemp.setGuiaGeradaCartorio(false);
		 cptemp.setStatus(0);

		 gerenteControleAcesso.incluirEntidade(cptemp);

		 mensageiro(INFO , "Pedido encaminhado com sucesso!");
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel solicitar o pedido da certidão.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(ERROR , e.getMessage());
		 LOGGER.equals(e);
		 return;
	  }
   }

   public String linkCorreios() {
	  String url="http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=";
	  String correios=url + getCertidaoPedido2().getCodigoRastreio();
	  try {
		 FacesContext.getCurrentInstance().getExternalContext().redirect(correios);
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
	  return null;
   }

   public void definirCampos() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  if(getCustoPedidoSelecionado().getTipoCertidao().equals("CASAMENTO")) {
		 renderizaCasamento=true;
		 renderizaNascimento=false;
		 renderizaObitoNatimorto=false;
	  } else if(getCustoPedidoSelecionado().getTipoCertidao().equals("NASCIMENTO")) {
		 renderizaCasamento=false;
		 renderizaNascimento=true;
		 renderizaObitoNatimorto=false;
	  } else if(getCustoPedidoSelecionado().getTipoCertidao().equals("ÓBITO")) {
		 renderizaCasamento=false;
		 renderizaNascimento=false;
		 renderizaObitoNatimorto=true;
	  }
	  context.update("formCadastro:dadosCertidaoPanel");
	  context.update("formCadastro:custosPanelGrid");
   }

   public void verificaTipoEndereco() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  if(getUsaEndereco() == true) {
		 br.com.datasind.entidade.Endereco endereco=getUsuarioSiteSessao().getUsuarioSitePerfil().getEndereco();
		 certidaoPedido.setEnderecoEntrega(endereco);
		 disableEndereco=true;
	  } else {
		 br.com.datasind.entidade.Endereco endereco=new br.com.datasind.entidade.Endereco();
		 certidaoPedido.setEnderecoEntrega(endereco);
		 disableEndereco=false;
	  }
	  context.update("formCadastro:panelEnderecoEntrega");
   }

   public void updateTipoEndereco() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  context.update("formCadastro:panelEnderecoEntrega");
	  context.update("formCadastro:groupEnvio");
   }

   public void acaoAlterar(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();

	  try {
		 gerenteControleAcesso.atualizarEntidade(getCertidaoPedido());
		 acaoLimpar();
		 ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possível atualizar o pedido.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(WARNING , e.getMessage().toString());
		 LOGGER.error(e);
		 return;
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
	  context.execute("PF('dlg1').hide();");
	  mensageiro(INFO , "Pedido alterado com sucesso.");
   }

   public void validaTermo() {
	  String termo=getCertidaoPedido().getTermo();
	  termo=termo.replace("_" , "");
	  RequestContext context=RequestContext.getCurrentInstance();
	  if( !termo.equals("")) {
		 getCertidaoPedido().setTermo(Utilitario.validaTermoPedido(termo));
	  }
	  context.update("formCadastro:nTermo");
   }

   public void downloadCertidaoPDF() {
	  try {
		 FacesContext aFacesContext=FacesContext.getCurrentInstance();
		 ServletContext context=(ServletContext) aFacesContext.getExternalContext().getContext();
		 InputStream stream=new FileInputStream(context.getRealPath("/").replace("registrocivil\\" , "") + getCertidaoPedido2().getCertidaoUpload().getCaminhoDocumento());
		 file2=new DefaultStreamedContent(stream, "application/pdf", getCertidaoPedido2().getCertidaoUpload().getNomeArquivo());
	  } catch (FileNotFoundException e) {
		 e.printStackTrace();
	  }
   }

   public void gerarBoleto() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  GerenteBoleto gerenteBoleto=getFabricaGerente().getGerenteBoleto();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 if(getCertidaoPedido2().getId() == null) {
			mensageiro(ERROR , "Selecione um pedido.");
			return;
		 }
		 if(getCertidaoPedido2().getBoletoGerado() == null || getCertidaoPedido2().getBoletoGerado() == false) {
			getCertidaoPedido2().setBoletoGerado(true);
			getCertidaoPedido2().setDataBoleto(new Date());
			gerenteControleAcesso.atualizarEntidade(getCertidaoPedido2());
		 }

		 Sacado sacado=new Sacado("Alex Rocha", "046.084.741-46");
		 Endereco enderecoSac=new Endereco();
		 enderecoSac.setUF(UnidadeFederativa.TO);
		 enderecoSac.setLocalidade("Palmas");
		 enderecoSac.setCep(new CEP("77000-000"));
		 enderecoSac.setBairro("Plano Diretor Sul");
		 enderecoSac.setLogradouro("308 Sul");
		 enderecoSac.setNumero("58");
		 sacado.addEndereco(enderecoSac);

		 BigDecimal a=new BigDecimal(getCertidaoPedido2().getCustoPedido().getValorCertidao().toString());
		 BigDecimal b=new BigDecimal(getCertidaoPedido2().getCustoPedido().getTaxaAdministrativa().toString());
		 BigDecimal c=new BigDecimal(getCertidaoPedido2().getCorreios().getValor().toString());

		 valor=valor.add(c.add(b.add(a)));
		 FacesContext aFacesContext=FacesContext.getCurrentInstance();
		 ServletContext context2=(ServletContext) aFacesContext.getExternalContext().getContext();
		 String realPath=context2.getRealPath("/");
		 File file3=new File(realPath + "/resources/pdf/");
		 file3.mkdirs();
		 Boleto boleto=gerenteBoleto.geraBoletoSICOOB(sacado , valor);
		 setNomePdf("resources/pdf/" + TempFileNameHelper.getName("rep") + ".pdf");
		 BoletoViewer.create(boleto).getPdfAsFile(getContext() + getNomePdf());

		 setRenderizaBoleto(true);
		 context.update("formPDF");

	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }
   }

   public void acaoExcluir(ActionEvent event) {

	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

	  try {
		 gerenteControleAcesso.excluirEntidade(getCertidaoPedido());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel excluir o pedido.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Pedido excluido com sucesso.");
   }

   public List<String> completeCidadesCartorioTO(String s) {
	  List<String> results=new ArrayList<String>();
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 for(Cartorio c : gerenteControleAcesso.obterCidadesCartorioTO(s)) {
			results.add(c.getMunicipio());
		 }
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
	  return results;

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

   public void acaoLimpar() {
	  setCartorios(null);
	  setCertidaoPedido(null);
	  setListaCertidaoPedido(null);
   }

   public void populaSectCartorio() {

	  if( !getCertidaoPedido().getCidadePedido().equals("")) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			setCartorios(gerenteControleAcesso.obterCartoriosPorMunicipio(getCertidaoPedido().getCidadePedido()));
		 } catch (ApplicationException e) {
			e.printStackTrace();
		 }
	  }
   }

   public void onRowSelect(SelectEvent event) {

	  setCertidaoPedido((CertidaoPedido) event.getObject());
	  mensageiro(WARNING , "Pedido Certidao " + ((CertidaoPedido) event.getObject()).getId());

   }

   public CertidaoPedido getCertidaoPedido() {
	  if(certidaoPedido == null) {
		 certidaoPedido=new CertidaoPedido();
	  }
	  return certidaoPedido;
   }

   public void carregaPedido() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 certidaoPedido2=gerenteControleAcesso.obterCertidaoPedidoPorId(getCertidaoPedidoSelecionado());
		 if(certidaoPedido2.getMatricula() != null)
			setRenderizarMatricula(true);
		 else
			setRenderizarMatricula(false);
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }
	  RequestContext context=RequestContext.getCurrentInstance();
	  if(certidaoPedido2.getCustoPedido().getTipoCertidao().equals("CASAMENTO")) {
		 renderizaCasamento=true;
		 renderizaNascimento=false;
		 renderizaObitoNatimorto=false;
	  } else if(certidaoPedido2.getCustoPedido().getTipoCertidao().equals("NASCIMENTO")) {
		 renderizaCasamento=false;
		 renderizaNascimento=true;
		 renderizaObitoNatimorto=false;
	  } else if(certidaoPedido2.getCustoPedido().getTipoCertidao().equals("ÓBITO")) {
		 renderizaCasamento=false;
		 renderizaNascimento=false;
		 renderizaObitoNatimorto=true;
	  }
	  context.update("formUpdate");
   }

   public List<Cartorio> getCartorios() {
	  return cartorios;
   }

   public void setCartorios(List<Cartorio> cartorios) {
	  this.cartorios=cartorios;
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

   public void setCertidaoPedido(CertidaoPedido certidaoPedido) {
	  this.certidaoPedido=certidaoPedido;
   }

   public List<CertidaoPedido> getListaCertidaoPedido() {
	  if(listaCertidaoPedido == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			listaCertidaoPedido=new ArrayList<CertidaoPedido>();

			listaCertidaoPedido=gerenteControleAcesso.obterListaCertidaoPedidos();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar os pedidos de certidoes");
		 }
	  }
	  return listaCertidaoPedido;
   }

   public List<CertidaoPedido> getMeusPedidos() {
	  if(listaCertidaoPedido == null) {
		 listaCertidaoPedido=new ArrayList<>();
	  }
	  return listaCertidaoPedido;
   }

   public void buscarPedidos() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 UsuarioSite usuarioSite=getUsuarioSiteSessao();
		 RequestContext context=RequestContext.getCurrentInstance();

		 if(getPeriodoInicial().after(getPeriodoFinal())) {
			mensageiro(ERROR , "A DATA INICIAL NÃO PODE SER MAIOR QUE A FINAL.");
			return;
		 }

		 listaCertidaoPedido=gerenteControleAcesso.obterListaPedidosByUsuarioPeriodo(usuarioSite , getPeriodoInicial() , getPeriodoFinal());
		 if(listaCertidaoPedido.isEmpty()) {
			mensageiro(WARNING , "Nenhum pedido encontrado.");
			return;
		 }
		 context.update("formCadastro");
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  } catch (FinderException fe) {
		 listaCertidaoPedido=new ArrayList<>();
		 mensageiro(WARNING , "Nenhum pedido encontrado.");
		 return;
	  }
   }

   public void habilitaPeriodoFinal() {
	  setDisablePeriodoFinal(false);
   }

   public void setListaCertidaoPedido(List<CertidaoPedido> listaCertidaoPedido) {
	  this.listaCertidaoPedido=listaCertidaoPedido;
   }

   public CertidaoPedido getCertidaoPedidoSelecionado() {
	  if(certidaoPedidoSelecionado == null) {
		 certidaoPedidoSelecionado=new CertidaoPedido();
	  }
	  return certidaoPedidoSelecionado;
   }

   public void setCertidaoPedidoSelecionado(CertidaoPedido certidaoPedidoSelecionado) {
	  this.certidaoPedidoSelecionado=certidaoPedidoSelecionado;
   }

   public List<Correios> getCorreios() {
	  if(correios == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			correios=new ArrayList<Correios>();
			correios=gerenteControleAcesso.obterListaPrecosCorrreios();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de dados do envio");
		 }
	  }
	  return correios;
   }

   public void habilitaListCidades() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidade(false);
	  context.update("formCadastro:municipio");
   }

   public List<CustoPedido> getCustosPedido() {
	  if(custosPedido == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			custosPedido=gerenteControleAcesso.obterCustosPedido();
		 } catch (ApplicationException e) {
			e.printStackTrace();
			mensageiro(ERROR , "Erro ao buscar os Custos do Pedido");
		 }
	  }
	  return custosPedido;
   }

   public void setCustosPedido(List<CustoPedido> custosPedido) {
	  this.custosPedido=custosPedido;
   }

   public void setCorreios(List<Correios> correios) {
	  this.correios=correios;
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

   public Boolean getDisableCidade() {
	  return disableCidade;
   }

   public void setDisableCidade(Boolean disableCidade) {
	  this.disableCidade=disableCidade;
   }

   public Boolean getRenderizaCasamento() {
	  return renderizaCasamento;
   }

   public void setRenderizaCasamento(Boolean renderizaCasamento) {
	  this.renderizaCasamento=renderizaCasamento;
   }

   public Boolean getRenderizaNascimento() {
	  return renderizaNascimento;
   }

   public void setRenderizaNascimento(Boolean renderizaNascimento) {
	  this.renderizaNascimento=renderizaNascimento;
   }

   public Boolean getRenderizaObitoNatimorto() {
	  return renderizaObitoNatimorto;
   }

   public void setRenderizaObitoNatimorto(Boolean renderizaObitoNatimorto) {
	  this.renderizaObitoNatimorto=renderizaObitoNatimorto;
   }

   public CustoPedido getCustoPedidoSelecionado() {
	  return custoPedidoSelecionado;
   }

   public void setCustoPedidoSelecionado(CustoPedido custoPedidoSelecionado) {
	  this.custoPedidoSelecionado=custoPedidoSelecionado;
   }

   public void setFile(StreamedContent file) {
	  this.file=file;
   }

   public StreamedContent getFile() throws FileNotFoundException {
	  InputStream stream=new FileInputStream(getContext() + "resources/pdf/" + getNomePdf()); // Caminho onde está salvo
	  file=new DefaultStreamedContent(stream, "application/pdf", getNomePdf());
	  return file;
   }

   public String getNomePdf() {
	  return nomePdf;
   }

   public void setNomePdf(String nomePdf) {
	  this.nomePdf=nomePdf;
   }

   public Boolean getRenderizaBoleto() {
	  return renderizaBoleto;
   }

   public void setRenderizaBoleto(Boolean renderizaBoleto) {
	  this.renderizaBoleto=renderizaBoleto;
   }

   public CertidaoPedido getCertidaoPedido2() {
	  return certidaoPedido2;
   }

   public void setCertidaoPedido2(CertidaoPedido certidaoPedido2) {
	  this.certidaoPedido2=certidaoPedido2;
   }

   public Boolean getUsaEndereco() {
	  return usaEndereco;
   }

   public void setUsaEndereco(Boolean usaEndereco) {
	  this.usaEndereco=usaEndereco;
   }

   public Boolean getDisableEndereco() {
	  return disableEndereco;
   }

   public void setDisableEndereco(Boolean disableEndereco) {
	  this.disableEndereco=disableEndereco;
   }

   public BigDecimal getValor() {
	  return valor;
   }

   public void setValor(BigDecimal valor) {
	  this.valor=valor;
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

   public Boolean getPossuiMatricula() {
	  return possuiMatricula;
   }

   public void setPossuiMatricula(Boolean possuiMatricula) {
	  this.possuiMatricula=possuiMatricula;
   }

   public Boolean getRenderizarMatricula() {
	  return renderizarMatricula;
   }

   public void setRenderizarMatricula(Boolean renderizarMatricula) {
	  this.renderizarMatricula=renderizarMatricula;
   }

   public Date getPeriodoInicial() {
	  return periodoInicial;
   }

   public void setPeriodoInicial(Date periodoInicial) {
	  this.periodoInicial=periodoInicial;
   }

   public Date getPeriodoFinal() {
	  return periodoFinal;
   }

   public void setPeriodoFinal(Date periodoFinal) {
	  this.periodoFinal=periodoFinal;
   }

   public Boolean getDisablePeriodoFinal() {
	  return disablePeriodoFinal;
   }

   public void setDisablePeriodoFinal(Boolean disablePeriodoFinal) {
	  this.disablePeriodoFinal=disablePeriodoFinal;
   }

   public Correios getCorreio() {
	  return correio;
   }

   public void setCorreio(Correios correio) {
	  this.correio=correio;
   }

   public StreamedContent getFile2() {
	  return file2;
   }

   public void setFile2(StreamedContent file2) {
	  this.file2=file2;
   }
}
