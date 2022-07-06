package br.com.datasind.conversao;

import br.com.datasind.entidade.Modulo;

/**
 * @Author ailton
 * @since
 * 
 **/
public class ConversorModulo extends ConversorPadrao {

	public Object converter(Object object) {
		Modulo modulo = (Modulo) object;
		modulo.setDescricao((String) super.converter(modulo.getDescricao()));

		modulo.setNomeInterno((String) super.converter(modulo.getNomeInterno()));
		return modulo;
	}

}