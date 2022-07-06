
package br.com.datasind.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.TipoLivro;
import br.com.datasind.gerente.GerenteControleAcesso;

@ManagedBean(name="matriculaCertidao")
@SessionScoped
public class MatriculaCertidaoController extends CadastroControler{

   private static final long serialVersionUID=5053810522524260166L;

   private String codigoServentia;
   private String acervo;
   private String rcpn;
   private String anoRegistro;
   private TipoLivro tipoLivro;
   private List<TipoLivro> tiposLivro;
   private String nLivro;
   private String nFolha;
   private String nTermo;
   
   private String matricula=null;

   public void acaoGeraMatricula() {
	  String valores=null;
	  GerenteControleAcesso gerenteControleAcesso=getFabricaGerente().getGerenteControleAcesso();

	  valores=getCodigoServentia() + getAcervo() + getRcpn() + getAnoRegistro() + getTipoLivro().getId() + getnLivro() + getnFolha() + getnTermo();

	  if(valores.length() < 30) {
		 mensageiro(ERROR , "Não foi possível obter o CV.");
		 return;
	  }

	  try {
		 setMatricula(valores+gerenteControleAcesso.obterCodigoVerificador(valores));
	  } catch (ApplicationException e) {
		 mensageiro(ERROR , "Não foi possível obter o CV.");
		 e.printStackTrace();
		 return;
	  }
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
}
