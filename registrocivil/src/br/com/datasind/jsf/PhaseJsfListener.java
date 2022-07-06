package br.com.datasind.jsf;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 * @Author osmar
 * @since 07/06/2011
 * 
 **/
public class PhaseJsfListener implements PhaseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6566340388778161531L;

	@SuppressWarnings("unused")
	private static Logger LOGGER = Logger.getLogger(PhaseJsfListener.class);

	@SuppressWarnings("unused")
	private static String phase = null;

	public void afterPhase(PhaseEvent e) {

	}

	public void beforePhase(PhaseEvent e) {
		if (e.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			FacesContext fc = e.getFacesContext();
			UIViewRoot root = fc.getViewRoot();

			for (Iterator<?> iter = root.getChildren().iterator(); iter.hasNext();) {
				UIComponent element = (UIComponent) iter.next();
				changeTreeToTransient(element);
			}

			// ExternalContext ext = fc.getExternalContext();
			// HttpSession session = (HttpSession) ext.getSession(false);
			// boolean newSession = (session == null) || (session.isNew());
			// boolean postback = !ext.getRequestParameterMap().isEmpty();
			// boolean timedout = postback && newSession;
			// if (timedout) {
			// // doRedirect(fc, "/index.jsf?faces-redirect=true");
			// }

		}

	}

	private void changeTreeToTransient(UIComponent component) {
		component.setId(component.getId());

		for (Iterator<?> iter = component.getChildren().iterator(); iter.hasNext();) {
			UIComponent element = (UIComponent) iter.next();
			changeTreeToTransient(element);
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public void doRedirect(FacesContext fc, String redirectPage)
			throws FacesException {
		ExternalContext ec = fc.getExternalContext();

		try {
			if (ec.isResponseCommitted()) {
				// redirect is not possible
				return;
			}

			// fix for renderer kit (Mojarra's and PrimeFaces's ajax redirect)
			if ((RequestContext.getCurrentInstance().isAjaxRequest() || fc.getPartialViewContext().isPartialRequest()) && fc.getResponseWriter() == null
					&& fc.getRenderKit() == null) {
				ServletResponse response = (ServletResponse) ec.getResponse();
				ServletRequest request = (ServletRequest) ec.getRequest();
				response.setCharacterEncoding(request.getCharacterEncoding());

				RenderKitFactory factory = (RenderKitFactory)
						FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);

				RenderKit renderKit = factory.getRenderKit(fc,
						fc.getApplication().getViewHandler().calculateRenderKitId(fc));

				ResponseWriter responseWriter =
						renderKit.createResponseWriter(
								response.getWriter(), null, request.getCharacterEncoding());
				fc.setResponseWriter(responseWriter);
			}

			ec.redirect(ec.getRequestContextPath() +
					(redirectPage != null ? redirectPage : ""));
		} catch (IOException e) {

			throw new FacesException(e);
		}
	}

}
