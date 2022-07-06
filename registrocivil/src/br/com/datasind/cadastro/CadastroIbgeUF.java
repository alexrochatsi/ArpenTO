
package br.com.datasind.cadastro;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.UF;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroIbgeUF extends CadastroPadrao{

   @SuppressWarnings("deprecation")
   public Connection retornaConnexao() throws CadastroException {
	  return getSession().connection();
   }

   @SuppressWarnings("unchecked")
   public List<IbgeUF> listaTodosIbgeUFs() throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  try {
		 Criteria c=session.createCriteria(IbgeUF.class);

		 return c.list();
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }
   
   @SuppressWarnings("rawtypes")
   public IbgeUF findById(IbgeUF ibgeUF) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from IbgeUF u where u.id = :id ");
		 query.setInteger("id" , ibgeUF.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("IbgeUF nao encontrado.");
		 }

		 return (IbgeUF) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("rawtypes")
   public IbgeUF findById(Integer id) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from IbgeUF u where u.id = :id ");
		 query.setInteger("id" , id);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("IbgeUF nao encontrado.");
		 }

		 return (IbgeUF) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);
	  }
   }

   @SuppressWarnings("unchecked")
   public List<IbgeUF> listaIbgeUFsPorCidadeUF(String cidade, UF ufSelecionada) throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  try {
		 Query query=session.createQuery("from IbgeUF u where u.uf like :uf and u.nomeMunicipio like :cidade");
		 query.setString("cidade" , cidade.toUpperCase() + "%");
		 query.setString("uf" , ufSelecionada.getSiglaUF());

		 List<IbgeUF> results=query.list();
		 if(results.size() < 1) {
			throw new FinderException("IbgeUF nao encontrado.");
		 }
		 return results;
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }
   }

}
