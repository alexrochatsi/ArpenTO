
package br.com.datasind.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utilitario{
   public static final Locale LOCALE_BR=new Locale("pt", "BR");
   public static SimpleDateFormat dataformato;

   public static Date convertStringParaData(String str) throws ParseException {
	  dataformato=new SimpleDateFormat("dd/MM/yyyy :hh:mm");
	  return dataformato.parse(str);
   }

   public static Date convertStringParaDatayyyyMMddhhmmss(String str) throws ParseException {
	  dataformato=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	  return dataformato.parse(str);
   }

   public static String convertDataParaStringyyyyMMddhhmmss(Date data) throws ParseException {
	  SimpleDateFormat out=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  return out.format(data);
   }

   public static final String converterDataForString(Date data) {
	  SimpleDateFormat out=new SimpleDateFormat("yyyy-MM-dd");
	  return out.format(data);
   }

   public static final String converterDataForStringBR(Date data) {
	  SimpleDateFormat out=new SimpleDateFormat("dd/MM/yyyy");
	  return out.format(data);
   }

   public static final String converterDataHHMMForString(Date data) {
	  SimpleDateFormat out=new SimpleDateFormat("yyyy-MM-dd hh:mm");
	  return out.format(data);
   }

   public static final String converterDataForStringddMMyyyyhhmm(Date data) {
	  SimpleDateFormat out=new SimpleDateFormat("dd-MM-yyyy hh:mm");
	  return out.format(data);
   }

   public static Date convertDataParaDataBR(Date data) throws ParseException {
	  dataformato=new SimpleDateFormat("dd/MM/yyyy");
	  return dataformato.parse(converterDataForStringBR(data));
   }

   public static String validaTermoPedido(String termo) {
	  DecimalFormat df=new DecimalFormat("0000000");
	  return df.format(Integer.parseInt(termo.toString()));
   }

   // O metodo retornara null se a string nao for adequada
   public static Date convStringParaData(String dataS) {
	  Date data=null;
	  if(dataS != null) {
		 try {
			DateFormat formataData=DateFormat.getDateInstance(DateFormat.MEDIUM , LOCALE_BR);
			formataData.setLenient(false);
			data=formataData.parse(dataS);
		 } catch (Exception ex) {
			if(dataS.trim().length() > 0) {
			   System.out.println("Utilitaria:convStringParaData:Data Incorreta:" + dataS);
			}
			data=null;
		 }
	  }
	  return data;
   }

   /**
    * Este metodo monta a data com verificacao do mes de fevereiro.
    * 
    * @author OsmarJunior
    * @since 22/02/2012
    * @param dataS
    * @return
    */
   @SuppressWarnings("static-access")
   public static Date montaDataComVerificacao(String dia, String mes, String ano) {
	  Date data=null;
	  Integer diaInt;
	  Calendar calendar=new GregorianCalendar();
	  if(dia != null && mes != null && ano != null) {
		 try {
			if(mes.equals("02")) {
			   if(dia.equals("29") || dia.equals("30") || dia.equals("31")) {
				  dia="28";
				  calendar.set(Integer.parseInt(ano) , Integer.parseInt(mes) - 1 , Integer.parseInt(dia));
				  diaInt=calendar.getActualMaximum(calendar.DAY_OF_MONTH);
				  calendar=new GregorianCalendar();
				  calendar.set(Integer.parseInt(ano) , Integer.parseInt(mes) - 1 , diaInt);
			   } else {
				  calendar.set(Integer.parseInt(ano) , Integer.parseInt(mes) - 1 , Integer.parseInt(dia));
			   }
			} else if(dia.equals("31")) {
			   diaInt=10;
			   calendar.set(Integer.parseInt(ano) , Integer.parseInt(mes) - 1 , diaInt);
			   diaInt=calendar.getActualMaximum(calendar.DAY_OF_MONTH);
			   calendar=new GregorianCalendar();
			   calendar.set(Integer.parseInt(ano) , Integer.parseInt(mes) - 1 , diaInt);

			} else {
			   calendar.set(Integer.parseInt(ano) , Integer.parseInt(mes) - 1 , Integer.parseInt(dia));
			}

			DateFormat formataData=DateFormat.getDateInstance(DateFormat.MEDIUM , LOCALE_BR);
			formataData.setLenient(false);
			data=calendar.getTime();
			data=convertDataParaDataBR(data);
		 } catch (Exception ex) {

			System.out.println("Utilitaria:convStringParaData:Data Incorreta:");

			data=null;
		 }
	  }
	  return data;
   }

   public static Date zerarHoraMinutoSegundoMilisegundo(Date date) {
	  Calendar calendar=Calendar.getInstance();
	  calendar.setTime(date);
	  calendar.set(Calendar.HOUR_OF_DAY , calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
	  calendar.set(Calendar.MINUTE , calendar.getActualMinimum(Calendar.MINUTE));
	  calendar.set(Calendar.SECOND , calendar.getActualMinimum(Calendar.SECOND));
	  calendar.set(Calendar.MILLISECOND , calendar.getActualMinimum(Calendar.MILLISECOND));
	  return calendar.getTime();
   }

   public static Date maximizarHoraMinutoSegundoMilisegundo(Date date) {
	  Calendar calendar=Calendar.getInstance();
	  calendar.setTime(date);
	  calendar.set(Calendar.HOUR_OF_DAY , calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
	  calendar.set(Calendar.MINUTE , calendar.getActualMaximum(Calendar.MINUTE));
	  calendar.set(Calendar.SECOND , calendar.getActualMaximum(Calendar.SECOND));
	  calendar.set(Calendar.MILLISECOND , calendar.getActualMaximum(Calendar.MILLISECOND));
	  return calendar.getTime();
   }

   /**
    * Retorna a data com a hora 00:00:00
    * 
    * @author OsmarJunior
    * @param date
    * @return
    * @since 10/09/2014
    * @return Date
    */
   @SuppressWarnings("static-access")
   public static Date menorHoraDoDia(Date date) {
	  Calendar calendar=GregorianCalendar.getInstance();
	  calendar.setTime(date);
	  calendar.set(calendar.HOUR , 0);
	  calendar.set(Calendar.MINUTE , 00);
	  calendar.set(Calendar.SECOND , 00);
	  calendar.set(Calendar.MILLISECOND , 00);

	  // System.out.println(calendar.getTime());
	  return calendar.getTime();
   }

   /**
    * Retorna a data com a hora 23:59:59
    * 
    * @author OsmarJunior
    * @param date
    * @return
    * @since 10/09/2014
    * 
    */
   public static Date maiorHoraDoDia(Date date) {
	  Calendar calendar=GregorianCalendar.getInstance();
	  calendar.setTime(date);
	  calendar.set(Calendar.HOUR_OF_DAY , 23);
	  calendar.set(Calendar.MINUTE , 59);
	  calendar.set(Calendar.SECOND , 59);
	  calendar.set(Calendar.MILLISECOND , 59);
	  // System.out.println(calendar.getTime());
	  return calendar.getTime();
   }

   /**
    * Adiciona a hora 12:00 à data que foi passada
    * 
    * @author OsmarJunior
    * @param date
    * @return <code>Date</code>
    * @since 13/03/2014
    * 
    */
   public static Date adicionarHorarioEntradaNaDataHospedagem(Date date) {
	  Calendar calendar=Calendar.getInstance();

	  calendar.setTime(date);
	  // calendar.setTimeZone(TimeZone.getTimeZone("Brazil/East"));
	  calendar.set(Calendar.HOUR , 12);
	  calendar.set(Calendar.MINUTE , calendar.getActualMinimum(Calendar.MINUTE));
	  calendar.set(Calendar.SECOND , calendar.getActualMinimum(Calendar.SECOND));
	  calendar.set(Calendar.MILLISECOND , calendar.getActualMinimum(Calendar.MILLISECOND));
	  calendar.set(Calendar.AM_PM , 0);
	  return calendar.getTime();
   }

   /**
    * Adiciona 1 dia a data e Adiciona hora 11:59
    * 
    * @author OsmarJunior
    * @param date
    * @return <code>Date</code>
    * @since 13/03/2014
    * 
    */
   public static Date adicionarHorarioSaidaNaDataHospedagem(Date date) {
	  Calendar calendar=GregorianCalendar.getInstance();
	  calendar.setTime(date);
	  // calendar.setTimeZone(TimeZone.getTimeZone("Brazil/East"));
	  calendar.set(Calendar.HOUR , 11);
	  calendar.set(Calendar.MINUTE , calendar.getActualMaximum(Calendar.MINUTE));
	  calendar.set(Calendar.SECOND , calendar.getActualMaximum(Calendar.SECOND));
	  calendar.set(Calendar.MILLISECOND , calendar.getActualMaximum(Calendar.MILLISECOND));
	  calendar.add(Calendar.DAY_OF_MONTH , 1);
	  calendar.set(Calendar.AM_PM , 0);
	  return calendar.getTime();
   }

   public static Date adicionarDiaNaData(Date date, int dias) {
	  Calendar calendar=GregorianCalendar.getInstance();
	  calendar.setTime(date);
	  // calendar.setTimeZone(TimeZone.getTimeZone("Brazil/East"));
	  calendar.add(Calendar.DAY_OF_MONTH , dias);
	  calendar.set(Calendar.AM_PM , 0);
	  return calendar.getTime();
   }

   public static final String anoDaData(Date data) {

	  SimpleDateFormat out=new SimpleDateFormat("yyyy");
	  return out.format(data);
   }

   public static final String mesDaData(Date data) {
	  SimpleDateFormat out=new SimpleDateFormat("MM");
	  return out.format(data);
   }

   public static final String diaDaData(Date data) {
	  SimpleDateFormat out=new SimpleDateFormat("dd");
	  return out.format(data);
   }

   public static final String anoMesDaData(Date data) {

	  SimpleDateFormat out=new SimpleDateFormat("MM/yyyy");
	  return out.format(data);
   }

   public static final String mesDoAnoMes(String data) {
	  String mes=data.substring(0 , 2);
	  return mes;
   }

   public static final String anoDoAnoMes(String data) {
	  String ano=data.substring(3 , 7);
	  return ano;
   }

   /**
    * Verifica se mesAno e valido
    * 
    * @param mesAno
    *           - formato
    * @return boolean - false se nao estiver no format especificado e true se estiver no format especificado
    */
   public static boolean isMesAno(String mesAno) {
	  String pattern="MMyyyy";
	  if(mesAno == null || mesAno.length() != pattern.length()) {
		 return false;
	  }
	  SimpleDateFormat formato=new SimpleDateFormat(pattern);
	  formato.setLenient(false);
	  try {
		 formato.parse(mesAno);
	  } catch (ParseException e) {
		 return false;
	  }
	  return true;
   }

   /**
    * Verifica se anoMes e valido
    * 
    * @param anoMes
    *           - formato
    * @return boolean - false se nao estiver no format especificado e true se estiver no format especificado
    */
   public static boolean isAnoMes(String anoMes) {
	  if(anoMes == null) {
		 return false;
	  }
	  SimpleDateFormat formato=new SimpleDateFormat("yyyyMM");
	  formato.setLenient(false);
	  try {
		 formato.parse(anoMes);
	  } catch (ParseException e) {
		 return false;
	  }
	  return true;
   }

   public static String retiraMascaras(String campo) {
	  campo=campo.trim();
	  char[] v=campo.toCharArray();
	  StringBuffer res=new StringBuffer("");
	  for(int i=0; i < v.length; i++) {
		 if(Character.isLetterOrDigit(v[i])) {
			res.append(v[i]);
		 }
	  }
	  return res.toString();
   }

   public static String matriculaDV(String valores) {
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
			multiplicador-=11;
		 arrayCalculada1[i]=arrayNumeros[i] * multiplicador;
		 multiplicador=multiplicador + 1;
		 somaArray+=arrayCalculada1[i];
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
			multiplicador-=11;
		 if(i == vlExtendido - 1) {
			arrayCalculada2[i]=dv1 * multiplicador;
		 } else {
			arrayCalculada2[i]=arrayNumeros[i] * multiplicador;
		 }
		 multiplicador=multiplicador + 1;
		 somaArray+=arrayCalculada2[i];
	  }

	  if(somaArray % 11 == 10) {
		 dv2=1;
	  } else {
		 dv2=somaArray % 11;
	  }
	  cv=dv1 + "" + dv2;

	  return cv;
   }

   public static int diferencaEmMesesEntreMesAno(String mesAnoInicio, String mesAnoFim) throws FormatoInadequadoException {
	  if(mesAnoInicio == null || !isMesAno(mesAnoInicio.replaceAll("[\\p{Punct}]" , ""))) {
		 throw new FormatoInadequadoException("Mes/Ano de inicio invalido!");
	  } else if(mesAnoFim == null || !isMesAno(mesAnoFim.replaceAll("[\\p{Punct}]" , ""))) {
		 throw new FormatoInadequadoException("Mes/Ano de fim invalido!");
	  } else {
		 String anoMesInicio=convMesAnoParaAnoMes(mesAnoInicio);
		 String anoMesFim=convMesAnoParaAnoMes(mesAnoFim);

		 return diferencaEmMesesEntreAnoMes(anoMesInicio , anoMesFim);
	  }
   }

   public static int diferencaEmMesesEntreAnoMes(String anoMesInicio, String anoMesFim) throws FormatoInadequadoException {
	  if(anoMesInicio == null || !isAnoMes(anoMesInicio.replaceAll("[\\p{Punct}]" , ""))) {
		 throw new FormatoInadequadoException("Mes/Ano de inicio invalido!");
	  } else if(anoMesFim == null || !isAnoMes(anoMesFim.replaceAll("[\\p{Punct}]" , ""))) {
		 throw new FormatoInadequadoException("Mes/Ano de fim invalido!");
	  } else {
		 return diferencaEmMesesEntreAnoMes(Integer.parseInt(anoMesInicio) , Integer.parseInt(anoMesFim));
	  }
   }

   public static int diferencaEmMesesEntreAnoMes(int anoMesInicio, int anoMesFim) throws FormatoInadequadoException {
	  if( !isAnoMes(anoMesInicio)) {
		 throw new FormatoInadequadoException("Mes/Ano de inicio invalido!");
	  } else if( !isAnoMes(anoMesFim)) {
		 throw new FormatoInadequadoException("Mes/Ano de fim invalido!");
	  } else {
		 int anoInicio=anoMesInicio / 100;
		 int mesInicio=anoMesInicio % 100;
		 int anoFim=anoMesFim / 100;
		 int mesFim=anoMesFim % 100;

		 int difAno=anoFim - anoInicio;
		 int dif=(mesFim + difAno * 12) - mesInicio;

		 return dif;
	  }
   }

   /**
    * Converte um mesAno para formato do banco
    * 
    * @param mesAno
    *           <code>String</code> no formato mmaaaa
    * @return <code>String</code> no formato aaaamm
    * @author osmar
    * @since 03/10/2012
    */
   public static String convMesAnoParaAnoMes(String mesAno) {
	  if(mesAno == null || mesAno.length() != 6) {
		 return null;
	  }
	  return mesAno.substring(2) + mesAno.substring(0 , 2);
   }

   /**
    * Converte um mesAno para formato do banco
    * 
    * @param mesAno
    *           <code>String</code> no formato mmaaaa
    * @return <code>int</code> no formato aaaamm
    * @author osmar
    * @since 03/02/2012
    */
   public static int convMesAnoParaAnoMesInt(String mesAno) {
	  if(mesAno == null || mesAno.length() != 6) {
		 return 0;
	  }
	  return Integer.parseInt(mesAno.substring(2) + mesAno.substring(0 , 2));
   }

   /**
    * Verifica se anoMes e valido
    * 
    * @param anoMes
    *           - formato
    * @return boolean - false se nao estiver no format especificado e true se estiver no format especificado
    */
   public static boolean isAnoMes(int anoMes) {
	  return isAnoMes(String.valueOf(anoMes));
   }

   public static Calendar montarCalendar(String dia, String mes, String ano) {
	  Calendar calTemp=Calendar.getInstance(LOCALE_BR);
	  calTemp.set(Integer.parseInt(ano) , Integer.parseInt(mes) , Integer.parseInt(dia));
	  // System.out.println(calTemp.getTime());
	  return calTemp;
   }

   /**
    * 
    * @author OsmarJunior
    * @param qntMeses
    * @return
    * 
    * @since 28/02/2012
    */
   public static Date subtraiMesesDaDataAtual(String qntMeses) {

	  if(qntMeses == null || "".equals(qntMeses)) {
		 qntMeses="0";
		 return null;
	  }
	  Integer mesInt=Integer.parseInt(qntMeses);
	  mesInt=mesInt * -1;
	  Date d=new Date();
	  Calendar cal=new GregorianCalendar();
	  cal.setTime(d);
	  cal.add(Calendar.MONTH , mesInt);

	  return cal.getTime();
   }

   public static Boolean isDataVencida(Date data) {
	  SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
	  Date hoje=new Date();
	  Boolean vencida=false;

	  try {
		 hoje=format.parse(Utilitario.converterDataForStringBR(hoje));

		 data=format.parse(Utilitario.converterDataForStringBR(data));

		 if(data.after(new Date()) || data.equals(hoje)) {
			vencida=Boolean.FALSE;
		 } else if(data.before(new Date())) {
			vencida=Boolean.TRUE;
		 }
	  } catch (ParseException e) {
		 e.printStackTrace();
	  }

	  return vencida;
   }

   public static int calculeDVModulo11(String numero) {
	  int dv=0;
	  int[] values=new int[numero.length()];
	  for(int i=0; i < numero.length(); i++) {
		 values[i]=Integer.parseInt(numero.charAt(i) + "");
	  }
	  int soma=0;
	  int vetpos=numero.length() - 1;
	  while (vetpos >= 0) {
		 for(int i=2; i < 10; i++) {
			soma+=values[vetpos] * i;
			vetpos--;
			if(vetpos < 0)
			   break;
		 }
	  }

	  if(soma < 11) {
		 dv=soma - 11;
	  } else {
		 int resto=soma % 11;
		 dv=11 - resto;
	  }

	  if(dv > 9)
		 dv=0;

	  return dv;
   }

   /**
    * 
    * @autor OsmarJunior
    * @param dataDateInicio
    * @param dataDateFim
    * @return
    * @since 10/08/2012
    * 
    */
   public static long diffDias(Date dataDateInicio, Date dataDateFim) {
	  Calendar dataInicio=Calendar.getInstance();

	  dataInicio.setTime(dataDateInicio);

	  Calendar dataFinal=Calendar.getInstance();
	  dataFinal.setTime(dataDateFim);

	  long diferenca=dataFinal.getTimeInMillis() -

		 dataInicio.getTimeInMillis();

	  int tempoDia=(1000 * 60 * 60 * 24);

	  long diasDiferenca=diferenca / tempoDia;

	  return diasDiferenca;
   }

   /**
    * 
    * @autor OsmarJunior
    * @param dataDateInicio
    * @param dataDateFim
    * @return
    * @since 10/08/2012
    * 
    */
   public static Integer diffDiasInt(Date dataDateInicio, Date dataDateFim) {
	  Calendar dataInicio=Calendar.getInstance();

	  dataInicio.setTime(dataDateInicio);

	  Calendar dataFinal=Calendar.getInstance();
	  dataFinal.setTime(dataDateFim);

	  long diferenca=dataFinal.getTimeInMillis() - dataInicio.getTimeInMillis();

	  int tempoDia=(1000 * 60 * 60 * 24);

	  Integer diasDiferenca=(int) (diferenca / tempoDia);

	  return diasDiferenca;
   }

   /**
    * 
    * @autor OsmarJunior
    * @param data
    * @return Se da data estiver vencida retorna TRUE
    * @since 10/08/2012
    * 
    */
   public static Boolean verificaVencimento(Date data) {
	  if(diffDias(data , new Date()) > 0) {
		 return Boolean.TRUE;
	  } else {
		 return Boolean.FALSE;
	  }

   }

   public static Integer calculaCheckOutHospedagem(Date dataIn, Date dataOut, BigDecimal valorDiaria, Integer carenciaIN, Integer carenciaOut) {

	  Calendar calendarIn=Calendar.getInstance();
	  calendarIn.setTime(dataIn);
	  Calendar calendarOut=Calendar.getInstance();
	  calendarOut.setTime(dataOut);

	  Integer horaEntrada=calendarIn.get(Calendar.HOUR_OF_DAY);
	  Integer horaSaida=calendarOut.get(Calendar.HOUR_OF_DAY);
	  Integer diarias=0;
	  horaEntrada=horaEntrada + carenciaIN;
	  horaSaida=horaSaida - carenciaOut;
	  if(horaEntrada < 12) {
		 diarias=diarias + 1;
	  }
	  if(horaSaida > 11) {
		 diarias=diarias + 1;
	  }
	  diarias=diarias + Utilitario.diffDiasInt(dataIn , dataOut);

	  return diarias;
   }

   /**
    * É passado um Date com hora e o metódo dominui os minutos também passados.
    * 
    * @author OsmarJunior
    * @param data
    * @param minutos
    * @return
    * @throws ParseException
    * @since 27 de abr de 2016
    *
    */
   public static Date diminuiTempoDaData(Date data, int minutos) throws ParseException {
	  Calendar c=Calendar.getInstance();
	  c.setTime(data);
	  c.setTimeZone(TimeZone.getTimeZone("GMT-3"));
	  c.add(Calendar.MINUTE , -minutos);
	  return c.getTime();
   }
}
