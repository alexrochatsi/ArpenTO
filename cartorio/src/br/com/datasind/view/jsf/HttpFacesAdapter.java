package br.com.datasind.view.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class HttpFacesAdapter implements HttpAdapter, SessionSystemAttibutes {

	private static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	private HttpSession getHttpSession() {
		return (HttpSession) getFacesContext().getExternalContext().getSession(true);
	}

	public Object getSessionAttribute(String parameterName) {
		return getHttpSession().getAttribute(parameterName);
	}

	public void setSessionAttribute(String parameterName, Object parameterValue) {
		getHttpSession().setAttribute(parameterName, parameterValue);
	}

	public void addErrorMessage(String message) {
		getFacesContext().addMessage("loginMessage", new FacesMessage(message));
	}

	public String getRootRelativePath() {
		ServletContext context = (ServletContext) getFacesContext().getExternalContext().getContext();
		return context.getRealPath(".");
	}

	public String getInitParameter(String key) {
		ServletContext context = (ServletContext) getFacesContext().getExternalContext().getContext();
		return context.getInitParameter(key);
	}
}
