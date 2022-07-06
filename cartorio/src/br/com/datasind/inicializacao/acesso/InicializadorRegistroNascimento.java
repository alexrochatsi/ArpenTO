package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.RegistroNascimento;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorRegistroNascimento implements Inicializador {

	@Override
	public void inicializar(Object object) {
		// TODO Auto-generated method stub
		try{
			RegistroNascimento registroNascimento = (RegistroNascimento) object;
			Hibernate.initialize(registroNascimento.getGenitor());
			Hibernate.initialize(registroNascimento.getGenitora());
			Hibernate.initialize(registroNascimento.getUsuarioCartorio());
			Hibernate.initialize(registroNascimento.getMunicipioUF());
			Hibernate.initialize(registroNascimento.getLocalNascimento());
			}catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}