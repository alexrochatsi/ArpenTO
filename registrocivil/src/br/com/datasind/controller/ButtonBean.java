package br.com.datasind.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class ButtonBean {
	
	private String text;
	
	
	public void destroyWorld(ActionEvent actionEvent){  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error",  "Please try again later.");  
          
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
	
	
}
