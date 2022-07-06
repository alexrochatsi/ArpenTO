package br.com.datasind.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pais")
public class Pais extends EntidadePadrao{

   private static final long serialVersionUID= -3363925480047337445L;
   
   @Id
   @GeneratedValue
   private Integer id;
   private String nome;
   private String codigo;

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

   public String getCodigo() {
      return codigo;
   }

   public void setCodigo(String codigo) {
      this.codigo=codigo;
   }

}