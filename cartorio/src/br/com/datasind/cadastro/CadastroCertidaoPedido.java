
package br.com.datasind.cadastro;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.Correios;
import br.com.datasind.entidade.CustoPedido;
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

		 Query query=session.createQuery("from CertidaoPedido cp where cp.id = :id ");
		 query.setInteger("id" , certidaoPedido.getId());

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Pedido de certidao nao encontrado.");
		 }

		 return (CertidaoPedido) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public CertidaoPedido findById(Integer certidaoPedido) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.id = :id ");
		 query.setInteger("id" , certidaoPedido);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Pedido de certidao nao encontrado.");
		 }

		 return (CertidaoPedido) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
   
   public CertidaoPedido findByIdUpload(Integer idCertidaoUpload) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.certidaoUpload.id = :id ");
		 query.setInteger("id" , idCertidaoUpload);

		 List results=query.list();

		 if(results.size() < 1) {
			throw new FinderException("Pedido de certidao nao encontrado.");
		 }

		 return (CertidaoPedido) results.get(0);

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   public List<CertidaoPedido> findByCartorioId(Cartorio cartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.cartorio.id = " + cartorio.getId());

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
   public List<CertidaoPedido> findByCartorioIdParcial(Cartorio cartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("SELECT cp.id, cp.status, csp.tipoCertidao, csp.valorCertidao, csp.taxaAdministrativa, cor.valor, cp.dataPedido from CertidaoPedido cp "
			+ "JOIN cp.custoPedido csp JOIN cp.correios cor where cp.cartorio.id = " + cartorio.getId() + " order by cp.id desc");

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

   @SuppressWarnings({"unchecked"})
   public List<CertidaoPedido> findByCartorioIdParcialFiltrado(Cartorio cartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.cartorio.id = " + cartorio.getId()
			+ "  and cp.certidaoUpload = null and cp.status = 4 and cp.formaEntrega = 3 order by cp.id desc");

		 List<CertidaoPedido> l= query.list();

		 if(l.size() < 1) {
			throw new FinderException("Não foi encontrado nenhum Pedido de Certidão.");
		 }

		 return l;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }

   @SuppressWarnings({"unchecked"})
   public List<CertidaoPedido> findByIntegerIdParcial(Integer idPedido) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("SELECT cp.id, cp.dataPedido from CertidaoPedido cp where cp.id = " + idPedido + " order by cp.id desc");

		 List<Object[]> lista=query.list();
		 List<CertidaoPedido> l=new ArrayList<CertidaoPedido>();
		 for(Object[] o : lista) {
			CertidaoPedido cp=new CertidaoPedido();
			cp.setId((Integer) o[0]);
			cp.setDataPedido((Date) o[1]);
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

   @SuppressWarnings({"unchecked"})
   public List<CertidaoPedido> findByIntegerIdParcialComplete(Integer idPedido) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("SELECT cp.id, cp.dataPedido from CertidaoPedido cp where cp.id = " + idPedido + " and cp.certidaoUpload = null and cp.status = 4 order by cp.id desc");

		 List<Object[]> lista=query.list();
		 List<CertidaoPedido> l=new ArrayList<CertidaoPedido>();
		 for(Object[] o : lista) {
			CertidaoPedido cp=new CertidaoPedido();
			cp.setId((Integer) o[0]);
			cp.setDataPedido((Date) o[1]);
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

   public BigDecimal findPedidosByCartorioData(Cartorio cartorio, Calendar data) throws CadastroException, FinderException {
	  Session session=null;
	  BigDecimal total=new BigDecimal(0);
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 @SuppressWarnings("deprecation")
		 Query query=session.createQuery("from CertidaoPedido cp where cp.boletoGerado = :valor and month(cp.dataBoleto) = " + (data.getTime().getMonth() + 1) + " and year(cp.dataBoleto) = " + (data
			.getTime().getYear() + 1900) + " and cp.cartorio.id = " + cartorio.getId());
		 query.setBoolean("valor" , Boolean.TRUE);

		 List results=query.list();

		 if(results.isEmpty()) {
			results=new ArrayList<CertidaoPedido>();
			throw new FinderException("Não foi encontrado nenhum Boleto Gerado.");
		 }
		 for(int i=0; i < results.size(); i++) {
			BigDecimal a=new BigDecimal(((CertidaoPedido) results.get(i)).getCustoPedido().getValorCertidao().toString());
			BigDecimal b=new BigDecimal(((CertidaoPedido) results.get(i)).getCustoPedido().getTaxaAdministrativa().toString());
			BigDecimal c=new BigDecimal(((CertidaoPedido) results.get(i)).getCorreios().getValor().toString());
			total=total.add(c.add(b.add(a)));
		 }
		 return total;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);
	  }
   }

   @SuppressWarnings({"unchecked"})
   public List<CertidaoPedido> findByCartorioIdParcialDate(Cartorio cartorio, Date data1, Date data2) throws CadastroException, FinderException {
	  Session session=null;

	  data1=Utilitario.zerarHoraMinutoSegundoMilisegundo(data1);
	  data2=Utilitario.maximizarHoraMinutoSegundoMilisegundo(data2);

	  SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("SELECT cp.id, cp.status, csp.tipoCertidao, csp.valorCertidao, csp.taxaAdministrativa, cor.valor, cp.dataPedido, cp.formaEntrega from CertidaoPedido cp "
			+ "JOIN cp.custoPedido csp JOIN cp.correios cor where cp.dataPedido BETWEEN '" + sf.format(data1) + "' AND '" + sf.format(data2) + "' and cp.cartorio.id = " + cartorio.getId()
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
			cp.setFormaEntrega((Integer) o[7]);
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
   public List<CertidaoPedido> findPedidosBoletoByCartorioId(Cartorio cartorio, Date data1, Date data2) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 data1=Utilitario.zerarHoraMinutoSegundoMilisegundo(data1);
		 data2=Utilitario.maximizarHoraMinutoSegundoMilisegundo(data2);

		 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 Query query=session.createQuery(
			"SELECT cp.id, cp.nomeRegistrado, cp.conjugue1, cp.conjugue2, cp.boletoPago, cp.dataBoleto, csp.tipoCertidao, csp.valorCertidao, csp.taxaAdministrativa, csp.id, cor.valor from CertidaoPedido cp "
			   + "JOIN cp.custoPedido csp JOIN cp.correios cor where cp.boletoGerado = :valor and cp.dataBoleto BETWEEN '" + sf.format(data1) + "' AND '" + sf.format(data2) + "' and cp.cartorio.id = "
			   + cartorio.getId());
		 query.setBoolean("valor" , Boolean.TRUE);

		 List<Object[]> lista=query.list();
		 List<CertidaoPedido> l=new ArrayList<CertidaoPedido>();
		 for(Object[] o : lista) {
			CertidaoPedido cp=new CertidaoPedido();
			cp.setCustoPedido(new CustoPedido());
			cp.setCorreios(new Correios());
			cp.setId((Integer) o[0]);
			if(o[1] != null)
			   cp.setNomeRegistrado((String) o[1]);
			if(o[2] != null)
			   cp.setConjugue1((String) o[2]);
			if(o[3] != null)
			   cp.setConjugue2((String) o[3]);
			cp.setBoletoPago((Boolean) o[4]);
			cp.setDataBoleto((Date) o[5]);
			cp.getCustoPedido().setTipoCertidao((String) o[6]);
			cp.getCustoPedido().setValorCertidao((BigDecimal) o[7]);
			cp.getCustoPedido().setTaxaAdministrativa((BigDecimal) o[8]);
			cp.getCustoPedido().setId((Integer) o[9]);
			cp.getCorreios().setValor((BigDecimal) o[10]);
			l.add(cp);
		 }

		 if(l.size() < 1) {
			throw new FinderException("Não foi encontrado nenhum Pedido de Certidão.");
		 }

		 return l;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);
	  } catch (FinderException e) {
		 throw new CadastroException(e);
	  }
   }

   @SuppressWarnings("unchecked")
   public List<CertidaoPedido> findPedidosBoletoByCartorioIdAll(Cartorio cartorio) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.boletoGerado = :valor and cp.cartorio.id = " + cartorio.getId());
		 query.setBoolean("valor" , Boolean.TRUE);

		 List<CertidaoPedido> results=query.list();

		 if(results.isEmpty()) {
			results=new ArrayList<CertidaoPedido>();
			throw new FinderException("Não foi encontrado nenhum Boleto Gerado.");
		 }

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);
	  } catch (FinderException e) {
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
