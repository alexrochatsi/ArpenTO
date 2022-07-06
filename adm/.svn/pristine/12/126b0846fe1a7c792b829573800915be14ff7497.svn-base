package br.com.datasind.inicializacao;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import br.com.datasind.reflection.MethodUtils;



/**
 * @Author osmar
 * @since 26/12/2011
 *
 **/
public class InicializadorPath implements Inicializador {
	private static Log logger = LogFactory.getLog(InicializadorPath.class);
	
	@SuppressWarnings("rawtypes")
   private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
	
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	
	
	private String[] paths;

	public void inicializar(Object object) {
		for (int i = 0; i < paths.length; i++) {
			inicializarPath(object, paths[i].split("\\."));
		}
	}
	
	private void inicializarPath(Object object, String[] path){
		if(path.length == 0){
			Hibernate.initialize(object);
			return;
		}
		
		String getterName = "get" + Character.toUpperCase(path[0].charAt(0))
				+ path[0].substring(1);
		
		Method method = MethodUtils.getMethod(object.getClass(), getterName,
				EMPTY_CLASS_ARRAY);
		
		if(method == null) {
			throw new RuntimeException("Nao foi encontrado o metodo "
					+ getterName + " em " + object);
		}
		
		try {
			Object result = method.invoke(object, EMPTY_OBJECT_ARRAY);
			if(result == null){
				return;
			}
			String[] newPath = new String[path.length - 1];
			System.arraycopy(path, 1, newPath, 0, newPath.length);
			
			if(result instanceof Iterable){
				for(Object obj: (Iterable<?>)result){
					inicializarPath(obj, newPath);
				}
			}
			
			else {
				inicializarPath(result, newPath);
			}
						
		} catch (Exception e) {
			logger.warn("Problemas inicializando path " + Arrays.toString(path), e);
		}
	}

	public String[] getPaths() {
		return paths;
	}

	public void setPaths(String[] paths) {
		this.paths = paths;
	}
	
	
	

}

