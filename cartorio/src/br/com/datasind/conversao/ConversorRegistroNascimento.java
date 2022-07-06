package br.com.datasind.conversao;

import br.com.datasind.entidade.RegistroNascimento;;



/**
 * @Author alex
 * @since 24/06/2016
 *
 **/
public class ConversorRegistroNascimento extends ConversorPadrao {

 
    public Object converter(Object object) {
       RegistroNascimento registroNascimento = (RegistroNascimento) object;
        return registroNascimento;
    }

}