package br.com.datasind.controller.sca;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.datasind.controller.CadastroControler;

/**
 * 
 * @author OsmarJunior
 * @since 11/09/2013
 */
@ManagedBean(name = "redireciona")
@RequestScoped
public class RedirecionaController extends CadastroControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -153067346306791454L;

	public String redirecionaLogin() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		session.invalidate();
		FacesContext temp = FacesContext.getCurrentInstance();
		try {
			temp.getExternalContext().redirect("../login.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CURRENT_PAGE;

	}

}
