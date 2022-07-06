
package br.com.datasind.cadastro;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.TipoLivro;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroTipoLivro extends CadastroPadrao{

   /**
    * @param nome
    * @return
    * @throws CadastroException
    */

   public String obterCV(String valores) {
	  String cv=null;

	  char[] numberStrs=valores.toCharArray();
	  int[] arrayNumeros=new int[numberStrs.length];
	  int[] arrayCalculada1=new int[numberStrs.length];
	  int vlExtendido=numberStrs.length + 1;
	  int[] arrayCalculada2=new int[vlExtendido];
	  int multiplicador=2;
	  int dv1, dv2=0;

	  int somaArray=0;
	  for(int i=0; i < numberStrs.length; i++) {
		 arrayNumeros[i]=Character.getNumericValue(numberStrs[i]);
	  }
	  for(int i=0; i < numberStrs.length; i++) {
		 if(multiplicador == 11)
			multiplicador -= 11;
		 arrayCalculada1[i]=arrayNumeros[i] * multiplicador;
		 multiplicador=multiplicador + 1;
		 somaArray += arrayCalculada1[i];
	  }
	  if(somaArray % 11 == 10) {
		 dv1=1;
	  } else {
		 dv1=somaArray % 11;
	  }
	  multiplicador=1;
	  somaArray=0;
	  for(int i=0; i < vlExtendido; i++) {
		 if(multiplicador == 11)
			multiplicador -= 11;
		 if(i == vlExtendido - 1) {
			arrayCalculada2[i]=dv1 * multiplicador;
		 } else {
			arrayCalculada2[i]=arrayNumeros[i] * multiplicador;
		 }
		 multiplicador=multiplicador + 1;
		 somaArray += arrayCalculada2[i];
	  }

	  if(somaArray % 11 == 10) {
		 dv2=1;
	  } else {
		 dv2=somaArray % 11;
	  }
	  cv = dv1 + "" + dv2;
	  
	  System.out.println("O CÓDIGO VERIFICADOR É: "+cv);

	  return cv;
   }

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
			throw new FinderException("Cartório nao encontrado.");
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
			throw new FinderException("Ato/Tipo Livro não encontrado.");
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
