Events = {
	
}
Events.add = function(obj, type, fn) {
	if (obj.attachEvent) {
	    var key = Math.random();
	    fn._key = key;
	    
		obj['e' + type + key] = fn;
		obj[type + key] = function() {obj['e' + type + key](window.event); }
		
		obj.attachEvent('on'+ type , obj[type + key]);
	} else
	obj.addEventListener(type,fn,false);
}

Events.remove = function(obj, type, fn) {
	if (obj.detachEvent) {
		obj.detachEvent('on'+ type, obj[type + fn._key]);
		obj[type + fn._key] = null;
	} else
	obj.removeEventListener(type,fn,false);
}