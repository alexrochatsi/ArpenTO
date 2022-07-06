
package br.com.datasind.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.FinderException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Banco;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.ContaBancaria;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.util.Utilitaria;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="contaBancaria")
@ViewScoped
public class ContaBancariaController extends CadastroControler{

   private static final long serialVersionUID=1893513995855967747L;
   private ContaBancaria contaBancaria=new ContaBancaria();
   private ContaBancaria contaBancariaSelecionada=new ContaBancaria();
   private List<Banco> bancos=new ArrayList<>();
   private List<ContaBancaria> contasBancarias;
   private Boolean disableCPF;

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 String cpfCnpj=getContaBancaria().getCpfCNPJ().replaceAll("[./,-]" , "");
		 if( !Utilitaria.isCnpjCpf(cpfCnpj)) {
			mensageiro(ERROR , "O CPF / CNPJ informado não é válido.");
			return;
		 }
		 Cartorio cTmp=getUsuarioCartorioSessao().getCartorio();
		 ContaBancaria contaBancaria = gerenteControleAcesso.obterContaBancariaByCartorioAtiva(cTmp);
		 if(contaBancaria.getId() != null) {
			contaBancaria.setContaAtiva(false);
			gerenteControleAcesso.atualizarEntidade(contaBancaria);
		 }
		 getContaBancaria().setCartorio(getUsuarioCartorioSessao().getCartorio());
		 getContaBancaria().setNomeCompleto(getContaBancaria().getNomeCompleto().toUpperCase());
		 getContaBancaria().setContaAtiva(true);
		 getContaBancaria().setDataCadastro(new Date());
		 List<ContaBancaria> contasbancariasTemp=gerenteControleAcesso.obterContasBancariasCartorio(cTmp);

		 for(ContaBancaria cb : contasbancariasTemp) {
			if(cb.getContaAtiva() != null && cb.getContaAtiva() == true) {
			   cb.setContaAtiva(false);
			   gerenteControleAcesso.atualizarEntidade(cb);
			}
		 }

		 gerenteControleAcesso.incluirEntidade(getContaBancaria());

		 mensageiro(INFO , "Conta Cadastrada com Sucesso!");
		 acaoLimpar();
		 context.update("pnlContasBancarias");
		 context.execute("PF('dlgContaBancaria').hide();");
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel cadastrar sua conta.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(ERROR , e.getMessage());
		 LOGGER.equals(e);
		 return;
	  } catch (FinderException e) {
		 e.printStackTrace();
	  }
   }

   public void acaoAlterar(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  RequestContext context=RequestContext.getCurrentInstance();
	  try {
		 gerenteControleAcesso.atualizarEntidade(getContaBancariaSelecionada());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possível atualizar sua conta.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(WARNING , e.getMessage().toString());
		 LOGGER.error(e);
		 return;
	  }
	  context.execute("PF('dlg1').hide();");
	  context.update("pnlContasBancarias");
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
		 context.update("formContasBancarias");
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(WARNING , "Nao foi possivel excluir. Conta associada a uma guia de pagamento.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Conta excluida com sucesso.");
   }

   public void acaoLimpar() {
	  setContaBancaria(new ContaBancaria());
	  setBancos(new ArrayList<Banco>());
   }

   public void statusConta() {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  Cartorio cTmp=getUsuarioCartorioSessao().getCartorio();
	  try {
		 List<ContaBancaria> contasbancariasTemp;
		 contasbancariasTemp=gerenteControleAcesso.obterContasBancariasCartorio(cTmp);
		 for(ContaBancaria cb : contasbancariasTemp) {
			if(cb.getContaAtiva() != null && cb.getContaAtiva() == true) {
			   cb.setContaAtiva(false);
			   gerenteControleAcesso.atualizarEntidade(cb);
			}
		 }
		 gerenteControleAcesso.atualizarEntidade(getContaBancariaSelecionada());
		 
	  } catch (FinderException | ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }
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
	  if(contasBancarias == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			contasBancarias=gerenteControleAcesso.obterContasBancariasCartorio(getUsuarioCartorioSessao().getCartorio());
		 } catch (FinderException fe) {
			fe.printStackTrace();
			mensageiro(ERROR , "Nenhuma conta encontrada! Adicione uma nova.");
			return contasBancarias=new ArrayList<ContaBancaria>();
		 }
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
