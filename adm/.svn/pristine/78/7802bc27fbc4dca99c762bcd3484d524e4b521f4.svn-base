package br.com.datasind.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orgaoemissor")
public class OrgaoEmissor extends EntidadePadrao{
   
   private static final long serialVersionUID= -5918018013970137950L;
   
   @Id
   @GeneratedValue
   private Integer id;
   private String abreviatura;
   private String descricao;
         
   public String getDescricao() {
      return descricao;
   }
   public void setDescricao(String descricao) {
      this.descricao=descricao;
   }
   public Integer getId() {
      return id;
   }
   public void setId(Integer id) {
      this.id=id;
   }
   public String getAbreviatura() {
      return abreviatura;
   }
   public void setAbreviatura(String abreviatura) {
      this.abreviatura=abreviatura;
   }

}
