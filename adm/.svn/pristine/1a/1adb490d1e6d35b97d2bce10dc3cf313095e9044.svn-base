
package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.UsuarioAdministrador;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorLoginAdministrador implements Inicializador{
   public void inicializar(Object object) {
	  try {
		 UsuarioAdministrador usuarioAdministrador=(UsuarioAdministrador) object;
		 Hibernate.initialize(usuarioAdministrador);

	  } catch (HibernateException e) {
		 throw new RuntimeException(e);
	  }
   }
}
