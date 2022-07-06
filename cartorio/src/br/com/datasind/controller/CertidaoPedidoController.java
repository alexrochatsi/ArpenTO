
package br.com.datasind.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.FinderException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.CertidaoUpload;
import br.com.datasind.entidade.Correios;
import br.com.datasind.entidade.CustoPedido;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.UF;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="certidaoPedido")
@ViewScoped
public class CertidaoPedidoController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;

   private CertidaoPedido certidaoPedido;
   private List<Correios> correios;
   private List<CertidaoPedido> listaCertidaoPedido;
   private List<CertidaoPedido> financeiroPedidos;
   private CertidaoPedido certidaoPedidoSelecionado;
   private List<Cartorio> cartorios=new ArrayList<>();
   private List<CustoPedido> custosPedido;
   private List<UF> ufs=new ArrayList<>();
   private UF ufSelecionada;
   private List<IbgeUF> municipios=new ArrayList<IbgeUF>();
   private Boolean disableCidade=true;
   private Boolean disablePeriodoFinal=true;
   private Boolean renderizaCasamento=false;
   private Boolean renderizaNascimento=true;
   private Boolean renderizaObitoNatimorto=false;
   private BigDecimal totalPedido;
   private BigDecimal totalPedidoPago;
   private BigDecimal totalPedidoNaoPago;
   private Date periodoInicial;
   private Date periodoFinal;
   private Boolean renderizarMatricula=false;
   private CertidaoUpload certidaoUpload=new CertidaoUpload();
   private File file;
   private String caminho;
   private byte[] arquivo;

   @PostConstruct
   public void init() {
	  TimeZone tz=TimeZone.getTimeZone("America/Sao_Paulo");
	  TimeZone.setDefault(tz);
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 CertidaoPedido cptemp=new CertidaoPedido();

		 cptemp=getCertidaoPedido();
		 cptemp.setDataPedido(new Date());
		 cptemp.setBoletoPago(false);
		 cptemp.setGuiaGeradaCartorio(false);
		 cptemp.setStatus(0);

		 gerenteControleAcesso.incluirEntidade(cptemp);

		 mensageiro(INFO , "Pedido encaminhado com sucesso!");
		 acaoLimpar();
		 setCertidaoPedido(null);
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel solicitar o pedido da certidao.");
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
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 if(getCertidaoPedido().getCodigoRastreio() != null) {
			if(getCertidaoPedido().getStatus() != 4) {
			   getCertidaoPedido().setCodigoRastreio("");
			}
		 }
		 if(getCertidaoPedido().getStatus() == 3 || getCertidaoPedido().getStatus() == 4) {
			if(getCertidaoPedido().getBoletoGerado() == true && getCertidaoPedido().getDataBoleto() != null) {
			   getCertidaoPedido().setBoletoPago(true);
			} else {
			   mensageiro(ERROR , "O Boleto ainda não foi gerado.");
			   return;
			}
		 } else {
			getCertidaoPedido().setBoletoPago(false);
		 }

		 if(getCertidaoPedido().getStatus() == 4) {
			CertidaoUpload ctupTemp=getCertidaoUpload();
			if(ctupTemp == null || ctupTemp.getNomeArquivo() == null || ctupTemp.getNomeArquivo().equals("")) {
			   mensageiro(ERROR , "Anexar a certidão certificada!");
			   return;
			}
			// esse trecho grava o arquivo no diretório
			FileOutputStream fos;
			try {
			   fos=new FileOutputStream(caminho);
			   fos.write(arquivo);
			   fos.close();
			} catch (IOException e) {
			   e.printStackTrace();
			}
			ctupTemp.setDataUpload(new Date());
			ctupTemp.setCertificada(true);
			if(getCertidaoPedido().getCertidaoUpload() == null) {
			   gerenteControleAcesso.incluirEntidade(ctupTemp);
			} else {
			   ctupTemp.setId(getCertidaoPedido().getCertidaoUpload().getId());
			   gerenteControleAcesso.atualizarEntidade(ctupTemp);
			}
			getCertidaoPedido().setCertidaoUpload(ctupTemp);
		 }

		 gerenteControleAcesso.atualizarEntidade(getCertidaoPedido());
		 acaoLimpar();
		 context.execute("PF('dlgStatus').hide();");
		 context.update("formCadastro");
		 context.update("formStatusPedido");
		 context.update("formUpdate");
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel atualizar o pedido.");
		 LOGGER.error(e);
		 acaoLimpar();
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(WARNING , e.getMessage().toString());
		 LOGGER.error(e);
		 acaoLimpar();
		 return;
	  }

	  mensageiro(INFO , "Pedido alterado com sucesso.");
   }

   @SuppressWarnings("unused")
   public void doUpload(FileUploadEvent event) throws FileNotFoundException {
	  RequestContext rc=RequestContext.getCurrentInstance();
	  ExternalContext externalContext=FacesContext.getCurrentInstance().getExternalContext();
	  HttpServletResponse response=(HttpServletResponse) externalContext.getResponse();
	  FacesContext aFacesContext=FacesContext.getCurrentInstance();
	  ServletContext context=(ServletContext) aFacesContext.getExternalContext().getContext();
	  String realPath=context.getRealPath("/");
	  setArquivo(event.getFile().getContents());
	  setCaminho(realPath.replace("cartorio\\" , "") + "/CERTIDOES/" + event.getFile().getFileName());

	  // Aqui cria o diretorio caso não exista
	  setFile(new File(realPath.replace("cartorio\\" , "") + "/CERTIDOES/"));
	  file.mkdirs();
	  getCertidaoUpload().setCaminhoDocumento("/CERTIDOES/" + event.getFile().getFileName());
	  getCertidaoUpload().setNomeArquivo(event.getFile().getFileName());
	  rc.update("formStatusPedido:caminhoDoc");
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

   public void carregaPedido() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 certidaoPedido=gerenteControleAcesso.obterCertidaoPedidoPorId(getCertidaoPedidoSelecionado());
		 if(certidaoPedido.getMatricula() != null)
			setRenderizarMatricula(true);
		 else
			setRenderizarMatricula(false);
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }
	  RequestContext context=RequestContext.getCurrentInstance();
	  if(certidaoPedido.getCustoPedido().getTipoCertidao().equals("CASAMENTO")) {
		 renderizaCasamento=true;
		 renderizaNascimento=false;
		 renderizaObitoNatimorto=false;
	  } else if(certidaoPedido.getCustoPedido().getTipoCertidao().equals("NASCIMENTO")) {
		 renderizaCasamento=false;
		 renderizaNascimento=true;
		 renderizaObitoNatimorto=false;
	  } else if(certidaoPedido.getCustoPedido().getTipoCertidao().equals("ÓBITO")) {
		 renderizaCasamento=false;
		 renderizaNascimento=false;
		 renderizaObitoNatimorto=true;
	  }
	  context.update("formUpdate");
	  context.update("formStatusPedido");
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
	  setCertidaoPedidoSelecionado(null);
	  setCertidaoPedido(null);
	  setListaCertidaoPedido(null);
	  setCertidaoUpload(new CertidaoUpload());
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

   public void habilitaPeriodoFinal() {
	  setDisablePeriodoFinal(false);
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

   public List<CertidaoPedido> getPedidosCartorio() {
	  if(listaCertidaoPedido == null) {
		 listaCertidaoPedido=new ArrayList<CertidaoPedido>();
	  }
	  return listaCertidaoPedido;
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
			mensageiro(ERROR , "Erro ao buscar os pedidos de certidoes");
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
			custosPedido=new ArrayList<CustoPedido>();

			custosPedido=gerenteControleAcesso.obterCustosPedido();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar os Custos do Pedido");
		 }
	  }
	  return custosPedido;
   }

   public Calendar converterData(Date data) {
	  Calendar cal=Calendar.getInstance();
	  cal.setTime(data);
	  return cal;
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

   public List<CertidaoPedido> getFinanceiroPedidos() {
	  if(financeiroPedidos == null) {
		 financeiroPedidos=new ArrayList<>();
		 /*
		  * try { GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		  * UsuarioCartorio usuarioCartorio=getUsuarioCartorioSessao();
		  * 
		  * financeiroPedidos=gerenteControleAcesso.obterListaPedidosBoletoGeradosAll(usuarioCartorio.getCartorio());
		  * if(financeiroPedidos.isEmpty()) { mensageiro(WARNING , "Nenhum boleto encontrado."); return new
		  * ArrayList<>(); } totalPedido=new BigDecimal(0); totalPedidoPago=new BigDecimal(0); totalPedidoNaoPago=new
		  * BigDecimal(0); if(financeiroPedidos.size() > 0) { for(int i=0; i < financeiroPedidos.size(); i++) {
		  * BigDecimal a=new BigDecimal(financeiroPedidos.get(i).getCustoPedido().getValorCertidao().toString());
		  * BigDecimal b=new BigDecimal(financeiroPedidos.get(i).getCustoPedido().getTaxaAdministrativa().toString());
		  * BigDecimal c=new BigDecimal(financeiroPedidos.get(i).getCorreios().getValor().toString());
		  * if(financeiroPedidos.get(i).getBoletoPago() == true) totalPedidoPago=totalPedidoPago.add(c.add(b.add(a)));
		  * if(financeiroPedidos.get(i).getBoletoPago() == false)
		  * totalPedidoNaoPago=totalPedidoNaoPago.add(c.add(b.add(a))); totalPedido=totalPedido.add(c.add(b.add(a))); }
		  * } } catch (ApplicationException e) { mensageiro(WARNING , "Não há boletos gerados."); } catch
		  * (FinderException e) { totalPedido=new BigDecimal(0); totalPedidoPago=new BigDecimal(0);
		  * totalPedidoNaoPago=new BigDecimal(0); financeiroPedidos=new ArrayList<>(); mensageiro(WARNING ,
		  * "Nenhum boleto encontrado."); return financeiroPedidos; }
		  */
	  }
	  return financeiroPedidos;
   }

   @SuppressWarnings("deprecation")
   public void buscarBoletos() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 UsuarioCartorio usuarioCartorio=getUsuarioCartorioSessao();
		 RequestContext context=RequestContext.getCurrentInstance();

		 if(getPeriodoInicial().getMonth() != getPeriodoFinal().getMonth() || getPeriodoInicial().getYear() != getPeriodoFinal().getYear()) {
			mensageiro(ERROR , "O MÊS E ANO DEVEM SER IGUAIS.");
			totalPedido=new BigDecimal(0);
			totalPedidoPago=new BigDecimal(0);
			totalPedidoNaoPago=new BigDecimal(0);
			financeiroPedidos=new ArrayList<>();
			return;
		 }

		 if(getPeriodoInicial().after(getPeriodoFinal())) {
			mensageiro(ERROR , "A DATA INICIAL NÃO PODE SER MAIOR QUE A FINAL.");
			return;
		 }

		 financeiroPedidos=gerenteControleAcesso.obterListaPedidosBoletoGerados(usuarioCartorio.getCartorio() , getPeriodoInicial() , getPeriodoFinal());
		 if(financeiroPedidos.isEmpty()) {
			mensageiro(WARNING , "Nenhum boleto encontrado.");
			return;
		 }
		 totalPedido=new BigDecimal(0);
		 totalPedidoPago=new BigDecimal(0);
		 totalPedidoNaoPago=new BigDecimal(0);
		 if(financeiroPedidos.size() > 0) {
			for(int i=0; i < financeiroPedidos.size(); i++) {
			   BigDecimal a=new BigDecimal(financeiroPedidos.get(i).getCustoPedido().getValorCertidao().toString());
			   BigDecimal b=new BigDecimal(financeiroPedidos.get(i).getCustoPedido().getTaxaAdministrativa().toString());
			   BigDecimal c=new BigDecimal(financeiroPedidos.get(i).getCorreios().getValor().toString());
			   if(financeiroPedidos.get(i).getBoletoPago() == true)
				  totalPedidoPago=totalPedidoPago.add(c.add(b.add(a)));
			   if(financeiroPedidos.get(i).getBoletoPago() == false)
				  totalPedidoNaoPago=totalPedidoNaoPago.add(c.add(b.add(a)));
			   totalPedido=totalPedido.add(c.add(b.add(a)));
			}
		 }
		 context.update("formCadastro");
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  } catch (FinderException fe) {
		 totalPedido=new BigDecimal(0);
		 totalPedidoPago=new BigDecimal(0);
		 totalPedidoNaoPago=new BigDecimal(0);
		 financeiroPedidos=new ArrayList<>();
		 mensageiro(WARNING , "Nenhum boleto encontrado.");
		 return;
	  }
   }

   public void buscarPedidos() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 UsuarioCartorio usuarioCartorio=getUsuarioCartorioSessao();
		 RequestContext context=RequestContext.getCurrentInstance();

		 if(getPeriodoInicial().after(getPeriodoFinal())) {
			mensageiro(ERROR , "A DATA INICIAL NÃO PODE SER MAIOR QUE A FINAL.");
			return;
		 }

		 listaCertidaoPedido=gerenteControleAcesso.obterListaPedidos(usuarioCartorio.getCartorio() , getPeriodoInicial() , getPeriodoFinal());
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

   public void setFinanceiroPedidos(List<CertidaoPedido> financeiroPedidos) {
	  this.financeiroPedidos=financeiroPedidos;
   }

   public BigDecimal getTotalPedido() {
	  return totalPedido;
   }

   public void setTotalPedido(BigDecimal totalPedido) {
	  this.totalPedido=totalPedido;
   }

   public BigDecimal getTotalPedidoPago() {
	  return totalPedidoPago;
   }

   public void setTotalPedidoPago(BigDecimal totalPedidoPago) {
	  this.totalPedidoPago=totalPedidoPago;
   }

   public BigDecimal getTotalPedidoNaoPago() {
	  return totalPedidoNaoPago;
   }

   public void setTotalPedidoNaoPago(BigDecimal totalPedidoNaoPago) {
	  this.totalPedidoNaoPago=totalPedidoNaoPago;
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

   public Boolean getRenderizarMatricula() {
	  return renderizarMatricula;
   }

   public void setRenderizarMatricula(Boolean renderizarMatricula) {
	  this.renderizarMatricula=renderizarMatricula;
   }

   public CertidaoUpload getCertidaoUpload() {
	  return certidaoUpload;
   }

   public void setCertidaoUpload(CertidaoUpload certidaoUpload) {
	  this.certidaoUpload=certidaoUpload;
   }

   public File getFile() {
	  return file;
   }

   public void setFile(File file) {
	  this.file=file;
   }

   public String getCaminho() {
	  return caminho;
   }

   public void setCaminho(String caminho) {
	  this.caminho=caminho;
   }

   public byte[] getArquivo() {
	  return arquivo;
   }

   public void setArquivo(byte[] arquivo) {
	  this.arquivo=arquivo;
   }
}
