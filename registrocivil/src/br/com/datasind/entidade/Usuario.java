
package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="usuario")
public class Usuario extends EntidadePadrao{

   @Transient
   private static final long serialVersionUID=3834594322212599345L;
   @Transient
   public static final String PRIMEIRA_SENHA="datasind123";
   @Transient
   public static final int TAMANHO_MINIMO_SENHA=5;

   @Id
   @GeneratedValue
   private Integer id;
   private String senha;
   private Boolean flagAtivo;
   private Date dataUltimoLogin;
   private String login;
   private String nomeCompleto;
   private String telefone;
   private Integer ramal;
   private String email;
   private Boolean administradorGeral;

   public String getTelefone() {
	  return telefone;
   }

   public void setTelefone(String telefone) {
	  this.telefone=telefone;
   }

   public String getEmail() {
	  return email;
   }

   public void setEmail(String email) {
	  this.email=email;
   }

   public String getNomeCompleto() {
	  return nomeCompleto;
   }

   public void setNomeCompleto(String nomeCompleto) {
	  this.nomeCompleto=nomeCompleto;
   }

   public Integer getRamal() {
	  return ramal;
   }

   public void setRamal(Integer ramal) {
	  this.ramal=ramal;
   }

   public void setLogin(String login) {
	  this.login=login;
   }

   public Usuario() {
	  super();
   }

   public Integer getId() {
	  return id;
   }

   public void setId(Integer id) {
	  this.id=id;
   }

   public String getSenha() {
	  return senha;
   }

   public void setSenha(String senha) {
	  this.senha=senha;
	  boolean foundLowerCase=false;
	  for(int i=0; i < senha.length() && !foundLowerCase; i++) {
		 if( !Character.isUpperCase(senha.charAt(i))) {
			foundLowerCase=true;
		 }
	  }

	  if( !foundLowerCase) {
		 System.out.println(senha);
	  }
   }

   public Boolean getFlagAtivo() {
	  return flagAtivo;
   }

   public void setFlagAtivo(Boolean flagAtivo) {
	  this.flagAtivo=flagAtivo;
   }

   public Date getDataUltimoLogin() {
	  return dataUltimoLogin;
   }

   public void setDataUltimoLogin(Date dataUltimoLogin) {
	  this.dataUltimoLogin=dataUltimoLogin;
   }

   public String getLogin() {
	  return login;
   }

   public Boolean getAdministradorGeral() {
	  return administradorGeral;
   }

   public void setAdministradorGeral(Boolean administradorGeral) {
	  this.administradorGeral=administradorGeral;
   }

}
