include("Variables.js");
include("Events.js");
include("ClassBehaviorApplier.js");
ComponentsManager = function(id, options){
	this.id = id;

	this.options = options ? options : {};
	if(this.options.submit){
		Events.add(options.document.forms[0], "submit", function(){
			this.updateInputHidden();
		}.bind(this));
	}
	
	Events.add(options.document.forms[0], "submit", function(){
		if(this.isEditor){
			this.salvar();
		}
	}.bind(this));
	
	
	
}
ComponentsManager.prototype = {
	loadObjects: function(){
		if(this.options.objectsInput){
		this.updateObjectsFromString($(this.options.objectsInput).value);
		} 
		else {
			this.getObjectsFromServer(id);
		}
	},
	
	updateInputHidden: function(){
		if(!this.objects){
			return;
		}
		var result = "{";
		for(var i = 0; i < this.objects.length; i++){
			if(!this.objects[i].onlyView){
				result += "'" + this.objects[i]._id + "':"
				var obj = this.objects[i].get();
				if(typeof(obj) == "string"){
					if(obj.charAt(0) == "[" || obj.charAt(0) == "{"){
						result += obj + ",";
					}
					else {
						result += "'"+ obj + "',";
					}
				}
				else {
					result += "'"+ obj + "',";
				}
			}
		}
		result = result.substring(0, result.length - 1);
		result += "}";
	
		$(this.options.input).value = result;
	},
	
	updateObjectsFromInput: function(){
		if(!$(this.options.input) || !$(this.options.input).value || !this.objects){
			return;
		}
		var inputValuesMap = eval("("+ $(this.options.input).value +")");
		for (var i = 0; i < this.objects.length; i++) {
			if (!this.objects[i].onlyView) {
				var _idField = this.objects[i]._id;
				if (inputValuesMap[_idField]) {
					this.objects[i].set(inputValuesMap[_idField]);
				}
			}
		}
		// COMENTADO POIS O ID DO CAMPO NAO E O _ID:
//		if(!$(this.options.input) || !$(this.options.input).value){
//			return;
//		}
//		var obj = eval("("+ $(this.options.input).value +")");
//		for(var i in obj){
//			try {
//				$(i).set(obj[i]);
//			}catch(e){ 
//			
//			}
//		}
	},

	setDragger: function(dragManager){
		this.dragManager = dragManager;
		Events.add(document, "mouseup", this.click.bind(this));
		
		dragManager.addObjectSelectionListener(function(object){
			this.lastSelectedObject = this.selectedObject;
			this.selectedObject = object;
			
			if(this.lastSelectedObject != this.selectedObject){
				this.updateProperties();
			}
		}.bind(this));
	},
	
	EDITOR_ID: "_ComponentsManager",	
	
	/**
	 * Mostra na tela o editor de propriedades
	 **/
	printEditor: function(){
		this.isEditor = true;
		var div = this.createDiv("Componentes", 800, 120);
		document.body.appendChild(div);	
		
		var html = "";
		html += "<div id='"+ this.EDITOR_ID +"_top' class='IgnoreDragg' style='background-color: #de6b01; color: white '>"
		html += "<a href='#' onclick=\"window.toggle('"+ this.EDITOR_ID +"_area')\" style='color: white'>[-]</a>"
		html += "Componentes"
		html += "</div>"
		html += "<div id='"+ this.EDITOR_ID +"_area' style='background-color: white; width: 120px; text-align: right;' class='IgnoreDragg'>"
		html += "<div id='"+ this.EDITOR_ID +"'>"
		html += "<input type='button' style='width: 120px;' value='Fieldset' id='"+ this.EDITOR_ID + "_AddFieldset'/>"		
		html += "<input type='button' style='width: 120px;' value='TextField' id='"+ this.EDITOR_ID + "_AddTextField'/>"	
		html += "<input type='button' style='width: 120px;' value='RadioButton' id='"+ this.EDITOR_ID + "_AddRadio'/>"	
		html += "<input type='button' style='width: 120px;' value='CheckBox' id='"+ this.EDITOR_ID + "_AddCheckbox'/>"	
		html += "<input type='button' style='width: 120px;' value='Label' id='"+ this.EDITOR_ID + "_AddLabel'/>"
		html += "<input type='button' style='width: 120px;' value='Entidade' id='"+ this.EDITOR_ID + "_AddEntidade'/>"
		html += "<hr />"
		html += "<input type='button' style='width: 60px;' value='Subir' id='"+ this.EDITOR_ID + "_Up'/>"
		html += "<input type='button' style='width: 60px;' value='Descer' id='"+ this.EDITOR_ID + "_Down'/>"
		html += "<hr />"
		html += "<input type='button' style='width: 60px;' value='Excluir' id='"+ this.EDITOR_ID + "_Excluir'/>"	
		html += "</div>"
		html += "</div>"
		div.innerHTML = html;
		
		var div = this.createDiv("Propriedades", 565, 200);
		document.body.appendChild(div);		
		
		var html = "";
		html += "<div id='"+ this.EDITOR_ID +"_topProps' class='IgnoreDragg' style='background-color: #de6b01; color: white'>"
		html += "<a href='#' onclick=\"window.toggle('"+ this.EDITOR_ID +"_areaProps')\" style='color: white'>[-]</a>"
		html += "Propriedades"
		html += "</div>"
		html += "<div id='"+ this.EDITOR_ID +"_areaProps' style='background-color: white;' class='IgnoreDragg'></div>"

		div.innerHTML = html;
		/*		
		var div = this.createDiv("Menu");
		document.body.appendChild(div);	
		
		var html = "";
		html += "<div id='"+ this.EDITOR_ID +"_topMenu' class='IgnoreDragg' style='background-color: red; color: white'>"
		html += "<a href='#' onclick=\"window.toggle('"+ this.EDITOR_ID +"_areaMenu')\" style='color: white'>[-]</a>"
		html += "Menu"
		html += "</div>"
		html += "<div id='"+ this.EDITOR_ID +"_areaMenu' style='background-color: white;' class='IgnoreDragg'>";
		html += "<input type='button' style='width: 120px;' value='Salvar' id='"+ this.EDITOR_ID + "_Salvar'/>";
		html += "</div>";

		div.innerHTML = html;
		, this.EDITOR_ID + "Menu"
		*/
		new DragManager([this.EDITOR_ID + "Componentes", this.EDITOR_ID + "Propriedades"], {x: 5, y: 5});
				
		this.setupActions();
	},
	
	createDiv: function(str, left, top){
		var div = document.createElement("div");
		div.id = this.EDITOR_ID + str;
		var pos = Cookies.read(div.id);
		if(pos){
			var arr = pos.split("&");
			div.style.left = arr[0];
			div.style.top = arr[1];
		}else{
			div.style.left = left;
			div.style.top = top;
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
		return div;
	},
	
	setupActions: function(){
		$(this.EDITOR_ID + '_AddFieldset').onclick = function(){
			this.create("fieldset");
		}.bind(this);
		
		$(this.EDITOR_ID + '_AddTextField').onclick = function(){
			this.create("inputText");
		}.bind(this);
		
		$(this.EDITOR_ID + '_AddLabel').onclick = function(){
			this.create("label");
		}.bind(this);
		
		$(this.EDITOR_ID + '_AddEntidade').onclick = function(){
			this.create("entidade");
		}.bind(this);
		
		$(this.EDITOR_ID + '_AddRadio').onclick = function(){
			this.create("radioButton");
		}.bind(this);

		$(this.EDITOR_ID + '_AddCheckbox').onclick = function(){
			this.create("checkbox");
		}.bind(this);		
		
		
		$(this.EDITOR_ID + '_Up').onclick = function(){
			this.moveUp();
		}.bind(this);
		
		$(this.EDITOR_ID + '_Down').onclick = function(){
			this.moveDown();
		}.bind(this);
		
		$(this.EDITOR_ID + '_Excluir').onclick = function(){
			this.excluir();
		}.bind(this);

/*
		$(this.EDITOR_ID + '_Salvar').onclick = function(){
			this.salvar();
		}.bind(this);
*/
	},
	
	moveUp: function(){
		if(!this.selectedObject){
			return;
		}
		if(this.selectedObject.style.zIndex < this.selectedObject.parentNode.length){
			this.selectedObject.style.zIndex++
		}
	},
	
	updateZIndex: function(parent){
		for(var i = 0; i < parent.childNodes.length; i++){
			if(parent.childNodes[i].style){
				parent.childNodes[i].style.zIndex = parent.childNodes.length - i;
			}
		}
	},
	
	moveDown: function(){
		if(!this.selectedObject){
			return;
		}
		if(this.selectedObject.style.zIndex > 0){
			this.selectedObject.style.zIndex--
		}
	},
	
	getMouseCoords: function(ev){
		if(ev.pageX || ev.pageY){
			return {x:ev.pageX, y:ev.pageY};
		}
		return {
			x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
			y:ev.clientY + document.body.scrollTop  - document.body.clientTop
		};
	},
	
	excluir: function(e){
		if(!this.selectedObject){
			return;
		}
		
		if(!confirm("Deseja mesmo excluir '"+ this.selectedObject.id +"' ?")){
			return;
		}
		
		this.selectedObject.parentNode.removeChild(this.selectedObject);
		dragManager.removeObject(this.selectedObject);
	},
	
	salvar: function(){
		//Criar lista de objetos a serem salvos;
		var objetos = [];
		var nodes = document.forms[0].childNodes;
		for(var i = 0; i < nodes.length; i++){
			if(nodes[i].properties){
				objetos[objetos.length] = nodes[i];
			}
		}
		
		var properties = "";
		
		for(var i = 0; i < objetos.length; i++){
			properties += "{";
			properties += "'_id':'"+ objetos[i]._id + "',";
			properties += "'_type':'"+ objetos[i]._type + "',";
			properties += "'properties': {";
			for(var j in objetos[i].properties){
				if(j.charAt(0) == '_'){
					continue;
				}
				var value = "" + objetos[i].properties[j]();
				if(value.charAt(0) == '{' || value.charAt(0) == '[') {
					properties += "'"+ j +"':"+ value +",";
				}
				else {
					properties += "'"+ j +"':'"+ value +"',";
				}
			}
			properties = properties.substring(0, properties.length - 1);
			properties += "}";
			properties += "};";
		}
		if(objetos.length > 0){
			properties = properties.substring(0, properties.length - 1);
		}
		
		var styles = "";
		for(var i = 0; i < objetos.length; i++){
			styles += "#" + objetos[i]._id;
			styles += "{";
			styles += objetos[i].style.cssText;
			styles += "}\n";
		}
		
		$(this.options.objectsInput).value = properties;
		if(this.options.stylesInput){
			$(this.options.stylesInput).value = styles;			
		}
		
		/*
		new Ajax.Request("ViewEditorServlet", {
			parameters: {
				action: "salvar",
				name: $('viewName').value,
				styles: styles,
				properties: escape(properties)
			},
			
			onSuccess: function(http) {
				//alert("Salvo com sucesso.");
				
			}.bind(this),
			
			onFailure: function(http) {
				throw http.statusText;
			},
			
			onException: function(http, e){
				throw e;
			}
		});
		*/
		
	},
	
	addObject: function(obj) {
		if(!this.objects){
			this.objects = [];
		}
		this.objects[this.objects.length] = obj;
	},
	
	updateObjectsFromString: function(str){
		var response = unescape(str);
		if(!response){
			return;
		}
		
		var tokens = response.split(";");
		
		for(var i = 0; i < tokens.length; i++){
			if(tokens[i].indexOf("dados:") == 0){
				var data = eval("("+ tokens[i].substring("dados:".length) +")");
				for(var i in data){
					$(i).set(data[i]);
				}
				continue;
			}
			
			var data = eval("("+ tokens[i] +")");
			var object = this.createObject(data._type, null, data._id);
			
			var p = data.properties;
			for(var j in p){
				object.properties[j].set(p[j]);
			}
			
			var styles = document.styleSheets;
			for(var j = 0; j < styles.length; j++){
				var rules = styles[j].cssRules ? styles[j].cssRules: styles[j].rules;
				for(var k = 0; k < rules.length; k++){
					if(rules[k].selectorText.toLowerCase() == ("#" + object._id).toLowerCase()){
						object.style.cssText = rules[k].style.cssText;
					}
				}			
			}
			this.addObject(object);
			if(this.dragManager){
				this.dragManager.addObject(object);
				this.dragManager.selectedObjectChanged(null);
			}
		}
		this.updateObjectsFromInput();
		if(ClassBehaviorApplier.apply){
			ClassBehaviorApplier.apply(document);
		}
	},
	
	getObjectsFromServer: function(){
		new Ajax.Request("ViewEditorServlet", {
			parameters: {
				action: "properties",
				name: $('viewName').value,
				pagina: $('paginaName').value
			},
			
			onSuccess: function(http) {
				this.updateObjectsFromString(http.responseText);
			}.bind(this),
			
			onFailure: function(http) {
				throw http.statusText;
			},
			
			onException: function(http, e){
				throw e;
			}
		});
	},
	
	createObject: function(objectType, e, id){
		if(objectType){
			var obj;
			if(objectType == "fieldset"){
				obj = document.createElement("fieldset");
				obj.id = this.getRandomId("Fieldset");
				obj.innerHTML = "<legend>Fieldset</legend>";
				//obj.style.margin = "0px";
				//obj.style.padding = "0px";
				obj.onlyView = true;
//				obj.style.border = "0px";
				
				p = {};
				p._i = obj;
				
				p.nome = function(){
					return this._i.id;
				}
				
				p.nome.set = function(str){
					this._i.id = str;
				}.bind(p);
				
				p.titulo = function(){
					var obj = this._i;
					return obj.getElementsByTagName("legend")[0].innerHTML;
				}
				
				p.titulo.set = function(str){
					var obj = this._i;
					obj.getElementsByTagName("legend")[0].innerHTML = str;
				}.bind(p);
				
				obj.properties = p;
			}
			else if(objectType == "inputText"){
				obj = document.createElement("input");
				obj.id = this.getRandomId("InputText");
				obj.type = "text";
				
				p = {};
				p._i = obj;
				
				p.nome = function(){
					return this._i.id;
				}
				
				p.nome.set = function(str){
					this._i.id = str;
				}.bind(p);
				
				p.texto = function(){
					return this._i.value;
				}
				
				p.texto.set = function(str){
					this._i.value = str;
				}.bind(p);
				
				p.formato = function(){
					return this._i.className;
				}
				
				p.formato.set = function(str){
					this._i.className = str;
				}.bind(p);
				
				p.formato.options = {
					tipo: "select",
					valores: ["TEXTO" 
							,"NUMERICO"
							,"CEP"
							,"CNPJ"
							,"CPF"
							,"DATA"
							,"MAIL"
							,"MOEDA"]
				}
				p.formato.set("TEXTO");
				
				p.obrigatorio = function(){
					return this._i.obrigatorio ? true : false;
				}
							
				p.obrigatorio.set = function(str){
					this._i.obrigatorio = ((str != null && (str == true || str.toLowerCase() == "on" || str.toLowerCase() == "true")) ? true : false);
				}.bind(p);
				
				p.obrigatorio.options = {
					tipo: "boolean"
				}
				
				p.bloqueado = function(){
					return this._i.bloqueado ? true : false;
				}
				
				p.bloqueado.set = function(str){
					this._i.bloqueado = ((str != null && (str == true || str.toLowerCase() == "on" || str.toLowerCase() == "true")) ? true : false);
				}.bind(p);
				
				p.bloqueado.options = {
					tipo: "boolean"
				}
				
				p.tamanho = function(){
					return  this._i.tamanho ? this._i.tamanho : 30;
				}
				
				p.tamanho.set = function(str){
					return  this._i.tamanho = parseInt(str);
				}.bind(p)
				
				p.tamanho.options = {
					classe: "inputTextInteger"
				}
				
				obj.properties = p;
				obj.get = function(){
					return obj.value;
				}
				
				obj.set = function(str){
					obj.value = str;
				}
			}
			else if(objectType == "radioButton"){
				obj = document.createElement("span");
				obj.id = this.getRandomId("Radio");
				
				p = {};
				p._i = obj;
				
				p.nome = function(){
					return this._i.id;
				}
				
				p.nome.set = function(str){
					this._i.id = str;
				}.bind(p);
				
				p.valores = function(){
					var inputs = this._i.getElementsByTagName("input");
					var value = "";
					for(var i = 0; i < inputs.length; i++){
						value += inputs[i].value + ",";
					}
					return value.substring(0, value.length - 1);
				}
				
				p.valores.set = function(str){
					var layout = this._i.layout;
					html = "";
					var arr = str.split(",");
					
					if(layout == "Fluxo"){
						for(var i = 0; i < arr.length; i++){
							html += "<input id='"+ this._i.id +"_Radio"+ i +"' type='radio' value='"+ arr[i] +"' name='"+ obj.id +"'>";
							html += "<label for='"+ this._i.id +"_Radio"+ i +"' id='"+ this._i.id +"_Label"+ i +"'>"+ arr[i] +"</label>"
						}
					}
					else if(layout == "Horizontal"){
						html += "<table>"
						html += "<tr>"
						for(var i = 0; i < arr.length; i++){
							html += "<td>";
							html += "<input id='"+ this._i.id +"_Radio"+ i +"' type='radio' value='"+ arr[i] +"' name='"+ obj.id +"'>";
							html += "</td><td>";
							html += "<label for='"+ this._i.id +"_Radio"+ i +"' id='"+ this._i.id +"_Label"+ i +"'>"+ arr[i] +"</label>"
							html += "</td>";
						}
						html += "</tr>"
						html += "</table>"
					}
					else if(layout == "Vertical"){
						html += "<table>"						
						for(var i = 0; i < arr.length; i++){
							html += "<tr>"
							html += "<td>";
							html += "<input id='"+ this._i.id +"_Radio"+ i +"' type='radio' value='"+ arr[i] +"' name='"+ obj.id +"'>";
							html += "</td><td>";
							html += "<label for='"+ this._i.id +"_Radio"+ i +"' id='"+ this._i.id +"_Label"+ i +"'>"+ arr[i] +"</label>"
							html += "</td>";
							html += "</tr>"
						}
						
						html += "</table>"
					}
					obj.innerHTML = html;
				}.bind(p);
				
				p.layout = function(){
					return this._i.layout;
				}
				
				p.layout.set = function(str){
					this._i.layout = str;
					this.valores.set(this.valores());
					
				}.bind(p);
				
				p.layout.options = {
					tipo: "select",
					valores: ["Fluxo" 
							,"Horizontal"
							,"Vertical"]
				}
				
				p.layout.set("Fluxo");
				p.valores.set("A, B, C");
				
				p.obrigatorio = function(){
					return this._i.obrigatorio ? true : false;
				}
	
				p.obrigatorio.set = function(str){
					this._i.obrigatorio = ((str != null && (str == true || str.toLowerCase() == "on" || str.toLowerCase() == "true")) ? true : false);
				}.bind(p);
				
				p.obrigatorio.options = {
					tipo: "boolean"
				}
				
				p.bloqueado = function(){
					return this._i.bloqueado ? true : false;
				}
				
				p.bloqueado.set = function(str){
					this._i.bloqueado = ((str != null && (str == true || str.toLowerCase() == "on" || str.toLowerCase() == "true")) ? true : false);
				}.bind(p);
				
				p.bloqueado.options = {
					tipo: "boolean"
				}
				
				obj.get = function(){
					var inputs = this.getElementsByTagName("input");
					var labels = this.getElementsByTagName("label");
					for(var i = 0; i < inputs.length; i++){
						if(inputs[i].checked){
							return labels[i].innerHTML;
						}
					}
				}
				
				obj.set = function(str){
					var inputs = this.getElementsByTagName("input");
					var labels = this.getElementsByTagName("label");
					for(var i = 0; i < inputs.length; i++){
						if(labels[i].innerHTML.toLowerCase() == str.toLowerCase()){
							inputs[i].checked = true;
							return;
						}
					}
				}
				
				obj.properties = p;
			}
			else if(objectType == "checkbox"){
				obj = document.createElement("span");
				obj.id = this.getRandomId("Checkbox");
				
				html = "";
				var input = document.createElement("input");
				input.id = obj.id + "_Checkbox";
				input.type = "checkbox";
				input.name = obj.id;
				obj.appendChild(input);
				
				var label = document.createElement("label");
				label.id = obj.id + "_Label";
				label.innerHTML = "Checkbox";
				obj.appendChild(label);				
				
				obj.label = label;
				obj.input = input;
				
				p = {};
				p._i = obj;
				
				p.nome = function(){
					return this._i.id;
				}
				
				p.nome.set = function(str){
					this._i.id = str;
				}.bind(p);
				
				p.label = function(){
					return this._i.label.innerHTML;
				}
				
				p.label.set = function(str){
					this._i.label.innerHTML = str;
				}.bind(p);
				
				
				p.bloqueado = function(){
					return this._i.bloqueado ? true : false;
				}
				
				p.bloqueado.set = function(str){
					this._i.bloqueado = ((str != null && (str == true || str.toLowerCase() == "on" || str.toLowerCase() == "true")) ? true : false);
				}.bind(p);
				
				p.bloqueado.options = {
					tipo: "boolean"
				}
				
				obj.get = function(){
					return this.input.checked
				}
				
				obj.set = function(str){
					return this.input.checked = ((str != null && (str == true || str.toLowerCase() == "on" || str.toLowerCase() == "true")) ? true : false);
				}
				
				obj.properties = p;
			}
			else if(objectType == "label"){
				obj = document.createElement("label");
				obj.id = this.getRandomId("Label");
				obj.innerHTML = "Label";
				obj.onlyView = true;
				
				p = {};
				p._i = obj;
				
				p.nome = function(){
					return this._i.id;
				}
				
				p.nome.set = function(str){
					this._i.id = str;
				}.bind(p);
				
				p.texto = function(){
					return this._i.innerHTML;
				}
				
				p.texto.set = function(str){
					this._i.innerHTML = str;
				}.bind(p);
				
				obj.properties = p;
			}
			else if(objectType == "entidade"){
				obj = document.createElement("span");
				obj.id = this.getRandomId("Entidade");
				
				var link = document.createElement("a");
				link.href = "#";
				link.innerHTML = "#";
				obj.appendChild(link);
				
				var value = document.createElement("input");
				value.id = "_value";
				value.type = "hidden";
				obj.appendChild(value);
				
				var p = {};
				p._i = obj;
				
				if(!this.dragManager){
					link.onclick = function(){
						//var url = '../spr/listagemDadoTabelaExterna.jsf?dados='+ escape(this.dados());
						//alert(this._i.id);
						//alert (this.dados());
						//alert (escape(this.dados()));
						var url = '../spr/listagemEntidadeServlet?dados='+ escape(this.dados()) + "&id_entidade=" + this._i.id;
						window.open(url, "Listagem", "width=600, height=500, menubar=no,location=no,resizable=yes,scrollbars=yes,status=yes");
					}.bind(p);				
				}
				
				p.nome = function(){
					return this._i.id;
				}
				
				p.nome.set = function(str){
					this._i.id = str;
				}.bind(p);
				
				p.dados = function(){
					var d = "{"
					d += "'tabela':'" + this._tabela + "',";
					d += "'campos':";
					d += "[";
					if(this._campos){
						for(var i = 0; i < this._campos.length; i++){
							if(!this._campos[i]){
								continue;
							}
							d += "{'campo': '"+ this._campos[i].campo +"', 'view':'"+ this._campos[i].view +"', 'pesquisa':'"+ this._campos[i].pesquisa +"'},";
						}
						if(this._campos.length > 0){
							d = d.substring(0, d.length - 1);
						}
					}
					d += "]"
					d += "}";
					
					return d;
				}.bind(p);	
				
				p.dados.set = function(obj){
					this._tabela = obj.tabela;
					this._campos = obj.campos;
					
					for(var i = 0; i < this._campos.length; i++){
						if(!this._campos[i].input){
							var input = document.createElement("input");
							input.id = this._campos[i].campo;
							input.size="30";
							input.readOnly = true;
							this._i.appendChild(input);
							
							this._campos[i].input = input;
						}
					}
				}.bind(p);			
				
				p.dados.options = {
					tipo: "editor",
					editor : function(){
						var r = "";
						r += "<label>Tabela </label>";
						r += "<select id='"+ this._i._id +"_Tabelas' style='width: 250px;'>";
						r += "</select>";
						
						r += "<table id='"+ this._i._id +"_Campos'>";
						r += "<tr><th>Campo</th><th title='Visual'>V</th><th title='Pesquisa'>P</th></tr>";
						r += "</table>";
						
						r += "<input type='button' id='"+ this._i._id +"_AddCammpo' value='+' title='Add. Campo'  />";
						return r;
					}.bind(p),
					
					setup : function(){
						$(this._i._id + "_Tabelas").onchange = function(){
							this._tabela = $(this._i._id + "_Tabelas").getValue();
							this._atualizarPropriedades();
							this._excluirCampos();
						}.bind(p);
						
						if(this._tabelas){
							this._atualizarSelectTabelas();
						}
						else {
							this._atualizarListaTabelasESelect();	
						}
						
						if(this._propriedades){
							this._autalizarPropriedadesSelect();							
						}
						
						$(this._i._id +"_AddCammpo").onclick = function(){
							this._criarCampo();
						}.bind(p);
					}.bind(p)
				};
				
				p._atualizarListaTabelasESelect = function(){
					new Ajax.Request("ViewEditorServlet", {
						parameters: {
							action: "tabelas"
						},
						onSuccess: function(http) {
							this._tabelas = eval("("+ http.responseText +")");
							this._atualizarSelectTabelas();
						}.bind(p),
						
						onFailure: function(http) {
							throw http.statusText;
						},
						
						onException: function(http, e){
							throw e;
						}
					});
				};
				
				p._atualizarSelectTabelas = function(){
					if(!$(this._i._id + "_Tabelas")){
						return;
					}
				
					var option = document.createElement("option");
					option.innerHTML = "";
					option.label = "";
					option.value = "";
					$(this._i._id + "_Tabelas").appendChild(option);
										
					for(var i = 0; i < this._tabelas.length; i++){
						var option = document.createElement("option");
						if(this._tabela){
							if(this._tabelas[i] == this._tabela){
								option.selected = true;
								if(!this._propriedades){
									this._atualizarPropriedades();
								}
							} 
						}
						option.innerHTML = this._tabelas[i];
						option.label = this._tabelas[i];
						option.value = this._tabelas[i];
						$(this._i._id + "_Tabelas").appendChild(option);
					}
				}.bind(p);
				
				p._atualizarPropriedades = function(){
					new Ajax.Request("ViewEditorServlet", {
						parameters: {
							action: "propriedades",
							tabela: this._tabela
						},
						onSuccess: function(http) {
							var props = eval("("+ http.responseText +")");
							this._propriedades = props;
							this._autalizarPropriedadesSelect();
						}.bind(p),
						
						onFailure: function(http) {
							throw http.statusText;
						},
						
						onException: function(http, e){
							throw e;
						}
					});
				}
				
				p._autalizarPropriedadesSelect = function(){
					if(this._campos){
						for(var i = 0; i < this._campos.length; i++){
							this._criarCampo(i);
						}
					}
				}.bind(p);
				
				p._criarCampo = function(index){
					if(!this._propriedades){
						alert("Voce deve selecionar uma tabela.");
						return;
					}
					
					if(!this._campos){
						this._campos = [];
					}
					
					var tr = document.createElement("tr");
					$(this._i._id +"_Campos").firstChild.appendChild(tr);
					
					var tds = [];
					for(var i = 0; i < 4; i++ ){
						tds[i] = document.createElement("td");
						tr.appendChild(tds[i]);
					}
										
					var select = document.createElement("select");
					if(index == null){
						index = this._campos.length;
					}
					
					select.id = this._i._id + "_Campos_" + index;
					select._index = index;
					
					
					var value;
					if(index != this._campos.length){
						value = this._campos[index].campo;
					} 
					else {
						value = null;
						this._campos[index] = value;
					}
					
					var option = document.createElement("option");
					option.innerHTML = "";
					option.label = "";
					option.value = "";
					select.appendChild(option);
					
					var props = this._propriedades;
					for(var i = 0; i < props.length; i++){
						var option = document.createElement("option");
						if(value == props[i]){
							option.selected = true;
						}
						option.innerHTML = props[i];
						option.text = props[i];
						option.value = props[i];
						select.appendChild(option);
					}
					
					if(!this._campos[index]){
						this._campos[index] = {};
					}
					
					if(!this._campos[index].input){
						var input = document.createElement("input");
						input.readOnly = true;
						this._i.appendChild(input);
						this._campos[index].input = input;
					}
					
					select.input = this._campos[index].input;
					select.onchange = function(){
						this._p._campos[this._index].campo = Form.Element.Methods.getValue(this);						
						this.input.value = this._p._campos[this._index].campo;
					}
					
					select._p = this;
					tds[0].appendChild(select);
					//$(this.dados._container).appendChild(select);
					
					var view = document.createElement("input");
					view.type = "checkbox";
					view.id = this._i._id + "_Campos_" + index + "_ViewCheck";
					tds[1].appendChild(view);
					
					if(!this._campos[index].view){
						this._campos[index].view = false;
					}
					if(this._campos[index]){
						var valorView = new String(this._campos[index].view);
						view.checked = (valorView.toLowerCase() == "on" || valorView.toLowerCase() == "true");
					}
					view.onclick = function(){
						this._p._campos[this._campo._index].view = this.checked;
					}
					view._p = this;
					view._campo = select;
					
					var pesquisa = document.createElement("input");
					pesquisa.type = "checkbox";
					pesquisa.id = this._i._id + "_Campos_" + index + "_PesquisaCheck";
					tds[2].appendChild(pesquisa);
					if(!this._campos[index].pesquisa){
						this._campos[index].pesquisa = false;
					}
					if(this._campos[index]){
						var valorPesquisa = new String(this._campos[index].pesquisa);
						pesquisa.checked = (valorPesquisa.toLowerCase() == "on" || valorPesquisa.toLowerCase() == "true");
					}
					
					pesquisa.onclick = function(){			
						this._p._campos[this._campo._index].pesquisa = this.checked;
					}
					pesquisa._p = this;
					pesquisa._campo = select;
//					$(this.dados._container).appendChild(pesquisa);
					
					var remove = document.createElement("input");
					remove.type = "button";
					remove.value = "-"
					remove.onclick = function(){						
						this._p._campos[this._campo._index] = null;
						
						var node = $(this._campo);
						var parent = $(this._campo).parentNode;
						
						while(node.nodeName.toLowerCase() != "tr"){
							var node = node.parentNode;
							var parent = node.parentNode;							
						}
						parent.removeChild(node);
					}
					
					remove._p = this;
					remove._campo = select;
					
					select._remover = remove;
					tds[3].appendChild(remove);
//					$(this.dados._container).appendChild(remove);

//					$(this._i._id +"_Campos").firstChild.appendChild(tr);
				}.bind(p);
				p._excluirCampos = function(){
					if(!this._campos){
						return;
					}
					$(this._i._id +"_Campos").firstChild.innerHTML = "";
					this._campos = [];
				}
				
				p.obrigatorio = function(){
					return this._i.obrigatorio ? true : false;
				}
				
				p.obrigatorio.set = function(str){
					this._i.obrigatorio = ((str != null && (str == true || str.toLowerCase() == "on" || str.toLowerCase() == "true")) ? true : false);
				}.bind(p);
				
				p.obrigatorio.options = {
					tipo: "boolean"
				}
				
				p.bloqueado = function(){
					return this._i.bloqueado ? true : false;
				}
				
				p.bloqueado.set = function(str){
					this._i.bloqueado = ((str != null && (str == true || str.toLowerCase() == "on" || str.toLowerCase() == "true")) ? true : false);
				}.bind(p);
				
				p.bloqueado.options = {
					tipo: "boolean"
				}
				
				obj.get = function(){
					var result = "[";
					var inputs = this.getElementsByTagName("input");
					for(var i = 0; i < inputs.length; i++){
						result += "'"+ inputs[i].value +"',";
					}
					result = result.substring(0, result.length - 1);
					result += "]";
					return result;
				}
				
				obj.set = function(arr){
					var inputs = this.getElementsByTagName("input");
					for(var i = 0; i < arr.length; i++){
						inputs[i].value = arr[i];
					}
				}
				
				obj.properties = p;
			}
			obj._type = objectType;
			if(!id){
				id = obj.properties._i.id;
			}
			obj._id = id;
			if(!obj.get){
				obj.get = function(){ 
					return "" ;
				}
			}
			
			if(!obj.set){
				obj.set = function(str){
				}
			}
			
			if(e){
				var coords = this.getMouseCoords(e);
				obj.style.position = "absolute";			
				obj.style.left = coords.x + "px";
				obj.style.top = coords.y + "px";
			}
			
			this.putObjectOnDocument(obj);
			
			if(this.dragManager){
				this.dragManager.addObject(obj);
				this.dragManager.selectedObjectChanged(obj);
			}
			
			return obj;
		}
	},
	
	create: function(type){
		this.createOnNextClick = type;
	},
	
	click: function(e){
		this.createObject(this.createOnNextClick, e);
		this.createOnNextClick = null;
	},
	
	updateProperties: function(){
		if(!this.selectedObject){
			$(this.EDITOR_ID +'_areaProps').innerHTML = "";
			return;
		}
	
		var props = this.selectedObject.properties;
		var html = "<table>";
		for(var i in props){
			if(i.charAt(0) == '_'){
				continue;
			}
			if(props[i].options && props[i].options.tipo != null){
				if(props[i].options.tipo == "select"){
					html += "<tr><td>";
					html+= this.getLabel(i);
					html += "</td><td>";
					html += "<select id='"+ this.EDITOR_ID + '_editor_' + i +"'>"
					var valores = props[i].options.valores; 
					var valor = eval("props." + i + "()");
					for(var j = 0; j < valores.length; j++){
						html += "<option "+ (valor == valores[j]? "selected='true'": "") +" >" + valores[j] + "</option>";						
					}
					html += "</select>"
				}
				else if(props[i].options.tipo == "boolean"){
					html += "<tr><td>";
					html += this.getLabel(i);
					html += "</td><td>";
					var valor = eval("props." + i + "()");
					html += "<input type='checkbox' id='"+ this.EDITOR_ID + '_editor_' + i +"' ";
					if(valor){
						html += "checked='"+ valor +"' ";
					}

					html += "/>";
				}
				else if(props[i].options.tipo == "editor"){
					html += "<tr><td colspan='2'>";
					html += "<fieldset>";
					html += "<legend>"+ this.getLabel(i) +"</legend>";
					html += "<span id='"+ this.EDITOR_ID + '_editor_' + i +"'>"
					html += props[i].options.editor();
					html += "</span>"
					html += "</fieldset>";
				}
			} else {
				html += "<tr><td>";
				html+= this.getLabel(i);
				html += "</td><td>";
				html+= "<input type='text' id='"+ this.EDITOR_ID + '_editor_' + i + "' ";
				if(props[i].options){
					if(props[i].options.classe){
						html += "class='"+ props[i].options.classe +"' ";
					}
				}
				html += "value='"+ eval("props." + i + "()") +"'>";
			}
			html+= "</td></tr>";
		}
		html+= "</table>";
		$(this.EDITOR_ID +'_areaProps').innerHTML = html;
		
		if(ClassBehaviorApplier.apply){
			ClassBehaviorApplier.apply($(this.EDITOR_ID +'_areaProps'));
		}
		
		for(var i in props){
			if(i.charAt(0) == '_'){
				continue;
			}
			var editor = $(this.EDITOR_ID + '_editor_' + i);
			editor._property = i;			
			editor._i = this.selectedObject;
			
			if(props[i].options && props[i].options.tipo == "editor"){
				props[i].options.setup();
			}
			else {
				if(props[i].options && props[i].options.tipo == "boolean"){
					editor.onclick = function(){
						var obj = this._i;
						var property = this._property;
						
						obj.properties[property].set(Form.Element.Methods.getValue(this));
					}
				}
				else {
					editor.onchange = function(){
						var obj = this._i;
						var property = this._property;
						
						obj.properties[property].set(Form.Element.Methods.getValue(this));
					}
				}
			}
			editor._i.properties[i]._container = this.EDITOR_ID + '_editor_' + i;
		}
	},
	
	putObjectOnDocument: function(object){
		var nodes = document.forms[0].childNodes;
		document.forms[0].appendChild(object);
		
		var index = 0;
		for(var i = 0; i < nodes.length; i++){
			if(!nodes[i].properties){
				continue;
			}
			if(index < nodes[i].style.zIndex){
				index = nodes[i].style.zIndex;
			}
		}
		object.style.zIndex = index;
	},
	
	getLabel: function(header){
		header = header.charAt(0).toUpperCase() + header.substring(1);
		for(var i = header.length -1; i > 1; i--){
			if(header.charAt(i) == header.charAt(i).toUpperCase()){
				header = header.substring(0, i) + " " + header.substring(i);
			}
		}
		return header;
	}, 
	
	getRandomId: function(name){
		return name + parseInt(Math.random() * 1000);
	},
	isTrue: function(str){
		return (str.toLowerCase() == "on" || str.toLowerCase() == "true" || str == true ? true : false);
	}
}
