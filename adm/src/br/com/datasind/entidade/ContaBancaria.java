
package br.com.datasind.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @Author alex_rocha
 * @since 16/09/2016
 *
 **/

@Entity
@Table(name="contabancaria")
public class ContaBancaria extends EntidadePadrao{

   private static final long serialVersionUID=4623512851502149147L;
   @Id
   @GeneratedValue
   private Integer id;
   private String nomeCompleto;
   @Column(length=4)
   private String agencia;
   private String contaCorrente;
   private String cpfCNPJ;
   private Boolean contaAtiva;
   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(referencedColumnName="id", insertable=true, updatable=true)
   private Banco banco;
   @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
   @JoinColumn(name="cartorio_id")
   private Cartorio cartorio;

   @Override
   public Integer getId() {
	  return id;
   }

   public String getAgencia() {
	  return agencia;
   }

   public void setAgencia(String agencia) {
	  this.agencia=agencia;
   }

   public String getContaCorrente() {
	  return contaCorrente;
   }

   public void setContaCorrente(String contaCorrente) {
	  this.contaCorrente=contaCorrente;
   }

   public String getCpfCNPJ() {
	  return cpfCNPJ;
   }

   public void setCpfCNPJ(String cpfCNPJ) {
	  this.cpfCNPJ=cpfCNPJ;
   }

   public String getNomeCompleto() {
	  return nomeCompleto;
   }

   public void setNomeCompleto(String nomeCompleto) {
	  this.nomeCompleto=nomeCompleto;
   }

   public Boolean getContaAtiva() {
	  return contaAtiva;
   }

   public void setContaAtiva(Boolean contaAtiva) {
	  this.contaAtiva=contaAtiva;
   }

   public Banco getBanco() {
	  return banco;
   }

   public void setBanco(Banco banco) {
	  this.banco=banco;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public Cartorio getCartorio() {
      return cartorio;
   }

   public void setCartorio(Cartorio cartorio) {
      this.cartorio=cartorio;
   }
}
