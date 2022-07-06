package br.com.datasind.cadastro;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Local;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroLocal extends CadastroPadrao {

	@SuppressWarnings("deprecation")
	public Connection retornaConnexao() throws CadastroException {
		return getSession().connection();
	}

	@SuppressWarnings("unchecked")
	public List<Local> listaTodosLocais() throws ApplicationException, CadastroException {
		Session session = null;
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();
		try {
			Criteria c = session.createCriteria(Local.class);
			
			if(c.list().size() < 1) {
				throw new FinderException("Locais nao encontrado.");
			 }

			return c.list();
		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}

	}

	public Local findById(Local local) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Local u where u.id = :id ");
			query.setInteger("id", local.getId());

			List<?> results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Local nao encontrado.");
			}

			return (Local) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public Local findById(Integer id) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Local u where u.id = :id ");
			query.setInteger("id", id);

			List<?> results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Local nao encontrado.");
			}

			return (Local) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

}
