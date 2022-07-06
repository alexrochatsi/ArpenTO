package br.com.datasind.aplicacao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Configuracao implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Configuracao instancia;

	private Properties properties;

	private Configuracao() {
		properties = new Properties();
		try {
		//	System.out.println(System.getProperty("user.dir"));
			properties.load(getClass().getResourceAsStream("/sgi.properties"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Configuracao getInstancia() {
		if (instancia == null) {
			instancia = new Configuracao();
		}
		return instancia;
	}

	public Object get(String chave) {
		Object obj = properties.get(chave);
		if(obj == null){
			obj = getArray(chave);
		}
		return obj;
	}
	
	private Object getArray(String chave){
		List<String> array = new ArrayList<String>();
		
		String current = (String) properties.get(chave + "[0]");
		for (int i = 1; current != null; i++) {
			array.add(current);
			current = (String) properties.get(chave + "[" + i + "]");
		}
		
		if (array.size() == 0) {
			return null;
		}
		
		return array.toArray();
	}
}
