
package br.com.datasind.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.ContaBancaria;
import br.com.datasind.entidade.Correios;
import br.com.datasind.entidade.CustoPedido;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.Pagamento;
import br.com.datasind.entidade.UF;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="certidaoPedido")
@ViewScoped
public class CertidaoPedidoController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;

   private CertidaoPedido certidaoPedido;
   private List<Correios> correios;
   private Cartorio cartorioSelecionado;
   private List<CertidaoPedido> listaCertidaoPedido;
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
   private String mes, ano="";
   private List<SelectItem> anos=new ArrayList<SelectItem>();

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
		 if(getCertidaoPedido().getStatus() != 0 || getCertidaoPedido().getStatus() != 1) {
			getCertidaoPedido().setBoletoPago(true);
		 } else {
			getCertidaoPedido().setBoletoPago(false);
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

   public void buscarPedidosPagarCartorio() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 RequestContext context=RequestContext.getCurrentInstance();
		 if(getCartorioSelecionado() == null) {
			setListaCertidaoPedido(gerenteControleAcesso.obterListaCertidoesPagarByPeriodo(getMes() , getAno()));
		 } else {
			setListaCertidaoPedido(gerenteControleAcesso.obterListaCertidoesPagarByCartorioPeriodo(getCartorioSelecionado() , getMes() , getAno()));
		 }
		 if(getListaCertidaoPedido().size() < 1) {
			mensageiro(ERROR , "Nenhum certid??o a pagar encontrada.");
			setListaCertidaoPedido(new ArrayList<CertidaoPedido>());
		 }
		 context.update("formCadastro:pagamentosToCartorio");
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
   }

   public void gerarGuiaPagamento() {
	  if(getCartorioSelecionado() == null) {
		 gerarTodasGuias();
	  } else {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			FacesContext fc=FacesContext.getCurrentInstance();
			Flash flash=fc.getExternalContext().getFlash();
			Pagamento pagamento=new Pagamento();
			pagamento.setPago(false);
			pagamento.setQtdBoletos(getListaCertidaoPedido().size());
			BigDecimal soma=new BigDecimal(0);
			for(CertidaoPedido cp : getListaCertidaoPedido()) {
			   BigDecimal a=new BigDecimal(cp.getCustoPedido().getValorCertidao().toString());
			   BigDecimal b=new BigDecimal(cp.getCustoPedido().getTaxaAdministrativa().toString());
			   BigDecimal c=new BigDecimal(cp.getCorreios().getValor().toString());
			   soma=soma.add(c.add(b.add(a)));
			}
			pagamento.setCartorio(getListaCertidaoPedido().get(0).getCartorio());
			pagamento.setPeriodo(getListaCertidaoPedido().get(0).getDataBoleto());
			for(ContaBancaria cb : getListaCertidaoPedido().get(0).getCartorio().getContasBancarias()) {
			   if(cb.getContaAtiva() == true) {
				  pagamento.setContaBancaria(cb);
			   }
			}
			if(pagamento.getContaBancaria() == null) {
			   mensageiro(ERROR , "O Cart??rio n??o possui uma conta ativa!");
			   return;
			}
			pagamento.setTotal(soma);
			gerenteControleAcesso.incluirEntidade(pagamento);

			for(CertidaoPedido cp : getListaCertidaoPedido()) {
			   cp.setGuiaGeradaCartorio(true);
			   cp.setPagamento(pagamento);
			   gerenteControleAcesso.atualizarEntidade(cp);
			}

			flash.setKeepMessages(true);
			flash.setRedirect(true);

			ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
			fc.addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO, "Guia gerada com sucesso!", null));

			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		 } catch (IOException | ApplicationException | ValidacaoException e) {
			e.printStackTrace();
		 }
	  }
   }

   public void gerarTodasGuias() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 FacesContext fc=FacesContext.getCurrentInstance();
		 Flash flash=fc.getExternalContext().getFlash();

		 List<CertidaoPedido> certidoesPedidoT=getListaCertidaoPedido();
		 Integer tamanhoCertidao=getListaCertidaoPedido().size();
		 while ( !certidoesPedidoT.isEmpty()) {
			List<CertidaoPedido> certTemp=new ArrayList<CertidaoPedido>();
			Integer contador=0;
			Pagamento pagamento=new Pagamento();
			pagamento.setPago(false);
			BigDecimal soma=new BigDecimal(0);
			List<Cartorio> cartTemp=getCartorios();
			Integer tamCart = cartTemp.size();
			for(CertidaoPedido cp : getListaCertidaoPedido()) {
			   while ( !cartTemp.isEmpty()) {
				  // verifica se o valor da lista ?? igual ao cartorio e separa
				  if(cp.getCartorio().equals(cartTemp.get(tamCart - 1))) {
					 certTemp.add(cp);
					 BigDecimal a=new BigDecimal(cp.getCustoPedido().getValorCertidao().toString());
					 BigDecimal b=new BigDecimal(cp.getCustoPedido().getTaxaAdministrativa().toString());
					 BigDecimal c=new BigDecimal(cp.getCorreios().getValor().toString());
					 soma=soma.add(c.add(b.add(a)));
					 // saber a quantidade de boletos para o cartorio
					 contador=contador + 1;
				  }
				  cartTemp.remove(tamCart-1);
				  tamCart = tamCart - 1;
			   }
			}
			pagamento.setQtdBoletos(contador);
			pagamento.setCartorio(certTemp.get(0).getCartorio());
			pagamento.setPeriodo(certTemp.get(0).getDataBoleto());
			for(ContaBancaria cb : certTemp.get(0).getCartorio().getContasBancarias()) {
			   if(cb.getContaAtiva() == true) {
				  pagamento.setContaBancaria(cb);
			   }
			}
			if(pagamento.getContaBancaria() == null) {
			   mensageiro(ERROR , "O Cart??rio n??o possui uma conta ativa!");
			   return;
			}
			pagamento.setTotal(soma);
			gerenteControleAcesso.incluirEntidade(pagamento);

			for(CertidaoPedido cp : certTemp) {
			   cp.setGuiaGeradaCartorio(true);
			   cp.setPagamento(pagamento);
			   gerenteControleAcesso.atualizarEntidade(cp);
			   certidoesPedidoT.remove(cp);
			}

			tamanhoCertidao=tamanhoCertidao - contador;
		 }
		 flash.setKeepMessages(true);
		 flash.setRedirect(true);
		 ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
		 fc.addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO, "Guia gerada com sucesso!", null));
		 ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	  } catch (ApplicationException | ValidacaoException | IOException e) {
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

   public void reload() {
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
	  try {
		 ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
   }

   public void carregaPedido() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  certidaoPedido=getCertidaoPedidoSelecionado();
	  if(certidaoPedido.getCustoPedido().getTipoCertidao().equals("CASAMENTO")) {
		 renderizaCasamento=true;
		 renderizaNascimento=false;
		 renderizaObitoNatimorto=false;
	  } else if(certidaoPedido.getCustoPedido().getTipoCertidao().equals("NASCIMENTO")) {
		 renderizaCasamento=false;
		 renderizaNascimento=true;
		 renderizaObitoNatimorto=false;
	  } else if(certidaoPedido.getCustoPedido().getTipoCertidao().equals("??BITO")) {
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

   public List<Cartorio> getCartorios() {
	  if(cartorios.isEmpty()) {
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

   public String getMes() {
	  return mes;
   }

   public void setMes(String mes) {
	  this.mes=mes;
   }

   public String getAno() {
	  return ano;
   }

   public void setAno(String ano) {
	  this.ano=ano;
   }

   public List<SelectItem> getAnos() {
	  final int initialYear=2010;
	  final int currentYear=Calendar.getInstance().get(Calendar.YEAR);
	  for(int year=currentYear; year > initialYear; year--) {
		 anos.add(new SelectItem(year, String.valueOf(year)));
	  }
	  return anos;
   }

   public void setAnos(List<SelectItem> anos) {
	  this.anos=anos;
   }

   public Cartorio getCartorioSelecionado() {
	  return cartorioSelecionado;
   }

   public void setCartorioSelecionado(Cartorio cartorioSelecionado) {
	  this.cartorioSelecionado=cartorioSelecionado;
   }
}
