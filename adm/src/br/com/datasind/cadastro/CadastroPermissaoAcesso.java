package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Perfil;
import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.gerente.GerenteTransacao;

;

public class CadastroPermissaoAcesso extends CadastroPadrao {
	/**
	 * A-01
	 * 
	 * @param perfil
	 * @return
	 * @throws CadastroException
	 */

	public List<PermissaoAcesso> findPermissoesAcessoByPerfil(Perfil perfil) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from PermissaoAcesso o where o.perfil.id = :perfil");
			query.setInteger("perfil", perfil.getId().intValue());

			@SuppressWarnings("unchecked")
			List<PermissaoAcesso> results = query.list();

			return results;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public PermissaoAcesso findById(PermissaoAcesso PermissaoAcesso) throws CadastroException, FinderException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery("from PermissaoAcesso u where u.id = :id ");
			query.setInteger("id", PermissaoAcesso.getId());

			@SuppressWarnings("unchecked")
			List<PermissaoAcesso> results = query.list();

			if (results.size() < 1) {
				throw new FinderException("PermissaoAcesso nao encontrada.");
			}

			return (PermissaoAcesso) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

}
