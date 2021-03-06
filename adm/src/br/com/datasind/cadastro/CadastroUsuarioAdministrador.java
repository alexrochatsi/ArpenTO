package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.UsuarioAdministrador;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroUsuarioAdministrador extends CadastroPadrao {

	/**
	 * Localiza todos os objetos da classe UsuarioCartorio. O metodo e privado para
	 * servir como base para outros metodos finder. A nao ser que seja
	 * necessario, nao mude a visibilidade deste metodo.
	 * 
	 * @return Retorna todos os objetos UsuarioCartorio
	 * @throws CadastroException
	 */
	public UsuarioAdministrador findByLogin(String login) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioAdministrador ua where ua.nomeLogin = :nome ");
			query.setString("nome", login);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (UsuarioAdministrador) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioAdministrador> findByLoginForValidacao(String login) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioAdministrador ua where ua.nomeLogin = :nome ");
			query.setString("nome", login);
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

			Query query = session.createQuery("from UsuarioAdministrador u where u.nomeLogin like :nome ");
			query.setString("nome", nome + "%");

			List results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public UsuarioAdministrador findById(UsuarioAdministrador usuarioAdministrador) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioAdministrador ua where ua.id = :id ");
			query.setInteger("id", usuarioAdministrador.getId());

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (UsuarioAdministrador) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	public UsuarioAdministrador findByUID(String uid) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioAdministrador ua where ua.UID = :uid ");
			query.setString("uid", uid);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (UsuarioAdministrador) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioAdministrador> findAllUsuarioAdministrador() throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioAdministrador");
			List<UsuarioAdministrador> results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
}