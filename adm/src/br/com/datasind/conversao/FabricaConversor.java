package br.com.datasind.conversao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.datasind.aplicacao.ConfiguracaoDataSind;

public class FabricaConversor {

	private Map<String, Conversor> mapaConversores;

	private ConversorPadrao conversorPadrao;

	private static HashMap<ClassLoader, FabricaConversor> cache = new HashMap<ClassLoader, FabricaConversor>();

	private static final Logger logger = Logger.getLogger(FabricaConversor.class);

	public static synchronized FabricaConversor getInstancia() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		FabricaConversor obj = cache.get(classLoader);
		if (obj == null) {
			obj = new FabricaConversor();
			cache.put(classLoader, obj);
		}
		return obj;
	}

	private FabricaConversor() {

	}

	@SuppressWarnings("rawtypes")
	private List<String> getNomes(Class entidade) {
		Class current = entidade;
		List<String> result = new ArrayList<String>(5);
		while (current != null) {
			String nome = current.getName();
			nome = nome.substring(nome.lastIndexOf('.') + 1);

			result.add(nome);
			current = current.getSuperclass();
		}
		return result;
	}

	public Conversor getConversor(Object object) {
		if (object == null) {
			return null;
		}
		logger.debug("Procurando validador para " + object);

		Conversor conversor = null;
		Iterator<String> nomes = getNomes(object.getClass()).iterator();

		while (conversor == null && nomes.hasNext()) {
			String nomeConversor = ConfiguracaoDataSind.getInstancia().get("dataSind.conversao.pacote") + ".Conversor" + nomes.next();

			logger.debug("Analizando o nome " + nomeConversor);
			try {
				logger.debug("Procurando no mapa de conversores");
				conversor = (Conversor) getMapaConversores().get(nomeConversor);

				if (conversor == null) {
					logger.debug("Nao encontrado no mapa de conversores");

					logger.debug("Tentando instanciar " + nomeConversor);
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					conversor = (Conversor) classLoader.loadClass(nomeConversor).newInstance();
					// conversor = (Conversor)
					// Class.forName(nomeValidador).newInstance();

					logger.debug("Colocando validador no mapa de conversores");
					getMapaConversores().put(nomeConversor, conversor);
				}

			} catch (Exception e) {
				logger.debug(e.getClass() + ": " + e.getMessage());
			}
		}

		if (conversor == null) {
			conversor = getConversorPadrao();
		}

		logger.debug("Retornando conversor");
		return conversor;
	}

	private Map<String, Conversor> getMapaConversores() {
		if (mapaConversores == null) {
			mapaConversores = new HashMap<String, Conversor>();
		}
		return mapaConversores;
	}

	private ConversorPadrao getConversorPadrao() {
		if (conversorPadrao == null) {
			conversorPadrao = new ConversorPadrao();
		}
		return conversorPadrao;
	}

}
