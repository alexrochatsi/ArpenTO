package br.com.datasind.conversao.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.datasind.aplicacao.ApplicationException;

public class Codificador {

	/**
	 * Codifica a senha informada no login p/ trafegar na rede (internet)
	 */
	public String codifica(String str) {
		int tam = str.length();
		String par = "";
		String impar = "";
		int um = 1;
		int dois = 2;
		int incr1 = 30;
		int incr2 = 35;
		for (int j = 0; j < um; j++)
			for (int i = 0; i < tam; i += dois) {
				par = par + (char) (str.charAt(i) + (incr1));
				if (i < (tam - um))
					impar = impar + (char) (str.charAt(i + um) + (incr2));
			}

		return str;
	}

	/**
	 * Decodifica a senha trafegada na rede (internet)
	 */
	public String decodifica(String str) {
		int tam = str.length();
		int met = (int) (tam / 2);
		String impar = str.substring(0, met);
		String par = str.substring(met);
		str = "";
		for (int i = 0; i < par.length(); i++) {
			str = str + (char) (par.charAt(i) - 30);
			if (i == (par.length() - 1)) {
				if (par.length() == impar.length())
					str = str + (char) (impar.charAt(i) - 35);
			} else {
				str = str + (char) (impar.charAt(i) - 35);
			}
		}

		return str;
	}

	public static String codificaSenhaMD5(String senha) throws ApplicationException {

		byte[] defaultBytes = senha.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			/*
			 * System.out.println( "sessionid " + senha + " md5 version is " +
			 * hexString.toString());
			 */
			senha = hexString + "";
		} catch (NoSuchAlgorithmException nsae) {
			throw new ApplicationException(nsae);
		}
		return senha;
	}
	
	public static String geraUID(int lenght) {
		int length = lenght;
		char[] allowedCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
				'W', 'X', 'Y', 'Z'};
		
		String tmp = "";
		for (int i = 0; i < length; i++) {
			int c = (int) Math.floor(Math.random()*allowedCharacters.length);
			tmp += allowedCharacters[c];
		}
		return tmp.toString();
	}

}
