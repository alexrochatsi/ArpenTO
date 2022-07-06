package br.com.datasind.validacao;

import br.com.datasind.entidade.UsuarioSiteModuloPerfil;



public class ValidadorUsuarioModuloPerfil extends ValidadorPadrao {

	public void validar(Object object) throws ValidacaoException {
		UsuarioSiteModuloPerfil usuarioModuloPerfil = (UsuarioSiteModuloPerfil) object;
		if (usuarioModuloPerfil.getPerfil() == null) {
			throw new ValidacaoException("Informe o Perfil do usuario.");
		}
		if (usuarioModuloPerfil.getPerfil() == null
				|| usuarioModuloPerfil.getPerfil().getId() == null) {
			throw new ValidacaoException("Selecione um modulo.");
		}		
	}

}