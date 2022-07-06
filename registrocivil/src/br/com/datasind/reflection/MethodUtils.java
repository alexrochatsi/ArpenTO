package br.com.datasind.reflection;

import java.lang.reflect.Method;

public class MethodUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getMethod(Class clazz, String name, Class[] paramTypes) {
		Method[] methods = clazz.getMethods();

		outer: for (int i = 0; i < methods.length; i++) {
			if (!methods[i].getName().equals(name)) {
				continue;
			}

			Class[] methodParamTypes = methods[i].getParameterTypes();
			if (methodParamTypes.length != paramTypes.length) {
				continue;
			}

			for (int j = 0; j < methodParamTypes.length; j++) {
				if (!methodParamTypes[j].isAssignableFrom(paramTypes[j])) {
					continue outer;
				}
			}

			return methods[i];
		}
		return null;
	}
}
