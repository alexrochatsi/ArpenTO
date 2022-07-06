package br.com.datasind.validacao;

import br.com.datasind.entidade.Perfil;

public class ValidadorPerfil extends ValidadorPadrao{
	
	 public void validar(Object object) throws ValidacaoException {
		 
		 Perfil perfil = (Perfil) object;
		 if(perfil != null){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "Perfil Nulo!");
		 }
	 }
}
