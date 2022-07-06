package br.com.datasind.aop;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.aop.ThrowsAdvice;

import br.com.datasind.transacao.hibernate.FabricaTransacaoHibernate;


public class CloseSessionOnHibernateExceptionAdvice implements ThrowsAdvice {
	private static final Logger logger = Logger.getLogger(CloseSessionOnHibernateExceptionAdvice.class);
	
	public void afterThrowing(Exception exception) {
		logger.debug("Executando afterThrowing para " + exception);
		
		logger.debug("Procurando por um HibernateException");
		Throwable current = exception;
		while (current != null) {
			logger.debug("Analizando " + current);
			if (current instanceof HibernateException) {
				logger.debug("Encontrado. Finalizando Sessao");
				try {
					FabricaTransacaoHibernate.sessaoFinalizada();
					
				} catch (Exception e) {
					logger.error(e);
				}
				current = null;
				
			} else {
				if (current != current.getCause()) {
					current = current.getCause();
				} else {
					current = null;
				}
			}
		}
		logger.debug("Saindo do afterThrowing para " + exception);
	}
}

