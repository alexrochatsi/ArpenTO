package br.com.datasind.cadastro;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.UF;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroUF extends CadastroPadrao {

	@SuppressWarnings("deprecation")
	public Connection retornaConnexao() throws CadastroException {
		return getSession().connection();
	}

	@SuppressWarnings("unchecked")
	public List<UF> listaTodosUFs() throws ApplicationException, CadastroException {
		Session session = null;
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();
		try {
			Criteria c = session.createCriteria(UF.class);

			return c.list();
		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}

	}

	@SuppressWarnings("rawtypes")
   public UF findById(UF uf) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UF u where u.id = :id ");
			query.setInteger("id", uf.getId());

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("UF nao encontrado.");
			}

			return (UF) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	@SuppressWarnings("rawtypes")
   public UF findById(Integer id) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UF u where u.id = :id ");
			query.setInteger("id", id);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("UF nao encontrado.");
			}

			return (UF) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

}
