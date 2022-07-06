
package br.com.datasind.entidade;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuariositeperfil")
public class UsuarioSitePerfil extends EntidadePadrao{

   private static final long serialVersionUID=4431627469920376798L;
   @Id
   @GeneratedValue
   private Integer id;
   private String nome;
   private String cpf;
   @Embedded
   private Endereco endereco;

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

   public String getCpf() {
	  return cpf;
   }

   public void setCpf(String cpf) {
	  this.cpf=cpf;
   }

   public Endereco getEndereco() {
	  return endereco;
   }

   public void setEndereco(Endereco endereco) {
	  this.endereco=endereco;
   }
}
