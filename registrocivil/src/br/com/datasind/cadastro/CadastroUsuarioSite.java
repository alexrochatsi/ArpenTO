package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroUsuarioSite extends CadastroPadrao {

	/**
	 * Localiza todos os objetos da classe UsuarioSite. O metodo e privado para
	 * servir como base para outros metodos finder. A nao ser que seja
	 * necessario, nao mude a visibilidade deste metodo.
	 * 
	 * @return Retorna todos os objetos UsuarioSite
	 * @throws CadastroException
	 */

	public UsuarioSite findByEmail(String email) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSite us where us.email = :nome ");
			query.setString("nome", email);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (UsuarioSite) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioSite> findByEmailForValidacao(String email) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSite us where us.email = :nome ");
			query.setString("nome", email.toUpperCase());
			// System.out.println(login.toUpperCase());
			List results = query.list();

			// if (results.size() < 1) {
			// throw new FinderException("Usuario nao encontrado.");
			// }

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	public UsuarioSite findByEmailForValidacaoSingle(String email) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSite us where us.email = :nome ");
			query.setString("nome", email.toUpperCase());
			// System.out.println(login.toUpperCase());
			UsuarioSite result = (UsuarioSite) query.uniqueResult();

			// if (results.size() < 1) {
			// throw new FinderException("Usuario nao encontrado.");
			// }

			return result;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	/**
	 * @param nome
	 * @return
	 * @throws CadastroException
	 */
	public List findByNome(String nome) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSite u where u.nome like :nome ");
			query.setString("nome", nome + "%");

			List results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public UsuarioSite findById(UsuarioSite usuarioSite) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSite us where us.id = :id ");
			query.setInteger("id", usuarioSite.getId());

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (UsuarioSite) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	public UsuarioSite findByUID(String uid) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSite us where us.UID = :uid ");
			query.setString("uid", uid);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (UsuarioSite) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioSite> findAllUsuarioSite() throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSite us where us.id != 1");
			List<UsuarioSite> results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
}