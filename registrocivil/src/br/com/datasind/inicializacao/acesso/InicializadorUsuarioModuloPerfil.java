package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.UsuarioSiteModuloPerfil;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorUsuarioModuloPerfil implements Inicializador {

	@Override
	public void inicializar(Object object) {
		// TODO Auto-generated method stub
		try{
			UsuarioSiteModuloPerfil usuarioModuloPerfil = (UsuarioSiteModuloPerfil) object;
			Hibernate.initialize(usuarioModuloPerfil.getPerfil());
			Hibernate.initialize(usuarioModuloPerfil.getUsuarioSite());
			Hibernate.initialize(usuarioModuloPerfil.getPerfil().getModulo());
			}catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}
