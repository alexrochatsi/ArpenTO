
package br.com.datasind.entidade;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @Author alex_rocha
 * @since 14/11/2016
 *
 **/
@Entity
@Table(name="certidaoUpload")
public class CertidaoUpload extends EntidadePadrao{

   private static final long serialVersionUID=3703375470391305064L;

   @Id
   @GeneratedValue
   private Integer id;
   private String caminhoDocumento;
   private Boolean certificada;
   private Date dataUpload;
   private String nomeArquivo;
   @OneToMany(mappedBy="certidaoUpload", targetEntity=CertidaoPedido.class, fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
   @Fetch(FetchMode.SUBSELECT)
   private List<CertidaoPedido> certidoesPedido;
   
   public List<CertidaoPedido> getCertidoesPedido() {
      return certidoesPedido;
   }

   public void setCertidoesPedido(List<CertidaoPedido> certidoesPedido) {
      this.certidoesPedido=certidoesPedido;
   }

   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public String getCaminhoDocumento() {
      return caminhoDocumento;
   }

   public void setCaminhoDocumento(String caminhoDocumento) {
      this.caminhoDocumento=caminhoDocumento;
   }

   public Boolean getCertificada() {
      return certificada;
   }

   public void setCertificada(Boolean certificada) {
      this.certificada=certificada;
   }

   public Date getDataUpload() {
      return dataUpload;
   }

   public void setDataUpload(Date dataUpload) {
      this.dataUpload=dataUpload;
   }

   public String getNomeArquivo() {
      return nomeArquivo;
   }

   public void setNomeArquivo(String nomeArquivo) {
      this.nomeArquivo=nomeArquivo;
   }
}
