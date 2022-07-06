
package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorLoginCartorio implements Inicializador{
   public void inicializar(Object object) {
	  try {
		 UsuarioCartorio usuarioCartorio=(UsuarioCartorio) object;
		 Hibernate.initialize(usuarioCartorio);

	  } catch (HibernateException e) {
		 throw new RuntimeException(e);
	  }
   }
}
