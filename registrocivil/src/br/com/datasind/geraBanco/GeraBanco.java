
package br.com.datasind.geraBanco;

import java.util.Properties;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

public class GeraBanco{

   public static void main(String[] args) {

	  AnnotationConfiguration cfg=new AnnotationConfiguration();
	  Properties properties=new Properties();

	  properties.put("hibernate.connection.username" , "arpen1br3");
	  properties.put("hibernate.connection.password" , "grZZtgAQ2B");
	  //properties.put("hibernate.connection.url" , "jdbc:mysql://mysql.arpento.org.br/arpen1br3");
	  properties.put("hibernate.connection.url" , "jdbc:mysql://localhost/arpen1br3");
	  cfg.setProperties(properties);
	  cfg.configure();
	  SchemaUpdate se=new SchemaUpdate(cfg);
	  se.execute(true , true);
   }
}
