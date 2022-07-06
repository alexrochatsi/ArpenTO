package br.com.datasind.conversao;

import br.com.datasind.entidade.UsuarioCartorio;



/**
 * @Author alex
 * @since 24/06/2016
 *
 **/
public class ConversorUsuarioCartorio extends ConversorPadrao {

 
    public Object converter(Object object) {
        UsuarioCartorio usuarioCartorio = (UsuarioCartorio) object;
        usuarioCartorio.setNome((String)super.converter(usuarioCartorio.getNome()));
        usuarioCartorio.setEmail((String)super.converter(usuarioCartorio.getEmail()));
        return usuarioCartorio;
    }

}