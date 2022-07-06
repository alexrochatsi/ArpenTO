Menu2 = function(link, menu, options){
	this.link = $(link);
	this.menu = $(menu);
	this.isMouseOut = true;
	
	if(!options){
		this.options = {};
	} else {
		this.options = options;
	}
	
	if(!this.options.opacity){
		this.options.opacity = 1.0;
	}
	
	this.ajax = new Ajax.Request(
		'../menu.jsf', 
		{
			method: 'get',
			parameters: 'url=' + document.location.pathname.substring(document.location.pathname.indexOf("/", 1)),
			onSuccess: this.renderMenu.bind(this)
		});
	window._menuInstance = this;
	
	this.menu.onmouseout = function(event){
		this.isMouseOut = true;
		window.setTimeout("this._menuInstance.hidePanel()", this.options.duration);
	}.bind(this);
	
	this.menu.onmouseover = function(event){
		this.isMouseOut = false;
	}.bind(this);

	this.link.onmouseover = function(event){
		new Effect.Appear(this.menu, {duration: .1, to: this.options.opacity});
		this.isMouseOut = false;
	}.bind(this);
	
	

}
Menu2.prototype = {
	hidePanel: function(){
		if(this.isMouseOut){
			new Effect.Fade(this.menu, {duration: .1, from: this.options.opacity});
		}
	},
	
	renderMenu: function(request){
		menu = this.menu;
		xml = request.responseXML;
			
		if(xml == null){
			menu.innerHTML = "Problemas montando o menu";
			return;
		}
		
		dados   = new Array();
		dados[0] = new Array();
		dados[1] = new Array();
		dados[2] = new Array();


		funcionalidades = xml.getElementsByTagName("funcionalidade");
		for(i = 0; i < funcionalidades.length; i++){
			descricao = funcionalidades[i].getElementsByTagName("descricao")[0].firstChild.nodeValue;
			url = funcionalidades[i].getElementsByTagName("nomeInterno")[0].firstChild.nodeValue;
			
			descricaoEsc = escape(descricao.toLowerCase());
			if(descricaoEsc.indexOf("cadastro") > -1){
				dados[1][dados[1].length] = new Array(descricao, url);
			}
			else if(descricaoEsc.indexOf("relat%F3rio") > -1 || descricaoEsc.indexOf("relatorio") > -1){
				dados[2][dados[2].length] = new Array(descricao, url);
			}
			else {
				dados[0][dados[0].length] = new Array(descricao, url);
			}
		}


		m = "<table id=\"menu\">";
		m += "<tr>";
		m += (dados[0].length > 0) ? "<th>Funcionalidades</th>": "";
		m += (dados[1].length > 0) ? "<th>Cadastros</th>": "";
		m += (dados[2].length > 0) ? "<th>Relatorios</th>": "";
		m += "</tr>";
		m += "<tr>";
		for(i = 0; i < dados.length; i++){	
			if(dados[i].length > 0){
				m += "<td>";
				m += "<ul id=\"submenu\">";
				for(j = 0; j < dados[i].length; j++){
					m += "<li>";
					m += "<a href=\"../"+ dados[i][j][1] +"\">";
					m += dados[i][j][0];
					m += "</a>";
					m += "</li>";
				}
				m += "</ul>";
				m += "</td>";
			}
		}
		m += "</tr>";
		m += "</table>";
		
		menu.innerHTML = m;
		if(window.location.href.indexOf("debug") > -1){
			win = window.open();
			win.document.write(menu.innerHTML);
			win.document.close();
		}
	}
}