package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.UsuarioCartorioModuloPerfil;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorUsuarioCartorioModuloPerfil implements Inicializador {

	@Override
	public void inicializar(Object object) {
		// TODO Auto-generated method stub
		try{
			UsuarioCartorioModuloPerfil usuarioModuloPerfil = (UsuarioCartorioModuloPerfil) object;
			Hibernate.initialize(usuarioModuloPerfil.getPerfil());
			Hibernate.initialize(usuarioModuloPerfil.getUsuarioCartorio());
			Hibernate.initialize(usuarioModuloPerfil.getPerfil().getModulo());
			}catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}
