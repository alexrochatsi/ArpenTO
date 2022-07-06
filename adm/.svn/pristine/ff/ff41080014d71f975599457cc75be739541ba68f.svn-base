package br.com.datasind.transacao.hibernate;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.SessionFactory;
import br.com.datasind.transacao.ContextoFabricaTransacao;
import br.com.datasind.transacao.FabricaTransacao;
import br.com.datasind.transacao.OuvinteEstadoTransacao;
import br.com.datasind.transacao.Transacao;

public class FabricaTransacaoHibernate extends FabricaTransacao {
	private static final Logger LOGGER = Logger
			.getLogger(FabricaTransacaoHibernate.class);

	private static UserSessionMap userSessionMap;

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// private static HibernateInterceptor hibernateInterceptor;

	static {
		userSessionMap = new UserSessionMap();
		// hibernateInterceptor = new HibernateInterceptor();
	}

	@SuppressWarnings("unused")
	private ContextoFabricaTransacao contexto;

	public FabricaTransacaoHibernate() {

	}

	/**
	 * 
	 */
	public Transacao criarTransacao() throws ApplicationException {
		try {
			TransacaoHibernate transacao = new TransacaoHibernate(
					((Session) getSessaoAtual()).beginTransaction());

			transacao.addOuvinteEstadosTransacao(new OuvinteEstadoTransacao() {
				public void transacaoAbortada(Transacao transacao) {
					((Session) getSessaoAtual()).setFlushMode(FlushMode.MANUAL);
				}

				public void transacaoConcluida(Transacao transacao) {
					((Session) getSessaoAtual()).setFlushMode(FlushMode.MANUAL);
				}
			});

			return transacao;
		} catch (HibernateException e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * @deprecated
	 * 
	 * @param transacao
	 * @return Retorna a Sessao do Hibernate associada a transacao.
	 */
	public Session getSessaoHibernate(Transacao transacao) {
		return (Session) userSessionMap.get();
	}

	/**
	 * @return Retorna a Sessao do Hibernate associada a transacao.
	 * @throws ApplicationException
	 */
	public static Session getSessaoHibernate() throws ApplicationException {
		return (Session) userSessionMap.get();
	}

	public static synchronized void sessaoFinalizada() {
		Session sessaoAtual = (Session) userSessionMap.get();
		if (sessaoAtual != null) {
			try {
				LOGGER.info("Finalizando sessao atual");
				sessaoAtual.close();

				LOGGER.info("Sessao finalizada");

			} catch (HibernateException e) {
				LOGGER.error("Problemas finalizando a sessao", e);

				if (sessaoAtual != null) {
					try {
						sessaoAtual.close();
					} catch (HibernateException e1) {
						e1.printStackTrace();
					}
				}
			}
			userSessionMap.set(null);
		}
	}

	public Object getSessaoAtual() {
		Session sessaoAtual = (Session) userSessionMap.get();
		if (sessaoAtual == null) {
			try {
				LOGGER.info("Criando uma nova sessao.");
				sessaoAtual = getNewSession();
				sessaoAtual.setFlushMode(FlushMode.MANUAL);

				/*
				 * // Setando usuario q esta usando a conexao para esta sessao.
				 * PreparedStatement ps = sessaoAtual.connection().prepareCall(
				 * "begin dbms_session.set_identifier(?);end;");
				 * 
				 * ps.setString(1, contexto.getUserId()); ps.execute();
				 */

				setSessaoAtual(sessaoAtual);
			} catch (HibernateException e) {
				throw new RuntimeException(e);

			}/*
			 * catch (SQLException e) { throw new RuntimeException(e); }
			 */catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return sessaoAtual;
	}

	public static void setSessaoAtual(Session sessaoAtual) {
		userSessionMap.set(sessaoAtual);
	}

	public void setContexto(ContextoFabricaTransacao contexto) {
		this.contexto = contexto;
	}

	public Session getNewSession() throws HibernateException, SQLException {
		Session session = userSessionMap.get();
		if (session != null && session.isConnected()) {
			// return SessionFactory.createSession(session.connection(),
			// hibernateInterceptor);
			return sessionFactory.createSession(((SessionFactoryImplementor) session.getSessionFactory()).getConnectionProvider().getConnection());

		} else {
			// return SessionFactory.createSession(hibernateInterceptor);
			return sessionFactory.createSession();
		}
	}
}
