package br.com.datasind.entidade;

public enum SexoEnum {
   FEMININO("Feminino"), MASCULINO("Masculino"), IGNORADO("Ignorado");
   
   private String label = null;
   
   SexoEnum(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}
}
