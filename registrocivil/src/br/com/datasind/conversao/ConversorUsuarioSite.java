package br.com.datasind.conversao;

import br.com.datasind.entidade.UsuarioSite;



/**
 * @Author alex
 * @since 24/06/2016
 *
 **/
public class ConversorUsuarioSite extends ConversorPadrao {

 
    public Object converter(Object object) {
        UsuarioSite usuarioSite = (UsuarioSite) object;
        usuarioSite.setNome((String)super.converter(usuarioSite.getNome()));
        usuarioSite.setEmail((String)super.converter(usuarioSite.getEmail()));
        return usuarioSite;
    }

}