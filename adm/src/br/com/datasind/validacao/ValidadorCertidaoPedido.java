
package br.com.datasind.validacao;

import br.com.datasind.entidade.CertidaoPedido;

/**
 * @Author alex_rocha
 * @since 24/06/2016
 *
 **/
public class ValidadorCertidaoPedido extends ValidadorPadrao{

   public void validar(Object object) throws ValidacaoException {
	  CertidaoPedido certidaoPedido=(CertidaoPedido) object;
	  
	  if(certidaoPedido.getDataPedido() == null) {
		 throw new ValidacaoException("O campo 'DATA PEDIDO' deve ser preenchido.");
	  }

	  if(certidaoPedido.getCustoPedido() == null) {
		 throw new ValidacaoException("O campo 'TIPO DE CERTIDAO' deve ser selecionado.");
	  }

	  if(certidaoPedido.getCartorio() == null) {
		 throw new ValidacaoException("O 'CARTORIO' deve ser selecionado.");
	  }

	  if(certidaoPedido.getCorreios() == null) {
		 throw new ValidacaoException("O 'TIPO DE ENVIO' deve ser selecionado.");
	  }
   }
}
