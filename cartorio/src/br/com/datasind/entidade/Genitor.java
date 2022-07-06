
package br.com.datasind.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="genitor")
public class Genitor extends EntidadePadrao{

   private static final long serialVersionUID=8488992648732005456L;
   
   @Id
   @GeneratedValue
   private Integer id;
   @Column(length=100, nullable=false)
   private String nome;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
   private Pais nacionalidade;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
   private Pais paisNascimento;
   @OneToOne(cascade=CascadeType.ALL)
   private Documento documento;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
   private IbgeUF naturalidade;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
   private Ocupacao ocupacao;
   @Column(length=150)
   private String novoMunicipio;

   private String pai;
   private String mae;

   @Embedded
   private Endereco endereco;

   public Genitor() {
	  endereco=new Endereco();
   }

   /* ________________Getts e Settres____________________ */

   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public String getNome() {
	  return nome;
   }

   public void setNome(String nome) {
	  this.nome=nome;
   }

   public Documento getDocumento() {
	  return documento;
   }

   public void setDocumento(Documento documento) {
	  this.documento=documento;
   }

   public Pais getNacionalidade() {
	  return nacionalidade;
   }

   public void setNacionalidade(Pais nacionalidade) {
	  this.nacionalidade=nacionalidade;
   }

   public Pais getPaisNascimento() {
	  return paisNascimento;
   }

   public void setPaisNascimento(Pais paisNascimento) {
	  this.paisNascimento=paisNascimento;
   }

   public IbgeUF getNaturalidade() {
	  return naturalidade;
   }

   public void setNaturalidade(IbgeUF naturalidade) {
	  this.naturalidade=naturalidade;
   }

   public String getPai() {
	  return pai;
   }

   public void setPai(String pai) {
	  this.pai=pai;
   }

   public String getMae() {
	  return mae;
   }

   public void setMae(String mae) {
	  this.mae=mae;
   }

   public Endereco getEndereco() {
	  return endereco;
   }

   public void setEndereco(Endereco endereco) {
	  this.endereco=endereco;
   }

   public String getNovoMunicipio() {
	  return novoMunicipio;
   }

   public void setNovoMunicipio(String novoMunicipio) {
	  this.novoMunicipio=novoMunicipio;
   }

   public Ocupacao getOcupacao() {
	  return ocupacao;
   }

   public void setOcupacao(Ocupacao ocupacao) {
	  this.ocupacao=ocupacao;
   }
}
