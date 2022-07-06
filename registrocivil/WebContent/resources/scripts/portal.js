Portal = {};
Portal.Modulo = function(name, options){
	this.name = name;
	this.options = options;
	
	this.load();
}
Portal.Modulo.prototype = {
	load: function(){
		new Ajax.Request("modulosPortal.jsf", {
			parameters: "target=" + this.name, 
			method: "post", 
			onSuccess: this.options.onLoad,
			onFailure: this.options.onFailure
			}
		);
	}
}