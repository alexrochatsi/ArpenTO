package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.Perfil;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorPerfil implements Inicializador {

	@Override
	public void inicializar(Object object) {
		// TODO Auto-generated method stub
		try {
			Perfil perfil = (Perfil) object;
			Hibernate.initialize(perfil);
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}

}
