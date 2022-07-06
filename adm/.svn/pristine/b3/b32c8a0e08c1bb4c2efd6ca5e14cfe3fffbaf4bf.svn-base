package br.com.datasind.conversao;

import br.com.datasind.entidade.UF;

public class ConversorEstado extends ConversorPadrao {

	public Object converter(Object object) {

		UF uf = (UF) object;
		uf.setDescUF((String) super.converter(uf.getDescUF()));
		uf.setSiglaUF((String) super.converter(uf.getSiglaUF()));

		return uf;
	}

}