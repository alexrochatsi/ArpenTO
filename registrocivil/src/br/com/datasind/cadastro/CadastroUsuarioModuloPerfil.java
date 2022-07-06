package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Usuario;
import br.com.datasind.entidade.UsuarioSiteModuloPerfil;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroUsuarioModuloPerfil extends CadastroPadrao {

	@SuppressWarnings("unchecked")
   public List<UsuarioSiteModuloPerfil> obterListaUsuarioModuloPerfilPorUsuario(
			Usuario usuario) throws ApplicationException,
			CadastroException {
		Session session = null;

		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente()
				.getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();

		Query query = null;

		try {
			query = session
					.createQuery("FROM UsuarioModuloPerfil ump WHERE ump.usuario = :usuario");

			query.setInteger("usuario", usuario.getId());

			return (List<UsuarioSiteModuloPerfil>) query.list();

		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}
	}

	@SuppressWarnings("rawtypes")
   public UsuarioSiteModuloPerfil findById(UsuarioSiteModuloPerfil usuarioModuloPerfil) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto()
					.getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session
					.createQuery("from UsuarioModuloPerfil u where u.id = :id ");
			query.setInteger("id", usuarioModuloPerfil.getId());

			List results = query.list();

			return (UsuarioSiteModuloPerfil) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public UsuarioSiteModuloPerfil findByUsuarioPerfil(Integer idUsuario, Integer idPerfil)
			throws ApplicationException, CadastroException {
		Session session = null;
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente()
				.getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();

		try {
			Criteria criteria = session.createCriteria(UsuarioSiteModuloPerfil.class);
			criteria.add(Restrictions.eq("usuario.id", idUsuario));
			criteria.add(Restrictions.eq("perfil.id", idPerfil));
			return (UsuarioSiteModuloPerfil) criteria.uniqueResult();
		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}
	}
}
