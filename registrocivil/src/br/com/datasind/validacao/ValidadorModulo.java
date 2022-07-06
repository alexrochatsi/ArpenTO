package br.com.datasind.validacao;

import br.com.datasind.entidade.Modulo;

public class ValidadorModulo extends ValidadorPadrao{
	
	 public void validar(Object object) throws ValidacaoException {
		 
		 Modulo modulo = (Modulo) object;
		 if(modulo != null){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "Modulo Nulo!");
		 }
	 }
}
