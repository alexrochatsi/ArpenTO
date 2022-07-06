//$Revision: 1.1 $,$Date: 2009-06-16 17:00:21 $
window.debug = true;
window.include = function(library, path){
	var l = window.location
	var host = window.location.pathname;
	//alert("teste");
	alert(window.frameworkScriptsPath +" - "+ path);
	if(!(window.frameworkScriptsPath || path)){
		alert("Voce deve colocar o arquivo framework.js na base do contexto ou definir "+ 
			"as variaveis necessarias de outra maneira.");		
		return;
	}
	
	if(!path){
		path = window.frameworkScriptsPath;
	}
	
	var libraryName = l.protocol 
		+ "//" 
		+ l.host + host.substring(0, host.substring(1).indexOf("/") + 1)
		+ "/" + path + "/"
		+ library;
		
    document.write('<script type="text/javascript" src="'+libraryName+'"></script>');
}

window.include("framework.js", "/");

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
	
	var ul = document.getElementById("_messages");
	if(!ul){
		throw "Nao existe uma lista definida em " + document.location.url + " com id _messages";
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
	setTimeout("document.getElementById('_messages').innerHTML = ''", 5000);
}

window.debug = function(text){
	if(window.debug){
		try{
			if(!window.debugWin || !window.debugWin.document){
				window.debugWin = window.open();
				window.debugWin.onclose = function(){
					window.debugWin = null;
				}
			}
			window.debugWin.document.write("<b>" + text + "</b><br>");
			for(i in text){
				window.debugWin.document.write(i + ":" + "<input type='text' value='"+ eval("text." + i) +"'><br>");
			}
			window.debugWin.document.write("<hr>");
		}catch(e){
			debug(e);
		}
	}
}

window.submit = function(action){
	var input = document.createElement("input");
	input.setAttribute("name", "_action");
	input.setAttribute("value", action);
	input.setAttribute("type", "hidden");

	document.forms[0].appendChild(input);
	document.forms[0].submit();
}