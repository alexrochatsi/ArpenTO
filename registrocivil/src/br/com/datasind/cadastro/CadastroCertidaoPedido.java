
package br.com.datasind.cadastro;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.Correios;
import br.com.datasind.entidade.CustoPedido;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.gerente.GerenteTransacao;
import br.com.datasind.util.Utilitario;

@SuppressWarnings("rawtypes")
public class CadastroCertidaoPedido extends CadastroPadrao{

   /**
    * @param nome
    * @return
    * @throws CadastroException
    */

   public CertidaoPedido findById(CertidaoPedido certidaoPedido) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.id = :id");
		 query.setInteger("id" , certidaoPedido.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Pedido de certidão não encontrado.");
		 }

		 return (CertidaoPedido) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
   
   @SuppressWarnings({"unchecked"})
   public List<CertidaoPedido> findByUsuarioIdParcialDate(UsuarioSite usuarioSite, Date data1, Date data2) throws CadastroException, FinderException {
	  Session session=null;

	  data1=Utilitario.zerarHoraMinutoSegundoMilisegundo(data1);
	  data2=Utilitario.maximizarHoraMinutoSegundoMilisegundo(data2);

	  SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("SELECT cp.id, cp.status, csp.tipoCertidao, csp.valorCertidao, csp.taxaAdministrativa, cor.valor, cp.dataPedido from CertidaoPedido cp "
			+ "JOIN cp.custoPedido csp JOIN cp.correios cor where cp.dataPedido BETWEEN '" + sf.format(data1) + "' AND '" + sf.format(data2) + "' and cp.usuarioSite.id = " + usuarioSite.getId()
			+ " order by cp.id desc");

		 List<Object[]> lista=query.list();
		 List<CertidaoPedido> l=new ArrayList<CertidaoPedido>();
		 for(Object[] o : lista) {
			CertidaoPedido cp=new CertidaoPedido();
			cp.setCustoPedido(new CustoPedido());
			cp.setCorreios(new Correios());
			cp.setId((Integer) o[0]);
			cp.setStatus((Integer) o[1]);
			cp.getCustoPedido().setTipoCertidao((String) o[2]);
			cp.getCustoPedido().setValorCertidao((BigDecimal) o[3]);
			cp.getCustoPedido().setTaxaAdministrativa((BigDecimal) o[4]);
			cp.getCorreios().setValor((BigDecimal) o[5]);
			cp.setDataPedido((Date) o[6]);
			l.add(cp);
		 }

		 if(l.size() < 1) {
			throw new FinderException("Não foi encontrado nenhum Pedido de Certidão.");
		 }

		 return l;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public List<CertidaoPedido> findByUsuarioSiteId(UsuarioSite usuarioSite) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.usuarioSite.id = " + usuarioSite.getId() + " order by id desc");

		 @SuppressWarnings("unchecked")
		 List<CertidaoPedido> results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Não foi encontrado nenhum Pedido de Certidão.");
		 }

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings({"unchecked"})
   public List<CertidaoPedido> findByUsuarioSiteIdParcial(UsuarioSite usuarioSite) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("SELECT cp.id, cp.status, csp.tipoCertidao, csp.valorCertidao, csp.taxaAdministrativa,"
		 + "cor.valor, cp.dataPedido from CertidaoPedido cp "
			+ "JOIN cp.custoPedido csp JOIN cp.correios cor where cp.usuarioSite.id = " + usuarioSite.getId() + " order by cp.id desc");

		 List<Object[]> lista=query.list();
		 List<CertidaoPedido> l=new ArrayList<CertidaoPedido>();
		 for(Object[] o : lista) {
			CertidaoPedido cp=new CertidaoPedido();
			cp.setCustoPedido(new CustoPedido());
			cp.setCorreios(new Correios());
			cp.setId((Integer) o[0]);
			cp.setStatus((Integer) o[1]);
			cp.getCustoPedido().setTipoCertidao((String) o[2]);
			cp.getCustoPedido().setValorCertidao((BigDecimal) o[3]);
			cp.getCustoPedido().setTaxaAdministrativa((BigDecimal) o[4]);
			cp.getCorreios().setValor((BigDecimal) o[5]);
			cp.setDataPedido((Date) o[6]);
			l.add(cp);
		 }

		 if(l.size() < 1) {
			throw new FinderException("Não foi encontrado nenhum Pedido de Certidão.");
		 }

		 return l;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings("unchecked")
   public List<CertidaoPedido> findAll() throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.id != 1");
		 List<CertidaoPedido> results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
}
