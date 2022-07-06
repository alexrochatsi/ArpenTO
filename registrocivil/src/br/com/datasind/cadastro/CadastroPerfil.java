package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Perfil;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroPerfil extends CadastroPadrao {

	public CadastroPerfil() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Perfil> findAll() throws CadastroException {
		return findAll(Perfil.class);
	}

	/**
	 * A-01 Retorna a lista de perfis relacionados ao modulo
	 * 
	 * @param modulo
	 * @return lista de perfis
	 * @throws CadastroException
	 */
	@SuppressWarnings("unchecked")
	public List<Perfil> findPerfisByModulo(Modulo modulo) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Perfil o where o.modulo.id = :modulo");
			query.setInteger("modulo", modulo.getId().intValue());

			List<Perfil> results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	@SuppressWarnings("rawtypes")
   public Perfil findById(Integer id) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Perfil u where u.id = :id ");
			query.setInteger("id", id);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Perfil nao encontrado.");
			}

			return (Perfil) results.get(0);
			/*
			 * List<Perfil list = new ArrayList<Perfil>(); list.addAll(results);
			 * return list;
			 */

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public List<Perfil> findByModulo(Perfil perfil) throws CadastroException {

		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Perfil o where o.modulo.id = :modulo");
			/*
			 * query.setString("perfil", "%" +
			 * perfil.getDescricao().toUpperCase() + "%");
			 */

			query.setInteger("modulo", perfil.getModulo().getId().intValue());

			@SuppressWarnings("unchecked")
			List<Perfil> results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

}
