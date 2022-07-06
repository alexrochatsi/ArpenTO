package br.com.datasind.cadastro;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Pais;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroPais extends CadastroPadrao {

	@SuppressWarnings("deprecation")
	public Connection retornaConnexao() throws CadastroException {
		return getSession().connection();
	}

	@SuppressWarnings("unchecked")
	public List<Pais> listaTodosIbgeUFs() throws ApplicationException, CadastroException {
		Session session = null;
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();
		try {
			Criteria c = session.createCriteria(Pais.class);

			return c.list();
		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}

	}
	
	@SuppressWarnings("unchecked")
   public List<Pais> listaIbgeUFsPorCidade(String pais) throws ApplicationException, CadastroException {
		Session session = null;
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();
		try {
		   Query query = session.createQuery("from Pais u where u.nome like :pais");
			query.setString("pais", pais.toUpperCase() + "%");

			List<Pais> results = query.list();
			if (results.size() < 1) {
				throw new FinderException("Pais nao encontrado.");
			}
			return results;
		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}

	}

	public Pais findById(Pais pais) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Pais u where u.id = :id ");
			query.setInteger("id", pais.getId());

			List<?> results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Pais nao encontrado.");
			}

			return (Pais) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public Pais findById(Integer id) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Pais u where u.id = :id ");
			query.setInteger("id", id);

			List<?> results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Pais nao encontrado.");
			}

			return (Pais) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

}
