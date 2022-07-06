
package br.com.datasind.cadastro;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.CertidaoUpload;
import br.com.datasind.gerente.GerenteTransacao;

public class CadastroCertidaoUpload extends CadastroPadrao{

   @SuppressWarnings("deprecation")
   public Connection retornaConnexao() throws CadastroException {
	  return getSession().connection();
   }

   @SuppressWarnings("unchecked")
   public List<CertidaoUpload> findAll() throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  try {
		 Criteria c=session.createCriteria(CertidaoUpload.class);

		 return c.list();
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }

   }

   @SuppressWarnings("unchecked")
   public List<CertidaoUpload> listaCertidoesVencidas() throws ApplicationException, CadastroException {
	  Session session=null;
	  GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
	  session=(Session) gerenteTransacao.getSessaoAtual();
	  try {
		 SQLQuery query=session.createSQLQuery(
			"select * from certidaoUpload c where c.dataUpload < DATE_SUB(NOW(), interval 1 MONTH)");
		 List<Object[]> lista=query.list();
		 List<CertidaoUpload> l=new ArrayList<CertidaoUpload>();
		 for(Object[] o : lista) {
			CertidaoUpload c=new CertidaoUpload();
			c.setId((Integer) o[0]);
			c.setCaminhoDocumento((String) o[1]);
			c.setCertificada((Boolean) o[2]);
			c.setDataUpload((Date) o[3]);
			c.setNomeArquivo((String) o[4]);
			l.add(c);
		 }
		 if(l.size() < 1) {
			throw new FinderException("Certidao Upload nao encontrada.");
		 }
		 return l;
	  } catch (HibernateException e1) {
		 throw new CadastroException(e1);
	  }
   }

   public CertidaoUpload findById(CertidaoUpload certidaoUpload) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoUpload cu where cu.id = :id ");
		 query.setInteger("id" , certidaoUpload.getId());

		 List<?> results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Certidao Upload nao encontrado.");
		 }

		 return (CertidaoUpload) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public CertidaoUpload findById(Integer id) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoUpload cu where cu.id = :id ");
		 query.setInteger("id" , id);

		 List<?> results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Certidao Upload nao encontrado.");
		 }

		 return (CertidaoUpload) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);
	  }
   }

}
