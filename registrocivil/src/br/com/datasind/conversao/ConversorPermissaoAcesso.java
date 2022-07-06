package br.com.datasind.conversao;

import br.com.datasind.entidade.Perfil;
import br.com.datasind.entidade.PermissaoAcesso;

/**
 * @Author ailton
 * @since
 * 
 **/
public class ConversorPermissaoAcesso extends ConversorPadrao {

	public Object converter(Object object) {
		PermissaoAcesso permissaoAcesso = (PermissaoAcesso) object;
		permissaoAcesso.setAltera((Boolean) super.converter(permissaoAcesso.getAltera()));
		permissaoAcesso.setConsulta((Boolean) super.converter(permissaoAcesso.getConsulta()));
		permissaoAcesso.setExclui((Boolean) super.converter(permissaoAcesso.getExclui()));
		permissaoAcesso.setInclui((Boolean) super.converter(permissaoAcesso.getInclui()));
		permissaoAcesso.setPerfil((Perfil) super.converter(permissaoAcesso.getPerfil()));
		return permissaoAcesso;
	}

}