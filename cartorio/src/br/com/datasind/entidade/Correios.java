
package br.com.datasind.entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author alex_rocha
 * @since 07/07/2016
 *
 **/

@Entity
@Table(name="correios")
public class Correios extends EntidadePadrao{

   private static final long serialVersionUID=6547907811229322804L;

   @Id
   @GeneratedValue
   private Integer id;
   @Column(nullable=false)
   private String servico;
   @Column(nullable=false)
   private BigDecimal valor;

   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public String getServico() {
	  return servico;
   }

   public void setServico(String servico) {
	  this.servico=servico;
   }

   public BigDecimal getValor() {
	  return valor;
   }

   public void setValor(BigDecimal valor) {
	  this.valor=valor;
   }

}
