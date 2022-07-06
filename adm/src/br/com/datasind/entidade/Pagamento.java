
package br.com.datasind.entidade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="pagamento")
public class Pagamento extends EntidadePadrao{

   private static final long serialVersionUID=4375853417906026331L;
   @Id
   @GeneratedValue
   private Integer id;
   private Date periodo;
   private Date dataPagamento;
   private Integer qtdBoletos;
   // 1 - TED, 2 - DOC, 3 - CHEQUE
   private Integer formaPagamento;
   private Integer valorCheque;
   @OneToOne(cascade=CascadeType.MERGE)
   private Cartorio cartorio;
   @OneToOne(cascade=CascadeType.MERGE)
   private ContaBancaria contaBancaria;
   private BigDecimal total;
   private boolean pago;
   @OneToMany(mappedBy="pagamento", targetEntity=CertidaoPedido.class, fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
   @Fetch(FetchMode.SUBSELECT)
   private List<CertidaoPedido> certidoesPedido;

   public void setId(Integer id) {
	  this.id=id;
   }

   @Override
   public Integer getId() {
	  return id;
   }

   public Date getPeriodo() {
	  return periodo;
   }

   public void setPeriodo(Date periodo) {
	  this.periodo=periodo;
   }

   public Integer getQtdBoletos() {
	  return qtdBoletos;
   }

   public void setQtdBoletos(Integer qtdBoletos) {
	  this.qtdBoletos=qtdBoletos;
   }

   public Integer getFormaPagamento() {
	  return formaPagamento;
   }

   public void setFormaPagamento(Integer formaPagamento) {
	  this.formaPagamento=formaPagamento;
   }

   public Integer getValorCheque() {
	  return valorCheque;
   }

   public void setValorCheque(Integer valorCheque) {
	  this.valorCheque=valorCheque;
   }

   public Cartorio getCartorio() {
	  return cartorio;
   }

   public void setCartorio(Cartorio cartorio) {
	  this.cartorio=cartorio;
   }

   public ContaBancaria getContaBancaria() {
	  return contaBancaria;
   }

   public void setContaBancaria(ContaBancaria contaBancaria) {
	  this.contaBancaria=contaBancaria;
   }

   public BigDecimal getTotal() {
	  return total;
   }

   public void setTotal(BigDecimal total) {
	  this.total=total;
   }

   public Date getDataPagamento() {
	  return dataPagamento;
   }

   public void setDataPagamento(Date dataPagamento) {
	  this.dataPagamento=dataPagamento;
   }

   public boolean isPago() {
	  return pago;
   }

   public void setPago(boolean pago) {
	  this.pago=pago;
   }

   public List<CertidaoPedido> getCertidoesPedido() {
      return certidoesPedido;
   }

   public void setCertidoesPedido(List<CertidaoPedido> certidoesPedido) {
      this.certidoesPedido=certidoesPedido;
   }
}
