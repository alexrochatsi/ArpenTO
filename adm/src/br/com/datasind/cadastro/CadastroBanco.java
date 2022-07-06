
package br.com.datasind.cadastro;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Banco;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroBanco extends CadastroPadrao{

   @SuppressWarnings("deprecation")
   public Connection retornaConnexao() throws CadastroException {
	  return getSession().connection();
   }

   @SuppressWarnings("unchecked")
   public List<Banco> listaTodosBancos() throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  try {
		 Criteria c=session.createCriteria(Banco.class);

		 return c.list();
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }
   
   @SuppressWarnings("rawtypes")
   public Banco findById(Banco banco) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Banco b where b.id = :id ");
		 query.setInteger("id" , banco.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("IbgeUF nao encontrado.");
		 }

		 return (Banco) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("rawtypes")
   public Banco findById(Integer id) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Banco b where b.id = :id ");
		 query.setInteger("id" , id);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Banco nao encontrado.");
		 }

		 return (Banco) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);
	  }
   }
}
