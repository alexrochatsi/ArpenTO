
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.CustoPedido;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroCustoPedido extends CadastroPadrao{

   /**
    * @param nome
    * @return
    * @throws CadastroException
    */
   public List findByNome(String nome) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CustoPedido c where c.nome like :nome ");
		 query.setString("nome" , nome + "%");

		 List results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public CustoPedido findById(CustoPedido custoPedido) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CustoPedido c where c.id = :id ");
		 query.setInteger("id" , custoPedido.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Custo do Pedido nao encontrado.");
		 }

		 return (CustoPedido) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public CustoPedido findByIntegerId(Integer idCustoPedido) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CustoPedido c where c.id = :id ");
		 query.setInteger("id" , idCustoPedido);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Custo do Pedido não encontrado.");
		 }

		 return (CustoPedido) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<CustoPedido> findAll() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CustoPedido c");
		 List<CustoPedido> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

}
