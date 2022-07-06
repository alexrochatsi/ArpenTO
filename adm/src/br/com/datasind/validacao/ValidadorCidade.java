package br.com.datasind.validacao;

import br.com.datasind.entidade.Cidade;


public class ValidadorCidade extends ValidadorPadrao{
	
	 public void validar(Object object) throws ValidacaoException {
		 
		 Cidade cidade = (Cidade) object;
		 if(cidade != null){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "Cidade Nula!");
		 }
		 
		 if(cidade.getDescCidade() != null && !"".equals(cidade.getDescCidade())){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "Cidade de nome nulo!");
		 }
		 
		 if(cidade.getUf().getId() != null){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "Cidade de sem estado!");
		 }
	 }
}
