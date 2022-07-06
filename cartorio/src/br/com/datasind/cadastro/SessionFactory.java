
package br.com.datasind.cadastro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.datasind.aplicacao.ConfiguracaoDB;

public class SessionFactory{
   private static Configuration configuration;

   protected static Logger logger=LogManager.getLogger(SessionFactory.class);

   protected org.hibernate.SessionFactory sessionFactory;

   /**
    * @return Returns the sessionFactory.
    * @throws HibernateException
    */
   public synchronized Session createSession() throws HibernateException {
	  return getSessionFactory().openSession();
   }

   /**
    * @return Returns the sessionFactory.
    * @throws HibernateException
    */
   public synchronized Session createSession(Connection connection) throws HibernateException {
	  return getSessionFactory().openSession(connection);
   }

   /**
    * @return Returns the sessionFactory.
    * @throws HibernateException
    */
   public synchronized Session createSession(Interceptor interceptor) throws HibernateException {
	  return getSessionFactory().openSession(interceptor);
   }

   /**
    * @return Returns the sessionFactory.
    * @throws HibernateException
    */
   public synchronized Session createSession(Connection connection, Interceptor interceptor) throws HibernateException {
	  return getSessionFactory().openSession(connection , interceptor);
   }

   public void initialize() throws HibernateException {
	  getConfiguration();
   }

   public synchronized void exportSchema() throws HibernateException {
	  new SchemaExport(getConfiguration()).create(true , true);
   }

   private org.hibernate.SessionFactory getSessionFactory() throws HibernateException {

	  logger.info("Iniciando configuracao do Hibernate");

	  if(sessionFactory == null) {
		 try {
			Object configurationFileName=System.getenv("HIBERNATE_CONFIGURATION_FILE");

			boolean serialize=configurationFileName != null;
			boolean read;

			if(serialize) {
			   File configurationFile=new File((String) configurationFileName);
			   logger.debug("Tentando ler configuracao serializada");

			   read=configurationFile.exists() && configurationFile.canRead();
			   if(read) {
				  ObjectInputStream inputStream=null;
				  try {
					 inputStream=new ObjectInputStream(new FileInputStream(configurationFile));

					 configuration=(Configuration) inputStream.readObject();

					 read=true;

				  } catch (Exception e) {
					 logger.error("Problemas tentando ler a configuracao serializada" , e);
					 read=false;
				  } finally {
					 if(inputStream != null) {
						try {
						   inputStream.close();
						} catch (Exception e) {
						   logger.error(e);
						}
					 }
				  }
			   }

			   if( !read) {
				  logger.debug("Arquivo de configuracao serializado nao existe ou nao pode ser lido");
				  logger.debug("Criando nova configuracao");
				  configuration=createConfiguration();

				  ObjectOutputStream outputStream=null;
				  try {
					 logger.debug("Serializando a configuracao criada");
					 outputStream=new ObjectOutputStream(new FileOutputStream(configurationFile));

					 outputStream.writeObject(configuration);
				  } catch (Exception e) {
					 logger.error("Problemas serializando configuracao" , e);
				  } finally {
					 if(outputStream != null) {
						try {
						   outputStream.close();
						} catch (Exception e) {
						   logger.error(e);
						}
					 }
				  }
			   }
			}
			if( !serialize) {
			   configuration=createConfiguration();
			}

			sessionFactory=configuration.buildSessionFactory();
			logger.info("configuracao concluida");

		 } catch (MappingException e) {
			throw new RuntimeException(e);

		 } catch (Exception e) {
			throw new RuntimeException(e);
		 }
	  }
	  return sessionFactory;
   }

   /**
    * 
    */
   private SessionFactory() {
	  super();
   }

   private Configuration getConfiguration() throws HibernateException {
	  if(configuration == null) {
		 getSessionFactory();
	  }
	  return configuration;
   }

   private static Configuration createConfiguration() {
	  logger.debug("Construindo configuracao do hibernate dos arquivos de mapeamento");

	  Configuration configuration=new AnnotationConfiguration();

	  Properties properties=new Properties();
	  properties.put("hibernate.connection.username" , "arpen1br3");

	  ConfiguracaoDB configuracaoDB=new ConfiguracaoDB();
	  String nomeBD=configuracaoDB.pathBD();

	  properties.put("hibernate.connection.url" , "jdbc:mysql://localhost/" + nomeBD);
	  //properties.put("hibernate.connection.url","jdbc:mysql://mysql.arpento.org.br/"+nomeBD);

	  properties.put("hibernate.connection.password" , "grZZtgAQ2B");
	  configuration.setProperties(properties);

	  configuration.configure();
	  return configuration;

   }
}
