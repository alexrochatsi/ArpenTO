package br.com.datasind.conversao;

import br.com.datasind.entidade.CertidaoPedido;



/**
 * @Author alex
 * @since 24/06/2016
 *
 **/
public class ConversorCertidaoPedido extends ConversorPadrao {

 
    public Object converter(Object object) {
        CertidaoPedido certidaoPedido = (CertidaoPedido) object;
        return certidaoPedido;
    }

}