
package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorLoginSite implements Inicializador{
   public void inicializar(Object object) {
	  try {
		 UsuarioSite usuarioSite=(UsuarioSite) object;
		 Hibernate.initialize(usuarioSite);

	  } catch (HibernateException e) {
		 throw new RuntimeException(e);
	  }
   }
}
