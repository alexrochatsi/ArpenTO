package br.com.datasind.entidade;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class Endereco{

   private String logradouro;
   private String numero;
   private String cep;
   private String bairro;
   private String complemento;
   @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
   private IbgeUF municipioUF;
   
   
   public String getLogradouro() {
      return logradouro;
   }
   public void setLogradouro(String logradouro) {
      this.logradouro=logradouro.toUpperCase();
   }
   public String getNumero() {
      return numero;
   }
   public void setNumero(String numero) {
      this.numero=numero.toUpperCase();
   }
   public String getBairro() {
      return bairro;
   }
   public void setBairro(String bairro) {
      this.bairro=bairro.toUpperCase();
   }
   public String getComplemento() {
      return complemento;
   }
   public void setComplemento(String complemento) {
      this.complemento=complemento.toUpperCase();
   }
   public IbgeUF getMunicipioUF() {
      return municipioUF;
   }
   public void setMunicipioUF(IbgeUF municipioUF) {
      this.municipioUF=municipioUF;
   }
   public String getCep() {
      return cep;
   }
   public void setCep(String cep) {
      this.cep=cep;
   }
   
}
