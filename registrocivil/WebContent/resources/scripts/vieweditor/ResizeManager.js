//http://www.webreference.com/programming/javascript/mk/column2/index.html
include("prototype.js");
ResizeManager = function(dragManager){
	dragManager.addObjectSelectionListener(function(object){
		var handlers = this.updateHandlers(object);
		this.object = object;
		if(!this.handlersDragger){
			this.handlersDragger = new DragManager(handlers, {x: 2, y: 2});
		}
	}.bind(this));
}
ResizeManager.prototype = {
	updateHandlers: function(id){
		if(!id){
			if(this.handlers){
				this.handlers[0].style.display = "none";
				this.handlers[1].style.display = "none";
			}
			return;
		}
		var obj = $(id);
		obj.getPosition = function() {
			var obj = this;
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
		if(!this.handlers){
			this.handlers = [];
		}
		
		if(!this.handlers[0]){
			this.handlers[0] = document.createElement("div");
			obj.parentNode.appendChild(this.handlers[0]);
		}
		var div = this.handlers[0];
		div.id = "W-handler"
		div.style.position = "absolute";
		div.style.height = "5px";
		div.style.width = "5px";
		var pos = obj.getPosition();
		div.style.top = (pos.y + obj.getDimensions().height / 2 - 3) + "px";
		div.style.left = (pos.x + obj.getDimensions().width - 4) + "px";
		div.style.border = "1px solid black";
		div.style.fontSize = "1px";
		div.style.background = "black";
		div.style.cursor = "pointer";
		div.style.display = "block";
		div.style.zIndex = 1000000;
		
		div.ondrag = function(){
			var obj = this.object;
			var width = (parseInt(this.handlers[0].style.left) - obj.getPosition().x);
			obj.style.width = (width > 0 ? width: 0) + "px";		
		}.bind(this);
				
		if(!this.handlers[1]){
			this.handlers[1] = document.createElement("div");
			obj.parentNode.appendChild(this.handlers[1]);
		}
		div = this.handlers[1];
		div.id = "H-handler"
		div.style.position = "absolute";
		div.style.height = "5px";
		div.style.width = "5px";
		div.style.font.size= "1px;";
		var pos = obj.getPosition();
		div.style.top = (pos.y + obj.getDimensions().height - 4) + "px";
		div.style.left = (pos.x + obj.getDimensions().width / 2 - 3) + "px";
		div.style.border = "1px solid black";
		div.style.fontSize = "1px";
		div.style.background = "black";
		div.style.cursor = "pointer";
		div.style.display = "block";
		div.style.zIndex = 1000000;

		div.ondrag = function(){
			var obj = this.object;
			var height = (parseInt(this.handlers[1].style.top) - obj.getPosition().y - 2);
			obj.style.height = (height > 0 ? height: 0) + "px";
		}.bind(this);
		
		return [this.handlers[0].id, this.handlers[1].id];
	}
}
