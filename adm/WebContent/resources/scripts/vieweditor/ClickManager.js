include("Getters.js");

/**
* @autor bmendes
* Esta classe tem funcao de gerenciar as acoes de click.
*/
ClickManager = {
	
}

var CLASS_BLOCK_BUTTON = "isBlockButton";

var CLASS_STATE_DISABLED_BUTTON = "isStateDisabled";

/**
 * Desabilita todos os botoes de submit
 * Sera indicado um estado CLASS_STATE_DISABLED_BUTTON indicando que o botao foi desabilidado.
 * @autor bmendes
 * @since 10/08/07
 */ 
ClickManager.disabledAllButtonsAction = function() {
	
	var forms = document.forms;
	for(i = 0; i < forms.length; i++){
		var elements = forms[0].elements;
		for(j = 0; j < elements.length; j++){			
			if(elements[j].type=='submit' || elements[j].className.match(CLASS_BLOCK_BUTTON)){			
				if(!elements[j].disabled){
					elements[j].className = elements[j].className + ' ' + CLASS_STATE_DISABLED_BUTTON;					
					this.disabledButton(elements[j]);
				}				
			}	
		}
	}	
	agrupaSwitcher.disableAllSwitchers();	
}

/**
 * Desabilita um botao e altera o cursor sobre ele
 * @autor bmendes
 * @since 10/08/07
 */
ClickManager.disabledButton = function(botao) {	
	botao.disabled = true;
	botao.style.cursor = 'none';
	botao.className = botao.className.replace(/_On/i,"_Off");
}


/**
 * Reabilita todos os botoes de submit de uma pagina desabilitados
 * Sera excluido o estado CLASS_STATE_DISABLED_BUTTON para um botao que foi desabilidado. 	 
 * @autor bmendes
 * @since 10/08/07
 */
ClickManager.enabledAllButtonsAction = function() {
	
	var forms = document.forms;
	for(i = 0; i < forms.length; i++){
		var elements = forms[0].elements;
		for(j = 0; j < elements.length; j++){
			if(elements[j].type=='submit' || elements[j].className.match(CLASS_BLOCK_BUTTON)){
				if(elements[j].disabled && elements[j].className.match(CLASS_STATE_DISABLED_BUTTON)){
					elements[j].className = elements[j].className.replace(/CLASS_STATE_DISABLED_BUTTON/i,"");										
					this.enabledButton(elements[j]);
				}			
			}	
		}
	}
	
	agrupaSwitcher.enableAllSwitchers();
}

/**
 * Reabilita um botao desabilitado 
 * @autor bmendes
 * @since 10/08/07
 */
ClickManager.enabledButton = function(botao) {
	botao.disabled = false;
	botao.style.cursor = document.all ? 'hand' : 'pointer';
	botao.className = botao.className.replace(/_Off/i,"_On");
}

/**
 * Cancelar
 * @autor bmendes
 * @since 10/08/07
 */
ClickManager.cancel = function() {
	return false;
}


/**
 * Desabilita um link e altera o cursor sobre ele
 * @autor bmendes
 * @since 10/08/07
 */
ClickManager.disabledLink = function(link) {
	if (link.onclick)
		link.oldOnClick = link.onclick;
	link.onclick = cancel;
	if (link.style)
		link.style.cursor = 'default';
}

/**
 * Reabilita um link desabilitado.
 * @autor bmendes
 * @since 10/08/07
 */
ClickManager.enabledLink = function(link) {
	link.onclick = link.oldOnClick ? link.oldOnClick : null;
	if (link.style)
		link.style.cursor = document.all ? 'hand' : 'pointer';
}


/**
 * Desabilita todos os links de uma pagina
 * @autor bmendes
 * @since 10/08/07
 */
ClickManager.disabledAllLinks = function() {
	var links = document.links;
	for(i = 0; i < links.length; i++){
		disabledLink(links[i]);
	}
}


/**
 * Reabilita todos os links de uma pagina
 * @autor bmendes
 * @since 10/08/07
 */
ClickManager.enabledAllLinks = function() {
	var links = document.links;
	for(i = 0; i < links.length; i++){
		enabledLink(links[i]);
	}
}



window.addOnload(function(){
	ClickManager.refreshImageButtons();
});
