
package br.com.datasind.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Banco;
import br.com.datasind.entidade.ContaBancaria;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="contaBancaria")
@ViewScoped
public class ContaBancariaController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;
   private ContaBancaria contaBancaria=new ContaBancaria();
   private ContaBancaria contaBancariaSelecionada=new ContaBancaria();
   private List<Banco> bancos=new ArrayList<>();
   private List<ContaBancaria> contasBancarias=new ArrayList<>();
   private Boolean disableCPF;

   public void acaoIncluir(ActionEvent event) {
	  /* GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 String cpfCnpj=getContaBancaria().getCpfCNPJ().replaceAll("[./,-]" , "");
		 System.out.println("VALOR SEM MASCARA: "+cpfCnpj);
		 if(!Utilitaria.isCnpjCpf(cpfCnpj)) {
			mensageiro(ERROR , "O CPF / CNPJ informado não é válido.");
			return;
		 }
		 getContaBancaria().setCartorio(getUsuarioCartorioSessao().getCartorio());
		 getContaBancaria().setNomeCompleto(getContaBancaria().getNomeCompleto().toUpperCase());
		 gerenteControleAcesso.incluirEntidade(getContaBancaria());

		 mensageiro(INFO , "Conta Cadastrada com Sucesso!");
		 acaoLimpar();
		 context.update("formCadastro");
		 context.execute("PF('dlgContaBancaria').hide();");
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel cadastrar sua conta.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(ERROR , e.getMessage());
		 LOGGER.equals(e);
		 return;
	  }*/
   }

   public void acaoAlterar(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();

	  try {
		 gerenteControleAcesso.atualizarEntidade(getContaBancariaSelecionada());
		 acaoLimpar();
		 ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possível atualizar sua conta.");
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
	  mensageiro(INFO , "Conta alterada com sucesso.");
   }

   public void acaoExcluir(ActionEvent event) {
	  RequestContext context=RequestContext.getCurrentInstance();
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

	  try {

		 if(getContaBancariaSelecionada().getContaAtiva() != null && getContaBancariaSelecionada().getContaAtiva() == true) {
			mensageiro(ERROR , "Não é possível excluir uma conta ativa! Alterne para outra conta.");
			return;
		 }

		 gerenteControleAcesso.excluirEntidade(getContaBancariaSelecionada());
		 context.update("formCadastro");
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel excluir sua conta.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Conta excluida com sucesso.");
   }

   public void acaoLimpar() {
	  setContaBancaria(new ContaBancaria());
	  setBancos(new ArrayList<Banco>());
   }

   public ContaBancaria getContaBancaria() {
	  return contaBancaria;
   }

   public void setContaBancaria(ContaBancaria contaBancaria) {
	  this.contaBancaria=contaBancaria;
   }

   public List<Banco> getBancos() {
	  if(bancos.isEmpty()) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			bancos=gerenteControleAcesso.obterBancos();
		 } catch (ApplicationException e) {
			e.printStackTrace();
			mensageiro(ERROR , "Erro ao buscar os Bancos");
		 }
	  }
	  return bancos;
   }

   public void setBancos(List<Banco> bancos) {
	  this.bancos=bancos;
   }

   public List<ContaBancaria> getContasBancarias() {
	  if(contasBancarias.isEmpty()) {
		 /*try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			contasBancarias=gerenteControleAcesso.obterContasBancariasCartorio(getUsuarioCartorioSessao().getCartorio());
		 } catch (ApplicationException e) {
			e.printStackTrace();
			mensageiro(ERROR , "Erro ao buscar os Bancos");
		 }*/
	  }
	  return contasBancarias;
   }

   public void setContasBancarias(List<ContaBancaria> contasBancarias) {
	  this.contasBancarias=contasBancarias;
   }

   public ContaBancaria getContaBancariaSelecionada() {
	  return contaBancariaSelecionada;
   }

   public void setContaBancariaSelecionada(ContaBancaria contaBancariaSelecionada) {
	  this.contaBancariaSelecionada=contaBancariaSelecionada;
   }

   public Boolean getDisableCPF() {
	  return disableCPF;
   }

   public void setDisableCPF(Boolean disableCPF) {
	  this.disableCPF=disableCPF;
   }
}
