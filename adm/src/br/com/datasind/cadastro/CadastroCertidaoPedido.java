
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

   @SuppressWarnings("unchecked")
   public List<CertidaoPedido> findByCartorioPeriodo(Cartorio cartorio, String mes, String ano) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where cp.cartorio.id = :id and MONTH(cp.dataBoleto) = :mes "
			+ "and YEAR(cp.dataBoleto) = :ano and cp.boletoPago = true and cp.guiaGeradaCartorio = false");
		 query.setInteger("id" , cartorio.getId());
		 query.setInteger("mes" , Integer.parseInt(mes));
		 query.setInteger("ano" , Integer.parseInt(ano));
		 List results=query.list();

		 return results;

	  } catch (HibernateException e) {
		 throw new CadastroException(e);

	  }
   }
   
   @SuppressWarnings("unchecked")
   public List<CertidaoPedido> findByPeriodo(String mes, String ano) throws CadastroException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 Query query=session.createQuery("from CertidaoPedido cp where MONTH(cp.dataBoleto) = :mes "
			+ "and YEAR(cp.dataBoleto) = :ano and cp.boletoPago = true and cp.guiaGeradaCartorio = false");
		 query.setInteger("mes" , Integer.parseInt(mes));
		 query.setInteger("ano" , Integer.parseInt(ano));
		 List results=query.list();

		 return results;

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
			throw new FinderException("N??o foi encontrado nenhum Pedido de Certid??o.");
		 }

		 return results;

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
			throw new FinderException("N??o foi encontrado nenhum Boleto Gerado.");
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

   @SuppressWarnings("unchecked")
   public List<CertidaoPedido> findPedidosBoletoByCartorioId(Cartorio cartorio, Date data1, Date data2) throws CadastroException, FinderException {
	  Session session=null;
	  try {
		 GerenteTransacao gerenteTransacao=getContexto().getFabricaGerente().getGerenteTransacao();
		 session=(Session) gerenteTransacao.getSessaoAtual();

		 data1=Utilitario.zerarHoraMinutoSegundoMilisegundo(data1);
		 data2=Utilitario.maximizarHoraMinutoSegundoMilisegundo(data2);

		 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 Query query=session.createQuery("from CertidaoPedido cp where cp.boletoGerado = :valor and " + "cp.dataBoleto BETWEEN '" + sf.format(data1) + "' AND '" + sf.format(data2)
			+ "' and cp.cartorio.id = " + cartorio.getId());
		 query.setBoolean("valor" , Boolean.TRUE);

		 List results=query.list();

		 if(results.isEmpty()) {
			results=new ArrayList<CertidaoPedido>();
			throw new FinderException("N??o foi encontrado nenhum Boleto Gerado.");
		 }

		 return results;

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
			throw new FinderException("N??o foi encontrado nenhum Boleto Gerado.");
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
