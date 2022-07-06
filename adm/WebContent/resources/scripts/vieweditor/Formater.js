Formater = {
	
}
Formater.LEFT_TO_RIGHT = 1;
Formater.RIGHT_TO_LEFT = 2;

Formater.format = function(value, format, direction, invalidCharsRegex) {
	if(direction == Formater.RIGHT_TO_LEFT){
		if(!invalidCharsRegex){
			invalidCharsRegex = /[^a-zA-Z0-9]/
		}
		value = value.replace(invalidCharsRegex, '');
		
		for(var i = 0, j = format.length - 1; i < value.length && j >= 0; i++, j--){
			var char = format.charAt(j);
			if(char != '#' && value.charAt(value.length - 1 - i) != char){
				value = value.substring(0, value.length - i) + char + value.substring(value.length - i);
			}
		}
		
	} else {
		if(value.length > format.length){
			return value;
		}
		
		for(var i = 0; i < value.length; i++){
			var char = format.charAt(i);
			if(char != '#' && value.charAt(i) != char){
				value = value.substring(0, i) + char + value.substring(i);
			}
		}
	}
	return value;
}