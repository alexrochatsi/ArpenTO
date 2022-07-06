Cookies = {
	
}
Cookies.create = function(name, value, days) {
	if (days) {
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}

Cookies.read = function(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

Cookies.erase = function(name) {
	Cookies.create(name, "", -1);
/*
	var cook = document.cookie;
	var start = cook.indexOf(name);
	var end = cook.indexOf(";", start);
	
	document.cookie = cook.substring(0, start) 
	if(end){
		document.cookie	+ cook.substring(end + ";".length);
	}	
*/
}

Server = {

}

Server.get = function(name) {
	get_string = document.location.search;         
	return_value = '';
 
	do { //This loop is made to catch all instances of any get variable.
    	name_index = get_string.indexOf(name + '=');
		if(name_index != -1) {
			get_string=get_string.substr(name_index + name.length + 1, get_string.length - name_index);
      		end_of_value = get_string.indexOf('&', name_index);
			
			if(end_of_value != -1)
	 	       value = get_string.substr(0, end_of_value);
			else                
    	    	value = get_string;
    	    	
			if(return_value == '' || value == '')
				return_value += value;
			else
				return_value += ', ' + value;
		}
    } while(name_index != -1)
    
	//Restores all the blank spaces.
	space = return_value.indexOf('+');
	while(space != -1){ 
		return_value = return_value.substr(0, space) + ' ' + 
		return_value.substr(space + 1, return_value.length);
					 
		space = return_value.indexOf('+');
	}
  
 	return(return_value);        
 }