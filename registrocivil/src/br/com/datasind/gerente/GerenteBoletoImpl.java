
package br.com.datasind.gerente;

import java.math.BigDecimal;
import java.sql.Connection;

import org.apache.log4j.Logger;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.financeiro.banco.Banco;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;
import org.jrimum.utilix.text.DateFormat;
import org.jrimum.utilix.text.Filler;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.CadastroCon;
import br.com.datasind.cadastro.ContextoCadastroBasico;

public class GerenteBoletoImpl extends GerentePadrao implements GerenteBoleto{

   @SuppressWarnings("unused")
   private static final Logger LOGGER=Logger.getLogger(GerenteBoletoImpl.class);

   public GerenteBoletoImpl() {
	  super();
   }

   public Connection retornaConexao() throws ApplicationException {
	  Connection connection;
	  CadastroCon cadastroCon=new CadastroCon();
	  cadastroCon.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 connection=cadastroCon.retornaConnexao();
	  } catch (Exception e) {
		 throw new ApplicationException(e.getMessage(), e.getCause());
	  }
	  return connection;
   }

   @Override
   public Boleto geraBoletoSICOOB(Sacado sacado, BigDecimal valor) throws ApplicationException {
	  Cedente cedente=new Cedente("Tecnotins", "79154076004704");
	  final Banco banco=BancosSuportados.BANCOOB.create();
	  ParametrosBancariosMap parametrosBancarios=new ParametrosBancariosMap();
	  
	  ContaBancaria contaBancariaCed=new ContaBancaria(banco);
	  contaBancariaCed.setAgencia(new Agencia(4198, "0"));
	  contaBancariaCed.setCarteira(new Carteira(1));
	  contaBancariaCed.setNumeroDaConta(new NumeroDaConta(39462, "0"));
	  cedente.addContaBancaria(contaBancariaCed);
	  
	  Titulo titulo=new Titulo(contaBancariaCed, sacado, cedente);
	  titulo.setNumeroDoDocumento("25445");
	  titulo.setNossoNumero("00117350");// NossNámero já Vem Calculado
	  titulo.setDigitoDoNossoNumero("0");
	  titulo.setValor(valor);
	  titulo.setDataDoDocumento(DateFormat.DDMMYYYY_B.parse("25/07/2016"));
	  titulo.setDataDoVencimento(DateFormat.DDMMYYYY_B.parse("28/07/2016"));
	  titulo.setTipoDeDocumento(TipoDeTitulo.FAT_FATURA);
	  titulo.setAceite(Aceite.A);
	  
	  parametrosBancarios.adicione("ModalidadeDeCobranca" , new Integer(2));
	  parametrosBancarios.adicione("NumeroDaParcela" , new Integer(1));
	  titulo.setParametrosBancarios(parametrosBancarios);

	  Boleto boleto=new Boleto(titulo);
	  boleto.setLocalPagamento("Pagável preferencialmente na Agencia do BANCOOB - PAC Centro em qualquer Banco até o Vencimento.");
	  boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado não o esperado, aproveite o DESCONTO!");
	  boleto.setInstrucao1("APÓS 5 DIAS DE ATRASO COBRAR MULTA DE 15%!");
	  boleto.setInstrucao2("MORA 2% AO DIA!");

	  String codigoAg="" + titulo.getContaBancaria().getAgencia().getCodigo();
	  String codAgPAC="00";
	  String codCed=Filler.ZERO_LEFT.fill(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta() , 7);

	  String agenciaCodigoCed=codigoAg + "/" + codAgPAC + "/" + codCed;

	  boleto.addTextosExtras("txtRsAgenciaCodigoCedente" , agenciaCodigoCed);
	  boleto.addTextosExtras("txtFcAgenciaCodigoCedente" , agenciaCodigoCed);

	  boleto.addTextosExtras("txtRsNossoNumero" , titulo.getNossoNumero());
	  boleto.addTextosExtras("txtFcNossoNumero" , titulo.getNossoNumero());
	  
	  return boleto;
   }

}
