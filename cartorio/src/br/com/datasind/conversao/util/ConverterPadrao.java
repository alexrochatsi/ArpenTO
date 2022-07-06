package br.com.datasind.conversao.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.datasind.gerente.FabricaGerente;

public class ConverterPadrao {

	private transient FabricaGerente fabricaGerente;

	protected transient ServletContext servletContext;

	public static final Severity ERROR = FacesMessage.SEVERITY_ERROR;

	public static final Severity WARNING = FacesMessage.SEVERITY_WARN;

	public static final Severity INFO = FacesMessage.SEVERITY_INFO;

	public FabricaGerente getFabricaGerente() {
		if (fabricaGerente == null) {
			if (FacesContext.getCurrentInstance() != null) {
				fabricaGerente = (FabricaGerente) FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance()).getBean(FabricaGerente.NOME);
			} else {
				fabricaGerente = (FabricaGerente) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(FabricaGerente.NOME);
			}

		}
		return fabricaGerente;
	}

	public ServletContext getServletContext() {
		if (servletContext == null) {
			servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		}
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	protected void mensageiro(Severity severity, String message) {
		FacesMessage msg = new FacesMessage(severity, message, "Message");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
