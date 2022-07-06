package br.com.datasind.entidade;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public abstract class EntidadePadrao implements Serializable, Cloneable,
		Comparable<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract Integer getId();

	public boolean entidadeTransitente() {
		return (this.getId() == null);
	}

	public Object clone() throws CloneNotSupportedException {
		Class<?> clazz = getClass();
		try {
			EntidadePadrao entidade = (EntidadePadrao) clazz.newInstance();
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (((methods[i].getDeclaringClass() == clazz) && (methods[i]
						.getName().startsWith("set")))) {

					String nome = methods[i].getName();
					nome = nome.substring(3, nome.length());

					Method setter = methods[i];
					Method getter = clazz.getDeclaredMethod("get" + nome,
							new Class[] {});

					setter.setAccessible(true);
					getter.setAccessible(true);

					setter.invoke(
							entidade,
							new Object[] { getter.invoke(this, new Object[] {}) });
				}
			}
			return entidade;

		} catch (InstantiationException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();

		} catch (SecurityException e) {
			e.printStackTrace();

		} catch (NoSuchMethodException e) {
			e.printStackTrace();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (InvocationTargetException e) {
			e.printStackTrace();

		}
		return null;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		/*
		 * if (clazz != getClass()) { return false; }
		 */

		Class<?> clazz = obj.getClass();
		if (!(clazz.isAssignableFrom(getClass()) || getClass()
				.isAssignableFrom(clazz))) {
			return false;
		}

		Integer thatId = ((EntidadePadrao) obj).getId();
		Integer thisId = getId();

		if (thatId != null) {
			if (thatId.equals(thisId)) {
				return true;

			} else {
				return false;
			}
		}

		try {
			Method[] methods = clazz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (((methods[i].getDeclaringClass() == clazz) && (methods[i]
						.getName().startsWith("get")))) {

					Method getter = methods[i];
					getter.setAccessible(true);

					Object thisValue = getter.invoke(this, new Object[] {});
					Object thatValue = getter.invoke(obj, new Object[] {});

					if (thatValue instanceof Collection) {
						continue;

					} else {
						if (!((thatValue == null) ? (thisValue == null)
								: thatValue.equals(thisValue))) {
							return false;
						}
					}
				}
			}
			return true;

		} catch (IllegalAccessException e) {
			e.printStackTrace();

		} catch (SecurityException e) {
			e.printStackTrace();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (InvocationTargetException e) {
			e.printStackTrace();

		}
		return false;
	}

	public int compareTo(Object object) {
		return getId().compareTo(((EntidadePadrao) object).getId());
	}

	private InstanceHolder holder;

	public InstanceHolder holder() {
		if (holder == null) {
			holder = new DefaultInstanceHolder(this);
		}
		return holder;
	}

}
