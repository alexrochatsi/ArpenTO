package br.com.datasind.conversao;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class ConversorPadrao implements Conversor {

	protected static final Logger logger = Logger.getLogger(ConversorPadrao.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object converter(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof String) {
			object = converter((String) object);
			return object;
		}
		// return object;

		Class clazz = object.getClass();

		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (((methods[i].getDeclaringClass().isAssignableFrom(clazz)) && (methods[i]
					.getName().startsWith("set")))) {

				String nome = methods[i].getName();
				nome = nome.substring(3, nome.length());

				Method setter = methods[i];
				try {
					Method getter = clazz.getMethod("get" + nome,
							new Class[] {});

					if (getter.getReturnType() == String.class) {
						setter.setAccessible(true);
						getter.setAccessible(true);
						setter.invoke(object,
								new Object[] { converter((String) getter
										.invoke(object, new Object[] {})) });

					}
				} catch (Exception e) {
					logger.debug(e);
				}
				/*
				 * else if (EntidadePadrao.class.isAssignableFrom(
				 * getter.getReturnType())) { Object value =
				 * getter.invoke(object, new Object[] {});
				 * if(Hibernate.isInitialized(value)) { //Nao funciona converter
				 * recursivamente! // setter.invoke(object, new Object[] {
				 * converter(value)}); } }
				 */
				// Collections nao sao mais convertidas para nao carregar
				// toda a arvore de
				// objetos associada ao objeto.

				// else if (getter.invoke(object, new Object[] {})
				// instanceof Collection) {
				//
				// Collection collection = (Collection) getter.invoke(
				// object, new Object[] {});
				//
				// for (Iterator j = collection.iterator(); j.hasNext();) {
				// EntidadePadrao element = (EntidadePadrao) j.next();
				// converter(element);
				// }
				// }
			}
		}
		return object;
	}

	private String converter(String string) {
		return removeEspacosMultiplos((string == null) ? null : string.trim()
				.toUpperCase().trim());
	}

	private String removeEspacosMultiplos(String string) {
		if (string == null) {
			return null;
		}
		return string.replaceAll("[\\s]+", " ");
	}
}
