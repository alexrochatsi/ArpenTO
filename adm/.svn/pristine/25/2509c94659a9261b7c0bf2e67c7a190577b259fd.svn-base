
package br.com.datasind.transacao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.transacao.ListenableTransaction;
import br.com.datasind.transacao.OuvinteEstadoTransacao;

public class TransacaoHibernate implements ListenableTransaction{
   private Transaction transaction;

   private List<OuvinteEstadoTransacao> ouvinteEstadoTransacao;

   /**
    * 
    */
   public TransacaoHibernate(Transaction transaction) {
	  super();
	  this.transaction=transaction;
	  this.ouvinteEstadoTransacao=new ArrayList<OuvinteEstadoTransacao>();
   }

   /**
    * 
    */
   public void rollback() throws ApplicationException {
	  try {
		 transaction.rollback();
		 notifyTransacaoAbortada();

	  } catch (HibernateException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * 
    */
   public void commit() throws ApplicationException {
	  try {
		 transaction.commit();
		 notifyTransacaoConcluida();

	  } catch (HibernateException e) {
		 throw new ApplicationException(e);
	  }
   }

   /**
    * @param ouvinte
    */
   public void addOuvinteEstadosTransacao(OuvinteEstadoTransacao ouvinte) {
	  ouvinteEstadoTransacao.add(ouvinte);
   }

   /**
    * 
    */
   private void notifyTransacaoConcluida() {
	  for(@SuppressWarnings("rawtypes")
	  Iterator i=ouvinteEstadoTransacao.iterator(); i.hasNext();) {
		 OuvinteEstadoTransacao ouvinte=(OuvinteEstadoTransacao) i.next();
		 ouvinte.transacaoConcluida(this);
	  }
   }

   /**
    * 
    */
   private void notifyTransacaoAbortada() {
	  for(@SuppressWarnings("rawtypes")
	  Iterator i=ouvinteEstadoTransacao.iterator(); i.hasNext();) {
		 OuvinteEstadoTransacao ouvinte=(OuvinteEstadoTransacao) i.next();
		 ouvinte.transacaoAbortada(this);
	  }
   }
}
