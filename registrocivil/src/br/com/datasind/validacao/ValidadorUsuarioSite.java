package br.com.datasind.validacao;

import br.com.datasind.entidade.UsuarioSite;




/**
 * @Author alex_rocha
 * @since 24/06/2016
 *
 **/
public class ValidadorUsuarioSite extends ValidadorPadrao {

  
    public void validar(Object object) throws ValidacaoException {
        UsuarioSite usuarioSite = (UsuarioSite) object;
        if (usuarioSite.getNome() == null || usuarioSite.getNome().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'NOME' deve ser preenchido.");
        }
        if (usuarioSite.getEmail() == null || usuarioSite.getEmail().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'EMAIL' deve ser preenchido.");
        }
       
        if (usuarioSite.getSenha() == null || usuarioSite.getSenha().equalsIgnoreCase("")) {
            throw new ValidacaoException(
                    "O campo 'SENHA' deve ser preenchido.");
        }
       
    }
}
