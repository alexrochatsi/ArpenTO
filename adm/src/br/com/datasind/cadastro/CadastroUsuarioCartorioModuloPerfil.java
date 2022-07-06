package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.entidade.UsuarioAdministradorModuloPerfil;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroUsuarioCartorioModuloPerfil extends CadastroPadrao {

	public List<UsuarioAdministradorModuloPerfil> obterListaUsuarioCartorioModuloPerfilPorUsuario(
			UsuarioCartorio usuarioCartorio) throws ApplicationException,
			CadastroException {
		Session session = null;

		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente()
				.getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();

		Query query = null;

		try {
			query = session
					.createQuery("FROM UsuarioCartorioModuloPerfil ump WHERE ump.usuario = :usuarioCartorio");

			query.setInteger("usuarioCartorio", usuarioCartorio.getId());

			@SuppressWarnings("unchecked")
			List<UsuarioAdministradorModuloPerfil> list= query.list();
			return list;

		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}
	}

	public UsuarioAdministradorModuloPerfil findById(UsuarioAdministradorModuloPerfil usuarioCartorioModuloPerfil) throws CadastroException {
		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto()
					.getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session
					.createQuery("from UsuarioCartorioModuloPerfil u where u.id = :id ");
			query.setInteger("id", usuarioCartorioModuloPerfil.getId());

			@SuppressWarnings("unchecked")
			List<UsuarioAdministradorModuloPerfil> results = query.list();

			return (UsuarioAdministradorModuloPerfil) results.get(0);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public UsuarioAdministradorModuloPerfil findByUsuarioCartorioPerfil(Integer idUsuarioCartorio, Integer idPerfil)
			throws ApplicationException, CadastroException {
		Session session = null;
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente()
				.getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();

		try {
			Criteria criteria = session.createCriteria(UsuarioAdministradorModuloPerfil.class);
			criteria.add(Restrictions.eq("usuarioCartorio.id", idUsuarioCartorio));
			criteria.add(Restrictions.eq("perfil.id", idPerfil));
			return (UsuarioAdministradorModuloPerfil) criteria.uniqueResult();
		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}
	}
}
