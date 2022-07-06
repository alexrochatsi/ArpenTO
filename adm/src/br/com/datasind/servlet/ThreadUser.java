package br.com.datasind.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ThreadUser implements Filter {

	private static ThreadUser instance;

	private ThreadLocal<String> threadLocal;

	/**
	 * @deprecated
	 * 
	 *             O construtor tem que ser publico porque tem que ser
	 *             instanciado pelo container. MAS NAO DEVE SER USADO!!
	 */
	public ThreadUser() {
		instance = this;
		threadLocal = new ThreadLocal<String>();
	}

	public static synchronized ThreadUser getInstance() {
		return instance;
	}

	public void update(String l) {
		threadLocal.set(l);
	}

	public String getId() {
		return threadLocal.get();
	}

	public void destroy() {
		threadLocal = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		update(((HttpServletRequest) request).getSession().getId());
		chain.doFilter(request, response);
		update(null);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
