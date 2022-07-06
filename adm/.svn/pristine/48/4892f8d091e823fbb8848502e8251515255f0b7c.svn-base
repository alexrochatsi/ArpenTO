
package br.com.datasind.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.TipoLivro;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.util.Utilitario;

@ManagedBean(name="matriculaCertidao")
@SessionScoped
public class MatriculaCertidaoController extends CadastroControler{

   private static final long serialVersionUID=5053810522524260166L;

   private String codigoServentia = null;
   private String acervo = null;
   private String rcpn = null;
   private String anoRegistro = null;
   private TipoLivro tipoLivro=null;
   private List<TipoLivro> tiposLivro;
   private String nLivro = null;
   private String nFolha = null;
   private String nTermo = null;
   private String numeroVerificar = null;

   private String matricula=null;
   private String dnv=null;   

   private String pathRelatorio = null;
   private Boolean abrirRelatorio;

   private int progress;

   public void loadModel() {
	  /*try {
		 GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
		 UsuarioCartorio usuarioCartorio=new UsuarioCartorio();

		 usuarioCartorio=gerenteControleAcesso.obterUsuarioCartorioPorId(getUsuarioCartorioSessao());
		 setCodigoServentia(usuarioCartorio.getCartorio().getCns());
		 setAcervo(usuarioCartorio.getCartorio().getAcervo());
		 setRcpn(usuarioCartorio.getCartorio().getRcpn());

	  } catch (ApplicationException | ValidacaoException e) {
		 e.printStackTrace();
	  }*/
   }

   public void acaoGeraMatricula() {
	  String valores=null;
	  if(getTipoLivro() == null) {
		 mensageiro(WARNING , "Selecione o ATO.");
		 return;
	  }
	  
	  setnTermo(Utilitario.validaTermoMatricula(getnTermo()));
	  setnFolha(Utilitario.validaFolhaMatricula(getnFolha()));
	  setnLivro(Utilitario.validaLivroMatricula(getnLivro()));
	  
	  valores=getCodigoServentia() + getAcervo() + getRcpn() + getAnoRegistro() + getTipoLivro().getId() + getnLivro() + getnFolha() + getnTermo();

	  if(valores.length() < 30) {
		 mensageiro(ERROR , "Não foi possível obter o CV.");
		 return;
	  }

	  setMatricula(valores +"-"+ Utilitario.matriculaDV(valores));
	  return;
   }
   
   public void acaoGeraDNV() {
	  String valores=null;
	  if(getNumeroVerificar().length() < 10) {
		 mensageiro(ERROR , "Não foi possível obter o CV.");
		 return;
	  }
	  valores = getNumeroVerificar();

	  setDnv(valores +"-"+ Utilitario.dnvDV(valores));
	  return;
   }

   public String getCodigoServentia() {
	  return codigoServentia;
   }

   public void setCodigoServentia(String codigoServentia) {
	  this.codigoServentia=codigoServentia;
   }

   public String getAcervo() {
	  return acervo;
   }

   public void setAcervo(String acervo) {
	  this.acervo=acervo;
   }

   public String getRcpn() {
	  return rcpn;
   }

   public void setRcpn(String rcpn) {
	  this.rcpn=rcpn;
   }

   public String getAnoRegistro() {
	  return anoRegistro;
   }

   public void setAnoRegistro(String anoRegistro) {
	  this.anoRegistro=anoRegistro;
   }

   public String getPathRelatorio() {
	  return pathRelatorio;
   }

   public void setPathRelatorio(String pathRelatorio) {
	  this.pathRelatorio=pathRelatorio;
   }

   public Boolean getAbrirRelatorio() {
	  return abrirRelatorio;
   }

   public void setAbrirRelatorio(Boolean abrirRelatorio) {
	  this.abrirRelatorio=abrirRelatorio;
   }

   public TipoLivro getTipoLivro() {
	  return tipoLivro;
   }

   public void setTipoLivro(TipoLivro tipoLivro) {
	  this.tipoLivro=tipoLivro;
   }

   public List<TipoLivro> getTiposLivro() {
	  if(tiposLivro == null) {
		 try {
			GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();
			tiposLivro=new ArrayList<TipoLivro>();

			tiposLivro=gerenteControleAcesso.obterListaTiposLivro();
		 } catch (ApplicationException e) {
			mensageiro(ERROR , "Erro ao buscar os tipos de livros/atos");
		 }
	  }
	  return tiposLivro;
   }

   public void setTiposLivro(List<TipoLivro> tiposLivro) {
	  this.tiposLivro=tiposLivro;
   }

   public String getnLivro() {
	  return nLivro;
   }

   public String getMatricula() {
	  return matricula;
   }

   public void setMatricula(String matricula) {
	  this.matricula=matricula;
   }

   public void setnLivro(String nLivro) {
	  this.nLivro=nLivro;
   }

   public String getnFolha() {
	  return nFolha;
   }

   public void setnFolha(String nFolha) {
	  this.nFolha=nFolha;
   }

   public String getnTermo() {
	  return nTermo;
   }

   public void setnTermo(String nTermo) {
	  this.nTermo=nTermo;
   }

   public int getProgress() {
	  return progress;
   }

   public void setProgress(int progress) {
	  this.progress=progress;
   }

   public String getNumeroVerificar() {
      return numeroVerificar;
   }

   public void setNumeroVerificar(String numeroVerificar) {
      this.numeroVerificar=numeroVerificar;
   }

   public String getDnv() {
      return dnv;
   }

   public void setDnv(String dnv) {
      this.dnv=dnv;
   }

}
