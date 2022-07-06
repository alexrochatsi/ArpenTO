
package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="documento")
public class Documento extends EntidadePadrao{

   private static final long serialVersionUID= -6377123063603177748L;

   @Id
   @GeneratedValue
   private Integer id;
   private Date dataEmissao;
   private String descricao;
   private String numero;
   private String numeroSerie;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
   private OrgaoEmissor orgaoEmissao;
   @Enumerated(EnumType.STRING)
   @Column(name = "tipodocumento", columnDefinition="varchar(40)")
   private TipoDocumentoEnum tipoDocumento;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
   private UF ufEmissao;

   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public Date getDataEmissao() {
	  return dataEmissao;
   }

   public void setDataEmissao(Date dataEmissao) {
	  this.dataEmissao=dataEmissao;
   }

   public String getDescricao() {
	  return descricao;
   }

   public void setDescricao(String descricao) {
	  this.descricao=descricao;
   }

   public String getNumero() {
	  return numero;
   }

   public void setNumero(String numero) {
	  this.numero=numero;
   }

   public String getNumeroSerie() {
	  return numeroSerie;
   }

   public void setNumeroSerie(String numeroSerie) {
	  this.numeroSerie=numeroSerie;
   }

   public OrgaoEmissor getOrgaoEmissao() {
	  return orgaoEmissao;
   }

   public void setOrgaoEmissao(OrgaoEmissor orgaoEmissao) {
	  this.orgaoEmissao=orgaoEmissao;
   }

   public TipoDocumentoEnum getTipoDocumento() {
	  return tipoDocumento;
   }

   public void setTipoDocumento(TipoDocumentoEnum tipoDocumento) {
	  this.tipoDocumento=tipoDocumento;
   }

   public UF getUfEmissao() {
	  return ufEmissao;
   }

   public void setUfEmissao(UF ufEmissao) {
	  this.ufEmissao=ufEmissao;
   }

}
