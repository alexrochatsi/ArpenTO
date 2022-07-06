package br.com.datasind.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import br.com.datasind.aplicacao.Configuracao;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class Utilitaria {
	public static final Locale LOCALE_BR = new Locale("pt", "BR");

	private static final DecimalFormat FORMAT = new DecimalFormat("##,###,###,###.##");

	public static final DecimalFormat FORMAT_MONETARIO_SEM_SIMBOLO_MOEDA = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(LOCALE_BR));

	public static final DecimalFormat FORMAT_MONETARIO_REAL = new DecimalFormat("\u00A4 #,##0.00", new DecimalFormatSymbols(LOCALE_BR));

	public static final DecimalFormat FORMAT_OITO_CASAS_SEM_VIRGULA = new DecimalFormat("00000000", new DecimalFormatSymbols(LOCALE_BR));

	public static final DecimalFormat FORMAT_QUATRO_CASAS_SEM_VIRGULA = new DecimalFormat("0000", new DecimalFormatSymbols(LOCALE_BR));

	public static final DecimalFormat FORMAT_DUAS_CASAS_SEM_VIRGULA = new DecimalFormat("00", new DecimalFormatSymbols(LOCALE_BR));

	public static final DecimalFormat FORMAT_UMA_CASA_APOS_VIRGULA = new DecimalFormat("0.0", new DecimalFormatSymbols(LOCALE_BR));

	/**
	 * <code>DateFormat</code> usado para logs, com data completa (
	 * <b>dd-MM-yyyy_HH.mm.ss.SSS</b>).
	 */
	private static final DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss.SSS");

	/**
	 * A <code>String</code> básica de um log (exemplo: <b>[eprev]</b>) para ser
	 * usada antes como prefixo de um log.
	 */
	// private static final String LOG_PREFIXO = "[" +
	// Configuracao.getInstance().getContexto() + "]";

	/**
	 * Indica se o BDR trabalha com datas no formato dd/mm/aaaa.
	 * 
	 * @deprecated seu uso é completamente desnecessário pois o método que o usa
	 *             é depreciado.
	 */
	private static String formatoJDBC = "BRA";

	/**
	 * Tipos de SGBDs usados
	 * 
	 * @deprecated seu uso é completamente desnecessário pois o método que o usa
	 *             é depreciado.
	 */
	public final static int CT_TIPO_SGBD_ORACLE = 1;

	/**
	 * Tipos de SGBDs usados
	 * 
	 * @deprecated seu uso é completamente desnecessário pois o método que o usa
	 *             é depreciado.
	 */
	public final static int CT_TIPO_SGBD_MSSQL = 2;

	/**
	 * Indica qual SGBD será usado.
	 * 
	 * @deprecated seu uso é completamente desnecessário pois o método que o usa
	 *             é depreciado.
	 */
	private static int tipoSGBD = CT_TIPO_SGBD_ORACLE;

	/**
	 * @deprecated nunca usado no sistema e em seu lugar deve ser usada uma
	 *             expressão regular
	 */
	private static String espacosEmBranco[] = { " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          ", "           ",
			"            ", "             ", "              ", "               " };

	public static String retirarCaracteresEspeciais(String texto, boolean uppercase) {

		try {
			String tmp = texto;

			tmp = tmp.replaceAll("[ÁÂÀÃÄ]", "A").replaceAll("[áâàãä]", "a");
			tmp = tmp.replaceAll("[ÉÊÈË]", "E").replaceAll("[éêèë]", "e");
			tmp = tmp.replaceAll("[ÍÎÌÏ]", "I").replaceAll("[íîìï]", "i");
			tmp = tmp.replaceAll("[ÓÔÒÕÖ]", "O").replaceAll("[óôòõö]", "o");
			tmp = tmp.replaceAll("[ÚÛÙÜ]", "U").replaceAll("[úûùü]", "u");
			tmp = tmp.replaceAll("Ç", "C").replaceAll("ç", "c");
			tmp = tmp.replaceAll("Ñ", "N").replaceAll("ñ", "n");
			tmp = tmp.replaceAll("'", "");
			tmp = tmp.replaceAll(" {2,}", " ");

			return (uppercase) ? tmp.toUpperCase() : tmp;
		} catch (Exception e) {
			return null;
		}
	}

	public static String retirarCaracteresEspeciais(String texto) {
		return retirarCaracteresEspeciais(texto, true);
	}

	public static String retirarCaracteresEspeciais2(String texto) {
		return retirarCaracteresEspeciais2(texto, true);
	}

	public static String retirarCaracteresEspeciais2(String texto, boolean uppercase) {

		if (texto == null || texto.equals(""))
			return null;

		String tmp = retirarCaracteresEspeciais(texto, uppercase);
		return retirarCarrierReturn(tmp);
	}

	public static String retirarCarrierReturn(String strTmp) {
		String noTagRegex = "<[^>]+>";
		return strTmp.replaceAll(" ", " ").replaceAll("\\n", " ").replaceAll("\\s", " ").replaceAll(noTagRegex, " ");
	}

	public static String retirarMascaraNumero(String texto) {
		try {
			return texto.replaceAll("[\\D]", "");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Metodo para gerar uma string a partir de uma data. A string retornada tem
	 * o seguinte formato: YYYYMMDD
	 */
	public static String inverteData(Date data) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		String ano = cal.get(Calendar.YEAR) + "";
		String mes = (cal.get(Calendar.MONTH) + 1) + ""; // porque month
		// comeca com zero
		String dia = cal.get(Calendar.DAY_OF_MONTH) + "";
		if (mes.length() == 1)
			mes = "0" + mes;
		if (dia.length() == 1)
			dia = "0" + dia;
		return ano + mes + dia;
	}

	/**
	 * Metodo para gerar uma data a partir de uma string composta por uma data
	 * invertida no formato YYYYMMDD
	 */
	public static Date dataFromStringInvertida(String dataInvertida) throws Exception {
		Calendar cal = new GregorianCalendar();
		// o mes do Calendar comeca de zero, devemos subtrair um.
		cal.set(Integer.parseInt(dataInvertida.substring(0, 4)), Integer.parseInt(dataInvertida.substring(4, 6)) - 1, Integer.parseInt(dataInvertida.substring(
				6, 8)));
		return cal.getTime();
	}

	/**
	 * Metodo para gerar uma string contendo uma data gerada a partir de uma
	 * string composta por uma data invertida no formato YYYYMMDD.
	 * 
	 * @return String no formato "dd/mm/aaaa"
	 */
	public static String inverteDataFromString(String data) throws Exception {
		String ano = data.substring(6, 10);
		String mes = data.substring(3, 5);
		String dia = data.substring(0, 2);
		return ano + mes + dia;
	}

	/**
	 * Metodo para gerar uma string contendo uma data invertida a partir de uma
	 * string composta por uma data no formato dd/mm/aaaa.
	 * 
	 * @return String no formato "aaaammdd"
	 */
	public static String dataStrFromStringInvertida(String dataInvertida) throws Exception {
		String ano = dataInvertida.substring(0, 4);
		String mes = dataInvertida.substring(4, 6);
		String dia = dataInvertida.substring(6, 8);
		return dia + "/" + mes + "/" + ano;
	}

	/**
	 * Retorna uma string com a quantidade de espacos em branco informada
	 * 
	 */
	public static String espacos(int qtde) {
		if (qtde == 0)
			return "";
		String esp = "";
		for (int i = 1; i <= qtde; i++)
			esp = esp + " ";
		return esp;
	}

	/**
	 * Retorna uma string com a quantidade de caracteres informada
	 * 
	 */
	public static String caracteres(String carac, int qtde) {
		if (qtde == 0)
			return "";
		String esp = "";
		for (int i = 1; i <= qtde; i++)
			esp = esp + carac;
		return esp;
	}

	/**
	 * Retorna uma string com a quantidade de caracteres informada
	 * 
	 */
	public static String preencheCaracteres(String numero, int qtde) {
		if (qtde == 0 || numero.length() > qtde)
			return ("" + numero);
		String esp = "";
		for (int i = 1; i <= (qtde - numero.length()); i++)
			esp = esp + "0";
		return esp + numero;
	}

	/**
	 * Retorna uma string com zeros a esquerda
	 * 
	 */
	public static String formatString(String carac, int qtde) {
		if (carac != null) {
			while (carac.length() < qtde)
				carac = "0" + carac;
		} else {
			carac = "0";
			do {
				carac = "0" + carac;
			} while (carac.length() < qtde);
		}
		return carac;
	}

	/**
	 * converte, arredondando, o argumento para 2 casas decimais.
	 * 
	 * @param valor
	 * @deprecated usa um metodo nao eficiente. Utilize o metodo
	 *             <code>arredondaParaDuasCasas(double)</code> no lugar deste
	 */
	public static double converteTo2Casas(double valor) throws FormatoInadequadoException {
		FORMAT.setMinimumFractionDigits(2);
		try {
			return FORMAT.parse(FORMAT.format(valor)).doubleValue();
		} catch (ParseException ex) {
			throw new FormatoInadequadoException();
		}
	}

	/**
	 * Converte o valor para um equivalente de duas casas decimais,
	 * arredondando, (tem o mesmo efeito de se somar 0,05 ao número e truncar da
	 * terceira casa decimal para frente
	 * 
	 * @param valor
	 * @return
	 * @author mborges
	 * @since 22/03/2006
	 */
	public static double arredondaParaDuasCasas(double valor) {
		valor = valor * 100;
		valor = Math.round(valor);
		valor = valor / 100;

		return valor;
	}

	/**
	 * Converte o valor para um equivalente de duas casas decimais, truncando,
	 * iguinorada terceira casa decimal para frente
	 * 
	 * @param valor
	 * @return
	 * @author Pedro Isaac MAIORAL
	 * @since 01/09/2009
	 */
	public static double truncaDoubleParaDuasCasas(double valor) {
		int valorTmp;
		valor = valor * 100;
		valorTmp = (int) valor;
		valor = ((double) valorTmp) / 100;
		return valor;
	}

	/**
	 * Devolve uma data que é "prazo" dias após a data informada.
	 * 
	 * @param data
	 * @param prazo
	 * @return
	 */
	public static Date getProximaData(Date data, int prazo) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		cal.add(Calendar.DATE, prazo);
		return cal.getTime();
	}

	// Leandro - 03042002 - inicio
	/**
	 * Metodo para geral um calendar dada uma data string
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 * @deprecated Use {@link #montarCalendar(String,String,String)} ao invés
	 *             deste
	 */
	public static Calendar montarData(String dia, String mes, String ano) {
		return montarCalendar(dia, mes, ano);
	}

	// Leandro - 03042002 - inicio
	/**
	 * Metodo para geral um calendar dada uma data string
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 */
	public static Calendar montarCalendar(String dia, String mes, String ano) {
		Calendar calTemp = Calendar.getInstance(LOCALE_BR);
		calTemp.set(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));
		// System.out.println(calTemp.getTime());
		return calTemp;
	}

	// Leandro - 03042002 - fim

	// Leandro - 05052002 - inicio
	/**
	 * Metodo para gerar um calendar dada uma data string no formato yyyy-MM-dd
	 * HH:mm:ss
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 * @deprecated Use {@link #montarCalendarDeDataCompleta(String)} ao invés
	 *             deste
	 */
	public static Calendar montarData(String dataCompleta) {
		return montarCalendarDeDataCompleta(dataCompleta);
	}

	// Leandro - 05052002 - inicio
	/**
	 * Metodo para gerar um calendar dada uma data string no formato yyyy-MM-dd
	 * HH:mm:ss
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 */
	public static Calendar montarCalendarDeDataCompleta(String dataCompleta) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", LOCALE_BR);
		Calendar calTemp = Calendar.getInstance(LOCALE_BR);

		try {
			calTemp.setTime(formatter.parse(dataCompleta));
		} catch (Exception e) {
			System.out.println("[xProc][ERROR]: " + e.getMessage());
			calTemp = null;
		}
		return calTemp;
	}

	/**
	 * @author dsilva
	 * @param data
	 * @return
	 * @deprecated Use {@link #montarCalendar(String)} ao invés deste
	 */
	public static Calendar montarDate(String data) {
		return montarCalendar(data);
	}

	/**
	 * @author dsilva
	 * @param data
	 * @return
	 */
	public static Calendar montarCalendar(String data) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("dd/MM/yyyy", LOCALE_BR);
		Calendar calTemp = Calendar.getInstance(LOCALE_BR);

		try {
			calTemp.setTime(formatter.parse(data));
		} catch (Exception e) {
			System.out.println("[xProc][ERROR]: " + e.getMessage());
			calTemp = null;
		}
		return calTemp;
	}

	/**
	 * Este metodo complementa a string passada com zeros aa esquerda ate
	 * alcancar a qtde de carcteres assinalada em qtde
	 * 
	 */
	public static String toStringComPreenchimento(String valor, int qtde) {
		if (valor.length() == qtde)
			return valor;
		int comp = qtde - valor.length();
		if (comp <= 0)
			return valor;
		String s = caracteres("0", comp);
		return s + valor;
	}

	public static String calculaDigVerificadorMod10P2e1(String chave) {
		int peso = 2;
		int soma = 0;
		String vS = null;
		for (int i = chave.length() - 1; i >= 0; i--) {
			vS = chave.charAt(i) + "";
			int prod = Integer.parseInt(vS) * peso;
			if (prod > 9)
				prod = prod - 9;
			soma += prod;
			if (peso == 2)
				peso = 1;
			else
				peso = 2;
		}
		int dig = soma % 10;
		if (dig > 0)
			dig = 10 - dig;
		String res = dig + "";
		return res.trim();
	}

	public static String calculaRestoPesosMod11P2a7(String chave) {
		int peso = 2;
		int soma = 0;
		// System.out.println("******Geral.Utilitaria:CalcResto:01:chave:"+chave);
		for (int i = chave.length() - 1; i >= 0; i--) {
			String vS = chave.charAt(i) + "";
			int prod = Integer.parseInt(vS) * peso;
			soma += prod;
			peso++;
			if (peso > 7)
				peso = 2;
		}
		// System.out.println("******Geral.Utilitaria:CalcResto:01:soma:"+soma);
		int dig = soma % 11;
		String res = dig + "";
		return res.trim();
	}

	public static String montaCodBarrasDeBoleto(String banco, Date dt, String codMoeda, String valor, String chave) {
		// Andre - 27/01/2003 - metodo modificado. chave e valor passam a ter
		// nova formatação pertencendo a chav.

		int peso = 2;
		int soma = 0, qtde;
		String chav = "", ch, vl;

		if (banco.equals("341")) // Itau
		{
			chav = banco + codMoeda + valor + chave;
		}

		if (banco.equals("748")) // Sicredi
		{
			chav = banco + codMoeda + valor + chave;
		}

		if (banco.equals("237")) {// Bradesco
			chav = banco + codMoeda + valor + chave;
			System.out.println("Chave Bradesco" + chav);
		}
		if (banco.equals("070")) {// BRB
			chav = banco + codMoeda + valor + chave;

		}

		for (int i = chav.length() - 1; i >= 0; i--) {
			// if(i!=4){
			String vS = chav.charAt(i) + "";
			int prod = Integer.parseInt(vS) * peso;
			soma += prod;
			peso++;
			if (peso > 9)
				peso = 2;
			// }
		}

		int dig = soma % 11;
		if ((dig == 0) || (dig == 10) || (dig == 1))
			dig = 1;
		else
			dig = 11 - dig;
		String digS = dig + "";
		String res = banco + codMoeda + digS + valor + chave;

		return res;
	}

	public static String montaLinhaDigDeBoleto(String codBanco, String codMoeda, String chaveBanco, String valor, String codBarras) {
		String grupo1 = codBanco.trim() + codMoeda + chaveBanco.substring(0, 5);
		grupo1 = grupo1 + Utilitaria.calculaDigVerificadorMod10P2e1(grupo1);
		System.out.println("*********:Utilitaria:montaLinhaDig:01:grupo1:" + grupo1);
		String grupo2 = chaveBanco.substring(5, 15);
		grupo2 = grupo2 + Utilitaria.calculaDigVerificadorMod10P2e1(grupo2);
		System.out.println("*********:Utilitaria:montaLinhaDig:02:grupo2:" + grupo2);
		String grupo3 = chaveBanco.substring(15);
		grupo3 = grupo3 + Utilitaria.calculaDigVerificadorMod10P2e1(grupo3);
		System.out.println("*********:Utilitaria:montaLinhaDig:03:grupo3:" + grupo3);
		String linhaDig = grupo1 + grupo2 + grupo3 + codBarras.charAt(4);
		int indV = 0;
		while ((valor.charAt(indV) == '0') && (indV < valor.length()))
			indV++;
		indV = valor.length() - indV;
		if (indV < 3)
			indV = 3; // tamanho minimo do campo digitavel
		linhaDig = linhaDig + valor.substring(valor.length() - indV);
		return linhaDig;
	}

	public static String convDataParaString(Date data) {
		String dataS = "";
		if (data != null) {
			dataS = DateFormat.getDateInstance(DateFormat.MEDIUM, LOCALE_BR).format(data);
		}
		return dataS;
	}

	public static String convDataParaStringDataHora(Date data) {
		String dataS = "";
		if (data != null) {
			dataS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
		}
		return dataS;
	}

	/**
	 * Verifica se uma String é uma data válida no formato dd/MM/yyyy
	 * 
	 * @param data
	 * @return
	 * @author mborges
	 * @since 26/12/2006
	 */
	public static boolean isDate(String data) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, LOCALE_BR);
		df.setLenient(false);
		try {
			Date parsed = df.parse(data);

			return (parsed != null);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verifica se uma String é uma data válida no formato dd/MM/yyyy
	 * 
	 * @param data
	 * @return
	 * @author mborges
	 * @since 26/12/2006
	 */
	public static boolean validaData(String data) {
		return isDate(data);
	}

	/**
	 * Verifica se a data informada está dentro do período informado.
	 * 
	 * @param dataInicio
	 *            Data inicial do período.
	 * @param dataFim
	 *            Data final do período.
	 * @param data
	 *            Data informada.
	 * @return true se estiver do período e false se não estiver.
	 * @author murilovaladares
	 * @since 04/01/2011
	 */
	public static boolean verificaDataNoIntervalo(Date dataInicio, Date dataFim, Date data) {

		if ((data.equals(dataInicio) || data.after(dataInicio)) && (dataFim == null || data.equals(dataFim) || data.before(dataFim)))
			return true;
		else
			return false;
	}

	/**
	 * Verifica se 2 períodos são concomitantes.
	 * 
	 * @param p1Inicio
	 *            Data inicial do 1º período.
	 * @param p1Fim
	 *            Data final do 1º período.
	 * @param p2Inicio
	 *            Data inicial do 2º período.
	 * @param p2Fim
	 *            Data final do 2º período. <br />
	 *            Não haverá concomitância se a data inicial de um período for >
	 *            que a data final do outro, ou se a data final de um período
	 *            for menor que a data inicial do outro.
	 * 
	 * @return <b>true</b> se houver concomitância e <b>false</b> se não houver.
	 * @since 25/04/2011
	 * @author murilo valadares
	 */
	public static boolean verificaConcomitancia(Date p1Inicio, Date p1Fim, Date p2Inicio, Date p2Fim) {
		// se não houver concomitância.
		if (p1Inicio.compareTo(p2Fim) > 0 || p1Fim.compareTo(p2Inicio) < 0)
			return false;
		else
			// significa que tem concomitância.
			return true;
	}

	public static String getHorarioDaData(Date data) {
		String dataS = "";
		if (data != null) {
			dataS = DateFormat.getTimeInstance().format(data);
		}
		return dataS;
	}

	public static Date convStringParaData(String dataS) throws Exception {
		Date data = null;
		if (dataS != null) {
			try {
				DateFormat formataData = DateFormat.getDateInstance(DateFormat.MEDIUM, LOCALE_BR);
				formataData.setLenient(false);
				data = formataData.parse(dataS);
			} catch (Exception ex) {
				throw new Exception("Data Incorreta '" + dataS + "'");
			}
		}
		return data;
	}

	public static Date convStringDataHoraParaData(String dataHora) {
		Date dth = null;
		if (dataHora != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				dth = sdf.parse(dataHora);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return dth;
	}

	// O metodo retornara null se a string nao for adequada
	public static Date convStringParaDataNull(String dataS) {
		Date data = null;
		if (dataS != null) {
			try {
				DateFormat formataData = DateFormat.getDateInstance(DateFormat.MEDIUM, LOCALE_BR);
				formataData.setLenient(false);
				data = formataData.parse(dataS);
			} catch (Exception ex) {
				if (dataS.trim().length() > 0) {
					System.out.println("Utilitaria:convStringParaData:Data Incorreta:" + dataS);
				}
				data = null;
			}
		}
		return data;
	}

	public static Map convSegundosParaHoraMinutoSegundo(long segundos) {
		Map qtds = new HashMap();
		qtds.put("hora", new Long(segundos / 3600));
		qtds.put("minuto", new Long((segundos % 3600) / 60));
		qtds.put("segundo", new Long(segundos % 60));

		return qtds;
	}

	public static Map<String, Integer> convDiasParaAnoMesDia(int dias) {
		Map<String, Integer> qtds = new HashMap<String, Integer>();
		qtds.put("ano", new Integer(convDiasParaAno(dias)));
		qtds.put("mes", new Integer(convDiasParaMes(dias)));
		qtds.put("dia", new Integer(convDiasParaDia(dias)));

		return qtds;
	}

	/**
	 * Retorna a formatação separada em um map.
	 */
	public static Map<String, Integer> convDateParaAnoMesDia(Date dt) {
		Map<String, Integer> ret = new HashMap<String, Integer>();
		String data = Utilitaria.convDataParaString(dt);
		ret.put("ano", new Integer(data.substring(6, 10)));
		ret.put("mes", new Integer(data.substring(3, 5)));
		ret.put("dia", new Integer(data.substring(0, 2)));

		return ret;
	}

	public static int diferencaEmMesesEntreMesAno(String mesAnoInicio, String mesAnoFim) throws FormatoInadequadoException {
		if (mesAnoInicio == null || !isMesAno(mesAnoInicio.replaceAll("[\\p{Punct}]", ""))) {
			throw new FormatoInadequadoException("Mes/Ano de início inválido!");
		} else if (mesAnoFim == null || !isMesAno(mesAnoFim.replaceAll("[\\p{Punct}]", ""))) {
			throw new FormatoInadequadoException("Mes/Ano de fim inválido!");
		} else {
			String anoMesInicio = convMesAnoParaAnoMes(mesAnoInicio);
			String anoMesFim = convMesAnoParaAnoMes(mesAnoFim);

			return diferencaEmMesesEntreAnoMes(anoMesInicio, anoMesFim);
		}
	}

	public static int diferencaEmMesesEntreAnoMes(String anoMesInicio, String anoMesFim) throws FormatoInadequadoException {
		if (anoMesInicio == null || !isAnoMes(anoMesInicio.replaceAll("[\\p{Punct}]", ""))) {
			throw new FormatoInadequadoException("Mes/Ano de início inválido!");
		} else if (anoMesFim == null || !isAnoMes(anoMesFim.replaceAll("[\\p{Punct}]", ""))) {
			throw new FormatoInadequadoException("Mes/Ano de fim inválido!");
		} else {
			return diferencaEmMesesEntreAnoMes(Integer.parseInt(anoMesInicio.trim()), Integer.parseInt(anoMesFim));
		}
	}

	public static int diferencaEmMesesEntreAnoMes(int anoMesInicio, int anoMesFim) throws FormatoInadequadoException {
		if (!isAnoMes(anoMesInicio)) {
			throw new FormatoInadequadoException("Mes/Ano de início inválido!");
		} else if (!isAnoMes(anoMesFim)) {
			throw new FormatoInadequadoException("Mes/Ano de fim inválido!");
		} else {
			int anoInicio = anoMesInicio / 100;
			int mesInicio = anoMesInicio % 100;
			int anoFim = anoMesFim / 100;
			int mesFim = anoMesFim % 100;

			int difAno = anoFim - anoInicio;
			int dif = (mesFim + difAno * 12) - mesInicio;

			return dif;
		}
	}

	public static int diferencaEmMesesEntreDatasComRegras(Date dataInicio, Date dataFim) throws FormatoInadequadoException {
		if (dataInicio == null) {
			throw new FormatoInadequadoException("Data de início inválida!");
		} else if (dataFim == null) {
			throw new FormatoInadequadoException("Data de fim inválida!");
		} else {
			Calendar calInicio = Calendar.getInstance();
			calInicio.setTime(dataInicio);

			int anoMesInicio = Integer.parseInt(converteDateParaAnoMes(dataInicio));
			int anoMesFim = Integer.parseInt(converteDateParaAnoMes(dataFim));
			int anoMesAtual = Integer.parseInt(converteDateParaAnoMes(new Date()));

			if (!isAnoMes(anoMesInicio)) {
				throw new FormatoInadequadoException("Mes/Ano de início inválido!");
			} else if (!isAnoMes(anoMesFim)) {
				throw new FormatoInadequadoException("Mes/Ano de fim inválido!");
			} else {
				int anoInicio = anoMesInicio / 100;
				int mesInicio = anoMesInicio % 100;
				@SuppressWarnings("static-access")
				int diaInicio = calInicio.get(calInicio.DAY_OF_MONTH);

				int anoFim = anoMesFim / 100;
				int mesFim = anoMesFim % 100;
				int anoAtual = anoMesAtual / 100;

				int difAno = anoFim - anoInicio;
				int dif = (mesFim + difAno * 12) - mesInicio;

				if (anoInicio == anoAtual && mesInicio == 1) {
					if (diaInicio < 16) {
						dif += 1;
					}
				}

				if (anoFim == anoAtual) {
					if (diaInicio < 16) {
						dif += 1;
					}
				}

				return dif;
			}
		}
	}

	public static String adicionarMesesAoMesAno(String mesAno, int mesesParaAdicionar) throws FormatoInadequadoException, IllegalArgumentException {
		if (mesAno == null || !isMesAno(mesAno.replaceAll("[\\p{Punct}]", ""))) {
			throw new FormatoInadequadoException("Mes/Ano de início inválido!");
		} else if (mesesParaAdicionar < 0) {
			throw new IllegalArgumentException("Impossivel adicionar numero negativo de meses!");
		} else {
			String anoMesInicio = convMesAnoParaAnoMes(mesAno.replaceAll("[\\p{Punct}]", ""));

			return Utilitaria.convAnoMesParaMesAno(adicionarMesesAoAnoMes(anoMesInicio, mesesParaAdicionar));
		}
	}

	public static String adicionarMesesAoAnoMes(String anoMes, int mesesParaAdicionar) throws FormatoInadequadoException, IllegalArgumentException {
		if (anoMes == null || !isAnoMes(anoMes.replaceAll("[\\p{Punct}]", ""))) {
			throw new FormatoInadequadoException("Mes/Ano de início inválido!");
		} else if (mesesParaAdicionar < 0) {
			throw new IllegalArgumentException("Impossivel adicionar numero negativo de meses!");
		} else {
			return String.valueOf(adicionarMesesAoAnoMes(Integer.parseInt(anoMes), mesesParaAdicionar));
		}
	}

	public static int adicionarMesesAoAnoMes(int anoMes, int mesesParaAdicionar) throws FormatoInadequadoException, IllegalArgumentException {
		if (!isAnoMes(anoMes)) {
			throw new FormatoInadequadoException("Mes/Ano de início inválido!");
		} else if (mesesParaAdicionar < 0) {
			throw new IllegalArgumentException("Impossivel adicionar numero negativo de meses!");
		} else {
			int ano = anoMes / 100;
			int mes = anoMes % 100;
			// Indexando mes por 0:
			mes -= 1;

			mes += mesesParaAdicionar;

			ano += mes / 12;
			mes %= 12;

			// Voltando a indexar o mes por 1:
			mes += 1;
			int resultado = ano * 100 + mes;

			return resultado;
		}
	}

	/**
	 * @param anoMes
	 * @param mesesParaSubtrair
	 * @return
	 * @throws FormatoInadequadoException
	 * @throws IllegalArgumentException
	 * @author Pedro Isaac
	 * @since 15/06/2011
	 */
	public static String subtrairMesesAoAnoMes(String anoMes, int mesesParaSubtrair) throws FormatoInadequadoException, IllegalArgumentException {
		if (anoMes == null || !isAnoMes(anoMes.replaceAll("[\\p{Punct}]", ""))) {
			throw new FormatoInadequadoException("Mes/Ano de início inválido!");
		} else if (mesesParaSubtrair < 0) {
			throw new IllegalArgumentException("Impossivel adicionar numero negativo de meses!");
		} else {
			return String.valueOf(subtrairMesesAoAnoMes(Integer.parseInt(anoMes), mesesParaSubtrair));
		}
	}

	/**
	 * @param anoMes
	 * @param mesesParaSubtrair
	 * @return
	 * @throws FormatoInadequadoException
	 * @throws IllegalArgumentException
	 * @author Pedro Isaac
	 * @since 15/06/2011
	 */
	public static int subtrairMesesAoAnoMes(int anoMes, int mesesParaSubtrair) throws FormatoInadequadoException, IllegalArgumentException {
		if (!isAnoMes(anoMes)) {
			throw new FormatoInadequadoException("Mes/Ano de início inválido!");
		} else if (mesesParaSubtrair < 0) {
			throw new IllegalArgumentException("Impossivel adicionar numero negativo de meses!");
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(convAnoMesParaDate(String.valueOf(anoMes)));

			Format formato = new SimpleDateFormat("yyyyMM");

			calendar.add(Calendar.MONTH, -mesesParaSubtrair);

			return Integer.parseInt(formato.format(calendar.getTime()));
		}
	}

	public static int convDiasParaAno(int dias) {
		return dias / 365;
	}

	public static int convDiasParaMes(int dias) {
		int meses = (dias % 365) / 30;
		return (meses >= 12 ? 11 : meses);
		// return (int) (dias - ((int) (dias / 365)) * 365) / 30;
	}

	public static int convDiasParaDia(int dias) {
		int meses = (dias % 365) / 30;
		int diasRestantes = (dias % 365) % 30;
		return (diasRestantes >= 30 || meses >= 12 ? 29 : diasRestantes);
		// return (dias - (int) (dias / 365) * 365)
		// - ((int) (dias - (int) (dias / 365) * 365) / 30) * 30;
	}

	/**
	 * @param data
	 * @return
	 * @deprecated Nao converte datas para JDBC de forma conveniente. Usar
	 *             <code>convDataHoraParaJDBC</code>.
	 */
	public static String convParaJDBC(String data) {
		String ano = data.substring(6, 10);
		String mes = data.substring(3, 5);
		String dia = data.substring(0, 2);
		if (formatoJDBC.equals("BRA")) {
			return dia + "/" + mes + "/" + ano;
		} else
			return mes + "/" + dia + "/" + ano;
	}

	public static String convStringParaJDBC(String data) {
		String ano = data.substring(6, 10);
		String mes = data.substring(3, 5);
		String dia = data.substring(0, 2);
		return ano + "/" + mes + "/" + dia;
	}

	public static Date juntaDataHora(Date data, java.sql.Time hora) {
		String dataS = DateFormat.getDateInstance().format(data);
		String horaS = hora.toString().substring(0, 8);
		String dtH = dataS + " " + horaS;
		Date dt = null;
		if (dtH != null) {
			try {
				dt = DateFormat.getDateTimeInstance().parse(dtH);
			} catch (Exception ex) {
				System.out.println("Utilitaria:juntaDataHora:Data Incorreta:" + dtH);
				dt = null;
			}
		}
		return dt;
	}

	/**
	 * @param data
	 * @return
	 * @deprecated Nao converte datas para JDBC de forma conveniente. Usar
	 *             <code>convDataHoraParaJDBC</code>.
	 */
	public static String convParaJDBC(Date data) {
		String dt = convDataParaString(data);
		return convParaJDBC(dt);
	}

	/**
	 * Converte uma datetime para string com data e hora no formato JDBC
	 * adequado ao BD
	 * 
	 * @param dt
	 *            data a ser convertida
	 * @return
	 */
	public static String convDataHoraParaJDBC(Date dt) {
		if (dt == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		/*
		 * String hora= getHorarioDaData(dt); String data=
		 * convDataParaString(dt); data = convParaJDBC(data); data=data+"
		 * "+hora;
		 */
		return formatter.format(dt);
	}

	/**
	 * Converte uma datetime para string com data e hora no formato JDBC
	 * adequado ao BD
	 * 
	 * @param dt
	 *            data a ser convertida
	 * @return
	 * @deprecated O modo de conversão no banco foi alterado
	 */
	public static String convDataHoraParaJDBC_PTBR(Date dt) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		/*
		 * String hora= getHorarioDaData(dt); String data=
		 * convDataParaString(dt); data = convParaJDBC(data); data=data+"
		 * "+hora;
		 */
		return formatter.format(dt);
	}

	public static String convDataHoraParaJDBCSemHoras(Date dt) {
		if (dt == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		return formatter.format(dt);
	}

	public static Date convDataHoraStrParaData(String data, String hora) {
		Date dtB = convStringParaDataNull(data);
		Calendar cl = new GregorianCalendar();
		cl.setTime(dtB);
		int hor = Integer.parseInt(hora.substring(0, 2));
		int min = Integer.parseInt(hora.substring(3, 5));
		cl.set(Calendar.HOUR_OF_DAY, hor);
		cl.set(Calendar.MINUTE, min);
		return cl.getTime();
	}

	public static Date convDataHoraStrParaData(String dataHora) {

		if (dataHora == null || dataHora.equals(""))
			return null;

		String data = dataHora.substring(8, 10) + "/" + dataHora.substring(5, 7) + "/" + dataHora.substring(0, 4);
		String hora = dataHora.substring(11, 19);

		Date dtB = convStringParaDataNull(data);
		Calendar cl = new GregorianCalendar();
		cl.setTime(dtB);
		int hor = Integer.parseInt(hora.substring(0, 2));
		int min = Integer.parseInt(hora.substring(3, 5));
		int sec = Integer.parseInt(hora.substring(6, 8));
		cl.set(Calendar.HOUR_OF_DAY, hor);
		cl.set(Calendar.MINUTE, min);
		cl.set(Calendar.SECOND, sec);
		return cl.getTime();
	}

	public static Date convDataHoraStrParaData2(String dataHora) {

		if (dataHora == null || dataHora.equals(""))
			return null;

		String data = dataHora.substring(0, 2) + "/" + dataHora.substring(3, 5) + "/" + dataHora.substring(6, 10);
		String hora = dataHora.substring(11, 19);

		Date dtB = convStringParaDataNull(data);
		Calendar cl = new GregorianCalendar();
		cl.setTime(dtB);
		int hor = Integer.parseInt(hora.substring(0, 2));
		int min = Integer.parseInt(hora.substring(3, 5));
		int sec = Integer.parseInt(hora.substring(6, 8));
		cl.set(Calendar.HOUR_OF_DAY, hor);
		cl.set(Calendar.MINUTE, min);
		cl.set(Calendar.SECOND, sec);
		return cl.getTime();
	}

	/**
	 * compara duas datas sem levar em conta as horas(verifica somente o dia, o
	 * mes e o ano) se data1 == data2 retorna 0 se data1 < data2 retorn -1 se
	 * data1 > data2 retorna 1
	 * 
	 * @deprecated usar versao com o nome certo:
	 *             <code>comparaDatas(java.util.Date, java.util.Date)</code>
	 *             Esta versão (depreciada) do método simplesmente chama a
	 *             versão nova (com nome correto)
	 */
	public static int conparaDatas(Date data1, Date data2) {
		return comparaDatas(data1, data2);
	}

	/**
	 * compara duas datas sem levar em conta as horas(verifica somente o dia, o
	 * mes e o ano) se data1 == data2 retorna 0 se data1 < data2 retorn -1 se
	 * data1 > data2 retorna 1
	 */
	public static int comparaDatas(Date data1, Date data2) {
		Date dt1 = convStringParaDataNull(convDataParaString(data1));
		Date dt2 = convStringParaDataNull(convDataParaString(data2));
		return dt1.compareTo(dt2);
	}

	public static String convParaFormatoMonetario(double valor) {
		DecimalFormat form = new DecimalFormat("##,###,###,###.##");
		form.setMaximumFractionDigits(2);
		form.setMinimumFractionDigits(2);
		String s = form.format(valor);
		return s;
	}

	public static double convFormatoMonetarioParaDouble(String valor) {
		DecimalFormat form = new DecimalFormat("##,###,###,###.##");
		form.setMinimumFractionDigits(2);
		Number n = form.parse(valor, new ParsePosition(0));
		double f = n.doubleValue();
		return f;
	}

	public static String convParaFormatoMonetarioReal(double valor) {
		return FORMAT_MONETARIO_REAL.format(valor);
	}

	public static double convFormatoMonetarioRealParaDouble(String valor) throws ParseException {
		return FORMAT_MONETARIO_REAL.parse(valor).doubleValue();
	}

	public static String convParaFormatoMonetario12Cs(double valor) {
		NumberFormat nF = NumberFormat.getInstance(LOCALE_BR);
		nF.setMaximumFractionDigits(12);
		nF.setMinimumFractionDigits(2);
		String s = nF.format(valor);
		return s;
	}

	public static String convParaFormatoMonetario2Cs(double valor) {
		NumberFormat nF = NumberFormat.getInstance(LOCALE_BR);
		nF.setMaximumFractionDigits(2);
		nF.setMinimumFractionDigits(2);
		String s = nF.format(valor);
		return s;
	}

	/**
	 * Retorna a diferença entre as datas informadas em dias Se dt2 é maior que
	 * dt retorna o valor negativo(não leva em conta as horas e segundos)
	 */
	public static int calculaDifDatasEmDias(Date dt, Date dt2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		maximizarHoraMinutoSegundoMilisegundo(cal);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dt2);
		zerarHoraMinutoSegundoMilisegundo(cal2);
		long miliDt = cal.getTimeInMillis();
		long miliDt2 = cal2.getTimeInMillis();

		// Date data = convStringParaData(convDataParaString(dt));
		// Date data2 = convStringParaData(convDataParaString(dt2));
		// long miliDt = data.getTime();
		// long miliDt2 = data2.getTime();

		int dif = (int) ((miliDt - miliDt2) / (1000 * 60 * 60 * 24));
		return dif;
	}

	/**
	 * Retorna a diferença entre as datas informadas em dias Se dataFinal é
	 * maior que dataInicial retorna o valor negativo(não leva em conta as horas
	 * e segundos)
	 */
	public static int calculaDifDatasEmDiasParaBancos(Date dataInicial, Date dataFinal) {
		Calendar data1 = new GregorianCalendar();
		data1.setTime(dataInicial);
		Calendar data2 = new GregorianCalendar();
		data2.setTime(dataFinal);
		long m1 = data1.getTimeInMillis();
		long m2 = data2.getTimeInMillis();
		return (int) ((m2 - m1) / (24 * 60 * 60 * 1000));
	}

	/**
	 * Retorna a diferença entre as datas informadas em dias. A data inicial
	 * deve estar em dt2 e a final em dt1. Se dt2 for maior que dt1, o resultado
	 * será negativo.
	 * 
	 * Obs.: Quando converte meses para dias, considera os meses como sendo
	 * sempre de 30 dias e, quando converte anos para meses, considera os anos
	 * como sendo sempre de 365. Não leva em conta as horas e os segundos.
	 * 
	 * @param dt1
	 *            data final.
	 * @param dt2
	 *            data inicial.
	 * @return
	 */
	public static int calculaDifDatasEmDias_30_365(Date dt1, Date dt2) {
		int comp = comparaDatas(dt1, dt2);
		if (comp > 0) {
			int dif = 0;

			Map<String, Integer> data1 = convDateParaAnoMesDia(dt1);
			Map<String, Integer> data2 = convDateParaAnoMesDia(dt2);
			Integer dia1 = data1.get("dia");
			Integer mes1 = data1.get("mes");
			Integer ano1 = data1.get("ano");
			Integer dia2 = data2.get("dia");
			Integer mes2 = data2.get("mes");
			Integer ano2 = data2.get("ano");

			// diferença dos dias
			if (dia1 < dia2) {
				if (mes1 > 1)
					mes1--;
				else {
					ano1--;
					mes1 = 12;// fica logo 12 ou invés de 13, pois é retirado 1
								// para os dias.
				}
				dia1 += 30;
			}
			dif += dia1 - dia2;

			// diferença dos meses
			if (mes1 < mes2) {
				ano1--;
				mes1 += 12;
			}
			dif += (mes1 - mes2) * 30;

			// diferença dos anos
			dif += (ano1 - ano2) * 365;

			return dif;
		} else if (comp == 0) {
			return 0;
		} else
			return -1;
	}

	/**
	 * Retorna um ordinal correspondente ao dia da semana do calendar 0 -
	 * domingo ... 6 - sabado
	 */
	public static int getOrdinalDoDiaDaSemana(Calendar cal) {
		int dia = cal.get(Calendar.DAY_OF_WEEK);
		int cont = 0;
		if (dia == Calendar.SUNDAY) {
			cont = 0;
		}
		if (dia == Calendar.MONDAY) {
			cont = 1;
		}
		if (dia == Calendar.TUESDAY) {
			cont = 2;
		}
		if (dia == Calendar.WEDNESDAY) {
			cont = 3;
		}
		if (dia == Calendar.THURSDAY) {
			cont = 4;
		}
		if (dia == Calendar.FRIDAY) {
			cont = 5;
		}
		if (dia == Calendar.SATURDAY) {
			cont = 6;
		}
		return cont;
	}

	/**
	 * REtorna a proxima data (a partir do dia da semana indicado por
	 * ordinalDaData) cujo dia da semana e´indicado pelo ordinalDaProximaData
	 */
	public static int getQtdeDiasParaProximoDia(int ordinalDaData, int ordinalDaProximaData) {
		// pra segunda feira
		int qtdDias = 0;
		if (ordinalDaProximaData == 1) {
			if (ordinalDaData == 0)
				qtdDias = 1;
			if (ordinalDaData == 1)
				qtdDias = 0;
			if (ordinalDaData == 2)
				qtdDias = 6;
			if (ordinalDaData == 3)
				qtdDias = 5;
			if (ordinalDaData == 4)
				qtdDias = 4;
			if (ordinalDaData == 5)
				qtdDias = 3;
			if (ordinalDaData == 6)
				qtdDias = 2;
		}
		// pra terca feira
		if (ordinalDaProximaData == 2) {
			if (ordinalDaData == 0)
				qtdDias = 2;
			if (ordinalDaData == 1)
				qtdDias = 1;
			if (ordinalDaData == 2)
				qtdDias = 0;
			if (ordinalDaData == 3)
				qtdDias = 6;
			if (ordinalDaData == 4)
				qtdDias = 5;
			if (ordinalDaData == 5)
				qtdDias = 4;
			if (ordinalDaData == 6)
				qtdDias = 3;
		}
		// quarta
		if (ordinalDaProximaData == 3) {
			if (ordinalDaData == 0)
				qtdDias = 3;
			if (ordinalDaData == 1)
				qtdDias = 2;
			if (ordinalDaData == 2)
				qtdDias = 1;
			if (ordinalDaData == 3)
				qtdDias = 0;
			if (ordinalDaData == 4)
				qtdDias = 6;
			if (ordinalDaData == 5)
				qtdDias = 5;
			if (ordinalDaData == 6)
				qtdDias = 4;
		}
		// quinta
		if (ordinalDaProximaData == 4) {
			if (ordinalDaData == 0)
				qtdDias = 4;
			if (ordinalDaData == 1)
				qtdDias = 3;
			if (ordinalDaData == 2)
				qtdDias = 2;
			if (ordinalDaData == 3)
				qtdDias = 1;
			if (ordinalDaData == 4)
				qtdDias = 0;
			if (ordinalDaData == 5)
				qtdDias = 6;
			if (ordinalDaData == 6)
				qtdDias = 5;
		}
		// sexta
		if (ordinalDaProximaData == 5) {
			if (ordinalDaData == 0)
				qtdDias = 5;
			if (ordinalDaData == 1)
				qtdDias = 4;
			if (ordinalDaData == 2)
				qtdDias = 3;
			if (ordinalDaData == 3)
				qtdDias = 2;
			if (ordinalDaData == 4)
				qtdDias = 1;
			if (ordinalDaData == 5)
				qtdDias = 0;
			if (ordinalDaData == 6)
				qtdDias = 6;
		}
		// sabado
		if (ordinalDaProximaData == 6) {
			if (ordinalDaData == 0)
				qtdDias = 6;
			if (ordinalDaData == 1)
				qtdDias = 5;
			if (ordinalDaData == 2)
				qtdDias = 4;
			if (ordinalDaData == 3)
				qtdDias = 3;
			if (ordinalDaData == 4)
				qtdDias = 2;
			if (ordinalDaData == 5)
				qtdDias = 1;
			if (ordinalDaData == 6)
				qtdDias = 0;
		}
		// domingo
		if (ordinalDaProximaData == 0) {
			if (ordinalDaData == 0)
				qtdDias = 0;
			if (ordinalDaData == 1)
				qtdDias = 6;
			if (ordinalDaData == 2)
				qtdDias = 5;
			if (ordinalDaData == 3)
				qtdDias = 4;
			if (ordinalDaData == 4)
				qtdDias = 3;
			if (ordinalDaData == 5)
				qtdDias = 2;
			if (ordinalDaData == 6)
				qtdDias = 1;
		}

		return qtdDias;
	}

	public static boolean isCPF(String cpf) {
		if (cpf.length() == 11)
			return true;
		else
			return false;
	}

	/**
	 * @deprecated Use {@link #isCnpjCpf(String)} ao invés desse
	 */
	public static boolean isCGCCPF(String id) {
		return isCnpjCpf(id);
	}

	public static boolean isCnpjCpf(String id) {
		return (checaCNPJ(id) || checaCPF(id));
	}

	public static String removeZerosAEsquerda(String texto) {
		return texto.replaceAll("^0+", "");
	}

	/**
	 * @deprecated Use {@link #checaCNPJ(String)} ao invés desse
	 */
	public static boolean checaCGC(String nCGC) {
		return checaCNPJ(nCGC);
	}

	public static boolean checaCNPJ(String nCPF) {

		/*
		 * 
		 * Algoritmo do Cálculo dos Dígitos verificadores para CGC
		 * 
		 * 
		 * Exemplo:
		 * 
		 * 57.006.264/0001-70
		 * 
		 * Calcular o primeiro dígito verificador
		 * 
		 * Multiplicar da direita para a esquerda:
		 * 
		 * Soma = 5*5 + 4*7 + 3*0 + 2*0 + 9*6 + 8*2 + 7*6 + 6*4 + 5*0 + 4*0 +
		 * 3*0 + 2*1
		 * 
		 * Soma = 191
		 * 
		 * soma / 11
		 * 
		 * 191 / 11 = 17
		 * 
		 * Resto = 4
		 * 
		 * Se resto = 0 ou 1 então DV1 = 0
		 * 
		 * Se resto <> 0 ou 1 então DV1 = 11 - resto => (11 - 4) = DV1 = 7
		 * 
		 * 
		 * Calcular o segundo dígito verificador (o primeiro DV entra no
		 * cálculo)
		 * 
		 * Multiplicar da direita para a esquerda:
		 * 
		 * Soma = 6*5 + 5*7 + 4*0 + 3*0 + 2*6 + 9*2 + 8*6 + 7*4 + 6*0 + 5*0 +
		 * 4*0 + 3*1 + 2*7
		 * 
		 * Soma = 188
		 * 
		 * soma / 11
		 * 
		 * 188 / 11 = 17
		 * 
		 * Resto = 1
		 * 
		 * Se resto = 0 ou 1 então DV2 = 0
		 * 
		 * Se resto <> 0 ou 1 então DV2 = 11 - resto
		 * 
		 * DV2 = 0
		 */
		// /////////////////////////////////////////////////////////////////////////////////////
		// Se o tamanho do CGC estiver correto
		if (nCPF.length() == 14) {
			int num[] = new int[14];

			// dígitos verificadores
			int dv1, dv2;

			// auxiliares no cálculo
			int soma, resto;

			// guarda os números em um vetor de inteiros para cálculos
			// posteriores
			for (int i = 0; i < 14; i++)
				num[i] = Integer.parseInt(nCPF.substring(i, i + 1));

			// calcula o primeiro dígito verificador

			soma = 5 * num[0] + 4 * num[1] + 3 * num[2] + 2 * num[3] + 9 * num[4] + 8 * num[5] + 7 * num[6] + 6 * num[7] + 5 * num[8] + 4 * num[9] + 3
					* num[10] + 2 * num[11];

			resto = soma % 11;

			if ((resto == 0) || (resto == 1))
				dv1 = 0;
			else
				dv1 = 11 - resto;

			// verifica se o primeiro dígito verificador está correto
			if (dv1 == num[12]) {
				// calcula o segundo dígito verificador

				soma = 6 * num[0] + 5 * num[1] + 4 * num[2] + 3 * num[3] + 2 * num[4] + 9 * num[5] + 8 * num[6] + 7 * num[7] + 6 * num[8] + 5 * num[9] + 4
						* num[10] + 3 * num[11] + 2 * num[12];

				resto = soma % 11;

				if ((resto == 0) || (resto == 1))
					dv2 = 0;
				else
					dv2 = 11 - resto;

				if (dv2 == num[13])
					return true;
				else
					return false;

			} else
				return false;

		} else
			return false;
	}

	public static boolean checaCPF(String strCPF) {

		/*
		 * *********************************************** A * N * T * I * G * O
		 * ****************************************************
		 * 
		 * Algoritmo do Cálculo dos Dígitos verificadores para CPF
		 * 
		 * 
		 * Exemplo:
		 * 
		 * 785.184.909-87
		 * 
		 * Calcular o primeiro dígito verificador
		 * 
		 * Multiplicar da direita para a esquerda:
		 * 
		 * Soma = 10*7 + 9*8 + 8*5 + 7*1 + 6*8 + 5*4 + 4*9 + 3*0 + 2*9
		 * 
		 * Soma = 311
		 * 
		 * Soma / 11
		 * 
		 * 311 / 11 = 28
		 * 
		 * Resto = 3
		 * 
		 * Se Resto > 9 DV1 = 0
		 * 
		 * Se <= 9 DV1 = 11 - Resto => (11 - 3) = DV1 = 8
		 * 
		 * 
		 * Calcular o segundo dígito verificador (o primeiro DV entra no
		 * cálculo)
		 * 
		 * Multiplicar da direita para a esquerda:
		 * 
		 * Soma = 11*7 + 10*8 + 9*5 + 8*1 + 7*8 + 6*4 + 5*9 + 4*0 + 3*9 + 2*8
		 * 
		 * Soma = 378
		 * 
		 * Soma / 11
		 * 
		 * 378 / 11 = 34
		 * 
		 * Resto = 4
		 * 
		 * Se Resto > 9 DV2 = 0
		 * 
		 * Se <= 9 DV2 = 11 - Resto => (11 - 4) = DV2 = 7
		 */
		// /////////////////////////////////////////////////////////////////////////////////////
		// Se o tamanho do CPF estiver correto
		if (strCPF != null && strCPF.length() == 11) {
			int d1, d2;
			int digito1, digito2, resto;
			int digitoCPF;
			String nDigResult;

			d1 = d2 = 0;
			digito1 = digito2 = resto = 0;

			for (int nCount = 1; nCount < strCPF.length() - 1; nCount++) {
				digitoCPF = Integer.valueOf(strCPF.substring(nCount - 1, nCount)).intValue();

				// multiplique a ultima casa por 2 a seguinte por 3 a seguinte
				// por 4 e assim por diante.
				d1 = d1 + (11 - nCount) * digitoCPF;

				// para o segundo digito repita o procedimento incluindo o
				// primeiro digito calculado no passo anterior.
				d2 = d2 + (12 - nCount) * digitoCPF;
			}
			;

			// Primeiro resto da divisão por 11.
			resto = (d1 % 11);

			// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é
			// 11 menos o resultado anterior.
			if (resto < 2)
				digito1 = 0;
			else
				digito1 = 11 - resto;

			d2 += 2 * digito1;

			// Segundo resto da divisão por 11.
			resto = (d2 % 11);

			// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é
			// 11 menos o resultado anterior.
			if (resto < 2)
				digito2 = 0;
			else
				digito2 = 11 - resto;

			// Digito verificador do CPF que está sendo validado.
			String nDigVerific = strCPF.substring(strCPF.length() - 2, strCPF.length());

			// Concatenando o primeiro resto com o segundo.
			nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

			// comparar o digito verificador do cpf com o primeiro resto + o
			// segundo resto.
			return nDigVerific.equals(nDigResult);

		} else
			return false;
	}

	public static String montaDac(String chave) {
		// Andre - 02/06/2003 - metodo modificado
		String dig1 = calculaDigVerificadorMod10P2e1(chave);
		return dig1;
	}

	public static String calculaDivVerificadorModa10P2e9(String chav) {
		// Andre - 02/06/2003 - metodo modificado
		int peso = 2;
		int soma = 0, qtde;
		for (int i = chav.length() - 1; i >= 0; i--) {
			// if(i!=4){
			String vS = chav.charAt(i) + "";
			int prod = Integer.parseInt(vS) * peso;
			soma += prod;
			peso++;
			if (peso > 9)
				peso = 2;
			// }
		}
		int dig = soma % 11;
		if ((dig == 0) || (dig == 10) || (dig == 1))
			dig = 1;
		else
			dig = 11 - dig;
		String digS = dig + "";
		return digS;
	}

	/**
	 * @param dtVencto
	 * @deprecated Metodo nao faz nada! Nem sei porque existe!
	 */
	public static void ftVencimento(Date dtVencto) {
		/*
		 * Date dtBase=new Date(); dtBase. Date fator=dtVencto - dtBase;
		 */
	}

	/**
	 * Converte uma string para o formato SQL
	 * 
	 * @param nome
	 * @return String convertida
	 */
	public static String trataAspas(String nome) {
		int pos = nome.indexOf("'");
		while (pos != -1) {
			if (pos < nome.length())
				nome = nome.substring(0, pos) + "''" + nome.substring(pos + 1);
			else
				nome = nome.substring(0, pos - 1);
			pos = nome.indexOf("'", pos + 2);
		}
		pos = nome.indexOf("\"");
		while (pos != -1) {
			if (pos < nome.length())
				nome = nome.substring(0, pos) + "\"" + nome.substring(pos + 1);
			else
				nome = nome.substring(0, pos - 1);
			pos = nome.indexOf("\"", pos + 1);
		}
		return nome;
	}

	public static String trocaAspas(String nome) {
		int pos = nome.indexOf("'");
		while (pos != -1) {
			if (pos < nome.length())
				nome = nome.substring(0, pos) + "~" + nome.substring(pos + 1);// troca
			// a
			// aspa
			// pelo
			// ~
			else
				nome = nome.substring(0, pos - 1);
			pos = nome.indexOf("'", pos + 2);
		}
		pos = nome.indexOf("\"");
		while (pos != -1) {
			if (pos < nome.length())
				nome = nome.substring(0, pos) + "´" + nome.substring(pos + 1);// troca
			// aspa
			// pelo
			// ´
			else
				nome = nome.substring(0, pos - 1);
			pos = nome.indexOf("\"", pos + 1);
		}
		return nome;
	}

	/**
	 * Retorna a string usada para INSERTS em campos de auto incremento nas
	 * tabelas do BD A String retornada deve ser usada na clausula VALUES Para o
	 * Oracle sera retornado a string para uma sequencia chamada idglobal.
	 * 
	 * @return
	 * @deprecated não é usado no sistema e sua descrição não é clara
	 */
	public static String strAutoIncr() {
		if (tipoSGBD == CT_TIPO_SGBD_ORACLE) {
			return "idglobal.nextval,";
		} else
			return "";
	}

	/**
	 * Retorna o nome do campo que sera usado em INSERTS em tabelas com campos
	 * auto incremento. A String retornada deve ser usada na relação de campos
	 * do INSERT
	 * 
	 * @param prefTabela
	 *            prefixo da tabela
	 * @return
	 * @deprecated não é usado no sistema e sua descrição não é clara
	 */
	public static String strAutoIncrCampo(String prefTabela) {
		if (tipoSGBD == CT_TIPO_SGBD_ORACLE) {
			return prefTabela + "_iddoobjeto, ";
		} else
			return "";
	}

	/**
	 * @return
	 * @deprecated não é usado no sistema
	 */
	public static int getTipoSGBD() {
		return tipoSGBD;
	}

	// public static void throwTipoCerto(Exception ex) throws Exception,
	// NaoExisteException, JaExisteException, RepositorioException {
	// if (ex instanceof NaoExisteException) {
	// throw new NaoExisteException(ex.getMessage());
	// }
	// if (ex instanceof JaExisteException) {
	// throw new JaExisteException(ex.getMessage());
	// }
	// if (ex instanceof RepositorioException) {
	// throw new RepositorioException(ex.getMessage());
	// }
	// throw ex;
	// }

	public static String dataDeHoje() throws Exception {
		Calendar cal = new GregorianCalendar();
		java.util.Date data = cal.getTime();
		return dataParaString(data);
	}

	public static Date dataDeHojeData() throws Exception {
		Calendar cal = new GregorianCalendar();
		java.util.Date data = cal.getTime();
		return data;
	}

	public static String dataHjExtenso() throws Exception {
		DateFormat dfmt = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");
		Date hoje = Calendar.getInstance(Locale.getDefault()).getTime();
		return dfmt.format(hoje);
	}

	public static String horaAtual() throws Exception {
		Calendar cal = new GregorianCalendar();
		String hora = cal.get(Calendar.HOUR_OF_DAY) + "";
		String min = cal.get(Calendar.MINUTE) + "";
		String seg = cal.get(Calendar.SECOND) + "";
		if (hora.length() == 1)
			hora = "0" + hora;
		if (min.length() == 1)
			min = "0" + min;
		if (seg.length() == 1)
			seg = "0" + seg;
		String horaat = hora + ":" + min + ":" + seg;
		return horaat;
	}

	/**
	 * Metodo para gerar uma string a partir de uma data. A string retornada tem
	 * o seguinte formato: YYYYMMDD
	 */
	public static String dataParaString(Date data) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		String ano = cal.get(Calendar.YEAR) + "";
		String mes = (cal.get(Calendar.MONTH) + 1) + ""; // porque month
		// comeca com zero
		String dia = cal.get(Calendar.DAY_OF_MONTH) + "";
		if (mes.length() == 1)
			mes = "0" + mes;
		if (dia.length() == 1)
			dia = "0" + dia;
		return dia + "/" + mes + "/" + ano;
	}

	/**
	 * Retorna a diferença entre as horas informadas em HH:MM:SS Se hr2 é maior
	 * que dt retorna o valor negativo(não leva em conta as horas e segundos)
	 */
	public static int[] calculaDifHoras(String hr1, String hr2, String data) throws Exception {
		int horas1 = Integer.parseInt(hr1.substring(0, 2));
		int min1 = Integer.parseInt(hr1.substring(3, 5));
		int seg1 = Integer.parseInt(hr1.substring(6, 8));

		int horas2 = Integer.parseInt(hr2.substring(0, 2));
		int min2 = Integer.parseInt(hr2.substring(3, 5));
		int seg2 = Integer.parseInt(hr2.substring(6, 8));

		int difH = 0;
		int difM = 0;
		int difS = 0;
		int difD = 0;

		if (horas2 >= horas1 && data.equals(Utilitaria.dataDeHoje())) {
			difH = horas2 - horas1;
			if (horas2 == horas1) {
				difM = min2 - min1;
			} else {
				difM = (60 - min1) + min2;
				if (difM > 60) {
					difM = difM - 60;
				} else {
					difH--;
				}
			}
		} else {

			difD = calculaDifDatasEmDias(dataDeHojeData(), convStringParaData(data));

			difH = (23 - horas1) + horas2;
			difM = (60 - min1) + min2;
			if (difM > 60) {
				difM = difM - 60;
				difH++;
			}
		}

		int[] difHoras = { difH, difM, difD };
		return difHoras;
	}

	/**
	 * Metodo para formatar uma string em formato CNPJ para o Banco.
	 */
	public static String CNPJParaJDBC(String cnpj) {

		String formatado = cnpj.substring(0, 2) + cnpj.substring(3, 6) + cnpj.substring(7, 10) + cnpj.substring(11, 15) + cnpj.substring(16, 18);
		return formatado;
	}

	/**
	 * Metodo para formatar uma string em formato CNPJ.
	 * 
	 * @param cnpj
	 * @return
	 * @deprecated desde 18/05/2006 por não atender ao padrão de nomes de
	 *             métodos (comecar com letra minúscula). Substituido por
	 *             <code>formataCnpj(String)</code>
	 */
	public static String FormataCNPJ(String cnpj) {
		return formataCnpj(cnpj);
	}

	/**
	 * Metodo para formatar uma string em formato CNPJ.
	 * 
	 * @param cnpj
	 * @return
	 * @author mborges
	 * @since 18/05/2006
	 */
	public static String formataCnpj(String cnpj) {
		// Cópia do conteúdo antigo de FormataCNPJ(String)
		String formatado = "";
		if (cnpj != null && !cnpj.trim().equals("") && cnpj.trim().length() >= 14) {
			try {
				formatado = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-"
						+ cnpj.substring(12, 14);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return formatado;
		} else
			return formatado;
	}

	/**
	 * Converte um iterator em uma lista
	 */
	public static List toList(Iterator iter) {
		if (iter == null) {
			return null;
		}
		List list = new ArrayList();
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		return list;
	}

	/**
	 * Converte um iterator em um Vector
	 */
	public static Vector toVector(Iterator iter) {
		if (iter == null) {
			return null;
		}
		Vector vector = new Vector();
		while (iter.hasNext()) {
			vector.add(iter.next());
		}
		return vector;
	}

	/**
	 * Verifica se mesAno é valido
	 * 
	 * @param mesAno
	 *            - formato
	 * @return boolean - false se não estiver no format especificado e true se
	 *         estiver no format especificado
	 */
	public static boolean isMesAno(String mesAno) {
		String pattern = "MMyyyy";
		if (mesAno == null || mesAno.length() != pattern.length()) {
			return false;
		}
		SimpleDateFormat formato = new SimpleDateFormat(pattern);
		formato.setLenient(false);
		try {
			formato.parse(mesAno);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Verifica se anoMes é valido
	 * 
	 * @param anoMes
	 *            - formato
	 * @return boolean - false se não estiver no format especificado e true se
	 *         estiver no format especificado
	 */
	public static boolean isAnoMes(String anoMes) {
		if (anoMes == null) {
			return false;
		}
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMM");
		formato.setLenient(false);
		try {
			formato.parse(anoMes);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Verifica se anoMes é valido
	 * 
	 * @param anoMes
	 *            - formato
	 * @return boolean - false se não estiver no format especificado e true se
	 *         estiver no format especificado
	 */
	public static boolean isAnoMes(int anoMes) {
		return isAnoMes(String.valueOf(anoMes));
	}

	/**
	 * Valida e Formata MesAno. Corresponde a uma chamada ao método
	 * <code>formatMesAno(String, boolean)</code> com os argumentos
	 * <code>mesAno</code> e <code>true</code>.
	 * 
	 * @param mesAno
	 * @return String - retorna uma string no formato mm/aaaa
	 * @throws FormatoInadequadoException
	 *             caso o parametro informado não seja valido
	 */
	public static String formatMesAno(String mesAno) throws FormatoInadequadoException {
		try {
			return formatMesAno(mesAno, true);
		} catch (NullPointerException e) {
			throw new FormatoInadequadoException(e.getMessage());
		} catch (FormatoInadequadoException e) {
			throw e;
		}
	}

	/**
	 * Formata MesAno
	 * 
	 * @param mesAno
	 * @param validar
	 *            indica se o mes ano deve ser validado também
	 * @return String - retorna uma string no formato mm/aaaa
	 * @throws NullPointerException
	 *             caso o parametro informado seja <code>null</code>
	 * @throws FormatoInadequadoException
	 *             caso o parametro informado não seja valido
	 */
	public static String formatMesAno(String mesAno, boolean validar) throws NullPointerException, FormatoInadequadoException {
		if (mesAno == null) {
			throw new NullPointerException("O mes ano nao pode ser null!");
		} else if (validar && !isMesAno(mesAno)) {
			throw new FormatoInadequadoException("O formato MesAno inválido!");
		}

		return mesAno.subSequence(0, 2) + "/" + mesAno.subSequence(2, 6);
	}

	public static String formatAnoMesEmMesAno(String anoMes, boolean validar) throws NullPointerException, FormatoInadequadoException {
		if (anoMes != null) {
			anoMes = anoMes.replaceAll("/", "");
		} else {
			throw new NullPointerException("O AnoMes não pode ser null!");
		}
		if (validar && !isAnoMes(anoMes)) {
			throw new FormatoInadequadoException("O formato AnoMes é inválido!");
		}

		return anoMes.subSequence(4, 6) + "/" + anoMes.subSequence(0, 4);
	}

	/**
	 * Converte um ValorMesAno para formato do banco
	 * 
	 * @param mesAno
	 *            - valor no formato mmaaaa
	 * @return string no formato aaaamm
	 */
	public static String convMesAnoJDBC(String mesAno) {
		if (mesAno == null) {
			return null;
		}
		return mesAno.subSequence(2, 6) + "" + mesAno.subSequence(0, 2);
	}

	/**
	 * Converte um mesAno para formato do banco
	 * 
	 * @param mesAno
	 *            <code>String</code> no formato mmaaaa
	 * @return <code>String</code> no formato aaaamm
	 * @author mborges
	 * @since 03/02/2006
	 */
	public static String convMesAnoParaAnoMes(String mesAno) {
		if (mesAno == null || mesAno.length() != 6) {
			return null;
		}
		return mesAno.substring(2) + mesAno.substring(0, 2);
	}

	/**
	 * Converte um mesAno para formato do banco
	 * 
	 * @param mesAno
	 *            <code>String</code> no formato mmaaaa
	 * @return <code>int</code> no formato aaaamm
	 * @author mborges
	 * @since 03/02/2006
	 */
	public static int convMesAnoParaAnoMesInt(String mesAno) {
		if (mesAno == null || mesAno.length() != 6) {
			return 0;
		}
		return Integer.parseInt(mesAno.substring(2) + mesAno.substring(0, 2));
	}

	/**
	 * Converte um anoMes para formato do banco
	 * 
	 * @param mesAno
	 *            <code>String</code> no formato aaaamm
	 * @return <code>String</code> no formato mmaaaa
	 * @author mborges
	 * @since 03/02/2006
	 */
	public static String convAnoMesParaMesAno(int anoMes) {
		String mes = FORMAT_DUAS_CASAS_SEM_VIRGULA.format(anoMes % 100);
		String ano = FORMAT_DUAS_CASAS_SEM_VIRGULA.format(anoMes / 100);
		return mes + ano;
	}

	/**
	 * Retorna um calendar setado com o primeiro dia do mes/ano especificado.
	 * 
	 * @param mesAno
	 * @return
	 * @throws FormatoInadequadoException
	 * @author mborges
	 */
	public static Calendar convMesAnoParaCalendar(String mesAno) throws FormatoInadequadoException {
		if (mesAno == null || !isMesAno(mesAno)) {
			throw new FormatoInadequadoException("O formato MesAno inválido!");
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, (Integer.parseInt(mesAno.substring(0, 2)) - 1));
			calendar.set(Calendar.YEAR, Integer.parseInt(mesAno.substring(2)));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

			return calendar;
		}
	}

	/**
	 * Retorna um calendar setado com o primeiro dia do ano/mes especificado.
	 * 
	 * @param anoMes
	 *            exemplo: 201104 ==> Equivale a 01/04/2011
	 */
	public static Calendar convAnoMesParaCalendar(String anoMes) throws FormatoInadequadoException {
		if (anoMes == null || !isAnoMes(anoMes)) {
			throw new FormatoInadequadoException("O formato AnoMes é inválido!");
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, (Integer.parseInt(anoMes.substring(4)) - 1));
			calendar.set(Calendar.YEAR, Integer.parseInt(anoMes.substring(0, 4)));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

			return calendar;
		}
	}

	public static Date convMesAnoParaDate(String mesAno) throws FormatoInadequadoException {
		return convMesAnoParaCalendar(mesAno).getTime();
	}

	public static Date convAnoMesParaDate(String anoMes) throws FormatoInadequadoException {
		return convAnoMesParaCalendar(anoMes).getTime();
	}

	public static String converteCalendarParaAnoMes(Calendar calendar) {
		return FORMAT_QUATRO_CASAS_SEM_VIRGULA.format(calendar.get(Calendar.YEAR)) + FORMAT_DUAS_CASAS_SEM_VIRGULA.format(calendar.get(Calendar.MONTH) + 1);
	}

	public static String converteDateParaAnoMes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return converteCalendarParaAnoMes(calendar);
	}

	public static String converteCalendarParaMesAno(Calendar calendar) {
		return FORMAT_DUAS_CASAS_SEM_VIRGULA.format(calendar.get(Calendar.MONTH) + 1) + FORMAT_QUATRO_CASAS_SEM_VIRGULA.format(calendar.get(Calendar.YEAR));
	}

	public static String converteDateParaMesAno(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return converteCalendarParaMesAno(calendar);
	}

	/**
	 * Compara os dois argumentos para ordenação. Retorna um inteiro negativo,
	 * zero ou um inteiro positivo respectivamente se o primeiro argumento é
	 * menor que, igual ou maior que o segundo.
	 * 
	 * A relação entre os argumentos é transitiva: se
	 * <code>comparaMesAno(x, y) > 0</code> e
	 * <code>comparaMesAno(y, z) > 0</code>, isso implica que
	 * <code>comparaMesAno(x, z) > 0</code>.
	 * 
	 * Finalmente, este método assegura que
	 * <code>comparaMesAno(x, y) == 0</code> implica que o sinal de
	 * <code>comparaMesAno(x, z)</code> será sempre o mesmo de
	 * <code>comparaMesAno(y, z)</code>, para qualquer <code>z</code>
	 * 
	 * @param mesAno1
	 *            o primeiro mesAno (mmyyyy ou mm/yyyy) para comparar
	 * @param mesAno2
	 *            o segundo mesAno (mmyyyy ou mm/yyyy) para comparar
	 * @return um inteiro negativo, zero ou um inteiro positivo respectivamente
	 *         se o primeiro argumento é menor que, igual ou maior que o
	 *         segundo.
	 * @throws FormatoInadequadoException
	 *             caso algum dos argumentos não esteja no formato mmyyyy ou no
	 *             formato mm/yyyy.
	 * @author mborges
	 * @since 02/03/2006
	 */
	public static int comparaMesAno(String mesAno1, String mesAno2) throws FormatoInadequadoException {
		int anoMes1;
		int anoMes2;
		try {
			anoMes1 = Integer.parseInt(convMesAnoParaAnoMes(mesAno1.replaceAll("[\\p{Punct}]", "")));
			anoMes2 = Integer.parseInt(convMesAnoParaAnoMes(mesAno2.replaceAll("[\\p{Punct}]", "")));
		} catch (Exception e) {
			throw new FormatoInadequadoException("Formato MesAno inválido!");
		}

		return anoMes1 - anoMes2;
	}

	/**
	 * Compara a diferença do primeiro campo com o formato de (MM/yyyy) ou
	 * (MMyyyy) em relação ao segundo campo com o formato de (MM/yyyy) ou
	 * (MMyyyy).
	 * 
	 * 
	 * @param String
	 *            mesAno1 O primeiro mesAno (MMyyyy ou MM/yyyy) para comparar
	 * @param String
	 *            mesAno2 O segundo mesAno (MMyyyy ou MM/yyyy) para comparar
	 * @return um inteiro positivo respectivamente se o primeiro argumento é
	 *         menor ou igual ao segundo e se for maior retorna negativo.
	 * @throws FormatoInadequadoException
	 *             caso algum dos argumentos não esteja no formato mmyyyy ou no
	 *             formato mm/yyyy.
	 * @return Int Meses
	 * @author Lucas Siqueira
	 * @since 21/06/2011
	 */
	public static int comparaMesAnoRetornaMes(String mesAno1, String mesAno2) throws FormatoInadequadoException, IllegalArgumentException {
		int mes1 = 0, mes2 = 0, ano1 = 0, ano2 = 0, result = 0;
		mesAno1 = mesAno1.replaceAll("[\\p{Punct}]", "");
		mesAno2 = mesAno2.replaceAll("[\\p{Punct}]", "");

		try {
			if (isMesAno(mesAno1) && isMesAno(mesAno2)) {
				mes1 = Integer.parseInt(mesAno1.substring(0, 2));
				mes2 = Integer.parseInt(mesAno2.substring(0, 2));

				ano1 = Integer.parseInt(mesAno1.substring(2, 6));
				ano2 = Integer.parseInt(mesAno2.substring(2, 6));
				result = ((ano2 - ano1) * 12) + (mes2 - mes1);
			} else {
				result = 0;
			}

		} catch (Exception e) {
			System.out.println("Formato MesAno inválido!");
			throw new FormatoInadequadoException("Formato MesAno inválido!");
		}

		return result;
	}

	/**
	 * Lista, em ordem, todos os mesAno (MMyyyy) a partir do primeiro argumento
	 * (inclusive), que deve ser um mesAno (MMyyyy ou MM/yyyy), até o segundo
	 * argumento, também um mesAno. O terceiro argumento especifica se o segundo
	 * argumento deve ser incluído na lista ou não
	 * 
	 * @param aPartirDe
	 *            primeiro mês ano da lista
	 * @param ate
	 *            limite final para a lista
	 * @param incluirUltimo
	 *            indica se o limite final para a lista deve ser incluido ou nao
	 *            na lista.
	 * @return um <code>java.util.List</code> de <code>java.lang.String</code>
	 *         no formato MMyyyy (mesAno)
	 * @author mborges
	 * @throws FormatoInadequadoException
	 *             caso algum dos mesAno informado nao estiverem no formato
	 *             correto.
	 * @since 03/05/2006
	 */
	public static List<String> listarMesAno(String aPartirDe, String ate, boolean incluirUltimo) throws FormatoInadequadoException {
		if (aPartirDe == null || ate == null) {
			throw new FormatoInadequadoException("Formato MesAno inválido!");
		}
		aPartirDe = aPartirDe.replaceAll("/", "");
		ate = ate.replaceAll("/", "");
		if (!isMesAno(aPartirDe) || !isMesAno(ate)) {
			throw new FormatoInadequadoException("Formato MesAno inválido!!");
		}

		int aPartirDeMes;
		int aPartirDeAno;
		int ateMes;
		int ateAno;
		try {
			aPartirDeMes = Integer.parseInt(aPartirDe.substring(0, 2));
			aPartirDeAno = Integer.parseInt(aPartirDe.substring(2));
			ateMes = Integer.parseInt(ate.substring(0, 2));
			ateAno = Integer.parseInt(ate.substring(2));
		} catch (NumberFormatException e) {
			throw new FormatoInadequadoException("Formato MesAno inválido!!!");
		}

		List<String> retorno = new ArrayList<String>();
		for (int mes = aPartirDeMes, ano = aPartirDeAno; ano < ateAno || (ano == ateAno && mes < ateMes); mes++) {
			retorno.add(FORMAT_DUAS_CASAS_SEM_VIRGULA.format(mes) + FORMAT_QUATRO_CASAS_SEM_VIRGULA.format(ano));

			if (mes == 12) {
				mes = 0;
				ano++;
			}
		}

		if (incluirUltimo) {
			retorno.add(ate);
		}

		return retorno;
	}

	/**
	 * Lista, em ordem, todos os mesAno (incluindo o mes 13 para indicar o 13º)
	 * (MMyyyy) a partir do primeiro argumento (inclusive), que deve ser um
	 * mesAno (MMyyyy ou MM/yyyy), até o segundo argumento, também um mesAno. O
	 * terceiro argumento especifica se o segundo argumento deve ser incluído na
	 * lista ou não
	 * 
	 * @param aPartirDe
	 *            primeiro mês ano da lista
	 * @param ate
	 *            limite final para a lista
	 * @param incluirUltimo
	 *            indica se o limite final para a lista deve ser incluido ou nao
	 *            na lista.
	 * @return um <code>java.util.List</code> de <code>java.lang.String</code>
	 *         no formato MMyyyy (mesAno)
	 * @author mborges
	 * @throws FormatoInadequadoException
	 *             caso algum dos mesAno informado nao estiverem no formato
	 *             correto.
	 * @since 03/05/2006
	 */
	public static List<String> listarMesAnoCom13(String aPartirDe, String ate, boolean incluirUltimo) throws FormatoInadequadoException {
		if (aPartirDe == null || ate == null) {
			throw new FormatoInadequadoException("Formato MesAno inválido!");
		}
		aPartirDe = aPartirDe.replaceAll("/", "");
		ate = ate.replaceAll("/", "");
		if (!isMesAno(aPartirDe) || !isMesAno(ate)) {
			throw new FormatoInadequadoException("Formato MesAno inválido!!");
		}

		int aPartirDeMes;
		int aPartirDeAno;
		int ateMes;
		int ateAno;
		try {
			aPartirDeMes = Integer.parseInt(aPartirDe.substring(0, 2));
			aPartirDeAno = Integer.parseInt(aPartirDe.substring(2));
			ateMes = Integer.parseInt(ate.substring(0, 2));
			ateAno = Integer.parseInt(ate.substring(2));
		} catch (NumberFormatException e) {
			throw new FormatoInadequadoException("Formato MesAno inválido!!!");
		}

		List<String> retorno = new ArrayList<String>();
		for (int mes = aPartirDeMes, ano = aPartirDeAno; ano < ateAno || (ano == ateAno && mes < ateMes); mes++) {
			retorno.add(FORMAT_DUAS_CASAS_SEM_VIRGULA.format(mes) + FORMAT_QUATRO_CASAS_SEM_VIRGULA.format(ano));

			if (mes == 12) {
				retorno.add("13" + FORMAT_QUATRO_CASAS_SEM_VIRGULA.format(ano));
				mes = 0;
				ano++;
			}
		}

		if (incluirUltimo) {
			retorno.add(ate);
			if (ateMes == 12)
				retorno.add("13" + FORMAT_QUATRO_CASAS_SEM_VIRGULA.format(ateAno));
		}

		return retorno;
	}

	public static String cepComMascara(String cep) {
		if (cep.length() == 8) {
			cep = cep.substring(0, 2) + "." + cep.substring(2, 5) + "-" + cep.substring(5, 8);
		}
		return cep;
	}

	/**
	 * Converte um Valor anoMes para mesAno
	 * 
	 * @param anoMes
	 *            - valor no formato aaaamm
	 * @return string no formato mmaaaa
	 */
	public static String convAnoMesParaMesAno(String anoMes) {
		if (anoMes == null) {
			return null;
		}
		return anoMes.subSequence(4, 6) + "" + anoMes.subSequence(0, 4);
	}

	/**
	 * Converte um SortedSet <b>contendo apenas objetos do tipo ChaveValor</b>
	 * para um LinkedHashSet, mantendo a ordem dos elementos no SortedSet.
	 * 
	 * @param sortedSet
	 *            contendo apenas elementos do tipo <code>ChaveValor<code>.
	 * @return um LinkedHashMap onde a chave é um Integer encapsulando a chave
	 *         de um objeto <code>ChaveValor<code> e o valor é o String que é o
	 *         atributo valor de <code>ChaveValor<code>.
	 * @see com.evol.seg.util.ChaveValor
	 * @author mborges
	 * @since 30-03-2005
	 */
	// public static LinkedHashMap converterSortedSetParaLinkedHashMap(SortedSet
	// sortedSet) {
	// LinkedHashMap linkedHashMap = new LinkedHashMap(sortedSet.size());
	// Iterator iter = sortedSet.iterator();
	// while (iter.hasNext()) {
	// ChaveValor elemento = (ChaveValor) iter.next();
	// linkedHashMap.put(new Integer(elemento.getChave()), elemento.getValor());
	// }
	// return linkedHashMap;
	// }

	/**
	 * Clona um <code>java.util.Set</code>: cria um novo <code>Set</code> do
	 * mesmo tipo do original e copia as referências a seus elementos. A cópia é
	 * superficial, ou seja, copia apenas as referências contidas no
	 * <code>Set</code> original e não os objetos em si.
	 * 
	 * @param aClonar
	 *            o <code>Set</code> a ser copiado.
	 * @return uma cópia superficial do <code>Set</code> passado como argumento,
	 *         do mesmo tipo dele.
	 * @throws NullPointerException
	 *             caso o argumento passado seja <code>null</code>.
	 * @throws ConcurrentModificationException
	 *             pode ser lançada caso o <code>Set</code> tenha um
	 *             <i>fail-fast</i> <code>Iterator</code> e seja alterado
	 *             durante a cópia.
	 * @throws InstantiationException
	 *             caso a classe concreta do argumento não tenha um construtor
	 *             sem argumentos ou caso haja algum tipo de problema na hora de
	 *             instanciar o novo <code>Set</code>.
	 * @throws IllegalAccessException
	 *             caso a classe concreta do argumento ou seu construtor sem
	 *             argumentos não esteja acessível à classe
	 *             com.evol.seg.util.Utilitaria (sendo <code>private</code> por
	 *             exemplo)
	 * @throws ExceptionInInitializerError
	 *             se a inicialização da classe concreta do argumento falhar.
	 * @throws SecurityException
	 *             se não houver permissão para se criar um novo objeto da
	 *             classe concreta do argumento.
	 * @author mborges
	 * @since 02/03/2006
	 */
	public static Set clonarSet(Set aClonar) throws NullPointerException, ConcurrentModificationException, InstantiationException, IllegalAccessException,
			ExceptionInInitializerError, SecurityException {

		if (aClonar == null) {
			throw new NullPointerException();
		}

		Class classe = aClonar.getClass();
		Set clone = null;

		clone = (Set) classe.newInstance();

		Iterator iterator = aClonar.iterator();
		while (iterator.hasNext()) {
			Object element = iterator.next();
			clone.add(element);
		}

		return clone;
	}

	public static Object clonarSerializable(Serializable obj) {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bout);
			out.writeObject(obj);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			in = new ObjectInputStream(bin);
			Object copy = in.readObject();
			in.close();
			return copy;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ignore) {
			}
		}
		return null;
	}

	/**
	 * Clona um <code>java.util.List</code>: cria um novo <code>List</code> do
	 * mesmo tipo do original e copia as referências a seus elementos. A cópia é
	 * completa, ou seja, clona todos os objetos contidos no <code>List</code>
	 * original.
	 * 
	 * @param aClonar
	 *            o <code>List</code> a ser copiado.
	 * @return uma cópia completa do <code>List</code> passado como argumento,
	 *         do mesmo tipo dele.
	 * @throws NullPointerException
	 *             caso o argumento passado seja <code>null</code>.
	 * @throws ConcurrentModificationException
	 *             pode ser lançada caso o <code>List</code> tenha um
	 *             <i>fail-fast</i> <code>Iterator</code> e seja alterado
	 *             durante a cópia.
	 * @throws InstantiationException
	 *             caso a classe concreta do argumento não tenha um construtor
	 *             sem argumentos ou caso haja algum tipo de problema na hora de
	 *             instanciar o novo <code>List</code>.
	 * @throws IllegalAccessException
	 *             caso a classe concreta do argumento ou seu construtor sem
	 *             argumentos não esteja acessível à classe
	 *             com.evol.seg.util.Utilitaria (sendo <code>private</code> por
	 *             exemplo)
	 * @throws ExceptionInInitializerError
	 *             se a inicialização da classe concreta do argumento falhar.
	 * @throws SecurityException
	 *             se não houver permissão para se criar um novo objeto da
	 *             classe concreta do argumento.
	 * @throws CloneNotSupportedException
	 *             caso algum elemento contido no <code>List</code> lance essa
	 *             excecao ou o metodo clone nao possa ser executado.
	 * @author mborges
	 * @since 04/09/2006
	 */
	public static List clonarList(List aClonar) throws NullPointerException, ConcurrentModificationException, InstantiationException, IllegalAccessException,
			ExceptionInInitializerError, SecurityException, CloneNotSupportedException {

		if (aClonar == null) {
			throw new NullPointerException();
		}

		Class classe = aClonar.getClass();
		List clone = null;

		clone = (List) classe.newInstance();

		Iterator iterator = aClonar.iterator();
		while (iterator.hasNext()) {
			Object element = iterator.next();

			Class classeElement = element.getClass();
			try {
				Method metodoClone = classeElement.getDeclaredMethod("clone", new Class[0]);
				element = metodoClone.invoke(element, new Object[0]);
			} catch (Exception e) {
				throw new CloneNotSupportedException("Nao foi possivel encontrar ou utilizar o metodo clone() na classe " + classeElement.getName());
			}

			clone.add(element);
		}

		return clone;
	}

	/**
	 * Arredonda o número dado para cima.
	 * 
	 * @param numero
	 *            um <code>double</code>
	 * @return o menor <code>int</code> que é maior que o numero passado como
	 *         argumento
	 */
	public static int arredondarParaCima(double numero) {
		return (int) Math.ceil(numero);
	}

	/**
	 * Arredonda o número dado para baixo.
	 * 
	 * @param numero
	 *            um <code>double</code>
	 * @return o maior <code>int</code> que é menor que o numero passado como
	 *         argumento
	 */
	public static int arredondarParaBaixo(double numero) {
		return (int) Math.floor(numero);
	}

	/**
	 * Arredonda o número dado para o inteiro mais próximo.
	 * 
	 * @param numero
	 *            um <code>double</code>
	 * @return o menor <code>int</code> que é mais próximo do numero passado
	 *         como argumento. É o mesmo que somar <code>0.5</code> ao argumento
	 *         e depois arredondar o resultado para baixo.
	 */
	public static int arredondar(double numero) {
		return (int) Math.round(numero);
	}

	/**
	 * Trunca o número dado.
	 * 
	 * @param numero
	 *            um <code>double</code>
	 * @return o a parte inteira do numero passado como argumento,
	 *         desconsiderando sua parte decimal.
	 */
	public static int truncar(double numero) {
		return (int) numero;
	}

	/**
	 * Escreve a <code>String</code> passada como argumento no
	 * <code>System.out</code>, colocando como prefixo o contexo da aplicação e
	 * a data atual. O formato do log é o seguinte: <b><i>[contexo]
	 * dd-MM-yyyy_HH.mm.ss.SSS_<code>StackTraceElement</code> :</i>
	 * <code>log</code></b>. Tem o mesmo efeito que a chamada
	 * <code>Utilitaria.log(System.out, log, printMethodName);</code>
	 * 
	 * @param log
	 *            a <code>String</code> a ser logada.
	 * @param printMethodName
	 *            indica se o nome do método, mais precisamente, o
	 *            <code>StackTraceElement</code> do método que pediu o log deve
	 *            ser impresso com o log (logo após a data).
	 * @author mborges
	 * @since 03/03/2006
	 */
	// public static void log(String log, boolean printMethodName) {
	// log(System.out, log, printMethodName);
	// }

	/**
	 * Escreve a <code>String</code> passada como argumento no
	 * <code>System.out</code>, colocando como prefixo o contexo da aplicação e
	 * a data atual. O formato do log é o seguinte: <b><i>[contexo]
	 * dd-MM-yyyy_HH.mm.ss.SSS :</i> <code>log</code></b>. Tem o mesmo efeito
	 * que a chamada <code>Utilitaria.log(System.out, log, false);</code>
	 * 
	 * @param log
	 *            a <code>String</code> a ser logada.
	 * @author mborges
	 * @since 03/03/2006
	 */
	// public static void log(String log) {
	// log(System.out, log, false);
	// }

	/**
	 * Escreve a <code>String</code> passada como argumento no
	 * <code>PrintStream</code> também passado, colocando como prefixo o contexo
	 * da aplicação e a data atual. O formato do log é o seguinte:
	 * <b><i>[contexo] dd-MM-yyyy_HH.mm.ss.SSS :</i> <code>log</code></b> Tem o
	 * mesmo efeito que a chamada <code>Utilitaria.log(out, log, false);</code>
	 * 
	 * @param out
	 *            o <code>PrintStream</code> onde o log deve ser escrito
	 * @param log
	 *            a <code>String</code> a ser logada.
	 * @author mborges
	 * @since 03/03/2006
	 */
	// public static void log(PrintStream out, String log) {
	// log(out, log, false);
	// }

	/**
	 * Escreve a <code>String</code> passada como argumento no
	 * <code>PrintStream</code> também passado, colocando como prefixo o contexo
	 * da aplicação e a data atual. O formato do log é o seguinte:
	 * <b><i>[contexo] dd-MM-yyyy_HH.mm.ss.SSS_<code>StackTraceElement</code>
	 * :</i> <code>log</code></b>
	 * 
	 * @param out
	 *            o <code>PrintStream</code> onde o log deve ser escrito
	 * @param log
	 *            a <code>String</code> a ser logada.
	 * @param printMethodName
	 *            indica se o nome do método, mais precisamente, o
	 *            <code>StackTraceElement</code> do método que pediu o log deve
	 *            ser impresso com o log (logo após a data).
	 * @author mborges
	 * @since 03/03/2006
	 */
	// public static void log(PrintStream out, String log, boolean
	// printMethodName) {
	// if (printMethodName) {
	// StackTraceElement[] stack = new Exception().getStackTrace();
	// int index = 1;
	//
	// for (int i = 1; i < stack.length; i++) {
	// if (stack[i].getClassName().equals(Utilitaria.class.getName())) {
	// if (stack[i].getMethodName().indexOf("log") != -1) {
	// index++;
	// }
	// } else {
	// break;
	// }
	// }
	//
	// out.println(LOG_PREFIXO + " " + LOG_DATE_FORMAT.format(new Date()) + "_"
	// + stack[index] + " : " + log);
	// } else {
	// out.println(LOG_PREFIXO + " " + LOG_DATE_FORMAT.format(new Date()) +
	// " : " + log);
	// }
	// }

	/**
	 * Formata um cpf de acordo com a máscara <b>xxx.xxx.xxx-xx</b>.<br />
	 * Para isso, primeiro retira quaisquer pontos, traços ou sinais de
	 * pontuação do cpf. Caso não seja possível formatar o cpf, o método retorna
	 * o próprio argumento passado.
	 * 
	 * @param cpf
	 *            um cpf para ser formatado
	 * @return o cpf formatado de acordo com com a máscara <b>xxx.xxx.xxx-xx</b>
	 *         ou o próprio argumento <code>cpf</code>, caso a formatação não
	 *         seja possível.
	 */
	public static String formataCpf(String cpf) {
		try {
			StringBuffer cpfFormatado = new StringBuffer(14);
			char[] cpfChars = cpf.replaceAll("[\\p{Punct}]", "").toCharArray();
			cpfFormatado.append(cpfChars, 0, 3);
			cpfFormatado.append('.');
			cpfFormatado.append(cpfChars, 3, 3);
			cpfFormatado.append('.');
			cpfFormatado.append(cpfChars, 6, 3);
			cpfFormatado.append('-');
			cpfFormatado.append(cpfChars, 9, 2);
			return cpfFormatado.toString();
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("Retornando o proprio cpf nao formatado: '" +
			// cpf + "'");
			return cpf;
		}
	}

	/**
	 * Este método recebe uma <code>String</code> como parâmetro para gerar um
	 * objeto da classe <code>Long</code>. Se essa String for inválida ele
	 * retorna <code>null</code>
	 */
	public static Long convStringParaLong(String numero) {
		if (numero != null && numero.length() != 0) {
			try {
				return new Long(numero);
			} catch (NumberFormatException ex) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Este método recebe uma <code>String</code> como parâmetro para gerar um
	 * objeto da classe <code>Integer</code>. Se essa String for inválida ele
	 * retorna <code>null</code>
	 */
	public static Integer convStringParaInteger(String numero) {
		if (numero != null && numero.length() != 0) {
			try {
				return new Integer(numero);
			} catch (NumberFormatException ex) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Calcula a idade da pessoa até a data de referencia. Assume que nenhuma
	 * data é <code>null</code>.
	 * 
	 * @param dataReferencia
	 *            data na qual se quer saber a idade
	 * @param dataNascimento
	 *            data de nascimento da pessoa
	 * @return a idade na data de referencia
	 */
	public static int calculaIdade(Date dataReferencia, Date dataNascimento) {
		Calendar referencia = Calendar.getInstance();
		referencia.setTime(dataReferencia);
		Calendar nascimento = Calendar.getInstance();
		nascimento.setTime(dataNascimento);

		// Idade baseada na diferença dos anos
		int idade = referencia.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);

		// Adicionar a suposta idade à data de nascimento
		nascimento.add(Calendar.YEAR, idade);

		// Se o aniversário desse ano não tiver acontecido ainda, subtrair um da
		// idade suposta
		if (referencia.before(nascimento)) {
			idade--;
		}

		return idade;
	}

	/**
	 * Calcula a data na qual a idade passada como parâmetro será atingida,
	 * considerando a data de nascimento passada.
	 * 
	 * @param dataNascimento
	 * @param idade
	 * @return data na qual a idade será atingida
	 * @author mborges
	 * @since 26/04/2007
	 */
	public static Date calculaDataParaIdade(Date dataNascimento, int idade) {
		return somar(dataNascimento, 0, 0, idade);
	}

	/**
	 * Este método é responsável por extrair algumas informações sobre o
	 * endereço de uma pessoa quando está não possui o endereço armazenado de
	 * forma correta. Ele trata os casos em que o endereço da pessoa está todo
	 * no complemento do endereço.
	 * 
	 * @param complemento
	 * @return Um array <code>nulo</code> se não conseguir seguir o padrão ou um
	 *         array com as informações sobre o endereço contidas no
	 *         complemento. Se o array for diferente de <code>null</code> as
	 *         informações estarão organizadas da seguinte forma: <br>
	 *         - <code>array[0] -> (rua, numero, quadra,...)</code><br>
	 *         - <code>array[1] -> (nome bairro)</code><br>
	 *         - <code>array[2] -> (cep)</code><br>
	 *         - <code>array[3] -> (nome município)</code><br>
	 *         - <code>array[4] -> (nome estado)</code><br>
	 */
	public static String[] extraiInformacoesDoEnderecoPeloComplemento(String complemento) {
		String[] informacoesEndereco = null;
		if (complemento != null) {
			if (complemento.indexOf("@@$") != -1) {
				informacoesEndereco = complemento.split("@@\\$");
			}
		}
		return informacoesEndereco;
	}

	/**
	 * Retorna o numero da certidão completo
	 * 
	 * @param numeroCertidao
	 * @param anoCertidao
	 * @return
	 */
	public static String montaNumeroCertidao(int numeroCertidao, int anoCertidao) {
		return numeroCertidao + "/" + anoCertidao;
	}

	/**
	 * Concatena dois ou mais arquivos pdf em um novo arquivo pdf. Funciona como
	 * uma chamada
	 * <code>Utilitaria.concatenarPdfs(arquivosOrigem, arquivoDestino, false);</code>
	 * 
	 * @param arquivosOrigem
	 *            array contendo os arquivos a serem concatenados
	 * @param arquivoDestino
	 *            caminho para o novo pdf a ser gerado
	 * @throws FileNotFoundException
	 *             caso algum dos arquivos de origem nao exista ou nao seja um
	 *             arquivo valido
	 * @throws IOException
	 *             caso haja falha na leitura de algum dos arquivos ou na
	 *             escrita do novo arquivo
	 * @throws DocumentException
	 *             caso haja falha ao montar no novo documento pdf.
	 * @throws IllegalArgumentException
	 *             caso algum argumento seja <code>null</code> ou nao existam
	 *             pelo menos dois arquivos a serem concatenados.
	 * @author mborges (baseado no tutorial do iText)
	 * @since 28/03/2006
	 */
	// public static void concatenarPdfs(File[] arquivosOrigem, File
	// arquivoDestino) throws FileNotFoundException, IOException,
	// DocumentException,
	// IllegalArgumentException {
	// concatenarPdfs(arquivosOrigem, arquivoDestino, false);
	// }

	/**
	 * Concatena dois ou mais arquivos pdf em um novo arquivo pdf.
	 * 
	 * @param arquivosOrigem
	 *            array contendo os arquivos a serem concatenados
	 * @param arquivoDestino
	 *            caminho para o novo pdf a ser gerado
	 * @param log
	 *            se <code>true</code> loga na saida padrao as atividades da
	 *            copia do arquivo.
	 * @throws FileNotFoundException
	 *             caso algum dos arquivos de origem nao exista ou nao seja um
	 *             arquivo valido
	 * @throws IOException
	 *             caso haja falha na leitura de algum dos arquivos ou na
	 *             escrita do novo arquivo
	 * @throws DocumentException
	 *             caso haja falha ao montar no novo documento pdf.
	 * @throws IllegalArgumentException
	 *             caso algum argumento seja <code>null</code> ou nao existam
	 *             pelo menos dois arquivos a serem concatenados.
	 * @author mborges (baseado no tutorial do iText)
	 * @since 28/03/2006
	 */
	// public static void concatenarPdfs(File[] arquivosOrigem, File
	// arquivoDestino, boolean log) throws FileNotFoundException, IOException,
	// DocumentException,
	// IllegalArgumentException {
	// if (arquivosOrigem == null) {
	// throw new
	// IllegalArgumentException("O array de arquivos de origem nao pode ser
	// null");
	// } else if (arquivosOrigem.length < 2) {
	// throw new
	// IllegalArgumentException("O array de arquivos de origem nao pode ter
	// menos que 2 elementos");
	// } else if (arquivoDestino == null) {
	// throw new
	// IllegalArgumentException("O arquivo de destino nao pode ser null");
	// }
	//
	// int pageOffset = 0;
	// ArrayList master = new ArrayList();
	// String outFile = arquivoDestino.getAbsolutePath();
	// Document document = null;
	// PdfCopy writer = null;
	// for (int f = 0; f < arquivosOrigem.length; f++) {
	// if (log) {
	// log("Processando arquivo: " + arquivosOrigem[f].getAbsolutePath());
	// }
	//
	// if (!arquivosOrigem[f].exists()) {
	// throw new FileNotFoundException("Impossivel encontrar arquivo: " +
	// arquivosOrigem[f].getAbsolutePath());
	// } else if (!arquivosOrigem[f].isFile()) {
	// throw new FileNotFoundException("Arquivo invalido: " +
	// arquivosOrigem[f].getAbsolutePath());
	// }
	//
	// // Pegamos o Reader para um dos documentos de entrada
	// PdfReader reader = new PdfReader(arquivosOrigem[f].getAbsolutePath());
	// reader.consolidateNamedDestinations();
	// // Descobrimos o numero total de paginas
	// int n = reader.getNumberOfPages();
	// List bookmarks = SimpleBookmark.getBookmark(reader);
	// if (bookmarks != null) {
	// if (pageOffset != 0) {
	// SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
	// }
	// master.addAll(bookmarks);
	// }
	// pageOffset += n;
	//
	// if (log) {
	// log("Existem " + n + " paginas em: " + arquivosOrigem[f].getName());
	// }
	//
	// if (f == 0) {
	// // Passo 1: Criacao de um objeto-documento
	// document = new Document(reader.getPageSizeWithRotation(1));
	// // Passo 2: Criamos um writer que escute o documento
	// writer = new PdfCopy(document, new FileOutputStream(outFile));
	// // Passo 3: Abrimos o documento
	// document.open();
	// }
	// // Passo 4: Adicionamos conteudo
	// PdfImportedPage page;
	// for (int i = 0; i < n;) {
	// ++i;
	// page = writer.getImportedPage(reader, i);
	// writer.addPage(page);
	// if (log) {
	// log("Pagina processada: " + i + ", pdf: " + arquivosOrigem[f].getName());
	// }
	// }
	//
	// reader.close();
	// if (log) {
	// log("Arquivo processado: " + arquivosOrigem[f].getAbsolutePath() +
	// "\n----------");
	// }
	// }
	// if (master.size() > 0) {
	// writer.setOutlines(master);
	// }
	// // Passo 5: Fechamos o documento
	// document.close();
	// }

	/**
	 * Retorna um mapa com todos os dias que existem em cada mes dentro do
	 * período informado.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 */
	public static SortedMap geraMapaComDiasPorAnoMes(Date dataInicial, Date dataFinal) {
		SortedMap diasPorMes = new TreeMap();

		Calendar calInicio = Calendar.getInstance();
		Calendar calFim = Calendar.getInstance();
		calInicio.setTime(dataInicial);
		calFim.setTime(dataFinal);

		// percorre os meses para que seja feita a contagem
		boolean primeiraVez = true;
		while (calInicio.get(Calendar.YEAR) < calFim.get(Calendar.YEAR)
				|| (calInicio.get(Calendar.MONTH) <= calFim.get(Calendar.MONTH) && calInicio.get(Calendar.YEAR) == calFim.get(Calendar.YEAR))) {
			String anoMes = Utilitaria.FORMAT_QUATRO_CASAS_SEM_VIRGULA.format(calInicio.get(Calendar.YEAR))
					+ Utilitaria.FORMAT_DUAS_CASAS_SEM_VIRGULA.format((calInicio.get(Calendar.MONTH) + 1));

			if (primeiraVez && calInicio.get(Calendar.YEAR) == calFim.get(Calendar.YEAR) && calInicio.get(Calendar.MONTH) == calFim.get(Calendar.MONTH)) {
				diasPorMes.put(anoMes, new Integer(calFim.get(Calendar.DAY_OF_MONTH) - calInicio.get(Calendar.DAY_OF_MONTH) + 1));
				primeiraVez = false;
			} else if (primeiraVez) {
				diasPorMes.put(anoMes, new Integer(calInicio.getActualMaximum(Calendar.DAY_OF_MONTH) - calInicio.get(Calendar.DAY_OF_MONTH) + 1));
				primeiraVez = false;
			} else if (calInicio.get(Calendar.YEAR) == calFim.get(Calendar.YEAR) && calInicio.get(Calendar.MONTH) == calFim.get(Calendar.MONTH)) {
				diasPorMes.put(anoMes, new Integer(calFim.get(Calendar.DAY_OF_MONTH)));
			} else {
				diasPorMes.put(anoMes, new Integer(calInicio.getActualMaximum(Calendar.DAY_OF_MONTH)));
			}

			calInicio.add(Calendar.MONTH, 1);
		}

		return diasPorMes;
	}

	/**
	 * Pega mapa dos meses no ano. Calendar<b>As chaves do mapa são os números
	 * dos meses de acordo com os números dos mesmo na classe
	 * <code>Calendar</code></b>
	 * 
	 * @return mapa com os meses de um ano.
	 */
	public static SortedMap getMapaDeMesesNoAno() {
		SortedMap mapaMesesAno = new TreeMap();

		mapaMesesAno.put(new Integer(Calendar.JANUARY), "JANEIRO");
		mapaMesesAno.put(new Integer(Calendar.FEBRUARY), "FEVEREIRO");
		mapaMesesAno.put(new Integer(Calendar.MARCH), "MARÇO");
		mapaMesesAno.put(new Integer(Calendar.APRIL), "ABRIL");
		mapaMesesAno.put(new Integer(Calendar.MAY), "MAIO");
		mapaMesesAno.put(new Integer(Calendar.JUNE), "JUNHO");
		mapaMesesAno.put(new Integer(Calendar.JULY), "JULHO");
		mapaMesesAno.put(new Integer(Calendar.AUGUST), "AGOSTO");
		mapaMesesAno.put(new Integer(Calendar.SEPTEMBER), "SETEMBRO");
		mapaMesesAno.put(new Integer(Calendar.OCTOBER), "OUTUBRO");
		mapaMesesAno.put(new Integer(Calendar.NOVEMBER), "NOVEMBRO");
		mapaMesesAno.put(new Integer(Calendar.DECEMBER), "DEZEMBRO");

		SortedMap cloneMapaMesesAnoParaRetorno = Collections.unmodifiableSortedMap(mapaMesesAno);

		return cloneMapaMesesAnoParaRetorno;
	}

	/**
	 * Ordena os elementos de um <code>Map</code> pelo seu valor. Os elementos
	 * desse map devem implementar <code>Comparable</code> ou não poderão ser
	 * ordenados. O resultado é gravado em um <code>LinkedHashMap</code> e
	 * retornado. Este método sincroniza o map antes de utilizá-lo
	 * 
	 * @param map
	 *            o mapa a ser ordenado
	 * @return um <code>LinkedHashMap</code> com os elementos ordenados pela
	 *         ordem natural dos valores
	 * @throws ClassCastException
	 *             caso algum elemento do <code>Map</code> nao implemente
	 *             <code>Comparable</code>
	 * @throws NullPointerException
	 *             caso algum elemento do <code>Map</code> seja
	 *             <code>null</code>
	 * @author mborges
	 * @since 25/09/2006
	 */
	// public static LinkedHashMap ordenarMapPorValor(Map map) throws
	// ClassCastException, NullPointerException {
	// MapEntry[] entradas;
	// synchronized (map) {
	// entradas = new MapEntry[map.size()];
	//
	// Set entrySet = map.entrySet();
	// Iterator entrySetIterator = entrySet.iterator();
	// for (int i = 0; entrySetIterator.hasNext(); i++) {
	// Map.Entry entry = (Map.Entry) entrySetIterator.next();
	// entradas[i] = new MapEntry(entry.getKey(), (Comparable)
	// entry.getValue());
	// }
	// }
	//
	// Arrays.sort(entradas);
	//
	// LinkedHashMap retorno = new LinkedHashMap();
	//
	// for (int i = 0; i < entradas.length; i++) {
	// retorno.put(entradas[i].getKey(), entradas[i].getValue());
	// }
	//
	// return retorno;
	// }

	public static String extraiExtensao(String nome) {
		int indexOfPonto = nome.lastIndexOf('.');
		if (indexOfPonto == -1 || (indexOfPonto + 1) >= nome.length()) {
			return "";
		} else {
			return nome.substring(indexOfPonto + 1);
		}
	}

	/**
	 * Gera um Hash MD5 a partir de um InputStream.
	 * 
	 * @param is
	 *            Fluxo de entrada de dados. Como FileInputStream.
	 * @return bytes do hash MD5.
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	// public static String gerarHashMD5(InputStream is) throws
	// NoSuchAlgorithmException, IOException, ClassNotFoundException {
	// MessageDigest md = MessageDigest.getInstance("MD5");
	// byte[] dataBytes = new byte[1024 * 1024];
	// int nread = 0;
	// while ((nread = is.read(dataBytes)) != -1) {
	// md.update(dataBytes, 0, nread);
	// }
	// return Base16Encoder.encode(md.digest());
	// }

	/**
	 * Gera um Hash MD5 a partir de uma String.
	 * 
	 * @param md5
	 *            Fluxo de entrada de dados. Como String.
	 * @return String do hash MD5.
	 * @throws ClassNotFoundException
	 * @author Pedro Isaac
	 */
	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	// public static String hashSeguranca(String str) {
	// String ret = MD5(Constantes.KEY_SEGURANCA + str +
	// Constantes.KEY_SEGURANCA);
	// // System.out.println(str+" - "+ret);
	// return ret;
	// }

	// public static boolean isHashSegOK(String str, String hashSeg) {
	// return hashSeg.equals(hashSeguranca(str + ""));
	// }

	/**
	 * @author dsilva
	 * @param mesAno
	 * @return String
	 */
	public static String converteMesLiteralAno(String mesAno) {
		int mes = (Integer.parseInt(mesAno.substring(0, 2)) - 1);
		String ano = mesAno.substring(2).replaceAll("[\\p{Punct}]", "");

		return (String) getMapaDeMesesNoAno().get(new Integer(mes)) + "/" + ano;
	}

	public static void zerarHoraMinutoSegundoMilisegundo(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
	}

	public static void maximizarHoraMinutoSegundoMilisegundo(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
	}

	public static Date maximizarHoraMinutoSegundoMilisegundo(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);

		maximizarHoraMinutoSegundoMilisegundo(c1);

		return c1.getTime();
	}

	public static Date zerarHoraMinutoSegundoMilisegundo(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);

		zerarHoraMinutoSegundoMilisegundo(c1);

		return c1.getTime();
	}

	/**
	 * Soma os valores de dois objetos {@link Integer}, retornando um novo
	 * {@link Integer} como resultado. Caso um dos objetos passados como
	 * argumento seja <code>null</code> a ele será atribuído o valor
	 * <code>0</code> na soma
	 * 
	 * @param int1
	 * @param int2
	 * @return
	 * @author mborges
	 * @since 23/03/2007
	 */
	public static Integer somar(Integer int1, Integer int2) {
		return new Integer((int1 == null ? 0 : int1.intValue()) + (int2 == null ? 0 : int2.intValue()));
	}

	/**
	 * Soma os valores de dois objetos {@link Long}, retornando um novo
	 * {@link Long} como resultado. Caso um dos objetos passados como argumento
	 * seja <code>null</code> a ele será atribuído o valor <code>0</code> na
	 * soma
	 * 
	 * @param long1
	 * @param long2
	 * @return
	 * @author mborges
	 * @since 23/03/2007
	 */
	public static Long somar(Long long1, Long long2) {
		return new Long((long1 == null ? 0 : long1.longValue()) + (long2 == null ? 0 : long2.longValue()));
	}

	/**
	 * Soma os valores de dois objetos {@link Double}, retornando um novo
	 * {@link Double} como resultado. Caso um dos objetos passados como
	 * argumento seja <code>null</code> a ele será atribuído o valor
	 * <code>0</code> na soma
	 * 
	 * @param double1
	 * @param double2
	 * @return
	 * @author mborges
	 * @since 23/03/2007
	 */
	public static Double somar(Double double1, Double double2) {
		return new Double((double1 == null ? 0 : double1.doubleValue()) + (double2 == null ? 0 : double2.doubleValue()));
	}

	/**
	 * Soma dias, meses e anos a uma determinada data. Os três valores serão
	 * somados à data em questão, de forma cumulativa.A soma considera uma data
	 * dentro de um {@link Calendar} onde {@link Calendar#isLenient()} retorna
	 * <code>true</code>, ou seja, se você tentar somar 13 meses a uma data, o
	 * resultado será o mesmo que somar de 1 ano e 1 mês.
	 * 
	 * @param data
	 *            a data à qual serão somados dias, meses e anos
	 * @param dias
	 *            o número de dias a somar
	 * @param meses
	 *            o número de meses a somar
	 * @param anos
	 *            o número de anos a somar
	 * @return a nova data, após a soma de dias, soma de meses e soma de anos
	 * @author mborges
	 * @since 26/04/2007
	 */
	public static Date somar(Date data, int dias, int meses, int anos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.setLenient(true);
		calendar.add(Calendar.DAY_OF_MONTH, dias);
		calendar.add(Calendar.MONTH, meses);
		calendar.add(Calendar.YEAR, anos);

		return calendar.getTime();
	}

	public static List toList(Object[][] array) {
		List list;
		if (array == null) {
			list = null;
		} else {
			if (array[0] == null) {
				list = new ArrayList();
			} else {
				list = new ArrayList(array.length * array[0].length);
			}

			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[i].length; j++) {
					list.add(array[i][j]);
				}
			}
		}
		return list;
	}

	/**
	 * Copia o arquivo <code>arquivoParaCopiar</code> para o diretório
	 * <code>diretorioDestino</code>.
	 * 
	 * @param arquivoParaCopiar
	 *            {@link File} denotando o arquivo a ser copiado
	 * @param diretorioDestino
	 *            {@link File} representando o diretório de destino da cópia.
	 * @throws IllegalArgumentException
	 *             se <code>arquivoParaCopiar</code> não existir ou não for um
	 *             arquivo ou ainda se <code>diretorioDestino</code> não existir
	 *             ou não for um diretório
	 * @throws NullPointerException
	 *             se <code>arquivoParaCopiar</code> ou
	 *             <code>diretorioDestino</code> forem <code>null</code>
	 * @throws IOException
	 *             caso ocorra algum erro durante a cópia
	 * @author mborges
	 * @since 21/09/2007
	 */
	public static void copiarArquivos(File arquivoParaCopiar, File diretorioDestino) throws IllegalArgumentException, NullPointerException, IOException {
		copiarArquivos(new File[] { arquivoParaCopiar }, diretorioDestino);
	}

	/**
	 * Copia todos os arquivos do array <code>arquivosParaCopiar</code> para o
	 * diretório <code>diretorioDestino</code>.
	 * 
	 * @param arquivosParaCopiar
	 *            array de {@link File} denotando os arquivos a serem copiados
	 * @param diretorioDestino
	 *            {@link File} representando o diretório de destino da cópia.
	 * @throws IllegalArgumentException
	 *             se algum item do array <code>arquivosParaCopiar</code> não
	 *             existir ou não for um arquivo ou ainda se
	 *             <code>diretorioDestino</code> não existir ou não for um
	 *             diretório
	 * @throws NullPointerException
	 *             se <code>arquivosParaCopiar</code>, algum de seus itens ou
	 *             <code>diretorioDestino</code> forem <code>null</code>
	 * @throws IOException
	 *             caso ocorra algum erro durante a cópia de algum dos arquivos
	 * @author mborges
	 * @since 21/09/2007
	 */
	public static void copiarArquivos(File[] arquivosParaCopiar, File diretorioDestino) throws IllegalArgumentException, NullPointerException, IOException {
		if (arquivosParaCopiar == null || diretorioDestino == null) {
			throw new NullPointerException("Nem o array de arquivos a copiar nem o diretório de destino podem ser null!");
		} else if (!diretorioDestino.isDirectory()) {
			throw new IllegalArgumentException("O diretório de destino " + diretorioDestino.getAbsolutePath() + " deve existir e não pode ser um arquivo!");
		} else {
			for (int i = 0; i < arquivosParaCopiar.length; i++) {
				if (arquivosParaCopiar[i] == null) {
					throw new NullPointerException("Nenhum arquivo a ser copiado pode ser null!");
				} else if (!arquivosParaCopiar[i].isFile()) {
					throw new IllegalArgumentException("O arquivo a ser copiado " + arquivosParaCopiar[i].getAbsolutePath()
							+ " deve existir e não pode ser um diretório!");
				}
			}

			FileInputStream fileInputStream = null;
			FileOutputStream fileOutputStream = null;
			FileChannel fileInputChannel = null;
			FileChannel fileOutputChannel = null;

			for (int i = 0; i < arquivosParaCopiar.length; i++) {
				File originalFile = arquivosParaCopiar[i];
				File newFile = new File(diretorioDestino.getAbsolutePath() + File.separator + originalFile.getName());

				try {
					fileInputStream = new FileInputStream(originalFile);
					fileOutputStream = new FileOutputStream(newFile);

					fileInputChannel = fileInputStream.getChannel();
					fileOutputChannel = fileOutputStream.getChannel();

					long fileSize = fileInputChannel.size();

					MappedByteBuffer buffer = fileInputChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);

					fileOutputChannel.write(buffer);
				} catch (RuntimeException e) {
					throw e;
				} catch (FileNotFoundException e) {
					throw e;
				} catch (IOException e) {
					throw e;
				} finally {
					try {
						fileInputChannel.close();
					} catch (Exception e) {
						// Nada a fazer
					}
					try {
						fileInputStream.close();
					} catch (Exception e) {
						// Nada a fazer
					}
					try {
						fileOutputChannel.close();
					} catch (Exception e) {
						// Nada a fazer
					}
					try {
						fileOutputStream.close();
					} catch (Exception e) {
						// Nada a fazer
					}

					newFile.setLastModified(originalFile.lastModified());
				}
			}
		}
	}

	public static Object criaNovoCopiandoCampos(Object o, String campoParaCopiar) throws IllegalArgumentException, InvocationTargetException {
		return criaNovoCopiandoCampos(o, new String[] { campoParaCopiar });
	}

	public static Object criaNovoCopiandoCampos(Object o, String[] camposParaCopiar) throws IllegalArgumentException, InvocationTargetException {
		Set camposParaCopiarSet = new HashSet();
		if (camposParaCopiar != null) {
			for (int i = 0; i < camposParaCopiar.length; i++) {
				camposParaCopiarSet.add(camposParaCopiar[i]);
			}
		}

		try {
			Class clazz = o.getClass();
			Object novo = clazz.newInstance();
			Method[] methods = clazz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				String nome = methods[i].getName();
				Class[] tiposParametros = methods[i].getParameterTypes();
				if (tiposParametros.length == 1 && nome.startsWith("set")) {
					String nomePropriedadeOriginal = nome.substring(3);
					String nomePropriedade = nomePropriedadeOriginal.replaceFirst(nomePropriedadeOriginal.substring(0, 1), nomePropriedadeOriginal.substring(0,
							1).toLowerCase());
					if (camposParaCopiarSet.contains(nomePropriedade)) {
						Method getter = clazz.getMethod("get" + nomePropriedadeOriginal, new Class[0]);
						Object valor = getter.invoke(o, new Object[0]);
						methods[i].invoke(novo, new Object[] { valor });
					} else {
						methods[i].invoke(novo, new Object[1]);
					}
				}
			}

			return novo;
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("O argumento a ser copiado deve ter um construtor padrão, sem argumentos, como no padrão JavaBeans.");
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(
					"O argumento a ser copiado deve ter métodos getters e setters, bem como o construtor padrão, públicos, como no padrão JavaBeans");
		} catch (InvocationTargetException e) {
			throw e;
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(
					"O argumento a ser copiado deve ter um método getter e um setter para cada propriedade, como no padrão JavaBeans.");
		}
	}

	public static double getMaior(double[] numeros) {
		if (numeros.length == 0) {
			return 0;
		} else {
			double maior = Double.MIN_VALUE;
			for (int i = 0; i < numeros.length; i++) {
				if (numeros[i] > maior) {
					maior = numeros[i];
				}
			}

			return maior;
		}
	}

	public static int getMaior(int i1, int i2) {
		return (int) getMaior(new double[] { i1, i2 });
	}

	public static double getMaior(double d1, double d2) {
		return getMaior(new double[] { d1, d2 });
	}

	public static double getMenor(double[] numeros) {
		if (numeros.length == 0) {
			return 0;
		} else {
			double menor = Double.MAX_VALUE;
			for (int i = 0; i < numeros.length; i++) {
				if (numeros[i] < menor) {
					menor = numeros[i];
				}
			}

			return menor;
		}
	}

	public static int getMenor(int i1, int i2) {
		return (int) getMenor(new double[] { i1, i2 });
	}

	public static double getMenor(double d1, double d2) {
		return getMenor(new double[] { d1, d2 });
	}

	/**
	 * Corrige o número de dias a ser usado em cálculos para um dia plausível
	 * dentro de um mês comercial.
	 * <p>
	 * Por exemplo:
	 * <ul>
	 * <li>Caso o parâmetro <code>dias</code> seja <b>31</b>, o método retorna
	 * <b>30</b>.</li>
	 * <li>Caso o parâmetro <code>dias</code> seja <b>29</b> e o parâmetro
	 * <code>diaQualquerDoMesEmQuestao</code> seja um dia em Fevereiro, o método
	 * retorna <b>30</b>.</li>
	 * <li>Caso o parâmetro <code>dias</code> seja um dia comum (que não atende
	 * aos casos citados acima, por exemplo), o mesmo valor do parâmetro
	 * <code>dias</code> é retornado</li>
	 * </ul>
	 * </p>
	 * 
	 * @param dias
	 *            número de dias a ser usado em cálculos (como o número de dias
	 *            a pagar em uma aposentadoria em um mês)
	 * @param diaQualquerDoMesEmQuestao
	 *            um dia dentro do mês do cálculo
	 * @return um valor entre <b>1</b> e <b>30</b>
	 * @see com.evol.seg.util.Utilitaria#corrigeNumeroDiasParaMesComercial(int,
	 *      java.util.Date)
	 */
	public static int corrigeNumeroDiasParaMesComercial(int dias, Calendar diaQualquerDoMesEmQuestao) {
		final int diasNoMes = 30;

		dias = getMenor(dias, diasNoMes);
		/*
		 * if (dias ==
		 * diaQualquerDoMesEmQuestao.getActualMaximum(Calendar.DAY_OF_MONTH)) {
		 * dias = diasNoMes; }
		 */

		if (dias < 0) {
			dias = 0;
		}

		return dias;
	}

	/**
	 * Corrige o número de dias a ser usado em cálculos para um dia plausível
	 * dentro de um mês comercial.
	 * <p>
	 * Por exemplo:
	 * <ul>
	 * <li>Caso o parâmetro <code>dias</code> seja <b>31</b>, o método retorna
	 * <b>30</b>.</li>
	 * <li>Caso o parâmetro <code>dias</code> seja <b>29</b> e o parâmetro
	 * <code>diaQualquerDoMesEmQuestao</code> seja um dia em Fevereiro, o método
	 * retorna <b>30</b>.</li>
	 * <li>Caso o parâmetro <code>dias</code> seja um dia comum (que não atende
	 * aos casos citados acima, por exemplo), o mesmo valor do parâmetro
	 * <code>dias</code> é retornado</li>
	 * </ul>
	 * </p>
	 * 
	 * @param dias
	 *            número de dias a ser usado em cálculos (como o número de dias
	 *            a pagar em uma aposentadoria em um mês)
	 * @param diaQualquerDoMesEmQuestao
	 *            um dia dentro do mês do cálculo
	 * @return um valor entre <b>1</b> e <b>30</b>
	 * @see com.evol.seg.util.Utilitaria#corrigeNumeroDiasParaMesComercial(int,
	 *      Calendar)
	 */
	public static int corrigeNumeroDiasParaMesComercial(int dias, Date diaQualquerDoMesEmQuestao) {
		Calendar c = Calendar.getInstance();
		c.setTime(diaQualquerDoMesEmQuestao);

		return corrigeNumeroDiasParaMesComercial(dias, c);
	}

	/**
	 * Retorna uma data menor ou igual a dia 30 de um mês.
	 * 
	 * @param data
	 * @return
	 */
	public static Date corrigeParaMesComercial(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		corrigeParaMesComercial(c);
		return c.getTime();
	}

	/**
	 * Altera para uma data menor ou igual a dia 30 de um mês.
	 * 
	 * @param data
	 */
	public static void corrigeParaMesComercial(Calendar data) {
		final int diaMaximo = 30;

		if (data.get(Calendar.DAY_OF_MONTH) > diaMaximo) {
			data.set(Calendar.DAY_OF_MONTH, diaMaximo);
		}

		if (data.get(Calendar.MONTH) == Calendar.FEBRUARY && data.get(Calendar.DAY_OF_MONTH) == data.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			data.setLenient(true);
			data.set(Calendar.DAY_OF_MONTH, diaMaximo);
		}
	}

	/**
	 * Trunca uma string p/ quantidade passada e se for menor preenche com
	 * espaco em branco
	 * 
	 * @param palavra
	 * @param quantidade
	 * 
	 * @author osmar
	 */
	public static String truncaString(String palavra, int quantidade) {
		if (palavra.length() > quantidade) {
			palavra = palavra.substring(0, quantidade + 1);
		} else {
			int cont = quantidade - palavra.length();
			for (; cont >= 0; cont--) {
				palavra = palavra + " ";
			}
		}
		return palavra;
	}

	/**
	 * Metodo para gerar uma string contendo uma data gerada a partir de uma
	 * string composta por uma data invertida no formato YYYYMMDD.
	 * 
	 * @param data
	 * @return String no formato "ddmmaaaa"
	 * @author osmar
	 */
	public static String converteDataBDFromString(String data) {
		String ano = data.substring(0, 4);
		String mes = data.substring(5, 7);
		String dia = data.substring(8, 10);
		return dia + mes + ano;
	}

	/**
	 * Converte o array para list.
	 * 
	 * @param vet
	 * @return
	 */
	public static List<?> converteArrayParaList(Object[] vet) {
		List<Object> lst = new ArrayList<Object>();
		for (int i = 0; i < vet.length; i++) {
			lst.add(vet[i]);
		}
		return lst;
	}

	/**
	 * Metodo para retirar os espaços em branco da string, se possível
	 * 
	 * @param dados
	 * @return String sem espaços
	 * @author osmar
	 */
	public static String retiraEspacosisPosible(String dados) {
		if (dados != null) {
			dados = dados.trim();
		} else {
			dados = "";
		}
		return dados;
	}

	public static String dias2Data(String referencia, int dias) throws ParseException {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
		cal.setTime(new Date(formata.parse(referencia).getTime()));
		cal.add(Calendar.DAY_OF_YEAR, (-dias) + 1);

		String data = formata.format(cal.getTime());
		return data;
	}

	public static Boolean rubricaIncide13(int idRubrica, Set listaRubricasIncide13) {
		for (Iterator<Integer> it = listaRubricasIncide13.iterator(); it.hasNext();) {
			Integer idRubricaTmp = it.next();
			if (idRubricaTmp.intValue() == idRubrica) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Formata a data no formato AAAAMMDD
	 * 
	 */
	public static String formatarData(Date data) {
		if (data == null) {
			return "00000000";
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		return sd.format(data);
	}

	/**
	 * Formata a data no formato dd/MM/yyyy
	 * 
	 */
	public static String formatarData2(Date data) {
		if (data == null) {
			return "00000000";
		}
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		return sd.format(data);
	}

	/**
	 * Formata a hora no formato hhmm
	 * 
	 */
	public static String formatarHora(Date data) {
		if (data == null) {
			return "00000000";
		}
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		return sd.format(data);
	}

	/**
	 * Formata a data no formato dd/MM/yyyy hh:mm
	 * 
	 */
	public static String formatarDataHora(Date data) {
		if (data == null) {
			return "00000000";
		}
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sd.format(data);
	}

	/**
	 * metodo para quebrar a linha, retorna o valor passado com a quebra da
	 * linha
	 * 
	 * @param valor
	 * @return
	 */
	public static String quebraDeLinha(String valor) {
		return valor + "\r\n";

	}

	/**
	 * Formata o texto com a quantidade de casas passadas, se nao tiver
	 * nada(null) retorna uma string vazia com a quantidade de casa passado por
	 * parametro
	 * 
	 * @param texto
	 * @param casas
	 * @return
	 */
	public static String formatarTexto(String texto, int casas) {
		if (texto == null) {
			StringBuilder sb = new StringBuilder(" ");
			sb.setLength(casas);
			return sb.toString();
		}
		StringBuilder sb = new StringBuilder(texto);
		sb.setLength(casas);
		return sb.toString();

	}

	/**
	 * Formata o numero passando a ter 12 casas
	 * 
	 * @param numero
	 * @return
	 */
	public static String formatarInteiro(Integer numero) {
		NumberFormat nf = NumberFormat.getInstance(); // Get Instance of
		// NumberFormat
		// retira o agrupamento por ponto
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(12); // The minimum Digits required is 5
		nf.setMaximumIntegerDigits(12); // The maximum Digits required is 5
		String sb = (nf.format((long) numero));

		return sb;

	}

	/**
	 * Formata o numero passando a ter a quantidade de casas repassadas
	 * 
	 * @param numero
	 * @param casas
	 * @return
	 */

	public static String formatarInteiro(Integer numero, int casas) {
		NumberFormat nf = NumberFormat.getInstance(); // Get Instance of
		// NumberFormat
		if (numero == null) {
			numero = 0;
		}
		// retira o agrupamento por ponto
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(casas); // The minimum Digits required is 5
		nf.setMaximumIntegerDigits(casas); // The maximum Digits required is 5
		String sb = (nf.format((long) numero));

		return sb;

	}

	/**
	 * Formata o numero passando a ter a quantidade de casas repassadas
	 * 
	 * @param numero
	 * @param casas
	 * @return
	 */

	public static String formatarInteiro(Long numero, int casas) {
		NumberFormat nf = NumberFormat.getInstance(); // Get Instance of
		// NumberFormat
		if (numero == null) {
			numero = (long) 0;
		}
		// retira o agrupamento por ponto
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(casas); // The minimum Digits required is 5
		nf.setMaximumIntegerDigits(casas); // The maximum Digits required is 5
		String sb = (nf.format(numero));

		return sb;

	}

	public static boolean validaCampo(String valor) {
		if (valor != null) {
			valor = valor.trim();
			valor = valor.replaceAll("[\\p{Punct}]", "");
			if (!valor.equals("") && !(valor.toUpperCase()).equals("NULL")) {
				return true;
			}
		}
		return false;

	}

	/**
	 * @desc
	 * @param obj
	 * @author Pedro Isaac
	 * @since 09/06/2011
	 */
	public static ObjectOutputStream zipBytes(Object obj) {
		ObjectOutputStream objRetorno = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gz = new GZIPOutputStream(baos);
			ObjectOutputStream oos = new ObjectOutputStream(gz);
			oos.writeObject(obj);
			objRetorno = oos;
			oos.flush();
			oos.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetorno;
	}

	/**
	 * @author Pedro Isaac
	 * @since 09/06/2011
	 */
	public static Collection<?> unZipBytes(InputStream obj) {
		Collection<?> objRetorno = new ArrayList();
		GZIPInputStream gs = null;
		try {
			gs = new GZIPInputStream(obj);
			ObjectInputStream ois = new ObjectInputStream(gs);
			Object[] objeto = (Object[]) ois.readObject();
			objRetorno = Arrays.asList(objeto);
			ois.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				gs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return objRetorno;
	}

	/**
	 * @author Pedro Isaac
	 * @since 09/06/2011
	 */
	// public static final byte[] inputStreamZipBytes(Object obj) throws
	// RepositorioException {
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// byte[] data = null;
	// try {
	// GZIPOutputStream gz = new GZIPOutputStream(baos);
	// ObjectOutputStream oos = new ObjectOutputStream(gz);
	// oos.writeObject(obj);
	// oos.flush();
	// oos.close();
	//
	// data = baos.toByteArray();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new
	// RepositorioException("Impossível serializar prévia na base de dados!");
	// } finally {
	// try {
	// baos.close();
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }
	//
	// return data;
	// }

	/**
	 * Sempre retorna uma string válida diferente de null com espaços extra
	 * removidos.
	 * 
	 * @param str
	 *            Aceita null.
	 */
	public static String trim(String str) {
		if (str == null)
			return "";
		return str.trim();
	}

	/**
	 * Sempre retorna uma string válida diferente de null.
	 * 
	 * @param str
	 *            Aceita null.
	 */
	public static String getString(String str) {
		if (str == null)
			return "";
		return str;
	}

	/**
	 * Sempre retorna um número. Quando a string é invalida é retornado o número
	 * default.
	 * 
	 * @param str
	 *            String para conversão.
	 * @param def
	 *            Número default. Retornado quando a string é inválida.
	 */
	public static int parseInt(String str, int def) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return def;
		}
	}

	public static String comparaCampoIreport(String campoNovo, String campoAntigo) {
		String retorno = "";
		if (campoNovo == null && campoAntigo == null) {
			retorno = "";
		} else if (campoNovo == null) {
			retorno = "[-]";
		} else if (campoAntigo == null) {
			retorno = "[+] ".concat(campoNovo);
		} else if (!campoNovo.equalsIgnoreCase(campoAntigo)) {
			retorno = "[*] ".concat(campoNovo);
		} else {
			retorno = campoNovo;
		}
		return retorno.trim().replaceAll("\\s+", " ");
	}

	/**
	 * Calcula o tempo de execução em relação à data atual e a data de execução.
	 * Retorna a concatenação das diferenças entre as horas, minutos e segundos.
	 * 
	 * @return String hora:min:sec
	 */
	public static String calculaTempoExecucao(Date dt2) {

		long timeAtual = new Date().getTime();
		long timeExecucao = dt2.getTime();

		String tempoExecucao = DateFormatUtils.formatUTC(timeAtual - timeExecucao, "HH:mm:ss");
		// long tempoExecucao = timeAtual - timeExecucao;

		return tempoExecucao;
	}

	/**
	 * 
	 * @param pMask
	 * @param pValue
	 * @param pReturnValueEmpty
	 * @return
	 */
	public static String formataComMascara(String pMask, String pValue,
			boolean pReturnValueEmpty) {

		/*
		 * Verifica se se foi configurado para nao retornar a mascara se a
		 * string for nulo ou vazia se nao retorna somente a mascara.
		 */
		if (pReturnValueEmpty == true
				&& (pValue == null || pValue.trim().equals("")))
			return "";

		/*
		 * Substituir as mascaras passadas como 9, X, * por # para efetuar a
		 * formatcao
		 */
		pMask = pMask.replaceAll(Pattern.quote("*"), "#");
		pMask = pMask.replaceAll("9", "#");
		pMask = pMask.toUpperCase().replaceAll("X", "#");

		/*
		 * Formata valor com a mascara passada
		 */
		for (int i = 0; i < pValue.length(); i++) {
			pMask = pMask.replaceFirst("#", pValue.substring(i, i + 1));
		}

		/*
		 * Subistitui por string vazia os digitos restantes da mascara quando o
		 * valor passado é menor que a mascara
		 */
		return pMask.replaceAll("#", "");
	}

	public static Date addDiaInDate(Date dt, int qtdDias) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, qtdDias);
		return c.getTime();
	}

	public static Date addMesInDate(Date dt, int qtdMes) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, qtdMes);
		return c.getTime();
	}

	public static Date setMesInDate(Date dt, int mes) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.set(c.get(Calendar.YEAR), (mes - 1), c.get(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public static Date setAnoInDate(Date dt, int ano) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.set(ano, c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public static Date setDiaInDate(Date dt, int dia) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), dia);
		return c.getTime();
	}

	public static int getItemData(Date dt, int itemData) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int retorno;

		switch (itemData) {
		case Calendar.DAY_OF_MONTH:
		case Calendar.DAY_OF_WEEK:
		case Calendar.DAY_OF_WEEK_IN_MONTH:
		case Calendar.DAY_OF_YEAR:
		case Calendar.HOUR:
		case Calendar.HOUR_OF_DAY:
		case Calendar.MINUTE:
		case Calendar.MILLISECOND:
		case Calendar.SECOND:
		case Calendar.WEEK_OF_MONTH:
		case Calendar.WEEK_OF_YEAR:
		case Calendar.YEAR:
			retorno = cal.get(itemData);
			break;
		case Calendar.MONTH:
			retorno = (cal.get(itemData) + 1);
			break;
		default:
			retorno = -1;
			break;
		}

		return retorno;
	}

	public static int getMaxData(Date dt, int itemData) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int retorno;

		switch (itemData) {
		case Calendar.DAY_OF_MONTH:
		case Calendar.DAY_OF_WEEK:
		case Calendar.DAY_OF_WEEK_IN_MONTH:
		case Calendar.DAY_OF_YEAR:
		case Calendar.HOUR:
		case Calendar.HOUR_OF_DAY:
		case Calendar.MINUTE:
		case Calendar.MILLISECOND:
		case Calendar.MONTH:
		case Calendar.SECOND:
		case Calendar.WEEK_OF_MONTH:
		case Calendar.WEEK_OF_YEAR:
		case Calendar.YEAR:
			retorno = cal.getActualMaximum(itemData);
			break;
		default:
			retorno = -1;
			break;
		}

		return retorno;
	}

	public static int getMinData(Date dt, int itemData) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int retorno;

		switch (itemData) {
		case Calendar.DAY_OF_MONTH:
		case Calendar.DAY_OF_WEEK:
		case Calendar.DAY_OF_WEEK_IN_MONTH:
		case Calendar.DAY_OF_YEAR:
		case Calendar.HOUR:
		case Calendar.HOUR_OF_DAY:
		case Calendar.MINUTE:
		case Calendar.MILLISECOND:
		case Calendar.MONTH:
		case Calendar.SECOND:
		case Calendar.WEEK_OF_MONTH:
		case Calendar.WEEK_OF_YEAR:
		case Calendar.YEAR:
			retorno = cal.getActualMinimum(itemData);
			break;
		default:
			retorno = -1;
			break;
		}
		return retorno;
	}

	public static String[] GetStringsBetweenTags(String text, String opening, String closing) {
		List<String> result = new ArrayList<String>();
		try {
			List<Integer> openings = GetListOfOccurrences(text, opening);
			List<Integer> closings = GetListOfOccurrences(text, closing);
			if (openings.size() != closings.size())
				throw new Exception("There is " + (openings.size() > closings.size() ? "more " : "less ") + opening + " tags than " + closing + "!");
			for (int i = 0; i < openings.size(); i++)
				result.add(text.substring(openings.get(i) + opening.length(), closings.get(i) - openings.get(i) - opening.length()));
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return (String[]) result.toArray();
	}

	public static List<Integer> GetListOfOccurrences(String text, String substring) {
		List<Integer> pos = new ArrayList<Integer>();
		try {
			int ind = 0;
			while ((ind = text.indexOf(substring, ind)) != -1)
				pos.add(ind++);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return pos;
	}

	public static String gerarNumeroRecibo(int idRecibo, int idCentroCusto, Date data) {
		String centroCusto = String.valueOf(idCentroCusto);
		String recibo = String.valueOf(idRecibo);

		centroCusto = (centroCusto.length() < 2) ? (caracteres("0", 2 - centroCusto.length()) + centroCusto) : centroCusto;
		recibo = (recibo.length() < 4) ? (caracteres("0", 4 - recibo.length()) + recibo) : recibo;

		return centroCusto + "." + recibo + "/" + new SimpleDateFormat("yyyy").format(data);
	}

	// public static String encrypt(String key, String value)
	// throws GeneralSecurityException, UnsupportedEncodingException {
	//
	// String _key = "";
	//
	// if (key.length() > 16) {
	// _key = key.substring(0, 16);
	// } else if (key.length() < 16) {
	// for (int i = 1; i <= (16 - key.length()); i++) {
	// _key += "0";
	// }
	//
	// _key = key + _key;
	// }
	//
	// Key aesKey = new SecretKeySpec(_key.getBytes(), "AES");
	// Cipher cipher = Cipher.getInstance("AES");
	// cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	// byte[] encrypted = cipher.doFinal(value.getBytes("ISO-8859-1"));
	//
	// return Base64.encode(encrypted);
	// }
	//
	// public static String decrypt(String key, String valor)
	// throws GeneralSecurityException, UnsupportedEncodingException,
	// Base64DecodingException {
	//
	// String _key = "";
	//
	// if (key.length() > 16) {
	// _key = key.substring(0, 16);
	// } else if (key.length() < 16) {
	// for (int i = 1; i <= (16 - key.length()); i++) {
	// _key += "0";
	// }
	//
	// _key = key + _key;
	// }
	//
	// Key aesKey = new SecretKeySpec(_key.getBytes("ISO-8859-1"), "AES");
	//
	// Cipher cipher = Cipher.getInstance("AES");
	// cipher.init(Cipher.DECRYPT_MODE, aesKey);
	// String decrypted = new String(cipher.doFinal(Base64.decode(valor)),
	// "ISO-8859-1");
	//
	// return decrypted;
	// }

	public static String gerarNumeroProcesso(int id) {
		String novoNumero = Utilitaria.preencheCaracteres(String.valueOf(id), 6);
		novoNumero = new SimpleDateFormat("yyyyMMdd").format(new Date()) + novoNumero;
		return novoNumero;
	}

	public static String gerarRegexMySql(String str) {
		String retorno = "";
		if (str == null || str.trim() == "")
			return retorno;

		String[] coringas = str.trim().split("\\*");
		for (int i = 0; i < coringas.length; i++)
			retorno += coringas[i].replaceAll(" ", " ") + ".+";

		if (retorno.length() > 0)
			retorno = retorno.substring(0, retorno.length() - 2);

		return retorno;
	}

	public static boolean isIntegerParseInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	public static boolean isNumber(String str) {
		return StringUtils.isNumeric(str);
	}

	// public static JSONObject processarCaptchaRFB(Usuario userBD, String
	// captcha) {
	// JSONObject resultado = new JSONObject();
	// try {
	// Object obj = InfoSingleton.getMapPilha().get("rfb_" + userBD.getCpf());
	// if (obj == null) {
	// throw new Exception("Objeto não localizado.");
	// } else if (obj instanceof RFB) {
	// RFB rfb = (RFB) obj;
	// String ret = rfb.processarCaptcha(captcha);
	// if (ret.indexOf("viewstate") > 0) {
	// resultado.put("captchaErro", true);
	// }
	//
	// // System.out.println(ret);
	// resultado.put("resultado", ret);
	// } else if (obj instanceof RFB) {
	//
	// }
	// } catch (Exception e) {
	// resultado.put("error", e.getMessage());
	// } finally {
	// InfoSingleton.getMapPilha().remove("rfb_" + userBD.getCpf());
	// }
	// return resultado;
	// }

	// public static JSONObject getCaptchaRFB(Usuario userBD, String cpfCnpj,
	// HttpServletRequest req) {
	// InfoSingleton.getMapPilha().remove("rfb_" + userBD.getCpf());
	//
	// JSONObject resultado = new JSONObject();
	// RFB rfb = null;
	// try {
	// rfb = new RFB(cpfCnpj, req.getRealPath("/"));
	// InfoSingleton.getMapPilha().put("rfb_" + userBD.getCpf(), rfb);
	//
	// resultado.put("contextCaptcha", rfb.getContextCaptcha());
	// resultado.put("pathCaptcha", rfb.getPathCaptcha());
	// resultado.put("urlCaptcha", rfb.getUrlCaptcha());
	// resultado.put("cpfCnpj", cpfCnpj);
	// } catch (Exception e) {
	// InfoSingleton.getMapPilha().remove("rfb_" + userBD.getCpf());
	// resultado.put("error", e.getMessage());
	// }
	// return resultado;
	// }
	// public static JSONObject loginEprocTO(Usuario userBD, String usuario,
	// String senha) {
	// InfoSingleton.getMapPilha().remove("eproc_" + userBD.getCpf());
	//
	// JSONObject resultado = new JSONObject();
	// SiteAppAutenticado eprocAuth = null;
	// try {
	// eprocAuth = new SiteAppAutenticado(usuario, senha);
	// InfoSingleton.getMapPilha().put("eproc_" + userBD.getCpf(), eprocAuth);
	//
	// resultado.put("painelAdv", eprocAuth.getPainelAdv());
	// resultado.put("usuarioOAB", eprocAuth.getUsuarioOAB());
	// } catch (Exception e) {
	// InfoSingleton.getMapPilha().remove("eproc_" + userBD.getCpf());
	// resultado.put("error", e.getMessage());
	// }
	// return resultado;
	// }
	/**
	 * Encodes the byte array into base64 string
	 * 
	 * @param imageByteArray
	 *            - byte array
	 * @return String a {@link java.lang.String}
	 */
	// public static String encodeImage(byte[] imageByteArray) {
	// return
	// org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(imageByteArray);
	// }
	/**
	 * Decodes the base64 string into byte array
	 * 
	 * @param imageDataString
	 *            - a {@link java.lang.String}
	 * @return byte array
	 */
	// public static byte[] decodeImage(String imageDataString) {
	// return
	// org.apache.commons.codec.binary.Base64.decodeBase64(imageDataString);
	// }
	/**
	 * Decodes the base64 string into BufferImage
	 * 
	 * @param imageDataString
	 *            - a {@link java.lang.String}
	 * @return byte array
	 */
	public static BufferedImage decodeImage(byte[] imageByteArray) {
		BufferedImage image = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByteArray);
		try {
			image = ImageIO.read(bis);
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Decodes the base64 string into BufferImage
	 * 
	 * @param imageDataString
	 *            - a {@link java.lang.String}
	 * @return byte array
	 */
	// public static BufferedImage decodeImageBase64(String base64StrImage) {
	// BufferedImage image = null;
	// try {
	// byte[] imageByteArray = Utilitaria.decodeImage(base64StrImage);
	// ByteArrayInputStream bis = new ByteArrayInputStream(imageByteArray);
	//
	// /*
	// * File of = new File("c:/yourFile.jpg"); FileOutputStream osf = new
	// * FileOutputStream(of); osf.write(imageByteArray); osf.flush();
	// */
	//
	// // image = ImageIO.read(bis);
	// bis.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return image;
	// }

	public static boolean saveImage(BufferedImage buffer, String pathFileName, String tipoImagem) {
		boolean ret = false;
		try {
			File outputfile = new File(pathFileName);
			ImageIO.write(buffer, tipoImagem, outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	// public static Image redrawImageBase64(String base64Image, int x1, int y1,
	// int x2, int y2, int w, int h) {
	// boolean ret = false;
	//
	// BufferedImage bi = decodeImageBase64(base64Image);
	// BufferedImage out = bi.getSubimage(x1, y1, w, h);
	//
	// return (Image) out;
	// }

	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 * 
	 * @param srcImg
	 *            - source image to scale
	 * @param w
	 *            - desired width
	 * @param h
	 *            - desired height
	 * @return - the new resized image
	 */
	public static BufferedImage getScaledImage(BufferedImage src, int w, int h) {
		int finalw = w;
		int finalh = h;
		double factor = 1.0d;
		if (src.getWidth() > src.getHeight()) {
			factor = ((double) src.getHeight() / (double) src.getWidth());
			finalh = (int) (finalw * factor);
		} else {
			factor = ((double) src.getWidth() / (double) src.getHeight());
			finalw = (int) (finalh * factor);
		}

		BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(src, 0, 0, finalw, finalh, null);
		g2.dispose();
		return resizedImg;
	}

	/**
	 * Creates a new and empty directory in the default temp directory using the
	 * given prefix. This methods uses {@link File#createTempFile} to create a
	 * new tmp file, deletes it and creates a directory for it instead.
	 * 
	 * @param prefix
	 *            The prefix string to be used in generating the diretory's
	 *            name; must be at least three characters long.
	 * @return A newly-created empty directory.
	 * @throws IOException
	 *             If no directory could be created.
	 */
	public static File createTempDir(String prefix) throws IOException {
		String tmpDirStr = System.getProperty("java.io.tmpdir");
		if (tmpDirStr == null) {
			throw new IOException("System property 'java.io.tmpdir' does not specify a tmp dir");
		}

		File tmpDir = new File(tmpDirStr);
		if (!tmpDir.exists()) {
			boolean created = tmpDir.mkdirs();
			if (!created) {
				throw new IOException("Unable to create tmp dir " + tmpDir);
			}
		}

		File resultDir = null;
		int suffix = (int) System.currentTimeMillis();
		int failureCount = 0;
		do {
			resultDir = new File(tmpDir, prefix + suffix % 10000);
			suffix++;
			failureCount++;
		} while (resultDir.exists() && failureCount < 50);

		if (resultDir.exists()) {
			throw new IOException(failureCount + " attempts to generate a non-existent directory name failed, giving up");
		}
		boolean created = resultDir.mkdir();
		if (!created) {
			throw new IOException("Failed to create tmp directory");
		}

		return resultDir;
	}

	public static boolean isNullOrBlank(String param) {
		return param == null || param.trim().length() == 0;
	}

	// public static String mutageEncript(String string) {
	// Random gerador = new Random();
	// String tmp = "";
	// for (int i = 0; i < string.length(); i++) {
	// tmp += string.charAt(i) + "|" + gerador.nextInt(10) + "|";
	// }
	//
	// try {
	// tmp = encrypt(Constantes.KEY_SEGURANCA, tmp);
	// } catch (Exception e) {
	// tmp = null;
	// }
	//
	// return tmp;
	// }

	// public static String mutageDecript(String param) {
	// String tmp = null;
	// try {
	// tmp = decrypt(Constantes.KEY_SEGURANCA, param);
	// } catch (Exception e) {
	// return null;
	// }
	//
	// return tmp.replaceAll("[(|)\\d]", "");
	// }

	/**
	 * Calcula a diferença de duas datas em horas <br>
	 * <b>Importante:</b> Quando realiza a diferença em horas entre duas datas,
	 * este método considera os minutos restantes e os converte em fração de
	 * horas.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return quantidade de horas existentes entre a dataInicial e dataFinal.
	 */
	public static double diferencaEmHoras(Date dataInicial, Date dataFinal) {
		double result = 0;
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		long diferencaEmHoras = (diferenca / 1000) / 60 / 60;
		long minutosRestantes = (diferenca / 1000) / 60 % 60;
		double horasRestantes = minutosRestantes / 60d;
		result = diferencaEmHoras + (horasRestantes);

		return result;
	}

	/**
	 * Calcula a diferença de duas datas em minutos <br>
	 * <b>Importante:</b> Quando realiza a diferença em minutos entre duas
	 * datas, este método considera os segundos restantes e os converte em
	 * fração de minutos.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return quantidade de minutos existentes entre a dataInicial e dataFinal.
	 */
	public static double diferencaEmMinutos(Date dataInicial, Date dataFinal) {
		double result = 0;
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		double diferencaEmMinutos = (diferenca / 1000) / 60; // resultado é
																// diferença
																// entre as
																// datas em
																// minutos
		long segundosRestantes = (diferenca / 1000) % 60; // calcula os segundos
															// restantes
		result = diferencaEmMinutos + (segundosRestantes / 60d); // transforma
																	// os
																	// segundos
																	// restantes
																	// em
																	// minutos

		return result;
	}

	/**
	 * Retorna o interiro em binario 16 bits
	 * 
	 * @param inteiro
	 * @return "retorna string com binario 16bits 0000000000000001"
	 */
	public static String convertInteiroToBinario16bits(int inteiro) {
		if (inteiro < 0)
			inteiro = 0;

		return String.format("%16s", Integer.toBinaryString(inteiro)).replace(' ', '0');
	}

	/**
	 * Retorna o interiro em binario 8 bits
	 * 
	 * @param inteiro
	 * @return "retorna string com binario 8bits 00000001"
	 */
	public static String convertInteiroToBinario8bits(int inteiro) {
		if (inteiro < 0)
			inteiro = 0;

		return String.format("%8s", Integer.toBinaryString(inteiro)).replace(' ', '0');
	}

	public static boolean isEprocOnline() {
		boolean online = true;

		try {
			// URL do destino escolhido
			URL url = new URL((String) Configuracao.getInstancia().get("sgi.configuracao.urlEprcLogin"));

			// abre a conexão
			HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

			// tenta buscar conteúdo da URL
			// se não tiver conexão, essa linha irá falhar
			Object objData = urlConnect.getContent();

		} catch (Exception e) {
			online = false;
		}

		return online;
	}

	public static boolean isEprocOnline(String urlStr) {
		boolean online = true;

		try {
			// URL do destino escolhido
			URL url = new URL(urlStr);

			// abre a conexão
			HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

			// tenta buscar conteúdo da URL
			// se não tiver conexão, essa linha irá falhar
			Object objData = urlConnect.getContent();

		} catch (Exception e) {
			online = false;
		}

		return online;
	}

	// public static Date getUltimaSynCompleta() {
	// Date retorno = new Date();
	//
	// try {
	//
	// setDiaInDate(retorno, 1);
	// setMesInDate(retorno, 1);
	// setAnoInDate(retorno, 2000);
	//
	// GerenteCadastro gc = new GerenteCadastro();
	//
	// // verifica a ultima sincronização entre Clientes e Processos
	// Date ultimaSyncCompletaProcesso = gc.buscaUltimaSyncCompletaProcesso();
	// Date ultimaSyncCompletaPessoa = gc.buscaUltimaSyncCompletaPessoa();
	//
	// if (ultimaSyncCompletaProcesso != null && ultimaSyncCompletaPessoa !=
	// null) {
	// if (ultimaSyncCompletaProcesso.before(ultimaSyncCompletaPessoa)) {
	// retorno = ultimaSyncCompletaProcesso;
	// } else {
	// retorno = ultimaSyncCompletaPessoa;
	// }
	// } else if (ultimaSyncCompletaProcesso != null) {
	// retorno = ultimaSyncCompletaProcesso;
	// } else if (ultimaSyncCompletaPessoa != null) {
	// retorno = ultimaSyncCompletaPessoa;
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return retorno;
	// }

}
