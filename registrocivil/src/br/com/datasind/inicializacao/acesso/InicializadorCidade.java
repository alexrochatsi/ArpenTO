package br.com.datasind.inicializacao.acesso;

import org.hibernate.Hibernate;

import br.com.datasind.entidade.Cidade;
import br.com.datasind.inicializador.Inicializador;

public class InicializadorCidade implements Inicializador {

	@Override
	public void inicializar(Object object) {
		// TODO Auto-generated method stub
		Cidade cidade = (Cidade) object;

		Hibernate.initialize(cidade);
		Hibernate.initialize(cidade.getUf());
	}

}
