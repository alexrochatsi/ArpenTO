
package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="registronascimento")
public class RegistroNascimento extends EntidadePadrao{

   private static final long serialVersionUID=2029940104341452069L;
   @Id
   @GeneratedValue
   private Integer id;
   @Column(length=100, nullable=false)
   private String nome;
   private Date dataNascimento;
   @Column(length=120)
   private String dataNascimentoExtenso;
   @Column(length=2)
   private String horaNascimento;
   @Column(length=2)
   private String minutoNascimento;
   @Column(unique=true)
   private String matricula;
   private Boolean horaNascimentoIgnorada;
   @Enumerated(EnumType.STRING)
   private SexoEnum sexo;
   private Date dataRegistroNascimento;
   @Enumerated(EnumType.STRING)
   private LocalNascimentoEnum tipoLocalNascimento;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
   private Local localNascimento;
   private String dnv;
   private Boolean dnvInexistente;
   @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
   private IbgeUF municipioUF;
   @OneToOne(cascade=CascadeType.ALL)
   private Genitor genitor;
   @OneToOne(cascade=CascadeType.ALL)
   private Genitor genitora;
   @OneToOne(cascade=CascadeType.ALL)
   private UsuarioCartorio usuarioCartorio;
   private Boolean possuiGemeos;
   private Integer numeroGemeos;
   @Column(length=4000)
   private String observacao;
   @OneToOne(cascade=CascadeType.ALL)
   private ImpressoSeguranca impressoSeguranca;
   private Date dataCadastroSistema;


   /* __________ Gettres e Setters ___________ */

   @Override
   public Integer getId() {
	  return id;
   }

   public String getNome() {
	  return nome;
   }

   public void setNome(String nome) {
	  this.nome=nome;
   }

   public Date getDataNascimento() {
	  return dataNascimento;
   }

   public void setDataNascimento(Date dataNascimento) {
	  this.dataNascimento=dataNascimento;
   }

   public String getDataNascimentoExtenso() {
	  return dataNascimentoExtenso;
   }

   public void setDataNascimentoExtenso(String dataNascimentoExtenso) {
	  this.dataNascimentoExtenso=dataNascimentoExtenso;
   }

   public String getHoraNascimento() {
	  return horaNascimento;
   }

   public void setHoraNascimento(String horaNascimento) {
	  this.horaNascimento=horaNascimento;
   }

   public String getMinutoNascimento() {
	  return minutoNascimento;
   }

   public void setMinutoNascimento(String minutoNascimento) {
	  this.minutoNascimento=minutoNascimento;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public SexoEnum getSexo() {
	  return sexo;
   }

   public void setSexo(SexoEnum sexo) {
	  this.sexo=sexo;
   }

   public Date getDataRegistroNascimento() {
	  return dataRegistroNascimento;
   }

   public void setDataRegistroNascimento(Date dataRegistroNascimento) {
	  this.dataRegistroNascimento=dataRegistroNascimento;
   }
   
   public IbgeUF getMunicipioUF() {
	  if(municipioUF == null){
		 municipioUF = new IbgeUF();
	  }
	  return municipioUF;
   }

   public void setMunicipioUF(IbgeUF municipioUF) {
	  this.municipioUF=municipioUF;
   }

   public LocalNascimentoEnum getTipoLocalNascimento() {
      return tipoLocalNascimento;
   }

   public void setTipoLocalNascimento(LocalNascimentoEnum tipoLocalNascimento) {
      this.tipoLocalNascimento=tipoLocalNascimento;
   }

   public String getDnv() {
	  return dnv;
   }

   public void setDnv(String dnv) {
	  this.dnv=dnv;
   }

   public Boolean getHoraNascimentoIgnorada() {
	  return horaNascimentoIgnorada;
   }

   public void setHoraNascimentoIgnorada(Boolean horaNascimentoIgnorada) {
	  this.horaNascimentoIgnorada=horaNascimentoIgnorada;
   }

   public Boolean getDnvInexistente() {
	  return dnvInexistente;
   }

   public void setDnvInexistente(Boolean dnvInexistente) {
	  this.dnvInexistente=dnvInexistente;
   }

   public Boolean getPossuiGemeos() {
	  return possuiGemeos;
   }

   public void setPossuiGemeos(Boolean possuiGemeos) {
	  this.possuiGemeos=possuiGemeos;
   }

   public Integer getNumeroGemeos() {
	  return numeroGemeos;
   }

   public void setNumeroGemeos(Integer numeroGemeos) {
	  this.numeroGemeos=numeroGemeos;
   }

   public Genitor getGenitora() {
	  return genitora;
   }

   public void setGenitora(Genitor genitora) {
	  this.genitora=genitora;
   }

   public Genitor getGenitor() {
	  return genitor;
   }

   public void setGenitor(Genitor genitor) {
	  this.genitor=genitor;
   }

   public Local getLocalNascimento() {
      return localNascimento;
   }

   public void setLocalNascimento(Local localNascimento) {
      this.localNascimento=localNascimento;
   }

   public String getObservacao() {
      return observacao;
   }

   public void setObservacao(String observacao) {
      this.observacao=observacao;
   }

   public ImpressoSeguranca getImpressoSeguranca() {
      return impressoSeguranca;
   }

   public void setImpressoSeguranca(ImpressoSeguranca impressoSeguranca) {
      this.impressoSeguranca=impressoSeguranca;
   }

   public UsuarioCartorio getUsuarioCartorio() {
      return usuarioCartorio;
   }

   public void setUsuarioCartorio(UsuarioCartorio usuarioCartorio) {
      this.usuarioCartorio=usuarioCartorio;
   }

   public Date getDataCadastroSistema() {
      return dataCadastroSistema;
   }

   public void setDataCadastroSistema(Date dataCadastroSistema) {
      this.dataCadastroSistema=dataCadastroSistema;
   }

   public String getMatricula() {
      return matricula;
   }

   public void setMatricula(String matricula) {
      this.matricula=matricula;
   }
   
}
