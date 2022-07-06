package br.com.datasind.validacao;

import br.com.datasind.entidade.Usuario;




/**
 * @Author osmar
 * @since 06/06/2011
 *
 **/
public class ValidadorUsuario extends ValidadorPadrao {

  
    public void validar(Object object) throws ValidacaoException {
        Usuario usuario = (Usuario) object;
        if (usuario.getNomeCompleto() == null || usuario.getNomeCompleto().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'NOME' deve ser preenchido.");
        }
        if (usuario.getLogin() == null || usuario.getLogin().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'LOGIN' deve ser preenchido.");
        }
       
        if (usuario.getSenha() == null || usuario.getSenha().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'SENHA' deve ser preenchido.");
        }
       
    }
}
