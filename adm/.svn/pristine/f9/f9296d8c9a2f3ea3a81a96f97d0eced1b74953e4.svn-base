package br.com.datasind.validacao;

import br.com.datasind.entidade.UsuarioAdministradorModuloPerfil;



public class ValidadorUsuarioModuloPerfil extends ValidadorPadrao {

	public void validar(Object object) throws ValidacaoException {
		UsuarioAdministradorModuloPerfil usuarioModuloPerfil = (UsuarioAdministradorModuloPerfil) object;
		if (usuarioModuloPerfil.getPerfil() == null) {
			throw new ValidacaoException("Informe o Perfil do usuario.");
		}
		if (usuarioModuloPerfil.getPerfil() == null
				|| usuarioModuloPerfil.getPerfil().getId() == null) {
			throw new ValidacaoException("Selecione um modulo.");
		}		
	}

}