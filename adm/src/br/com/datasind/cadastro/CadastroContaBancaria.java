
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.ContaBancaria;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroContaBancaria extends CadastroPadrao{

   /**
    * @param nome
    * @return
    * @throws CadastroException
    */

   public ContaBancaria findById(ContaBancaria contaBancaria) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from ContaBancaria cb where cb.id = :id ");
		 query.setInteger("id" , contaBancaria.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Conta bancaria nao encontrada.");
		 }

		 return (ContaBancaria) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public List<ContaBancaria> findByCartorioId(Cartorio cartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from ContaBancaria cp where cb.cartorio.id = " + cartorio.getId());

		 @SuppressWarnings("unchecked")
		 List<ContaBancaria> results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Não foi encontrada nenhuma Conta Bancaria.");
		 }

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<ContaBancaria> findAll() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from ContaBancaria cb");
		 List<ContaBancaria> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
}
