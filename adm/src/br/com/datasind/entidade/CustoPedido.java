package br.com.datasind.entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author alex_rocha
 * @since 26/08/2016
 *
 **/

@Entity
@Table(name="custopedido")
public class CustoPedido extends EntidadePadrao {

   private static final long serialVersionUID= -4400830337438964220L;
   
   @Id
   @GeneratedValue
   private Integer id;
   private String tipoCertidao;
   @Column(nullable=false, precision=10, scale=2 )
   private BigDecimal valorCertidao;
   @Column(nullable=false, precision=10, scale=2 )
   private BigDecimal taxaAdministrativa;

   public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

   public BigDecimal getValorCertidao() {
      return valorCertidao;
   }

   public void setValorCertidao(BigDecimal valorCertidao) {
      this.valorCertidao=valorCertidao;
   }

   public BigDecimal getTaxaAdministrativa() {
      return taxaAdministrativa;
   }

   public void setTaxaAdministrativa(BigDecimal taxaAdministrativa) {
      this.taxaAdministrativa=taxaAdministrativa;
   }

   public String getTipoCertidao() {
      return tipoCertidao;
   }

   public void setTipoCertidao(String tipoCertidao) {
      this.tipoCertidao=tipoCertidao;
   }

}
