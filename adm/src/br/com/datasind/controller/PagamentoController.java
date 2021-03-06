
package br.com.datasind.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.Pagamento;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="pagamento")
@ViewScoped
public class PagamentoController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;
   private Pagamento pagamento;
   private List<Pagamento> listaPagamentos;
   private Pagamento pagamentoSelecionado=null;
   private List<Cartorio> cartorios;
   private Cartorio cartorioSelecionado = null;
   private String mes, ano="";
   private Boolean disableAno;
   private List<SelectItem> anos=new ArrayList<SelectItem>();

   public void buscarPagamentos() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 RequestContext context=RequestContext.getCurrentInstance();
		 if(getCartorioSelecionado() == null) {
			setListaPagamentos(gerenteControleAcesso.obterListaPagamentosPeriodo(getMes() , getAno()));
		 } else {
			setListaPagamentos(gerenteControleAcesso.obterListaPagamentosByCartorioPeriodo(getCartorioSelecionado() , getMes() , getAno()));
		 }
		 if(getListaPagamentos().size() < 1) {
			mensageiro(ERROR , "Nenhum pagamento encontrado.");
			setListaPagamentos(new ArrayList<Pagamento>());
		 }
		 context.update("formCadastro:pagamentosCartorio");
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
   }

   public void pagar() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 RequestContext context=RequestContext.getCurrentInstance();
		 getPagamento().setPago(true);
		 getPagamento().setDataPagamento(new Date());
		 context.execute("PF('dlg2').hide()");
		 context.update("formCadastro:pagamentosCartorio");

		 gerenteControleAcesso.atualizarEntidade(getPagamento());
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }
   }

   public void gerarPagamentosCartorioPeriodo() {
	  @SuppressWarnings("deprecation")
	  Date data=new Date(getAno() + "-" + getMes() + "-01");
	  System.out.println(data);
   }

   public void carregaPagamento() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setPagamento(getPagamentoSelecionado());
	  context.update("formPagamentoCartorio");
   }

   public Cartorio getCartorioSelecionado() {
	  return cartorioSelecionado;
   }

   public void setCartorioSelecionado(Cartorio cartorioSelecionado) {
	  this.cartorioSelecionado=cartorioSelecionado;
   }

   public void setPagamentoSelecionado(Pagamento pagamentoSelecionado) {
	  this.pagamentoSelecionado=pagamentoSelecionado;
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 gerenteControleAcesso.incluirEntidade(getPagamento());

		 mensageiro(INFO , "Pagamento cadastrado com sucesso!");
		 context.execute("PF('dlgNovoUsuario').hide()");
		 context.update("formCadastro:pagamentosCartorio");
		 acaoLimpar();
		 setPagamento(null);
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
	  RequestContext context=RequestContext.getCurrentInstance();

	  try {
		 gerenteControleAcesso.atualizarEntidade(getPagamento());
		 context.update("formCadastro:pagamentosCartorio");
		 context.execute("PF('dlgStatus').hide()");
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
	  RequestContext context=RequestContext.getCurrentInstance();
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 gerenteControleAcesso.excluirEntidade(getPagamento());
		 setpagamentoSelecionado(null);
		 getListaPagamentos().remove(getPagamento());
		 context.update("formCadastro:pagamentosCartorio");
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel excluir a guia para pagamento.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Usuario excluido com sucesso.");
   }

   public void acaoLimpar() {

	  setPagamento(null);
	  setpagamentoSelecionado(null);
	  setListaPagamentos(null);
   }

   public Pagamento getPagamento() {
	  if(pagamento == null) {
		 pagamento=new Pagamento();
	  }
	  return pagamento;
   }

   public void setPagamento(Pagamento pagamento) {
	  this.pagamento=pagamento;
   }

   public List<Pagamento> getListaPagamentos() {
	  if(listaPagamentos == null) {
		 listaPagamentos=new ArrayList<Pagamento>();
	  }
	  return listaPagamentos;
   }

   public void setListaPagamentos(List<Pagamento> listaPagamentos) {
	  this.listaPagamentos=listaPagamentos;
   }

   public Pagamento getPagamentoSelecionado() {
	  if(pagamentoSelecionado == null) {
		 pagamentoSelecionado=new Pagamento();
	  }
	  return pagamentoSelecionado;
   }

   public void setpagamentoSelecionado(Pagamento pagamentoSelecionado) {
	  this.pagamentoSelecionado=pagamentoSelecionado;
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

   public void habilitaAno() {
	  setDisableAno(false);
   }

   public void setCartorios(List<Cartorio> cartorios) {
	  this.cartorios=cartorios;
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

   public Boolean getDisableAno() {
	  return disableAno;
   }

   public void setDisableAno(Boolean disableAno) {
	  this.disableAno=disableAno;
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
}
