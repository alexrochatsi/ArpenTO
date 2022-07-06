package br.com.datasind.conversao;

import br.com.datasind.entidade.Cidade;
import br.com.datasind.entidade.UF;



public class ConversorCidade extends ConversorPadrao {

 
    public Object converter(Object object) {
        Cidade cidade = (Cidade) object;
        cidade.setDescCidade((String)super.converter(cidade.getDescCidade()));
        cidade.setUf((UF)super.converter(cidade.getUf()));
        return cidade;
    }

}