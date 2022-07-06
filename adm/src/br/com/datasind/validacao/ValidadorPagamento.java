
package br.com.datasind.validacao;

import br.com.datasind.entidade.Pagamento;

/**
 * @Author alex_rocha
 * @since 24/06/2016
 *
 **/
public class ValidadorPagamento extends ValidadorPadrao{

   public void validar(Object object) throws ValidacaoException {
	  Pagamento certidaoPedido=(Pagamento) object;

	  if(certidaoPedido.getCartorio() == null) {
		 throw new ValidacaoException("O 'CARTORIO' deve ser selecionado.");
	  }
   }
}
