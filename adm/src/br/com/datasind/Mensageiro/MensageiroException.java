package br.com.datasind.Mensageiro;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * 
 * @author osmar
 *
 */
public class MensageiroException {
	

	public static void errorMsg(String msg){
	
		//msg = bundle.getString(msg);
	
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, msg);
	
		FacesContext fc = FacesContext.getCurrentInstance();
	
		fc.addMessage("errorMessage", fm);

	}

	public static void infoMsg(String msg){

		//msg = bundle.getString(msg);
	
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
	
		FacesContext fc = FacesContext.getCurrentInstance();
	
		fc.addMessage("info", fm);

	}


}
