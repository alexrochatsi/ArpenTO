package br.com.datasind.aplicacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfiguracaoDataSind {

	private Log logger = LogFactory.getLog(ConfiguracaoDataSind.class);

	private Properties properties;

	private static Map<ClassLoader, ConfiguracaoDataSind> instances = new HashMap<ClassLoader, ConfiguracaoDataSind>();

	private ConfiguracaoDataSind() {
		properties = new Properties();
		ClassLoader classLoader = null;
		try {

			classLoader = Thread.currentThread().getContextClassLoader();
			logger.debug("Tentando configurar o DataSind a partir do arquivo " + classLoader.getResource("/") + "dataSind.properties");

			properties.load(classLoader.getResourceAsStream("/dataSind.properties"));

		} catch (IOException e) {
			logger.error("Problemas configurando o DataSind a partir do arquivo " + classLoader.getResource("/") + "dataSind.properties", e);
		}
	}

	public synchronized static ConfiguracaoDataSind getInstancia() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ConfiguracaoDataSind instancia = instances.get(classLoader);
		if (instancia == null) {
			instancia = new ConfiguracaoDataSind();
			instances.put(classLoader, instancia);
		}
		return instancia;
	}

	public Object get(String chave) {
		Object obj = properties.get(chave);
		if (obj == null) {
			obj = getArray(chave);
		}
		return obj;
	}

	private Object getArray(String chave) {
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
