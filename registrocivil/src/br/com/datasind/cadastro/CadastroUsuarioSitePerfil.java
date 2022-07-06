package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.UsuarioSitePerfil;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroUsuarioSitePerfil extends CadastroPadrao {

	/**
	 * Localiza todos os objetos da classe UsuarioSite. O metodo e privado para
	 * servir como base para outros metodos finder. A nao ser que seja
	 * necessario, nao mude a visibilidade deste metodo.
	 * 
	 * @return Retorna todos os objetos UsuarioSite
	 * @throws CadastroException
	 */

	public UsuarioSitePerfil findByCpf(String cpf) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSitePerfil us where us.cpf = :cpf ");
			query.setString("cpf", cpf);

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Perfil usuario nao encontrado.");
			}

			return (UsuarioSitePerfil) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	public List findByNome(String nome) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSitePerfil u where u.nome like :nome ");
			query.setString("nome", nome + "%");

			List results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public UsuarioSitePerfil findById(UsuarioSitePerfil usuarioSitePerfil) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSitePerfil us where us.id = :id ");
			query.setInteger("id", usuarioSitePerfil.getId());

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (UsuarioSitePerfil) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioSitePerfil> findAllUsuarioSitePerfil() throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from UsuarioSitePerfil us where us.id != -1");
			List<UsuarioSitePerfil> results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
}