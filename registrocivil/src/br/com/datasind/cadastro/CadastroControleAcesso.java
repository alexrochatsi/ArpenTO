package br.com.datasind.cadastro;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroControleAcesso extends CadastroPadrao {

	@SuppressWarnings("unused")
   private static final Logger logger = Logger.getLogger(CadastroControleAcesso.class);

	/**
	 * 
	 * @param moduloStr
	 * @return
	 * @throws ApplicationException
	 * @throws CadastroException
	 */
	public Modulo selecionarModulo(String moduloStr)
			throws ApplicationException, CadastroException {
		Session session = null;
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente()
				.getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();

		try {
			Criteria criteria = session.createCriteria(Modulo.class);
			criteria.add(Restrictions.like("nomeInterno", moduloStr + "%"));
			return (Modulo) criteria.uniqueResult();
		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}
	}

	/**
	 * 
	 * @param usuarioSessao
	 * @param modulo
	 * @return
	 * @throws ApplicationException
	 * @throws CadastroException
	 */
	@SuppressWarnings("unchecked")
	public PermissaoAcesso selecionarPermissoesAcessoPorUsuarioEModulo(
			UsuarioSite usuarioSessao, Modulo modulo)
			throws ApplicationException, CadastroException {

		Session session = null;
		GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
		session = (Session) gerenteTransacao.getSessaoAtual();

		try {
			
			//entender como a Tabela funciona para setar o usu�rio na sess�o conforme o n�vel de acesso.
			StringBuffer buffer = new StringBuffer();
			buffer.append("select p from UsuarioSiteModuloPerfil u, PermissaoAcesso p "
					+ "join fetch p.perfil "
					+ "join fetch p.perfil.modulo where "
					+ "p.perfil = u.perfil and "
					+ "u.usuarioSite = :usuario");

			Query query = session.createQuery(buffer.toString());
			query.setEntity("usuarioSite", usuarioSessao);

			List<PermissaoAcesso> permissoes = query.list();
			if (permissoes == null || permissoes.size() == 0) {
				return null;
			}

			return (PermissaoAcesso) permissoes.get(0);

		} catch (HibernateException e1) {
			throw new CadastroException(e1);
		}
	}

	// private Boolean toBoolean(int i) {
	// return (i != 0);
	// }

}