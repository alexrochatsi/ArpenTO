package br.com.datasind.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class WriteMethodPointcut extends StaticMethodMatcherPointcut {

	@SuppressWarnings("rawtypes")
	public boolean matches(Method method, Class clazz) {
		List annotations = getAnnotations(method, clazz);
		Iterator iter = annotations.iterator();
		while (iter.hasNext()) {
			Annotation annotation = (Annotation) iter.next();
			if (annotation.annotationType().equals(WriteMethod.class)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getAnnotations(Method method, Class clazz) {
		ArrayList<Annotation> list = new ArrayList<Annotation>(5);
		while (clazz != null) {
			try {
				Method current = clazz.getMethod(method.getName(), (Class[]) method.getParameterTypes());
				Annotation[] annotations = current.getAnnotations();
				for (int i = 0; i < annotations.length; i++) {
					list.add(annotations[i]);
				}
			} catch (Exception e) {

			}
			clazz = clazz.getSuperclass();
		}
		return list;
	}

}
