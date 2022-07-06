package br.com.datasind.gerente;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FabricaGerente extends FabricaGerentePadrao implements ApplicationContextAware {
	public static final String NOME = "fabricaGerente";

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public GerenteControleAcesso getGerenteControleAcesso() {
		return (GerenteControleAcesso) applicationContext.getBean("gerenteControleAcesso");
	}
	
	public GerenteRelatorio getGerenteRelatorio() {
		return (GerenteRelatorio) applicationContext.getBean("gerenteRelatorio");
	}

	@Override
	public GerenteTransacao getGerenteTransacao() {
		return (GerenteTransacao) applicationContext.getBean("gerenteTransacao");
	}

	public GerenteArquivos getGerenteArquivos() {
		return (GerenteArquivos) applicationContext.getBean("gerenteArquivos");
	}

}
