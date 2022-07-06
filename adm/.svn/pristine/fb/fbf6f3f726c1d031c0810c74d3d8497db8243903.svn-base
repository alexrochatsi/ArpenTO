package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="impressoseguranca")
public class ImpressoSeguranca extends EntidadePadrao{

   private static final long serialVersionUID= -2342173170227350946L;
   @Id
   @GeneratedValue
   private Integer id;
   @Column(length=15)
   private String numeroSerie;
   @Enumerated
   private ViaEnum via;
   private Date dataUtilizacao;
   @Enumerated
   private TipoImpressoSegurancaEnum tipoImpressoSeguranca;
   private String seloDigital;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id=id;
   }

   public String getNumeroSerie() {
      return numeroSerie;
   }

   public void setNumeroSerie(String numeroSerie) {
      this.numeroSerie=numeroSerie;
   }

   public ViaEnum getVia() {
      return via;
   }

   public void setVia(ViaEnum via) {
      this.via=via;
   }

   public Date getDataUtilizacao() {
      return dataUtilizacao;
   }

   public void setDataUtilizacao(Date dataUtilizacao) {
      this.dataUtilizacao=dataUtilizacao;
   }

   public TipoImpressoSegurancaEnum getTipoImpressoSeguranca() {
      return tipoImpressoSeguranca;
   }

   public void setTipoImpressoSeguranca(TipoImpressoSegurancaEnum tipoImpressoSeguranca) {
      this.tipoImpressoSeguranca=tipoImpressoSeguranca;
   }

   public String getSeloDigital() {
      return seloDigital;
   }

   public void setSeloDigital(String seloDigital) {
      this.seloDigital=seloDigital;
   }
}
