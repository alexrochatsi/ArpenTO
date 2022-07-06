
package br.com.datasind.cadastro;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.RegistroNascimento;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.gerente.GerenteTransacao;
import br.com.datasind.util.Utilitario;

@SuppressWarnings("rawtypes")
public class CadastroRegistroNascimento extends CadastroPadrao{

   @SuppressWarnings("unchecked")
   public List<RegistroNascimento> findByUsuarioCartorioId(UsuarioCartorio usuarioCartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from RegistroNascimento rn where rn.usuarioCartorio.id = :id ");
		 query.setInteger("id" , usuarioCartorio.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Registro nao encontrado.");
		 }

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<RegistroNascimento> findByUsuarioCartorioDateId(UsuarioCartorio usuarioCartorio, Date data1, Date data2) throws CadastroException, FinderException {
	  Session session=null;

	  data1=Utilitario.zerarHoraMinutoSegundoMilisegundo(data1);
	  data2=Utilitario.maximizarHoraMinutoSegundoMilisegundo(data2);

	  SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery(
			"SELECT rn.id, rn.nome, rn.dataNascimento, rn.dataCadastroSistema, rn.matricula from RegistroNascimento rn where rn.usuarioCartorio.id = :id and rn.dataCadastroSistema BETWEEN '" + sf
			   .format(data1) + "' AND '" + sf.format(data2) + "'");
		 query.setInteger("id" , usuarioCartorio.getId());

		 List<Object[]> lista=query.list();
		 List<RegistroNascimento> l=new ArrayList<RegistroNascimento>();
		 for(Object[] o : lista) {
			RegistroNascimento rn=new RegistroNascimento();
			rn.setId((Integer) o[0]);
			rn.setNome((String) o[1]);
			rn.setDataNascimento((Date) o[2]);
			rn.setDataCadastroSistema((Date) o[3]);
			rn.setMatricula((String) o[4]);
			l.add(rn);
		 }
		 
		 
		 if(l.isEmpty()) {
			l=new ArrayList<RegistroNascimento>();
			throw new FinderException("Não foi encontrado nenhum Registro.");
		 }

		 return l;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public RegistroNascimento findById(RegistroNascimento registroNascimento) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from RegistroNascimento rn where rn.id = :id ");
		 query.setInteger("id" , registroNascimento.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Usuario nao encontrado.");
		 }

		 return (RegistroNascimento) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<RegistroNascimento> findAll() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from RegistroNascimento");
		 List<RegistroNascimento> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
}
