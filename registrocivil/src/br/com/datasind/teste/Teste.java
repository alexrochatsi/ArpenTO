package br.com.datasind.teste;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Teste {

	/**
	 * @author OsmarJunior
	 * @param args
	 * @since 24/04/2013
	 * 
	 */

	@SuppressWarnings({"unused", "deprecation"})
   public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String temp = "0578";

		// System.out.println(UtilitarioString.calculeDVModulo11("221970"));
		// System.out.println(UtilitarioString.calculeDVModulo11("204515"));
		// System.out.println(UtilitarioString.calculaDvCNPJ("024076660001"));
		//
		// System.out.println(UtilitarioString.calculaDVModulo11("533939"));

		Date data = new Date("15/03/2015 10:00:00");
		Integer minutos = 60;

		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.setTimeZone(TimeZone.getTimeZone("GMT-3"));
		c.add(Calendar.MINUTE, -55);

		System.out.println(c.getTime());

	}
}
