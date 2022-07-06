package br.com.datasind.entidade;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="local")
public class Local extends EntidadePadrao{

   private static final long serialVersionUID=7910708074035088282L;
   
   @Id
   @GeneratedValue
   private Integer id;
   private String descricao;
   @Embedded
   private Endereco endereco; 
   
   public Local() {
	  endereco=new Endereco();
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id=id;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao=descricao;
   }

   public Endereco getEndereco() {
      return endereco;
   }

   public void setEndereco(Endereco endereco) {
      this.endereco=endereco;
   }
}
