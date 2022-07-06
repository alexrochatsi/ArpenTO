package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.inicializador.Inicializador;


public class InicializadorPermissaoAcesso implements Inicializador {

	@Override
	public void inicializar(Object object) {
		// TODO Auto-generated method stub
		PermissaoAcesso permissaoAcesso = (PermissaoAcesso) object;
		try {
			Hibernate.initialize(permissaoAcesso.getPerfil());
		}catch (HibernateException e) {
			throw new RuntimeException(e);
		}
		
	}

}
