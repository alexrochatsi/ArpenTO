package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorCertidaoPedido implements Inicializador {

	@Override
	public void inicializar(Object object) {
		try{
			CertidaoPedido certidaoPedido = (CertidaoPedido) object;
			Hibernate.initialize(certidaoPedido.getCartorio());
			Hibernate.initialize(certidaoPedido.getCorreios());
			Hibernate.initialize(certidaoPedido.getCustoPedido());
			}catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}
