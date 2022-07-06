/**
 * 
 */

package br.com.datasind.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;

/**
 * @author OsmarJunior 15:56:28
 */
@Entity
@Table(name="remessa")
public class Remessa extends EntidadePadrao{

   /**
    * 
    */
   private static final long serialVersionUID= -3493830411438388076L;
   @Id
   @GeneratedValue
   private Integer id;
   @Version
   private Integer versao;
   private Boolean testeDefinitiva;
   private Integer lote;
   private Integer tipoServico;
   private String nomeArquivo;
   private Date dataGeracao;
   @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
   private Usuario usuario;
   @OneToMany(mappedBy="remessa", fetch=FetchType.LAZY)
   @Cascade(org.hibernate.annotations.CascadeType.ALL)
   private List<GuiaAssistencial> guias;
   @Transient
   private String tipoServicoSRT;

   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public Integer getVersao() {
	  return versao;
   }

   public void setVersao(Integer versao) {
	  this.versao=versao;
   }

   public Boolean getTesteDefinitiva() {
	  return testeDefinitiva;
   }

   public void setTesteDefinitiva(Boolean testeDefinitiva) {
	  this.testeDefinitiva=testeDefinitiva;
   }

   public Integer getLote() {
	  return lote;
   }

   public void setLote(Integer lote) {
	  this.lote=lote;
   }

   public Integer getTipoServico() {
	  return tipoServico;
   }

   public void setTipoServico(Integer tipoServico) {
	  this.tipoServico=tipoServico;
   }

   public String getNomeArquivo() {
	  return nomeArquivo;
   }

   public void setNomeArquivo(String nomeArquivo) {
	  this.nomeArquivo=nomeArquivo;
   }

   public Date getDataGeracao() {
	  return dataGeracao;
   }

   public void setDataGeracao(Date dataGeracao) {
	  this.dataGeracao=dataGeracao;
   }

   public Usuario getUsuario() {
	  if(usuario == null) {
		 usuario=new Usuario();
	  }
	  return usuario;
   }

   public void setUsuario(Usuario usuario) {
	  this.usuario=usuario;
   }

   public List<GuiaAssistencial> getGuias() {
	  if(guias == null) {
		 guias=new ArrayList<>();
	  }
	  return guias;
   }

   public void setGuias(List<GuiaAssistencial> guias) {
	  this.guias=guias;
   }

   public String getTipoServicoSRT() {
	  if(tipoServico != null) {
		 if(tipoServico == 1) {
			tipoServicoSRT="REMESSA-TESTE";
		 } else {
			tipoServicoSRT="REMESSA-PRODUCAO";
		 }
	  }
	  return tipoServicoSRT;
   }

   public void setTipoServicoSRT(String tipoServicoSRT) {
	  this.tipoServicoSRT=tipoServicoSRT;
   }

}
