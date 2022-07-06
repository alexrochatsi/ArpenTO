package br.com.datasind.conversao;

import br.com.datasind.entidade.ContaBancaria;

public class ConversorContaBancaria extends ConversorPadrao {

 
    public Object converter(Object object) {
        ContaBancaria contaBancaria = (ContaBancaria) object;
        return contaBancaria;
    }

}