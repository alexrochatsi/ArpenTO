
package br.com.datasind.cadastro;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Cidade;
import br.com.datasind.entidade.UF;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroCidade extends CadastroPadrao{

   @SuppressWarnings("deprecation")
   public Connection retornaConnexao() throws CadastroException {
	  return getSession().connection();
   }

   @SuppressWarnings("unchecked")
   public List<Cidade> listaTodasCidades() throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  try {
		 Criteria c=session.createCriteria(Cidade.class);

		 return c.list();
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }

   @SuppressWarnings("unchecked")
   public List<Cidade> listaTodasCidades(UF uf) throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  try {
		 Query query=session.createQuery("from Cidade u where u.uf = :uf ");
		 query.setInteger("uf" , uf.getId());

		 List<Cidade> results=query.list();

		 return results;
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }

   @SuppressWarnings("unchecked")
   public List<Cidade> listaTodasCidadesAutoComplete(UF uf) throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  try {
		 Query query=session.createQuery("from Cidade u where u.uf = :uf ");
		 query.setInteger("uf" , uf.getId());

		 List<Cidade> results=query.list();

		 return results;
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }
   
   @SuppressWarnings("unchecked")
   public List<Cidade> listaCidadesBRAgrupadas(String nomeCidade) throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  List<Cidade> results=null;
	  try {
		 Query query=session.createQuery("from Cidade c where c.descCidade like :nCidade");
		 query.setString("nCidade" , nomeCidade.toUpperCase() + "%");

		 results=query.list();
		 return results;
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }

   @SuppressWarnings("unchecked")
   public List<Cidade> listaCidadesIdString(Integer idUF, String nomeCidade) throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  List<Cidade> results= null;
	  try {
		 Query query=session.createQuery("from Cidade c where c.uf.id = :idUF and c.descCidade like :nCidade ");
		 query.setInteger("idUF" , idUF);
		 query.setString("nCidade" ,nomeCidade.toUpperCase()+"%");

		 results=query.list();
		 
		 return results;
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }

   @SuppressWarnings("rawtypes")
   public Cidade findById(Cidade cidade) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Cidade u where u.id = :id ");
		 query.setInteger("id" , cidade.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Cidade nao encontrada.");
		 }

		 return (Cidade) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("rawtypes")
   public Cidade findById(Integer id) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from Cidade u where u.id = :id ");
		 query.setInteger("id" , id);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Cidade nao encontrado.");
		 }

		 return (Cidade) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);
	  }
   }

   @SuppressWarnings("unchecked")
   public Cidade findByDescCidadeESiglaUF(String descCidade, String siglaUF) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Criteria c=session.createCriteria(Cidade.class , "c");
		 c.createCriteria("c.uf" , "uf");
		 c.add(Restrictions.ilike("uf.siglaUF" , siglaUF.trim() + "%"));
		 c.add(Restrictions.ilike("c.descCidade" , "%" + descCidade.trim() + "%"));

		 List<Cidade> cidades=c.list();
		 if(cidades.size() > 0) {
			return cidades.get(0);
		 } else {
			return null;
		 }

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

}
