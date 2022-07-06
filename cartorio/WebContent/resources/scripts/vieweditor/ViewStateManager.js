include("prototype.js");
include("Variables.js");
include("DragManager.js");
ViewStateManager = function(viewName, layout){
	//Nome que identifica a visao atual
	this.viewName = viewName;
	
	//Nome que identifica a visao atual
	this.layout = layout ? layout: "default";
	
	this.pathReference = "";
}
ViewStateManager.prototype = {

	/**
	 * Referencia de caminho para a raiz do context (eg.: ../../, ../)
	 */
	setPathReference: function(path){
		this.pathReference = path;
	},

	/**
	 * DragManager usado para listar os objeto de devem ser salvos em saveState.
	 */
	setDragger: function(dragger){
		this.dragger = dragger;
		dragger.addObjectSelectionListener(this.printEditorFor.bind(this));
	},
	
	/**
	 * Metodo que salva o estado atual da visao.
	 * Este metodo itera no documento descobrinto todos os componentes que devem ser salvos
	 *  e envia para o Servlet que faz a persistencia do arquivo
	 **/
	saveState: function(){
		var buffer = "";
		for(var i = 0; i < this.dragger.objects.length; i++){
			buffer += "id=" + this.dragger.objects[i].id + ",style=" + this.dragger.objects[i].style.cssText;
			if(this.dragger.objects[i].type == "image"){
				buffer += ",src=" + this.dragger.objects[i].src;
				buffer += ",type=" + this.dragger.objects[i].type;
			}
			buffer += "\n";
		}
		
		buffer = escape(buffer);
		
		new Ajax.Request(
			this.pathReference + "ViewStateManagerServlet",{
				parameters: "data=" + buffer + "&view=" + this.viewName + "&layout=" + this.layout + "&action=saveState",
				onComplete: this.saveStateComplete.bind(this),
				onFailure: this.saveStateFailed.bind(this)
			});
	},
	
	saveStateComplete: function(http){
		alert(http.statusText);
	},
	
	saveStateFailed: function(http){
		alert(http.statusText);
	},
	
	
	 /**
	 * Metodo que salva o estado atual da visao.
	 * Este metodo itera no documento descobrinto todos os componentes que devem ser salvos
	 *  e envia para o Servlet que faz a persistencia do arquivo
	 **/
	loadState: function(){
		new Ajax.Request(
			this.pathReference + "ViewStateManagerServlet",{
				parameters: "&view=" + this.viewName + "&layout=" + this.layout + "&action=loadState",
				onComplete: this.loadStateFromHttp.bind(this),
				onFailure: this.loadStateFailed.bind(this)
			});
	},
	
	loadStateFromHttp: function(http){
		var data = http.responseText;
		var rows = data.split("\n");
		
		var row;
		var element;
		var notFound = [];
		
		for(var i = 0; i < rows.length; i++){
			row = rows[i].split(",");
			element = document.getElementById(row[0]);
			if(element != null){
				element.style.cssText = row[1];
			}
			else {
				notFound[notFound.length] = row[0];
			}
		}
		
		if(notFound.length > 0){
			var buffer = "Elementos no arquivo de layout " + this.viewName + " nao encontrados no documento:";
			for(var i = 0; i < notFound.length; i++){
				buffer += notFound[i] + ",";
			}
			throw buffer;
		}
	}, 
	
	loadStateFailed: function(http){
		alert("Falha ao carregar estado da visao: " + http.statusText);
	},
	
	EDITOR_ID: "_objectEditor",
	
	/**
	 * Mostra na tela o editor de propriedades
	 **/
	printViewEditor: function(viewStateManagerVariableName){
		var div = document.createElement("div");
		div.id = "ViewStateManager_ViewEditor"
		var pos = Cookies.read(div.id);
		if(pos){
			var arr = pos.split("&");
			div.style.left = arr[0];
			div.style.top = arr[1];
		}else{
			div.style.left = 460;
			div.style.top = 120;
		}
		div.style.background = "white";
		div.style.border = "1px solid black";
		div.style.padding = "5px";
		div.style.float = "rigth";
		div.style.position = "absolute";
		div.style.zIndex = 100000;
		div.className = "DraggIgnoreBlock";
		div.ondrag = function(){
			Cookies.create(this.id, this.style.left + "&" + this.style.top);
		}
		document.getElementsByTagName("body")[0].appendChild(div);
		
		var html = "";
		html += "<div id='"+ this.EDITOR_ID +"_top' class='IgnoreDragg' style='background-color: #de6b01; color: white'>"
		html += "<a href='#' onclick=\"window.toggle('"+ this.EDITOR_ID +"_area')\" style='color:white'>[-]</a>"
		html += "Aparencia"
		html += "</div>"
		html += "<div id='"+ this.EDITOR_ID +"_area' style='background-color: white; width: 308px; text-align: right;' class='IgnoreDragg'>"
		html += "<div id='"+ this.EDITOR_ID +"'></div>"
//		html += "<input id='ViewStateManager_salvarButton' type='button' value='Salvar' />"
		html += "</div>"

		div.innerHTML = html;		
		new DragManager(['ViewStateManager_ViewEditor'], {x: 5, y: 5});
		
		/*
		$('ViewStateManager_salvarButton').onclick = function(){
			this.saveState();
		}.bind(this);
		*/
	}, 
	
	printEditorFor: function(object){
		this.targetObject = object;
	
		var div = document.getElementById(this.EDITOR_ID);
		div.innerHTML = "";
		
		if(!object){
			return;
		}
		
		var html = "";
		html += "<div style='text-align: left; margin: 10px;'>";
		html += "&nbsp;";
		html += "<select id='"+ this.EDITOR_ID +"_fontSize' style='width: 80px; font-size: 14px; height: 20px;' class='IgnoreDragg'>";	
		html += "<option></option>";
		var options = [10, 10.5, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 24, 26, 28, 30, 32, 34, 36, 40];
		var template = "<option value='##' style='font-size: ##pt;'>##</option>"
		var templateSelected = "<option value='##' selected='true' style='font-size: ##pt;'>##</option>"
		for(var i = 0; i < options.length; i++){
			if(parseInt(object.style.fontSize) == options[i]){
				html += templateSelected.replace(/##/g, options[i]);
								
			} else {
				html += template.replace(/##/g, options[i]);
			}
		}
		html += "</select>";
		
		
		html += "&nbsp;";
		html += "<select id='"+ this.EDITOR_ID +"_color' style='width: 80px; height: 20px;' class='IgnoreDragg'>";	
		html += "<option value='' style='background-color: white;'>Default</option>";
		var colors = ['#000000', '#808080', '#FFFFFF', '#000080', '#0000FF', '#00FFFF', '#00FF00', '#80FF00', '#008000', '#FF0000', '#800000', '#A02820' ];
		var template = "<option value='##' style='background-color: ##;'>##</option>"
		var templateSelected = "<option value='##' selected='true' style='background-color: ##;'>##</option>"
		for(var i = 0; i < colors.length; i++){
			if(object.style.color == colors[i]){
				html += templateSelected.replace(/##/g, colors[i]);
								
			} else {
				html += template.replace(/##/g, colors[i]);
			}
		}
		html += "</select>";
		
		html += "&nbsp;";
		html += "<select id='"+ this.EDITOR_ID +"_backgroundColor' style='width: 80px; height: 20px;' class='IgnoreDragg'>";	
		html += "<option value='' style='background-color: white;'>Default</option>";
		var template = "<option value='##' style='background-color: ##;'>##</option>"
		var templateSelected = "<option value='##' selected='true' style='background-color: ##;'>##</option>"
		for(var i = 0; i < colors.length; i++){
			if(object.style.color == colors[i]){
				html += templateSelected.replace(/##/g, colors[i]);
								
			} else {
				html += template.replace(/##/g, colors[i]);
			}
		}
		html += "</select>";
		
		html += "&nbsp;";
		html += "<a href='#' id='"+ this.EDITOR_ID + "_cssPaneToggle'>Css</a>"
		html += "</div>"
		
		html += "<div id='"+ this.EDITOR_ID +"_cssPane' style='text-align: left; display: none'>";
		html += "Id: " + object.id + "<br>";
		html += "<textarea id='"+ this.EDITOR_ID +"-Style' style='width: 300px;'  rows='10' cols='20'>"
		html += object.style.cssText.replace(/;[\s]*/g, '\n');
		html += "</textarea>"
		
		if(object.nodeName.toLowerCase() == "input"){
			var type = object.type.toLowerCase();
			if(type == "button" || type == "submit" || type == "image"){
				var src = object.trueSrc ? object.trueSrc: object.src;
				html += "<br>Src:<br><input id='"+ this.EDITOR_ID +"-Src' type='text' value='"+ src +"'>" 
			}
		}
		html += "</div>"
		div.innerHTML = html;
		
		var fontSize = document.getElementById(this.EDITOR_ID + "_fontSize");
		fontSize.onchange = function(){
			var css = this.targetObject.style.cssText;

			var obj = $(this.EDITOR_ID + "_fontSize");
			var value = obj.options[obj.selectedIndex].value;
			
			if(value){
				this.targetObject.style.cssText += ";font-size: "+ value +"pt";
				
			} else {
				this.targetObject.style.cssText = css.replace(/font-size:.*?;/, "");
			}
			
			
			this.styleUpdated();
		}.bind(this);
		
		var color = document.getElementById(this.EDITOR_ID + "_color");
		color.onchange = function(){
			var css = this.targetObject.style.cssText;

			var obj = $(this.EDITOR_ID + "_color");			
			var value = obj.options[obj.selectedIndex].value;
			
			if(value){
				this.targetObject.style.cssText += ";color: "+ value;
			} else {
				this.targetObject.style.cssText = css.replace(/color:.*?;/, "");
			}
			obj.style.backgroundColor = value;
			this.styleUpdated();
		}.bind(this);
		
		var color = document.getElementById(this.EDITOR_ID + "_backgroundColor");
		color.onchange = function(){
			var css = this.targetObject.style.cssText;

			var obj = $(this.EDITOR_ID + "_backgroundColor");
			var value = obj.options[obj.selectedIndex].value;
			
			
			if(value){
				this.targetObject.style.cssText += ";background-color: "+ value;
			} else {
				this.targetObject.style.cssText = css.replace(/background-color:.*?;/, "");
			}
			this.targetObject.style.cssText += ";background-color: "+ value;
			
			obj.style.backgroundColor = value;
			this.styleUpdated();
		}.bind(this);
		
		var cssPanetoggle = $(this.EDITOR_ID + "_cssPaneToggle");
		cssPanetoggle.onclick = function(){
			Cookies.create(this.EDITOR_ID + '_cssPane', window.toggle(this.EDITOR_ID + '_cssPane'));
		}.bind(this);
		$(this.EDITOR_ID + '_cssPane').style.display = Cookies.read(this.EDITOR_ID + '_cssPane') == "true" ? "block": "none";
		
		var styleInput = document.getElementById(this.EDITOR_ID + "-Style");
		styleInput.className = "IgnoreDragg";
		styleInput.onblur = function() {
			this.targetObject.style.cssText = $(this.EDITOR_ID + "-Style").value.replace(/\n/g, "; ");
		}.bind(this);
		
		if(object.nodeName.toLowerCase() == "input"){
			var type = object.type.toLowerCase();
			if(type == "button" || type == "submit" || type == "image"){
				var srcInput = document.getElementById(this.EDITOR_ID + "-Src");
				srcInput.className = "IgnoreDragg";
				srcInput.onchange = function() {
//					debug(this.targetObject);
					this.targetObject.type = "image";
					this.targetObject.src = $(this.EDITOR_ID + "-Src").value;
				}.bind(this);
			}
		}
	},
	
	styleUpdated: function(){
		$(this.EDITOR_ID + "-Style").value = this.targetObject.style.cssText.replace(/;[\s]*/g, '\n');
	}
}
