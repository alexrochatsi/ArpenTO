package br.com.datasind.servlet;

import javax.servlet.http.HttpServletRequest;

public interface FreeResourceRule {
	public boolean isFreeResource(HttpServletRequest request);
}
