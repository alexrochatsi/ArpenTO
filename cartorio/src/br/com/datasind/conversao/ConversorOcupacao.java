package br.com.datasind.conversao;

import br.com.datasind.entidade.Ocupacao;



/**
 * @Author alex
 * @since 24/06/2016
 *
 **/
public class ConversorOcupacao extends ConversorPadrao {

 
    public Object converter(Object object) {
       Ocupacao ocupacao = (Ocupacao) object;
        return ocupacao;
    }

}