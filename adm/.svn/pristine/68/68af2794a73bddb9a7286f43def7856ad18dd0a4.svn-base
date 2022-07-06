//$Revision: 1.1 $,$Date: 2009-06-16 17:00:21 $
include("JSCookMenu/JSCookMenu.js");
link("resources/scripts/JSCookMenu/ThemeGray/theme.css");
include("JSCookMenu/ThemeGray/theme.js");


include("prototype.js");
var Menu = function(menu, div){
	this.menu = menu;
	this.div = div;
	this.url = "../../menu.jsf";
	
	new Ajax.Request(
			this.url,{
				parameters: "url=" + document.location.pathname.substring(document.location.pathname.indexOf("/", 1)),
				onComplete: this.buildMenu.bind(this),
				onFailure: this.onFailure.bind(this)
			});
}

Menu.prototype = {
	buildMenu: function(http){
		var arr = http.responseText.split(";");
		for(var i = 0; i < arr.length; i++){
			arr[i] = arr[i].substring(arr[i].lastIndexOf("/") + 1);
		}
		
		this.walk(this.menu, arr, true);
		cmDraw (this.div, this.menu, 'hbr', cmThemeGray, 'ThemeGray');
	},
	
	walk: function(no, permitidos, root){
		if(!no){
			return false;
		}
		
		if(no.length == 5){
			//folha
			var url = no[2];
			for(var i = 0; i < permitidos.length; i++){
				if(permitidos[i] == url){
					return true;
				}
			}
			return false;
		}
		else if(no.length > 5) {
			//pasta
			var permitido = false;
			for(var i = 5; i < no.length; i++){
				if(this.walk(no[i], permitidos, false)){
					permitido = true;
				} else {
					//Removendo no[i]
					no.splice(i--, 1);
				}
			}
			return permitido;
		}
		else if(root){
			var permitido = false;
			for(var i = 0; i < no.length; i++){
				if(this.walk(no[i], permitidos, false)){
					permitido = true;
				} else {
					//Removendo no[i]
					no.splice(i--, 1);
				}
			}
			return permitido;
		}
	},
	
	onFailure: function(http){
		alert(http.responseText);
	}
}