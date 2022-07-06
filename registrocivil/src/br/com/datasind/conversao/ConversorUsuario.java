package br.com.datasind.conversao;

import br.com.datasind.entidade.Usuario;



/**
 * @Author osmar
 * @since 06/06/2011
 *
 **/
public class ConversorUsuario extends ConversorPadrao {

 
    public Object converter(Object object) {
        Usuario usuario = (Usuario) object;
        usuario.setNomeCompleto((String)super.converter(usuario.getNomeCompleto()));
        usuario.setLogin(((String)super.converter(usuario.getLogin())));
        usuario.setEmail((String)super.converter(usuario.getEmail()));
        return usuario;
    }

}