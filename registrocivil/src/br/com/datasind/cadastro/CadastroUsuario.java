package br.com.datasind.cadastro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Perfil;
import br.com.datasind.entidade.Usuario;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroUsuario extends CadastroPadrao {

	/**
	 * Localiza todos os objetos da classe Usuario. O metodo e privado para
	 * servir como base para outros metodos finder. A nao ser que seja
	 * necessario, nao mude a visibilidade deste metodo.
	 * 
	 * @return Retorna todos os objetos Usuario
	 * @throws CadastroException
	 */

	public Usuario findByLogin(String login) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Usuario u where u.login = :nome ");
			query.setString("nome", login);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (Usuario) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findByLoginForValidacao(String login) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Usuario u where u.login = :nome ");
			query.setString("nome", login.toUpperCase());
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

			Query query = session.createQuery("from Usuario u where u.nomeCompleto like :nome ");
			query.setString("nome", nome + "%");

			List results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	@SuppressWarnings("unchecked")
	public List findByPerfil(Perfil perfil) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("select ump.usuario from UsuarioModuloPerfil ump where ump.perfil =:perfil ");

			query.setEntity("perfil", perfil);

			List results = query.list();
			TreeSet<Usuario> set = new TreeSet<Usuario>(new Comparator<Usuario>() {
				public int compare(Usuario arg0, Usuario arg1) {
					return arg0.getNomeCompleto().compareTo(arg1.getNomeCompleto());
				}
			});
			set.addAll(results);

			List lista = new ArrayList();

			lista.addAll(set);

			return lista;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public Usuario findById(Usuario usuario) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Usuario u where u.id = :id ");
			query.setInteger("id", usuario.getId());

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (Usuario) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public Usuario findById(String idUsuario) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Usuario u where u.id = :id ");
			query.setString("id", idUsuario);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (Usuario) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAllUsuario() throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from Usuario u where u.id != 1");
			List<Usuario> results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

}