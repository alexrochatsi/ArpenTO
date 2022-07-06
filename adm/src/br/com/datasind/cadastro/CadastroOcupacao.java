
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Ocupacao;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroOcupacao extends CadastroPadrao{

   public Ocupacao findById(Ocupacao orgaoEmissor) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Ocupacao c where c.id = :id ");
		 query.setInteger("id" , orgaoEmissor.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Ocupacao nao encontrada.");
		 }

		 return (Ocupacao) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public Ocupacao findByIntegerId(Integer idOcupacao) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Ocupacao c where c.id = :id ");
		 query.setInteger("id" , idOcupacao);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Ocupacao não encontrada.");
		 }

		 return (Ocupacao) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<Ocupacao> findAll() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Ocupacao o order by o.codigoCBO");
		 List<Ocupacao> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

}
