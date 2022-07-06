package br.com.datasind.validacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import br.com.datasind.aplicacao.ConfiguracaoDataSind;

public class FabricaValidador {
	private static final Logger logger = Logger.getLogger(FabricaValidador.class);

	private Map<String, Validador> mapaValidadores;

	private ContextoValidador contextoValidador;

	private static HashMap<ClassLoader, FabricaValidador> cache = new HashMap<ClassLoader, FabricaValidador>();

	private Validador validadorBase = new ValidadorBase();

	public ContextoValidador getContextoValidador() {
		return contextoValidador;
	}

	public void setContextoValidador(ContextoValidador contextoValidador) {
		this.contextoValidador = contextoValidador;
	}

	public static synchronized FabricaValidador getInstancia(ContextoValidador contextoValidador) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		FabricaValidador obj = cache.get(classLoader);
		if (obj == null) {
			obj = new FabricaValidador();
			cache.put(classLoader, obj);
		}
		return obj;
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

	public Validador getValidador(Object object) {
		if (object == null) {
			return null;
		}
		logger.debug("Procurando validador para " + object);

		Validador validador = null;
		Iterator<String> nomes = getNomes(object.getClass()).iterator();

		while (validador == null && nomes.hasNext()) {
			String nomeValidador = ConfiguracaoDataSind.getInstancia().get("dataSind.validacao.pacote") + ".Validador" + nomes.next();

			logger.debug("Analizando o nome " + nomeValidador);
			try {
				logger.debug("Procurando no mapa de validadores");
				validador = (Validador) getMapaValidadores().get(nomeValidador);

				if (validador == null) {
					logger.debug("Nao encontrado no mapa de validadores");

					logger.debug("Tentando instanciar " + nomeValidador);
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					validador = (Validador) classLoader.loadClass(nomeValidador).newInstance();
					// validador = (Validador)
					// Class.forName(nomeValidador).newInstance();

					logger.debug("Setando objeto de contexto");
					validador.setContexto(getContextoValidador());

					logger.debug("Colocando validador no mapa de validadores");
					getMapaValidadores().put(nomeValidador, validador);
				}

			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
			}
		}

		if (validador == null) {
			validador = validadorBase;
		}

		logger.debug("Retornando validador");
		return validador;
	}

	public Map<String, Validador> getMapaValidadores() {
		if (mapaValidadores == null) {
			mapaValidadores = new TreeMap<String, Validador>();
		}
		return mapaValidadores;
	}

}
