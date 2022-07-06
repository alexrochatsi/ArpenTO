
package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @Author alex_rocha
 * @since 04/07/2016
 *
 **/
@Entity
@Table(name="certidaopedido")
public class CertidaoPedido extends EntidadePadrao{

   private static final long serialVersionUID=3703375470391305064L;

   @Id
   @GeneratedValue
   private Integer id;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
   @JoinColumn(name="usuariosite_id", nullable=false)
   private UsuarioSite usuarioSite;
   private String nomeRegistrado;
   private String nomeGenitor;
   private String nomeGenitora;
   // refe-se à data de nascimento/casamento/natimorto
   private Date data;
   private String conjugue1;
   private String conjugue2;
   private String livro;
   private String folha;
   private String termo;
   private Date dataPedido;
   private String cidadePedido;
   // 0 - Enviado para o cartorio, 1 - Encontrada, habilita boleto, 2 - Nao Encontrada, 3 - preparando para envio,
   // 4 - certidao concluida, 5 - Download expirado 1 mes
   private Integer status;
   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(referencedColumnName="id", insertable=true, updatable=true)
   private Cartorio cartorio;
   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(referencedColumnName="id", insertable=true, updatable=true)
   private Correios correios;
   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(referencedColumnName="id", insertable=true, updatable=true)
   private CustoPedido custoPedido;
   @Embedded
   private Endereco enderecoEntrega;
   private Boolean boletoGerado;
   private Date dataBoleto;
   private Boolean boletoPago;
   private String codigoRastreio;
   private Boolean guiaGeradaCartorio;
   private String matricula;
   private Integer formaEntrega;
   @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
   @JoinColumn(referencedColumnName="id", insertable=true, updatable=true)
   private CertidaoUpload certidaoUpload;

   public CertidaoPedido() {
	  enderecoEntrega=new Endereco();
   }

   /* gets e setters */

   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public String getNomeRegistrado() {
	  return nomeRegistrado;
   }

   public CustoPedido getCustoPedido() {
	  return custoPedido;
   }

   public void setCustoPedido(CustoPedido custoPedido) {
	  this.custoPedido=custoPedido;
   }

   public void setNomeRegistrado(String nomeRegistrado) {
	  this.nomeRegistrado=nomeRegistrado.toUpperCase();
   }

   public Correios getCorreios() {
	  return correios;
   }

   public void setCorreios(Correios correios) {
	  this.correios=correios;
   }

   public Integer getStatus() {
	  return status;
   }

   public void setStatus(Integer status) {
	  this.status=status;
   }

   public String getNomeGenitor() {
	  return nomeGenitor;
   }

   public void setNomeGenitor(String nomeGenitor) {
	  this.nomeGenitor=nomeGenitor.toUpperCase();
   }

   public String getNomeGenitora() {
	  return nomeGenitora;
   }

   public void setNomeGenitora(String nomeGenitora) {
	  this.nomeGenitora=nomeGenitora.toUpperCase();
   }

   public String getConjugue1() {
	  return conjugue1;
   }

   public void setConjugue1(String conjugue1) {
	  this.conjugue1=conjugue1;
   }

   public String getConjugue2() {
	  return conjugue2;
   }

   public void setConjugue2(String conjugue2) {
	  this.conjugue2=conjugue2;
   }

   public String getLivro() {
	  return livro;
   }

   public void setLivro(String livro) {
	  this.livro=livro.toUpperCase();
   }

   public String getFolha() {
	  return folha;
   }

   public void setFolha(String folha) {
	  this.folha=folha;
   }

   public String getTermo() {
	  return termo;
   }

   public void setTermo(String termo) {
	  this.termo=termo;
   }

   public UsuarioSite getUsuarioSite() {
	  return usuarioSite;
   }

   public void setUsuarioSite(UsuarioSite usuarioSite) {
	  this.usuarioSite=usuarioSite;
   }

   public Date getDataPedido() {
	  return dataPedido;
   }

   public void setDataPedido(Date dataPedido) {
	  this.dataPedido=dataPedido;
   }

   public String getCidadePedido() {
	  return cidadePedido;
   }

   public void setCidadePedido(String cidadePedido) {
	  this.cidadePedido=cidadePedido;
   }

   public Cartorio getCartorio() {
	  return cartorio;
   }

   public void setCartorio(Cartorio cartorio) {
	  this.cartorio=cartorio;
   }

   public Endereco getEnderecoEntrega() {
	  return enderecoEntrega;
   }

   public void setEnderecoEntrega(Endereco enderecoEntrega) {
	  this.enderecoEntrega=enderecoEntrega;
   }

   public Boolean getBoletoGerado() {
	  return boletoGerado;
   }

   public void setBoletoGerado(Boolean boletoGerado) {
	  this.boletoGerado=boletoGerado;
   }

   public Date getDataBoleto() {
	  return dataBoleto;
   }

   public void setDataBoleto(Date dataBoleto) {
	  this.dataBoleto=dataBoleto;
   }

   public String getCodigoRastreio() {
	  return codigoRastreio;
   }

   public void setCodigoRastreio(String codigoRastreio) {
	  this.codigoRastreio=codigoRastreio;
   }

   public Date getData() {
	  return data;
   }

   public void setData(Date data) {
	  this.data=data;
   }

   public Boolean getGuiaGeradaCartorio() {
	  return guiaGeradaCartorio;
   }

   public void setGuiaGeradaCartorio(Boolean guiaGeradaCartorio) {
	  this.guiaGeradaCartorio=guiaGeradaCartorio;
   }

   public Boolean getBoletoPago() {
	  return boletoPago;
   }

   public void setBoletoPago(Boolean boletoPago) {
	  this.boletoPago=boletoPago;
   }

   public String getMatricula() {
	  return matricula;
   }

   public void setMatricula(String matricula) {
	  this.matricula=matricula;
   }

   public Integer getFormaEntrega() {
	  return formaEntrega;
   }

   public void setFormaEntrega(Integer formaEntrega) {
	  this.formaEntrega=formaEntrega;
   }

   public CertidaoUpload getCertidaoUpload() {
	  return certidaoUpload;
   }

   public void setCertidaoUpload(CertidaoUpload certidaoUpload) {
	  this.certidaoUpload=certidaoUpload;
   }
}
