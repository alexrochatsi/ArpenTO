package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.RegistroNascimento;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroRegistroNascimento extends CadastroPadrao {

	@SuppressWarnings("unchecked")
   public List<RegistroNascimento> findByUsuarioCartorioId(UsuarioCartorio usuarioCartorio) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from RegistroNascimento rn where rn.usuarioCartorio.id = :id ");
			query.setInteger("id", usuarioCartorio.getId());

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Registro nao encontrado.");
			}

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	public RegistroNascimento findById(RegistroNascimento registroNascimento) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from RegistroNascimento us where us.id = :id ");
			query.setInteger("id", registroNascimento.getId());

			List results = query.list();

			if (results.size() < 1) {
				throw new FinderException("Usuario nao encontrado.");
			}

			return (RegistroNascimento) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RegistroNascimento> findAll() throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from RegistroNascimento");
			List<RegistroNascimento> results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}
}