package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Modulo;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroModulo extends CadastroPadrao {

	@SuppressWarnings("unchecked")
	public List<Modulo> getListaModulos() throws CadastroException {
		return findAll(Modulo.class);
	}

	public Modulo findModuloByNomeInterno(String str) {
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();

		Session session = (Session) gerenteTransacao.getSessaoAtual();

		Query query = session.createQuery("from Modulo m where m.nomeInterno like :nomeInterno ");

		query.setString("nomeInterno", str + "/%");
		List<?> results = query.list();

		if (results.size() == 0) {
			return null;
		}
		return (Modulo) results.get(0);
	}
	
	public Modulo findById(Modulo modulo) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Modulo u where u.id = :id ");
			query.setInteger("id", modulo.getId());

			List<?> results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Modulo nao encontrado.");
			}

			return (Modulo) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public Modulo findById(Integer id) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Modulo u where u.id = :id ");
			query.setInteger("id", id);

			List<?> results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Modulo nao encontrado.");
			}

			return (Modulo) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
}