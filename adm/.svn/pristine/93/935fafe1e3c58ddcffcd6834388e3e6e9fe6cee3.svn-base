package br.com.datasind.validacao;

import br.com.datasind.entidade.UsuarioAdministrador;




/**
 * @Author alex_rocha
 * @since 24/06/2016
 *
 **/
public class ValidadorUsuarioAdministrador extends ValidadorPadrao {

  
    public void validar(Object object) throws ValidacaoException {
       UsuarioAdministrador usuarioAdministrador = (UsuarioAdministrador) object;
        if (usuarioAdministrador.getNome() == null || usuarioAdministrador.getNome().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'NOME' deve ser preenchido.");
        }
       
        if (usuarioAdministrador.getSenha() == null || usuarioAdministrador.getSenha().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'SENHA' deve ser preenchido.");
        }
       
    }
}
