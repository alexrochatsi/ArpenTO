package br.com.datasind.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.gerente.FabricaGerente;
import br.com.datasind.util.FocusManager;

public abstract class CadastroControler extends AbstractControler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6397925300573246217L;

	public static final String FABRICA_GERENTE = "fabricaGerente";

	private transient FabricaGerente fabricaGerente;

	private FocusManager focusManager;

	private String context;

	/**
	 * 
	 */
	public CadastroControler() {
		super();
		focusManager = new FocusManager();
	}

	/**
	 * 
	 * @return
	 */
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

	/**
	 * @deprecated
	 * @param object
	 */
	protected void atualizarSessao(Object object) {
		try {
			getFabricaGerente().getGerenteControleAcesso().atualizarSessao(object);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void adicionarObjetoDesatualizado(Object object) {
		List list = (List) getSessionScope().get(OuvinteSelecao.OBJETOS_DESATUALIZADOS);
		if (list == null) {
			list = new ArrayList();
			getSessionScope().put(OuvinteSelecao.OBJETOS_DESATUALIZADOS, list);
		}
		if (object instanceof Collection) {
			list.addAll((Collection) object);

		} else {
			list.add(object);
		}

	}

	protected Object inicializar(Object object) {
		try {
			getFabricaGerente().getGerenteControleAcesso().inicializarObjeto(object);
			return object;

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return object;
	}

	protected void liberarSessao(Object object) {
		try {
			getFabricaGerente().getGerenteControleAcesso().atualizarSessao(object);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public FocusManager getFocusManager() {
		return focusManager;
	}

	public String getContext() {
		if (context == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) fc.getExternalContext().getContext();
			context = servletContext.getRealPath("/");
		}
		return context;
	}
}
