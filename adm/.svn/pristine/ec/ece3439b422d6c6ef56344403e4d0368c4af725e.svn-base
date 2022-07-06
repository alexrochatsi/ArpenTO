package br.com.datasind.validacao;

import br.com.datasind.entidade.UF;

public class ValidadorUF extends ValidadorPadrao{
	
	 public void validar(Object object) throws ValidacaoException {
		 
		 UF uf = (UF) object;
		 if(uf != null){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "UF Nula!");
		 }
		 
		 if(uf.getDescUF() != null && !"".equals(uf.getDescUF())){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "UF nome nulo!");
		 }
		 
		 if(uf.getSiglaUF() != null && !"".equals(uf.getSiglaUF())){
			 //faz nada
		 }else{
			 throw new ValidacaoException(
             "UF sigla nula!");
		 }
	 }
}
