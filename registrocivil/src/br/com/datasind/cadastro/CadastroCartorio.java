
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroCartorio extends CadastroPadrao{

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

		 Query query=session.createQuery("from Cartorio c where c.nome like :nome ");
		 query.setString("nome" , nome + "%");

		 List results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public Cartorio findById(Cartorio cartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Cartorio c where c.id = :id ");
		 query.setInteger("id" , cartorio.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Cartório nao encontrado.");
		 }

		 return (Cartorio) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public Cartorio findByIntegerId(Integer idCartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Cartorio c where c.id = :id ");
		 query.setInteger("id" , idCartorio);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Cartório não encontrado.");
		 }

		 return (Cartorio) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<Cartorio> findAll() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Cartorio c where c.id != 1");
		 List<Cartorio> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<Cartorio> listaCidadesTOAgrupadas(String nomeCidade) throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  List<Cartorio> results=null;
	  try {
		 Query query=session.createQuery("from Cartorio c where c.municipio like :nCidade group by c.municipio ");
		 query.setString("nCidade" , nomeCidade.toUpperCase() + "%");

		 results=query.list();

		 return results;
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }

   @SuppressWarnings("unchecked")
   public List<Cartorio> listaCartorios(String nomeCidade) throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  List<Cartorio> results=null;
	  try {
		 Query query=session.createQuery("from Cartorio c where c.municipio = :nCidade");
		 query.setString("nCidade" , nomeCidade.toUpperCase());

		 results=query.list();

		 return results;
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }

}
