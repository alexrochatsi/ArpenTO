package br.com.datasind.util;

import java.io.Serializable;

/**
 * Classe responsavel pelo gerenciamento dos dados para 
 * setar focus em componentes da interface com o usuario.
 */
public class FocusManager implements Serializable {
	
	private static final long serialVersionUID = 2705753797094680008L;
	
	private String componentId;			
	private String styleName;	
	
	private Boolean enabled;
	private Boolean changeState;		
	
	public FocusManager() { 
		this.enabled = Boolean.TRUE;
		this.changeState = Boolean.FALSE;		
	}

	public FocusManager(String componentId) {
		this.componentId = componentId;		
		this.changeState = Boolean.FALSE;
		this.enabled = (componentId == null? Boolean.FALSE: Boolean.TRUE);
	}
	
	public FocusManager(String componentId, String styleName) {
		this.componentId = componentId;
		this.styleName = styleName;		
		this.enabled = (componentId == null? Boolean.FALSE: Boolean.TRUE);
		this.changeState = (styleName == null? Boolean.FALSE: Boolean.TRUE);			
	}		
	
	/**
	 * Retorna o id do componente.
	 * 
	 * @return componentId - String
	 */
	public String getComponentId() {
		return componentId;
	}

	/**
	 * Metodo para setar o id do componente.
	 * Automaticamente habilita o focus.
	 * 
	 * @param componentId - String
	 */
	public void setComponentId(String componentId) {		
		this.componentId = componentId;
		this.enabled = (componentId == null? Boolean.FALSE: Boolean.TRUE);
	}

	/**
	 * Retorna o nome do estilo.
	 * 
	 * @return styleName - String
	 */
	public String getStyleName() {
		return styleName;
	}

	/**
	 * Metodo para setar o nome do estilo.
	 * 
	 * @param styleName - String
	 */
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}	
	
	/**
	 * Retorna se o objeto deve mudar o seu estado ou nao.
	 * 
	 * @return changeState - Boolean
	 */
	public Boolean getChangeState() {
		return changeState;
	}		
	
	/**
	 * Retorna se o objeto esta habilitado ou nao.
	 *  
	 * @return enabled - Boolean
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	
	/**
	 * Habilita o focus.	 
	 */
	public void enable() {
		this.enabled = Boolean.TRUE;
	}
	
	/**
	 * Desabilita o focus.	 
	 */
	public void disable() {		
		this.enabled = Boolean.FALSE;
	}	
	
	/**
	 * Metodo para setar o id do componente que recebera o focus.
	 * Automaticamente configura o nome do estilo como nulo e 
	 * habilita o focus. 
	 * 
	 * @param componentId - String
	 */
	public void setValues(String componentId) {
		if (componentId != null) {
			this.styleName = null;
			this.componentId = componentId;
			this.changeState = Boolean.TRUE;
			this.enabled = Boolean.TRUE;
		}
		else disable();
	}
	
	/**
	 * Metodo para setar valores nos atributos 
	 * <code>componentId</code> e <code>styleName</code>. 
	 * Automaticamente habilita o focus.
	 * 
	 * @param componentId - String
	 * @param styleName - String
	 */
	public void setValues(String componentId, String styleName) {
		if (componentId != null) {
			this.componentId = componentId;					
			this.styleName = styleName;
			this.enabled = Boolean.TRUE;
			this.changeState = (styleName == null? Boolean.FALSE: Boolean.TRUE);																
		} 
		else disable();
	}	
	
	/**
	 * Metodo que retorna os atributos <code>componentId</code>, 
	 * <code>styleName</code> e <code>changeState</code> 
	 * concatenados e separados por barras ("/").
	 * 
	 * @return String
	 */
	public String getValues() {
		return (componentId == null? "": componentId) + "/" + 
			   (styleName == null? "": styleName) + "/" + 
			   (changeState); 
	}
	
}
