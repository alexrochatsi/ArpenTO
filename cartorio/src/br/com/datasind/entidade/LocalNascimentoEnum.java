
package br.com.datasind.entidade;

public enum LocalNascimentoEnum {
   UNIDADE_SAUDE("Unidade de Saúde"), FORA_UNIDADE_SAUDE("Fora da Unidade de Saúde");

   private String label=null;

   LocalNascimentoEnum(String label){
		this.label = label;
	}

   public String getLabel() {
	  return label;
   }
}
