package br.com.datasind.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.datasind.transacao.hibernate.FabricaTransacaoHibernate;


public class SessionFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request; 
		HttpSession session = httpServletRequest.getSession();
		List<String> urls = (List) session.getAttribute("SessionFilter.urls");
		if(urls == null) {
			urls = new ArrayList<String>();
			session.setAttribute("SessionFilter.urls", urls);
		}
		
		String currentUrl = httpServletRequest.getServletPath();
		urls.add(currentUrl);
		
		try {
			chain.doFilter(request, response);
			
		} finally {
			synchronized (urls) {
				urls.remove(currentUrl);
				if(urls.size() < 1) {
					FabricaTransacaoHibernate.sessaoFinalizada();	
				}
			}
		}
	}

	public void destroy() {

	}
}

