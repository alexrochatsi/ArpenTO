package br.com.datasind.importacao;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory factory;
	private static Logger logger = Logger.getLogger(HibernateUtil.class);

	static {
		Configuration configuration = new AnnotationConfiguration();

		Properties properties = new Properties();

		properties.put("hibernate.connection.username", "tecnotin_datasin");
		// properties.put( "hibernate.connection.username", "root" );
		properties.put("hibernate.connection.password", "rootdatasind1313131313");
		// ConfiguracaoDB configuracaoDB = new ConfiguracaoDB();
		// String nomeBD = configuracaoDB.pathBD();

		properties.put("hibernate.connection.url", "jdbc:mysql://localhost/tecnotin_dataproc");

		configuration.setProperties(properties);
		configuration.configure();

		factory = configuration.buildSessionFactory();
	}

	public static Session getSession() {
		try {
			logger.info("Open new Session");
			return factory.openSession();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro", "Erro Connection", JOptionPane.ERROR_MESSAGE);
			return null;
		}

	}

}
