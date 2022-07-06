package br.com.datasind.ciclovida;

import java.util.HashMap;

import br.com.datasind.aplicacao.ConfiguracaoDataSind;

public class FabricaCicloVida {

	private static HashMap<ClassLoader, FabricaCicloVida> cache = new HashMap<ClassLoader, FabricaCicloVida>();

	public static synchronized FabricaCicloVida getInstancia() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		FabricaCicloVida obj = cache.get(classLoader);
		if (obj == null) {
			obj = new FabricaCicloVida();
			cache.put(classLoader, obj);
		}
		return obj;
	}

	private FabricaCicloVida() {

	}

	public CicloVida getCicloVida(Object object) {
		if (object == null) {
			return null;
		}
		String nome = object.getClass().getName();
		int indice = nome.lastIndexOf('.');

		String nomeCicloVida = ConfiguracaoDataSind.getInstancia().get("dataSind.cicloVida.pacote") + ".CicloVida" + nome.substring(indice + 1);

		CicloVida cicloVida = null;
		try {
			cicloVida = (CicloVida) Class.forName(nomeCicloVida).newInstance();
			return cicloVida;
		} catch (Exception e) {

		}
		return null;
	}
}
