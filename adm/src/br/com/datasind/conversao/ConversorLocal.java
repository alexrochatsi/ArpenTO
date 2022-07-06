package br.com.datasind.conversao;

import br.com.datasind.entidade.Local;



/**
 * @Author alex
 * @since 24/06/2016
 *
 **/
public class ConversorLocal extends ConversorPadrao {

 
    public Object converter(Object object) {
       Local local = (Local) object;
        return local;
    }

}