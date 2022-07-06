package br.com.datasind.preparacao;

import java.util.HashMap;
import java.util.Map;

import br.com.datasind.aplicacao.ConfiguracaoDataSind;

public class FabricaPreparador {
	private Map<String, Preparador> mapaPreparadores;

	private static HashMap<ClassLoader, FabricaPreparador> cache = new HashMap<ClassLoader, FabricaPreparador>();

	private PreparadorPadrao preparadorPadrao;

	/**
	 * 
	 */
	private FabricaPreparador() {
		super();
	}

	/**
	 * @param object
	 * @return
	 */
	public Preparador getPreparador(Object object) {
		if (object == null) {
			return null;
		}
		String nome = object.getClass().getName();
		int indice = nome.lastIndexOf('.');

		String nomeValidador = ConfiguracaoDataSind.getInstancia().get("dataSind.preparacao.pacote") + ".Preparador" + nome.substring(indice + 1);

		Preparador preparador;

		try {
			preparador = (Preparador) getMapaPreparadores().get(nomeValidador);
			if (preparador == null) {

				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				preparador = (Preparador) classLoader.loadClass(nomeValidador).newInstance();

				// preparador = (Preparador)
				// Class.forName(nomeValidador).newInstance();
				getMapaPreparadores().put(nomeValidador, preparador);
			}
			return preparador;

		} catch (InstantiationException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			return getPreparadorPadrao();
		}

		return null;
	}

	private Map<String, Preparador> getMapaPreparadores() {
		if (mapaPreparadores == null) {
			mapaPreparadores = new HashMap<String, Preparador>();
		}
		return mapaPreparadores;
	}

	/**
	 * @return
	 */
	private PreparadorPadrao getPreparadorPadrao() {
		if (preparadorPadrao == null) {
			preparadorPadrao = new PreparadorPadrao();
		}
		return preparadorPadrao;
	}

	/**
	 * @return
	 */
	public static FabricaPreparador getInstancia() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		FabricaPreparador obj = cache.get(classLoader);
		if (obj == null) {
			obj = new FabricaPreparador();
			cache.put(classLoader, obj);
		}
		return obj;
	}
}
