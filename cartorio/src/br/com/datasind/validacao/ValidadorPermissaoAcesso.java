
package br.com.datasind.validacao;

import br.com.datasind.entidade.PermissaoAcesso;

public class ValidadorPermissaoAcesso extends ValidadorPadrao{

   @Override
   public void validar(Object object) throws ValidacaoException {
	  // TODO Auto-generated method stub
	  PermissaoAcesso permissaoAcesso=(PermissaoAcesso) object;

	  if(permissaoAcesso.getPerfil().getId() == null) {
		 throw new ValidacaoException("Selecione um Perfil!");
	  }

   }

}
