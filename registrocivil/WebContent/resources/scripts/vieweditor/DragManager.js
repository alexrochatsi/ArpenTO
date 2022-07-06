//http://www.webreference.com/programming/javascript/mk/column2/index.html
include("Events.js");
DragManager = function(objects, snap){
	Events.add(document, "mousemove", this.mouseMove.bind(this));
	Events.add(document, "mouseup", this.mouseUp.bind(this));
	
	this.setupSnap(snap);
	
	if(objects){
		this.addObjects(objects);
	} else {
		this.addAllObjects(document);
	}
}
DragManager.prototype = {
	/**
	 * Metodo que adiciona todos os objetos no documento que tem id ao DragManager
	 */
	addAllObjects: function(element){
		for(var i = 0; i < element.childNodes.length; i++){
			if(element.childNodes[i].className != "IgnoreDragg"){
				if(this.isMoveable(element.childNodes[i])){
					this.addObject(element.childNodes[i]);
				}
				this.addAllObjects(element.childNodes[i]);
			}
		}
	},
	
	MOVEABLES: ["DIV", "INPUT", "LABEL", "SPAN", "A", "TABLE", "UL", "SELECT", "TEXTAREA", "FIELDSET"],
	
	isMoveable: function(object){
		if(!object.id){
			return false;
		}
		for(var i = 0; i < this.MOVEABLES.length; i++){
			if(this.MOVEABLES[i] == object.nodeName.toUpperCase()){
				return true;
			}
		}
	},
	
	setupSnap: function(snap){
		this.snap = snap;
		if(!snap){
			this.snap = {x: 1, y: 1};
		}
		
		if(!this.snap.x){
			this.snap.x = 1;
		}
		
		if(!this.snap.y){
			this.snap.y = 1;
		}
	},
	
	setContainer: function(container){
		this.container = container;
	},
	
	mouseMove: function(ev){
		ev           = ev || window.event;
		var mousePos = this.getMouseCoords(ev);
		
		var pos = {}
		if(this.dragObject){
			this.dragObject.style.position = 'absolute';			
			pos.top      = this.getNearestSnapMultiple(mousePos.y - this.mouseOffset.y, this.snap.y);			
			pos.left     = this.getNearestSnapMultiple(mousePos.x - this.mouseOffset.x, this.snap.x);
			
			
			if(this.container){
				var limit = parseInt(this.container.style.top) + Element.Methods.getHeight(this.container) - Element.Methods.getHeight(this.dragObject);
				if(pos.top > limit){
					pos.top = limit;
				}
				
				var limit = parseInt(this.container.style.top);
				if(pos.top < limit){
					pos.top = limit;
				}
				
				var limit = parseInt(this.container.style.left) + Element.Methods.getWidth(this.container) - Element.Methods.getWidth(this.dragObject);
				if(pos.left > limit){
					pos.left = limit;
				}
				
				var limit = parseInt(this.container.style.left)
				if(pos.left < limit){
					pos.left = limit;
				}
			}
			
			var oldpos = this.getObjectPosition(this.dragObject);
			
			this.dragObject.style.top = pos.top + "px";
			this.dragObject.style.left = pos.left + "px";
			//window.status = this.dragObject.style.top + "," + this.dragObject.style.left;
			
			if(this.dragObject.ondrag){
				this.dragObject.ondrag();
			}
			
			var disp = {x: pos.left - oldpos.x, y: pos.top - oldpos.y};
			if(this.selectedObjects){
				for(var i = 0; i < this.selectedObjects.length; i++){
					if(this.selectedObjects[i] == this.dragObject){
						continue;
					}
					
					var start = this.getObjectPosition(this.selectedObjects[i]);
					start.x += disp.x;
					start.y += disp.y;
					
					this.selectedObjects[i].style.left = start.x + "px";
					this.selectedObjects[i].style.top = start.y + "px";
				}
			}
			
			if(this.dragObject.className != "DraggIgnoreBlock"){
				this.selectedObjectChanged(this.dragObject);
			}
			return true;
		}
	},
	
	getNearestSnapMultiple: function(value, snap){
		var resto = value % snap;
		if(resto > snap / 2){
			return value - resto + snap;
		}
		else {
			return value - resto;
		}
	},
	
	mouseUp: function(ev){
		this.dragObject = null;
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
	
	addObjects: function(objects){
		for(var i = 0; i < objects.length; i++){
			var object = document.getElementById(objects[i]);
			this.addObject(object);
		}
	},
	
	clear: function(){
		for(var i = 0; i < this.objects.length; i++){
			this.clear(this.objects[i], false);
		}
		this.objects = [];
	}, 
	
	clear: function(object, removeFromStack){
		if(!object){
			return;
		}
		Events.remove(object, "mousedown", this._objectMouseDown);		
		if(removeFromStack){
			for(var i = 0; i < this.objects.length; i++){
				if(this.objects[i] == object){
					this.objects.splice(i, 1);
				}
			}	
		}
	},
	
	_objectMouseDown: function(event){
		this.dragManager.mouseDown(this, event);
		return true;
	},
	
	addObject: function(object){
		if(!this.objects){
			this.objects = [];
		}
		
		if(object){
			object.dragManager = this;
			Events.add(object, "mousedown", this._objectMouseDown);
			
			if(object.className != "DraggIgnoreBlock"){
				object.title = object.id;				
				object.onclick = function(event){
					return false;
				}
				this.objects[this.objects.length] = object;
			}
		}
	},
	
	removeObject: function(object){
		if(!this.objects){
			return;
		}
		
		if(object){
			for(var i in this.objects){
				if(object == this.objects[i]){
					this.objects.splice(i, 1);
					this.selectedObjectChanged(null);
					return true;
				}
			}
			return false;
		}
	},
	
	mouseDown: function(object, event){
		this.clickOverObject = true;
		this.dragObject = object;
		
		if(this.selection != object){
			if(event.ctrlKey){
				if(!Element.Methods.hasClassName(object, "DraggIgnoreBlock")){
					if(!this.selectedObjects){
						this.selectedObjects = [];
					}
					if(!this.contains(this.selectedObjects, this.selection)){
						this.selectedObjects[this.selectedObjects.length] = this.selection;
						Element.Methods.addClassName(this.selection, "Selecionado");
					}
				}
			}
			else {
				if(this.selectedObjects){
					for(var i = 0; i < this.selectedObjects.length; i++){
						Element.Methods.removeClassName(this.selectedObjects[i], "Selecionado");
					}
				}
				this.selectedObjects = [];
			}
			this.selectionChanged(object);
		}
		
		this.mouseOffset = this.getMouseOffset(object, event);
		if(object.className != "DraggIgnoreBlock"){
			this.selectedObjectChanged(object);
		}
	},
	
	contains: function(array, object){
		for(var i = 0; i < array.length; i++){
			if(array[i] == object){
				return true;
			}
		}
		return false;
	},
	
	selectionChanged: function(object){
		this.selection = object;
	},

	selectedObjectChanged: function(object){
		this.selectionChanged(object);
		if(this.objectSelectionListeners){
			for(var i = 0; i < this.objectSelectionListeners.length; i++){
				this.objectSelectionListeners[i](object);
			}
		}
	}, 
	
	addObjectSelectionListener: function(listener){
		if(!this.objectSelectionListeners){
			this.objectSelectionListeners = [listener];
		} else {
			this.objectSelectionListeners[this.objectSelectionListeners.length] = listener;
		}
	},
	
	getMouseOffset: function(target, ev){
		ev = ev || window.event;
	
		var docPos = this.getPosition(target);
		var mousePos = this.getMouseCoords(ev);
		return {x:mousePos.x - docPos.x, y:mousePos.y - docPos.y};
	},
	
	getPosition: function(e){
		var left = 0;
		var top  = 0;
	
		while (e.offsetParent){
			left += e.offsetLeft;
			top  += e.offsetTop;
			e     = e.offsetParent;
		}
	
		left += e.offsetLeft;
		top  += e.offsetTop;
	
		return {x:left, y:top};
	},
	
	getObjectPosition: function(obj){
		if(obj == null){
			return {x: 0, y: 0};
		}
		var curleft = curtop = 0;
		if (obj.offsetParent) {
			curleft = obj.offsetLeft
			curtop = obj.offsetTop
			while (obj = obj.offsetParent) {
				curleft += obj.offsetLeft
				curtop += obj.offsetTop
			}
		}
		return {x: curleft, y: curtop};
	}
}
