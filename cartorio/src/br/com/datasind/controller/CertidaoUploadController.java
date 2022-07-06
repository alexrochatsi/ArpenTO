
package br.com.datasind.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.CertidaoUpload;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name="certidaoUpload")
@SessionScoped
public class CertidaoUploadController extends CadastroControler{

   /**
    * 
    */
   private static final long serialVersionUID=3513356358178649813L;

   private CertidaoUpload certidaoUpload=new CertidaoUpload();
   private CertidaoPedido certidaoPedido;
   private List<CertidaoPedido> certidoesPedido=null;

   public CertidaoUploadController() {
	  super();
   }

   public void acaoIncluir(ActionEvent event) {
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
	  try {
		 CertidaoUpload ctupTemp=getCertidaoUpload();
		 if(ctupTemp == null || ctupTemp.getNomeArquivo() == null || ctupTemp.getNomeArquivo().equals("")) {
			mensageiro(ERROR , "Anexar a certidão certificada!");
			return;
		 }
		 ctupTemp.setDataUpload(new Date());
		 ctupTemp.setCertificada(true);
		 gerenteControleAcesso.incluirEntidade(ctupTemp);
		 getCertidaoPedido().setCertidaoUpload(ctupTemp);
		 gerenteControleAcesso.atualizarEntidade(getCertidaoPedido());
		 mensageiro(INFO , "Certidão " + getCertidaoUpload().getNomeArquivo() + " salva com sucesso!");
		 acaoLimpar();
		 RequestContext context=RequestContext.getCurrentInstance();
		 context.update("formUpload");
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Nao foi possivel salvar a certidão.");
		 LOGGER.error(e);
		 return;
	  } catch (ValidacaoException e) {
		 mensageiro(ERROR , e.getMessage());
		 LOGGER.equals(e);
		 return;
	  }
   }

   @SuppressWarnings("unused")
   public void doUpload(FileUploadEvent event) throws FileNotFoundException {
	  try {
		 RequestContext rc=RequestContext.getCurrentInstance();
		 ExternalContext externalContext=FacesContext.getCurrentInstance().getExternalContext();
		 HttpServletResponse response=(HttpServletResponse) externalContext.getResponse();
		 FacesContext aFacesContext=FacesContext.getCurrentInstance();
		 ServletContext context=(ServletContext) aFacesContext.getExternalContext().getContext();
		 String realPath=context.getRealPath("/");
		 byte[] arquivo=event.getFile().getContents();
		 String caminho=realPath.replace("cartorio\\" , "") + "/CERTIDOES/" + event.getFile().getFileName();

		 // Aqui cria o diretorio caso não exista
		 File file=new File(realPath.replace("cartorio\\" , "") + "/CERTIDOES/");
		 file.mkdirs();
		 // esse trecho grava o arquivo no diretório
		 FileOutputStream fos;
		 fos=new FileOutputStream(caminho);
		 fos.write(arquivo);
		 fos.close();
		 getCertidaoUpload().setCaminhoDocumento("/CERTIDOES/" + event.getFile().getFileName());
		 getCertidaoUpload().setNomeArquivo(event.getFile().getFileName());
		 rc.update("formUpload:caminhoDoc");
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
   }

   public void acaoLimpar() {
	  certidaoUpload=new CertidaoUpload();
	  certidaoPedido=new CertidaoPedido();
	  certidoesPedido=null;
   }

   /*
    * public List<CertidaoPedido> completeCertidaoPedido(Integer pedido) { try { GerenteControleAcesso
    * gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
    * certidoesPedido=gerenteControleAcesso.obterListaPedidosPorIdComplete(pedido); } catch (ApplicationException e) {
    * e.printStackTrace(); } return certidoesPedido; }
    */

   public CertidaoUpload getCertidaoUpload() {
	  return certidaoUpload;
   }

   public void setCertidaoUpload(CertidaoUpload certidaoUpload) {
	  this.certidaoUpload=certidaoUpload;
   }

   public List<CertidaoPedido> getCertidoesPedido() {
	  if(certidoesPedido == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			certidoesPedido=new ArrayList<CertidaoPedido>();
			certidoesPedido=gerenteControleAcesso.obterListaPedidosPorCartorioFiltrado(getUsuarioCartorioSessao().getCartorio());
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar lista de Pedidos Certidões");
		 }
	  }
	  return certidoesPedido;
   }

   public void setCertidoesPedido(List<CertidaoPedido> certidoesPedido) {
	  this.certidoesPedido=certidoesPedido;
   }

   public CertidaoPedido getCertidaoPedido() {
	  return certidaoPedido;
   }

   public void setCertidaoPedido(CertidaoPedido certidaoPedido) {
	  this.certidaoPedido=certidaoPedido;
   }
}
