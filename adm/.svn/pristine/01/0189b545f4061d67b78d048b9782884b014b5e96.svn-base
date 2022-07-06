
package br.com.datasind.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author alex_rocha
 * @since 04/07/2016
 *
 **/
@Entity
@Table(name="tipolivro")
public class TipoLivro extends EntidadePadrao{

   private static final long serialVersionUID=3703375470391305064L;

   @Id
   @GeneratedValue
   private Integer id;
   private String tipo;
   private String descricao;

   /*Getters e Setters*/
   
   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public String getTipo() {
      return tipo;
   }

   public void setTipo(String tipo) {
      this.tipo=tipo;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao=descricao;
   }
}
