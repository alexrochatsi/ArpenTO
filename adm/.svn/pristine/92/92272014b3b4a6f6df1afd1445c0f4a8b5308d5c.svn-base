
package br.com.datasind.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="permissaoacesso")
public class PermissaoAcesso extends EntidadePadrao{

   private static final long serialVersionUID=3258416110171730736L;

   @Id
   @GeneratedValue
   private Integer id;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
   private Perfil perfil;
   private Boolean inclui;
   private Boolean altera;
   private Boolean exclui;
   private Boolean consulta;

   public Integer getId() {
	  return id;
   }

   public Perfil getPerfil() {
	  if(perfil == null) {
		 perfil=new Perfil();
	  }
	  return perfil;
   }

   public Boolean getInclui() {
	  return inclui;
   }

   public Boolean getAltera() {
	  return altera;
   }

   public Boolean getExclui() {
	  return exclui;
   }

   public Boolean getConsulta() {
	  return consulta;
   }

   public void setPerfil(Perfil perfil) {
	  this.perfil=perfil;
   }

   public void setInclui(Boolean inclui) {
	  this.inclui=inclui;
   }

   public void setAltera(Boolean altera) {
	  this.altera=altera;
   }

   public void setExclui(Boolean exclui) {
	  this.exclui=exclui;
   }

   public void setConsulta(Boolean consulta) {
	  this.consulta=consulta;
   }

   public void setId(Integer id) {
	  this.id=id;
   }
}
