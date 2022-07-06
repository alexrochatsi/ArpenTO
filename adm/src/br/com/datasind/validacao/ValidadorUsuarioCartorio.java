package br.com.datasind.validacao;

import br.com.datasind.entidade.UsuarioCartorio;




/**
 * @Author alex_rocha
 * @since 24/06/2016
 *
 **/
public class ValidadorUsuarioCartorio extends ValidadorPadrao {

  
    public void validar(Object object) throws ValidacaoException {
        UsuarioCartorio usuarioCartorio = (UsuarioCartorio) object;
        if (usuarioCartorio.getNome() == null || usuarioCartorio.getNome().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'NOME' deve ser preenchido.");
        }
        if (usuarioCartorio.getEmail() == null || usuarioCartorio.getEmail().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'EMAIL' deve ser preenchido.");
        }
       
        if (usuarioCartorio.getSenha() == null || usuarioCartorio.getSenha().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'SENHA' deve ser preenchido.");
        }
       
    }
}
