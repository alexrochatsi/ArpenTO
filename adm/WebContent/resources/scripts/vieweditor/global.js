//$Revision: 1.1 $,$Date: 2009-06-16 17:00:31 $
window.debug = true;

window.getContextPath = function(){
	var l = window.location
	var host = window.location.pathname;
	
	return l.protocol 
		+ "//" 
		+ l.host + host.substring(0, host.lastIndexOf("/") + 1)
		+ "/"
}

window.include = function(library, path){
	var l = window.location
	var host = window.location.pathname;
	if(!(window.frameworkScriptsPath || path)){
		window.frameworkScriptsPath = "../../resources/scripts/vieweditor";
	}
	
	if(!window.included){
		window.included = [];
	}
	
	if(!path){
		path = window.frameworkScriptsPath;
	}
	
	for(var i = 0; i < window.included.length; i++){
		if(window.included[i] == library){
			return;
		}
	}
	window.included[window.included.length] = library;
	
	//var libraryName = window.getContextPath()
	//	+ "/ResourceServlet?r="
	//	+ library;
	
	var libraryName = window.getContextPath() + 
		window.frameworkScriptsPath + "/" + library;
	
    document.write('<script type="text/javascript" src="'+libraryName+'"></script>');
}

window.link = function(library){
	var l = window.location
	var host = window.location.pathname;
	var libraryName = l.protocol 
		+ "//" 
		+ l.host + host.substring(0, host.substring(1).indexOf("/") + 1)
		+ "/"
		+ library;
		
    document.write('<link rel="stylesheet" href="'+libraryName+'" type="text/css" />');
}

window.addOnload = function(fun){
	if(!this.onLoadFunctions){
		this.onLoadFunctions = Array();
	}
	this.onLoadFunctions[this.onLoadFunctions.length] = fun;
}

window.onload = function(){
	if(this.onLoadFunctions){
		for(var i = 0; i < this.onLoadFunctions.length; i++){
			this.onLoadFunctions[i]();
		}
	}
}

window.addMessage = function(severity, message){	
	var ul = document.getElementById("messages_");
	if(!ul){
		throw "Nao existe uma lista definida em " + document.location.url + " com id messages_";
	}
	var style = null;
	if(severity == "ERROR"){
		style = "errorMessage";
	}
	else if(severity == "WARN"){
		style = "warnMessage";
	}
	else if(severity == "INFO"){
		style = "infoMessage";
	}
	
	ul.innerHTML = "<li><span class=\"" + style + "\">"+ message + "</span></li>"
	setTimeout("document.getElementById('messages_').innerHTML = ''", 5000);
}

window.toggle = function(obj){
	if($(obj).style.display == "none"){
		$(obj).style.display = "block";
		return true;
	} 
	else {
		$(obj).style.display = "none";
		return false;
	}

}

window.debug = function(text){
	var pane = $('_debug_pane');
	if(!pane){
		pane = document.createElement("div");
		pane.id = "_debug_pane";
		pane.style.overflow = "auto";
	}
	
	pane.innerHTML = "<b>" + text + "</b><br>";
	for(prop in text){
		if(!isNaN(prop)){
			pane.innerHTML += prop + ":" + "<input type='text' value='" +  (eval("text[" + prop+ "]")) + "' /><br>";
		} 
		else {
			pane.innerHTML += prop + ":" + "<input type='text' value='" +  (eval("text." + prop)) + "' /><br>";
		}
	}
	pane.innerHTML += "<hr>";
	document.body.appendChild(pane);
}

window.submit = function(action){
	var input = document.createElement("input");
	input.setAttribute("name", "_action");
	input.setAttribute("value", action);
	input.setAttribute("type", "hidden");

	document.forms[0].appendChild(input);
	document.forms[0].submit();
}