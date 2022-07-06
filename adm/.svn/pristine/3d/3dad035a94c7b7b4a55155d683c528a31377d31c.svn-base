
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.Pagamento;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroPagamento extends CadastroPadrao{

   public Pagamento findById(Pagamento pagamento) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Pagamento p where p.id = :id ");
		 query.setInteger("id" , pagamento.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Pagamento nao encontrado.");
		 }

		 return (Pagamento) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<Pagamento> findByCartorioPeriodo(Cartorio cartorio, String mes, String ano) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Pagamento p where p.cartorio.id = :id and MONTH(p.periodo) = :mes and YEAR(p.periodo) = :ano");
		 query.setInteger("id" , cartorio.getId());
		 query.setInteger("mes" , Integer.parseInt(mes));
		 query.setInteger("ano" , Integer.parseInt(ano));
		 List results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<Pagamento> findAllPagamentos() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Pagamento");
		 List<Pagamento> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
   
   @SuppressWarnings("unchecked")
   public List<Pagamento> findAllPagamentosPeriodo(String mes, String ano) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Pagamento p where MONTH(p.periodo) = :mes and YEAR(p.periodo) = :ano");
		 query.setInteger("mes" , Integer.parseInt(mes));
		 query.setInteger("ano" , Integer.parseInt(ano));
		 List<Pagamento> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
}
