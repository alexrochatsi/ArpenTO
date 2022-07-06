
package br.com.datasind.controller.cad;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
/*import javax.faces.bean.SessionScoped;*/
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.Local;
import br.com.datasind.entidade.UF;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="cadastroLocal")
@ViewScoped
public class LocalController extends CadastroControler{

   private static final long serialVersionUID=6430678064422014573L;

   private Local local = new Local();
   private UF ufSelecionada;
   private Boolean disableCidade=true;
   private List<IbgeUF> municipios=new ArrayList<IbgeUF>();
   private List<UF> ufs=new ArrayList<>();

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 gerenteControleAcesso.incluirEntidade(getLocal());
		 mensageiro(INFO , "Local " + getLocal().getDescricao() + " cadastrado com sucesso!");
		 acaoLimpar();
		 RequestContext context=RequestContext.getCurrentInstance();
		 context.update(":formCadastro:localNascimento");
		 context.execute("PF('dlg1').hide();");
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel cadastrar o Local.");
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

	  try {

		 gerenteControleAcesso.atualizarEntidade(getLocal());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel atualizar o Local.");
		 LOGGER.error(e);
		 acaoLimpar();
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(WARNING , e.getMessage().toString());
		 LOGGER.error(e);
		 acaoLimpar();
		 return;
	  }

	  mensageiro(INFO , "Local alterado com sucesso.");
   }
   
   public void acaoLimpar() {
	  local = new Local();
	  ufSelecionada = new UF();
	  disableCidade=true;
	  municipios=new ArrayList<IbgeUF>();
	  ufs=new ArrayList<>();

   }

   public void acaoExcluir(ActionEvent event) {

	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

	  try {
		 gerenteControleAcesso.excluirEntidade(getLocal());
		 acaoLimpar();
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel excluir Local.");
		 LOGGER.error(e);
		 return;
	  }

	  mensageiro(INFO , "Local excluido com sucesso.");
   }

   public List<IbgeUF> completeMunicipiosIBGE(String cidade) {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 municipios=gerenteControleAcesso.obterMunicipiosPorNomeUF(cidade,getUfSelecionada());
	  } catch (ApplicationException e) {
		 e.printStackTrace();
	  }
	  System.out.println("PRIMEIRO VALOR: "+municipios.get(0));
	  return municipios;
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

   public void habilitaListCidades() {
	  RequestContext context=RequestContext.getCurrentInstance();
	  setDisableCidade(false);
	  context.update("formLocal:cidadeLocal");
   }

   public void setUfs(List<UF> ufs) {
	  this.ufs=ufs;
   }

   public Local getLocal() {
	  return local;
   }

   public void setLocal(Local local) {
	  this.local=local;
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

}
