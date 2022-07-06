package br.com.datasind.conversao;

import br.com.datasind.entidade.UsuarioAdministrador;

/**
 * @Author alex
 * @since 24/06/2016
 *
 **/
public class ConversorUsuarioAdministrador extends ConversorPadrao {

 
    public Object converter(Object object) {
       UsuarioAdministrador usuarioAdministrador = (UsuarioAdministrador) object;
       usuarioAdministrador.setNome((String)super.converter(usuarioAdministrador.getNome()));
       usuarioAdministrador.setEmail((String)super.converter(usuarioAdministrador.getEmail()));
        return usuarioAdministrador;
    }

}