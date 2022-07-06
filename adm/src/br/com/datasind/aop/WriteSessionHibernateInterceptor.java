package br.com.datasind.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import br.com.datasind.gerente.FabricaGerente;
import br.com.datasind.gerente.GerenteTransacao;
import br.com.datasind.transacao.Transacao;

public class WriteSessionHibernateInterceptor implements MethodInterceptor, ApplicationContextAware {

	private ThreadLocal<Boolean> existingTransaction = new ThreadLocal<Boolean>();

	private static Logger logger = Logger.getLogger(WriteSessionHibernateInterceptor.class);

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		logger.debug("Interceptando " + methodInvocation.getMethod());

		FabricaGerente fabricaGerente = (FabricaGerente) applicationContext
				.getBean("fabricaGerente");

		GerenteTransacao gerenteTransacao = fabricaGerente.getGerenteTransacao();
		Session currentSession = (Session) gerenteTransacao.getSessaoAtual();

		Transacao transacao = null;
		if (existingTransaction.get() == null || existingTransaction.get() == Boolean.FALSE) {
			currentSession.clear();
			try {
				currentSession.setFlushMode(FlushMode.COMMIT);
				transacao = gerenteTransacao.criarTransacao();

				existingTransaction.set(Boolean.TRUE);
				Object value = methodInvocation.proceed();

				transacao.commit();
				return value;

			} catch (Exception e) {
				if (transacao != null) {
					transacao.rollback();
				}
				throw e;

			} finally {
				if (currentSession.isOpen()) {
					currentSession.setFlushMode(FlushMode.MANUAL);
				}
				existingTransaction.set(null);
			}

		} else {
			return methodInvocation.proceed();
		}

		/*
		 * 
		 * Session writeSession = null; if(writeSessionHolder.get() == null) {
		 * logger.debug("Criando uma nova sessao de escrita.");
		 * existingTransaction = false;
		 * 
		 * writeSession = FabricaTransacaoHibernate.getNewSession();
		 * writeSessionHolder.set(writeSession);
		 * 
		 * } else { logger.debug("Sessao de escrita ja existe: " +
		 * writeSessionHolder.get());
		 * 
		 * existingTransaction = true; writeSession = writeSessionHolder.get();
		 * }
		 * 
		 * if(writeSession != previousSession){
		 * FabricaTransacaoHibernate.setSessaoAtual(writeSession); }
		 * 
		 * FlushMode previousFlushMode = null; try { previousFlushMode =
		 * applyFlushMode(writeSession, existingTransaction); Object retVal =
		 * methodInvocation.proceed(); flushIfNecessary(writeSession,
		 * existingTransaction); return retVal;
		 * 
		 * } catch (HibernateException ex) { throw ex;
		 * 
		 * } finally { if (existingTransaction) { logger.debug(
		 * "Not closing pre-bound Hibernate Session after HibernateInterceptor"
		 * ); if (previousFlushMode != null) {
		 * writeSession.setFlushMode(previousFlushMode); } } else {
		 * writeSessionHolder.set(null);
		 * 
		 * previousSession.clear();
		 * FabricaTransacaoHibernate.setSessaoAtual(previousSession);
		 * writeSession.close(); } }
		 */
	}

	/*
	 * private void transferObjects(Object[] objects, Session source, Session
	 * dest) throws HibernateException { for (int i = 0; i < objects.length;
	 * i++) { if(objects[i] instanceof List) { List list = (List) objects[i];
	 * Iterator iter = list.iterator(); while(iter.hasNext()) { Object current =
	 * iter.next(); source.evict(current); if(current instanceof HibernateProxy)
	 * { LazyInitializer li = HibernateProxyHelper
	 * .getLazyInitializer((HibernateProxy) current);
	 * 
	 * li.setSession(null); } } } else { source.evict(objects[i]);
	 * dest.lock(objects[i], LockMode.NONE); }
	 * 
	 * } }
	 */

	// comentado por Osmar
	// private void flushIfNecessary(Session writeSession, boolean
	// existingTransaction) throws HibernateException {
	// if (existingTransaction) {
	// return;
	// } else {
	// writeSession.flush();
	// }
	// }
	//
	// private FlushMode applyFlushMode(Session writeSession, boolean
	// existingTransaction) {
	// FlushMode previous = writeSession.getFlushMode();
	// writeSession.setFlushMode(FlushMode.COMMIT);
	//
	// return previous;
	// }

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
