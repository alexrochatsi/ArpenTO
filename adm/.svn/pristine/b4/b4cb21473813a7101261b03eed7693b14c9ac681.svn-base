package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.UsuarioAdministradorModuloPerfil;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorUsuarioCartorioModuloPerfil implements Inicializador {

	@Override
	public void inicializar(Object object) {
		// TODO Auto-generated method stub
		try{
			UsuarioAdministradorModuloPerfil usuarioModuloPerfil = (UsuarioAdministradorModuloPerfil) object;
			Hibernate.initialize(usuarioModuloPerfil.getPerfil());
			Hibernate.initialize(usuarioModuloPerfil.getPerfil().getModulo());
			}catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}
