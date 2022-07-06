
package br.com.datasind.entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @Author alex_rocha
 * @since 04/07/2016
 *
 **/
@Entity
@Table(name="cartorio")
public class Cartorio extends EntidadePadrao{

   private static final long serialVersionUID=3703375470391305064L;

   @Id
   @GeneratedValue
   private Integer id;
   @Column(length=18)
   private String cpfCnpj;
   @Column(length=6)
   private String cns;
   @Column(length=2)
   private String acervo;
   @Column(length=2)
   private String rcpn;
   private String razao;
   private String nome;
   private String endereco;
   private String bairro;
   @Column(length=10)
   private String cep;
   private String municipio;
   private String comarca;
   @Column(length=2)
   private String uf;
   @Column(length=20)
   private String fone;
   @Column(length=20)
   private String fax;
   @Column(length=20)
   private String celular;
   private String email;
   @OneToMany(mappedBy = "cartorio", targetEntity = ContaBancaria.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
   private List<ContaBancaria> contasBancarias;
   @OneToMany(mappedBy = "cartorio", targetEntity = UsuarioCartorio.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
   private List<UsuarioCartorio> usuariosCartorio;
   
   
   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public String getCpfCnpj() {
	  return cpfCnpj;
   }

   public void setCpfCnpj(String cpfCnpj) {
	  this.cpfCnpj=cpfCnpj;
   }

   public String getAcervo() {
      return acervo;
   }

   public void setAcervo(String acervo) {
      this.acervo=acervo;
   }

   public String getRcpn() {
      return rcpn;
   }

   public void setRcpn(String rcpn) {
      this.rcpn=rcpn;
   }

   public String getCns() {
	  return cns;
   }

   public void setCns(String cns) {
	  this.cns=cns;
   }

   public String getRazao() {
	  return razao;
   }

   public void setRazao(String razao) {
	  this.razao=razao;
   }

   public String getNome() {
	  return nome;
   }

   public void setNome(String nome) {
	  this.nome=nome;
   }

   public String getEndereco() {
	  return endereco;
   }

   public void setEndereco(String endereco) {
	  this.endereco=endereco;
   }

   public String getBairro() {
	  return bairro;
   }

   public void setBairro(String bairro) {
	  this.bairro=bairro;
   }

   public String getCep() {
	  return cep;
   }

   public void setCep(String cep) {
	  this.cep=cep;
   }

   public String getMunicipio() {
	  return municipio;
   }

   public void setMunicipio(String municipio) {
	  this.municipio=municipio;
   }

   public String getComarca() {
	  return comarca;
   }

   public void setComarca(String comarca) {
	  this.comarca=comarca;
   }

   public String getUf() {
	  return uf;
   }

   public void setUf(String uf) {
	  this.uf=uf;
   }

   public String getFone() {
	  return fone;
   }

   public void setFone(String fone) {
	  this.fone=fone;
   }

   public String getFax() {
	  return fax;
   }

   public void setFax(String fax) {
	  this.fax=fax;
   }

   public String getCelular() {
	  return celular;
   }

   public void setCelular(String celular) {
	  this.celular=celular;
   }

   public String getEmail() {
	  return email;
   }

   public void setEmail(String email) {
	  this.email=email;
   }

   public List<ContaBancaria> getContasBancarias() {
      return contasBancarias;
   }

   public void setContasBancarias(List<ContaBancaria> contasBancarias) {
      this.contasBancarias=contasBancarias;
   }

}
