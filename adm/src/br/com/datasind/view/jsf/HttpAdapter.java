package br.com.datasind.view.jsf;

public interface HttpAdapter {	
	public Object getSessionAttribute(String parameterName);	
	public void setSessionAttribute(String parameterName, Object parameterValue);
}
