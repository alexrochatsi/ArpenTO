package br.com.datasind.aplicacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfiguracaoDB {

	private static final Logger logger = Logger.getLogger(ConfiguracaoDB.class);

	private Properties properties;

	public String pathBD() {

		logger.debug("Buscando Instancia do BD ");
		File retorno = null;
		String bd = null;
		try {
			retorno = getDiretorioArquivoBD();
			retorno = new File(retorno.getAbsolutePath() + "/bd.properties");

			InputStream input = new FileInputStream(retorno.getAbsolutePath());
			properties = new Properties();
			properties.load(input);

			bd = properties.getProperty("bd");
			if (bd == null) {
				throw new IOException();
			}

			input.close();

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.fatal("Erro *********** Arquivo de configuracao do banco nao encontrado", e);
		} catch (IOException e) {
			logger.fatal("Erro *********** Arquivo de configuracaoo do banco nao encontrado ou incorreto", e);
		}

		return bd;
	}

	public File getDiretorioArquivoBD() throws ApplicationException {

		Configuracao configuracao = Configuracao.getInstancia();

		File diretorioRetornoSindical = new File(getContext() + String.valueOf(configuracao.get("sgi.configuracao.bd")));
		if (!diretorioRetornoSindical.exists()) {
			diretorioRetornoSindical.mkdirs();
		}

		return diretorioRetornoSindical;
	}

	private String getContext() throws ApplicationException {

		String className = "/" + getClass().getName().replace('.', '/') +
				".class";
		URL u = getClass().getResource(className);
		String caracteres = u.toString();
		int posicao = caracteres.indexOf("registrocivil");

		caracteres = caracteres.substring(0, posicao + 17);

		caracteres = caracteres.substring(5, posicao + 14);

		return caracteres;
	}

}
