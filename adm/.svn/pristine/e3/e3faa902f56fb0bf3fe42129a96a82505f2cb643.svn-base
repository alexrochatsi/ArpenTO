
package br.com.datasind.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ocupacao")
public class Ocupacao extends EntidadePadrao{

   private static final long serialVersionUID= -3115777064884278309L;
   
   @Id
   @GeneratedValue
   private Integer id;
   private double codigoCBO;
   private String cbo;

   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public double getCodigoCBO() {
	  return codigoCBO;
   }

   public void setCodigoCBO(double codigoCBO) {
	  this.codigoCBO=codigoCBO;
   }

   public String getCbo() {
	  return cbo;
   }

   public void setCbo(String cbo) {
	  this.cbo=cbo;
   }
}
