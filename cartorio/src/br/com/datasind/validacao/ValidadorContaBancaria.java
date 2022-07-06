package br.com.datasind.validacao;

import br.com.datasind.entidade.ContaBancaria;


public class ValidadorContaBancaria extends ValidadorPadrao{
	
	 public void validar(Object object) throws ValidacaoException {
		 
		ContaBancaria contaBancaria = (ContaBancaria) object;
		 if(contaBancaria != null){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "ContaBancaria Nula!");
		 }
	 }
}
