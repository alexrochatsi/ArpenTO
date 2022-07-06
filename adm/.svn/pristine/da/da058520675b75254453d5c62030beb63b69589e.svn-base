
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.TipoLivro;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroTipoLivro extends CadastroPadrao{

   public List findByNome(String nome) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from TipoLivro t where t.tipo like :nome ");
		 query.setString("nome" , nome + "%");

		 List results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public TipoLivro findById(TipoLivro tipoLivro) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from TipoLivro t where t.id = :id ");
		 query.setInteger("id" , tipoLivro.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Cart�rio nao encontrado.");
		 }

		 return (TipoLivro) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public TipoLivro findByIntegerId(Integer idTipoLivro) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from TipoLivro t where t.id = :id ");
		 query.setInteger("id" , idTipoLivro);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Ato/Tipo Livro n�o encontrado.");
		 }

		 return (TipoLivro) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<TipoLivro> findAll() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from TipoLivro t");
		 List<TipoLivro> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

}
