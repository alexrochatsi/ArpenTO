package br.com.datasind.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="observacao")
public class Observacao extends EntidadePadrao{

   private static final long serialVersionUID=1117451570584468844L;
   @Id
   @GeneratedValue
   private Integer id;
   private String descricao;
   @ManyToOne
   private RegistroNascimento registroNascimento;
   
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
}
