package br.com.datasind.view.jsf;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class HttpServletAdapter implements HttpAdapter, SessionSystemAttibutes {

	private HttpServletRequest request;

	private HttpServletResponse response;

	public HttpServletAdapter(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public Object getSessionAttribute(String parameterName) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		return session.getAttribute(parameterName);
	}

	public void removeSessionAttribute(String parameterName) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(parameterName);
	}

	public void setSessionAttribute(String parameterName, Object parameterValue) {
		request.getSession(true).setAttribute(parameterName, parameterValue);
	}

	public void sendError(int code) throws IOException {
		response.sendError(code);
	}

	public void sendRedirect(String page) throws IOException {
		response.sendRedirect("http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/"
				+ page);
	}

	public void dispatcherRequestForward(String destino)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
		dispatcher.forward(request, response);
	}

	public void dispatcherRequestInclude(String destino)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
		dispatcher.include(request, response);
	}

}

