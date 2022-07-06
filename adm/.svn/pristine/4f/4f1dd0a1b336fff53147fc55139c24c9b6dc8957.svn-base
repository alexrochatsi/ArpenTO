
package br.com.datasind.inicializador;

import java.util.HashMap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.datasind.aplicacao.ConfiguracaoDataSind;

public class FabricaInicializador{
   private BeanFactory beanFactory=null;

   private static HashMap<ClassLoader, FabricaInicializador> cache=new HashMap<ClassLoader, FabricaInicializador>();

   private FabricaInicializador() {

   }

   public static synchronized FabricaInicializador getInstancia() {
	  ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
	  FabricaInicializador obj=cache.get(classLoader);
	  if(obj == null) {
		 obj=new FabricaInicializador();
		 cache.put(classLoader , obj);
	  }
	  return obj;
   }

   public Inicializador getInicializador(String uso) {
	  Object object=getBeanFactory().getBean(uso);
	  if( !(object instanceof Inicializador)) {
		 throw new RuntimeException("O objeto encontrado nao e um Inicializador");
	  }

	  return (Inicializador) object;
   }

   public void initialize() {
	  ConfiguracaoDataSind configuracao=ConfiguracaoDataSind.getInstancia();
	  
	  beanFactory=new ClassPathXmlApplicationContext((String) configuracao.get("dataSind.inicializacao.config"));
	  
   }

   private BeanFactory getBeanFactory() {
	  if(beanFactory == null) {
		 initialize();
	  }
	  return beanFactory;
   }

}
