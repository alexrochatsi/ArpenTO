
package br.com.datasind.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.CertidaoUpload;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="limpezaCertidoes")
@SessionScoped
public class LimpezaCertidoesController extends CadastroControler{

   /**
    * 
    */
   private static final long serialVersionUID=3513356358178649813L;

   private CertidaoUpload certidaoUpload;
   private List<CertidaoUpload> certidoesUpload;

   public LimpezaCertidoesController() {
	  super();
   }

   public void limparCertidoes() {
	  try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 FacesContext aFacesContext=FacesContext.getCurrentInstance();
		 ServletContext context=(ServletContext) aFacesContext.getExternalContext().getContext();
		 String realPath=context.getRealPath("/");

		 certidoesUpload=new ArrayList<CertidaoUpload>();
		 certidoesUpload=gerenteControleAcesso.obterListaCertidoesUploadVencidas();
		 if( !certidoesUpload.isEmpty()) {
			for(CertidaoUpload c : certidoesUpload) {
			   CertidaoPedido cp=new CertidaoPedido();
			   cp=gerenteControleAcesso.obterCertidaoPedidoPorIdUpload(c.getId());
			   File file=new File(realPath.replace("cartorio\\" , "") + c.getCaminhoDocumento());
			   file.delete();
			   cp.setCertidaoUpload(null);
			   cp.setStatus(5);
			   gerenteControleAcesso.atualizarEntidade(cp);
			   gerenteControleAcesso.excluirEntidade(c);
			}
		 }
	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }
   }

   public CertidaoUpload getCertidaoUpload() {
	  return certidaoUpload;
   }

   public void setCertidaoUpload(CertidaoUpload certidaoUpload) {
	  this.certidaoUpload=certidaoUpload;
   }

   public List<CertidaoUpload> getCertidoesUpload() {
	  return certidoesUpload;
   }

   public void setCertidoesUpload(List<CertidaoUpload> certidoesUpload) {
	  this.certidoesUpload=certidoesUpload;
   }
}
