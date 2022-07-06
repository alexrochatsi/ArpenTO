
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.OrgaoEmissor;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroOrgaoEmissor extends CadastroPadrao{

   public OrgaoEmissor findById(OrgaoEmissor orgaoEmissor) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from OrgaoEmissor c where c.id = :id ");
		 query.setInteger("id" , orgaoEmissor.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Orgao Emissor nao encontrado.");
		 }

		 return (OrgaoEmissor) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public OrgaoEmissor findByIntegerId(Integer idOrgaoEmissor) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from OrgaoEmissor c where c.id = :id ");
		 query.setInteger("id" , idOrgaoEmissor);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Orgao Emissor não encontrado.");
		 }

		 return (OrgaoEmissor) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<OrgaoEmissor> findAll() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from OrgaoEmissor c order by c.descricao");
		 List<OrgaoEmissor> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

}
