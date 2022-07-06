package br.com.datasind.conversao;

import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Perfil;

/**
 * @Author ailton
 * @since
 * 
 **/
public class ConversorPerfil extends ConversorPadrao {

	public Object converter(Object object) {
		Perfil perfil = (Perfil) object;
		perfil.setDescricao((String) super.converter(perfil.getDescricao()));
		perfil.setModulo((Modulo) super.converter(perfil.getModulo()));
		return perfil;
	}

}