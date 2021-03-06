
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroUsuarioCartorio extends CadastroPadrao{

   /**
    * Localiza todos os objetos da classe UsuarioCartorio. O metodo e privado para servir como base para outros metodos
    * finder. A nao ser que seja necessario, nao mude a visibilidade deste metodo.
    * 
    * @return Retorna todos os objetos UsuarioCartorio
    * @throws CadastroException
    */

   public UsuarioCartorio findByEmail(String email) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio us where us.email = :nome ");
		 query.setString("nome" , email);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Usuario nao encontrado.");
		 }

		 return (UsuarioCartorio) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public UsuarioCartorio findByLogin(String login) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio uc where uc.nomeLogin = :nome ");
		 query.setString("nome" , login);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Usuario nao encontrado.");
		 }

		 return (UsuarioCartorio) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<UsuarioCartorio> findByEmailForValidacao(String email) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio us where us.email = :nome ");
		 query.setString("nome" , email.toUpperCase());
		 // System.out.println(login.toUpperCase());
		 List results=query.list();

		 // if (results.size() < 1) {
		 // throw new FinderException("Usuario nao encontrado.");
		 // }

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<UsuarioCartorio> findByLoginForValidacao(String login) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio us where us.nomeLogin = :nome ");
		 query.setString("nome" , login);
		 // System.out.println(login.toUpperCase());
		 List results=query.list();

		 // if (results.size() < 1) {
		 // throw new FinderException("Usuario nao encontrado.");
		 // }

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public UsuarioCartorio findByEmailForValidacaoSingle(String email) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio us where us.email = :nome ");
		 query.setString("nome" , email.toUpperCase());
		 // System.out.println(login.toUpperCase());
		 UsuarioCartorio result=(UsuarioCartorio) query.uniqueResult();

		 // if (results.size() < 1) {
		 // throw new FinderException("Usuario nao encontrado.");
		 // }

		 return result;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

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

		 Query query=session.createQuery("from UsuarioCartorio u where u.nome like :nome ");
		 query.setString("nome" , nome + "%");

		 List results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public UsuarioCartorio findById(UsuarioCartorio usuarioCartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio us where us.id = :id ");
		 query.setInteger("id" , usuarioCartorio.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Usuario nao encontrado.");
		 }

		 return (UsuarioCartorio) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<UsuarioCartorio> findByCartorio(Cartorio cartorio) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio uc where uc.cartorio.id = :id ");
		 query.setInteger("id" , cartorio.getId());

		 List results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public UsuarioCartorio findByUID(String uid) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio us where us.UID = :uid ");
		 query.setString("uid" , uid);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Usuario nao encontrado.");
		 }

		 return (UsuarioCartorio) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<UsuarioCartorio> findAllUsuarioCartorio() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from UsuarioCartorio");
		 List<UsuarioCartorio> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
}
