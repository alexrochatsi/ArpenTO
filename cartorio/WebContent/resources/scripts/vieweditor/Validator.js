Validator = function(){
	
}

///////////////////////////////////////////////////////////////////////
// Funcao: Valida cpf.
///////////////////////////////////////////////////////////////////////
Validator.cpf = function(event,valor){
	//var msg = "O CPF informado nao e valido.";	
	s = valor.replace(/[^\d]/g,'');		   	
	if(s.length == 11){	
		var i; 
		var c = s.substr(0,9); 
		var dv = s.substr(9,2); 
		var d1 = 0; 
		for (i = 0; i < 9; i++) { 
			d1 += c.charAt(i)*(10-i); 
		} 
		  
		if (d1 == 0) {
			//window.addMessage("ERROR",msg);	
			return false; 
		} 
	
		d1 = 11 - (d1 % 11); 
		  
		if (d1 > 9) d1 = 0; 
		  
		if (dv.charAt(0) != d1) {
			//window.addMessage("ERROR",msg);	
			return false; 
		} 
	
		d1 *= 2; 
	
		for (i = 0; i < 9; i++) { 
			d1 += c.charAt(i)*(11-i); 
		} 
	
		d1 = 11 - (d1 % 11); 
	
		if (d1 > 9) d1 = 0; 
	
		if (dv.charAt(1) != d1) {
			//window.addMessage("ERROR",msg);	
			return false; 
		} 		
		return true; 
	}else{			
		return false;
	}			
}


///////////////////////////////////////////////////////////////////////
// Funcao: Valida cnpj.
///////////////////////////////////////////////////////////////////////
Validator.cnpj = function(event,valor){	
	//var msg = "O CNPJ informado nao e valido.";
	cnpj = valor.replace(/[^\d]/g,'');		   	
	if(cnpj.length == 14){	
	  	var numeros, digitos, soma, i, resultado, pos, tamanho, digitos_iguais;
      	digitos_iguais = 1;
      	if (cnpj.length < 14 && cnpj.length < 15){
      		//window.addMessage("ERROR", msg);	
        	return false;
      	}      
      	for (i = 0; i < cnpj.length - 1; i++){
			if (cnpj.charAt(i) != cnpj.charAt(i + 1)){
        		digitos_iguais = 0;
            	break;
        	}
        }	
      	if (!digitos_iguais){
			tamanho = cnpj.length - 2
            numeros = cnpj.substring(0,tamanho);
            digitos = cnpj.substring(tamanho);
            soma = 0;
            pos = tamanho - 7;
            for (i = tamanho; i >= 1; i--){
            	soma += numeros.charAt(tamanho - i) * pos--;
                if (pos < 2)
                	pos = 9;
            }
            resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
            if (resultado != digitos.charAt(0)){
				//window.addMessage("ERROR", msg);			
                return false;
            }      
            tamanho = tamanho + 1;
            numeros = cnpj.substring(0,tamanho);
            soma = 0;
            pos = tamanho - 7;
            for (i = tamanho; i >= 1; i--){
                  soma += numeros.charAt(tamanho - i) * pos--;
                  if (pos < 2)
                        pos = 9;
            }
            resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
            if (resultado != digitos.charAt(1)){
				//window.addMessage("ERROR", msg);	
                return false;
            }      
            return true;
		} else{
			//window.addMessage("ERROR", msg);	
      		return false;
      	}
    }else{
    	return false;
    }  	
				
}

///////////////////////////////////////////////////////////////////////
// Funcao: Valida se o evento e um numero.
///////////////////////////////////////////////////////////////////////
Validator.number = function(event, errorMessage){	

	if (window.event) event = window.event;			
	
	if ((event.keyCode == 13) || (event.keyCode == 8)||(event.keyCode == 46)||(event.keyCode == 16)||(event.keyCode == 9)||(event.shiftKey) || ((event.keyCode >= 37) && (event.keyCode <= 40)) || (event.keyCode == 27) || (event.keyCode == 20)){
		return true;
	} else if (!(((event.keyCode >= 48)&&(event.keyCode <= 57))||((event.keyCode >= 96) && (event.keyCode <= 105))||((event.ctrlKey)&&(event.keyCode == 86)))){
		//window.addMessage("ERROR", errorMessage);
		return false;
	}		
	return true;
}

///////////////////////////////////////////////////////////////////////
// Funcao: Valida se o evento e um numero.
///////////////////////////////////////////////////////////////////////
Validator.percent100 = function(value, errorMessage){
	
	// convertendo para locale us
	value = value.replace(',','.');

	
	// se valor for maior que 100%, apresenta mensagem
	if(value > 100){
		
		// Mensagem Default.
		if(!errorMessage){
			errorMessage = "Digite um valor inferior a 100%";
		}		
		
		window.addMessage("ERROR", errorMessage);
		return false;
	}	
		
	return true;
}

///////////////////////////////////////////////////////////////////////
// Funcao: Verifica se o evento e um Email valido.
///////////////////////////////////////////////////////////////////////
Validator.email = function(value){
	var msg = "O e-mail informado e invalido.";
	var regExp = /^[A-Za-z0-9_\-\.]+@[A-Za-z0-9_\-\.]{2,}\.[A-Za-z0-9]{2,}(\.[A-Za-z0-9])?/;
		
	return Validator.isValidRegex(regExp,value,msg);	
}

///////////////////////////////////////////////////////////////////////
// Funcao: Verifica se o evento e uma URL valida.              
///////////////////////////////////////////////////////////////////////
Validator.url = function(value){
	var msg = "A Homepage (Url) informada e invalida."
	var regExp = /^(([\w]+:)?\/\/)?(([\d\w]|%[a-fA-f\d]{2,2})+(:([\d\w]|%[a-fA-f\d]{2,2})+)?@)?([\d\w][-\d\w]{0,253}[\d\w]\.)+[\w]{2,4}(:[\d]+)?(\/([-+_~.\d\w]|%[a-fA-f\d]{2,2})*)*(\?(&?([-+_~.\d\w]|%[a-fA-f\d]{2,2})=?)*)?(#([-+_~.\d\w]|%[a-fA-f\d]{2,2})*)?$/;
	return Validator.isValidRegex(regExp,value,msg);
}

///////////////////////////////////////////////////////////////////////
// Funcao: Valida uma data              
/////////////////////////////////////////////////////////////////////// 
Validator.isValidDate = function(element){
	var msg = "A data informada esta incorreta.";	
	//var parts = element.value.split(/[/]/);
	var parts = element.value.split("/[/]/");
	if(parts.length < 3){
		window.addMessage("ERROR",msg);
		return false;
	}
	
	//bug 08 e 09
	for(var i = 0; i < parts.length; i++){
		parts[i] = parts[i].replace(/^0[0]*/, "");
	}
	
	var dt = new Date(parts[1] + "/" + parts[0] + "/" + parts[2]);
    if(dt.getDate() != parseInt(parts[0])){
    	window.addMessage("ERROR",msg);
		return false;
	}
	else if(dt.getMonth() != parseInt(parts[1]) - 1){
		window.addMessage("ERROR",msg);
		return false;
	}
	else if(parseInt(parts[2]) < 1783){
		window.addMessage("ERROR",msg);
		return false;
	}
    else if(dt.getFullYear() != parseInt(parts[2])){
    	window.addMessage("ERROR",msg);
		return false;
	}
	return true;
	
}

///////////////////////////////////////////////////////////////////////
// Funcao: Valida uma data mes/ano             
/////////////////////////////////////////////////////////////////////// 
Validator.isValidDateMesAno = function(element){

	var msg = "A data de Mes/Ano esta incorreta.";		
	//var parts = element.value.split(/[/]/);
	var parts = element.value.split("/[/]/");

	//bug 08 e 09
	for(var i = 0; i < parts.length; i++){
		parts[i] = parts[i].replace(/^0[0]*/, "");
	}	

	if(parts.length < 2){
		window.addMessage("ERROR", msg);
		return false;
	}
	
	if (parts[0] < 1 || parts[0] > 12) {
		window.addMessage("ERROR", msg);
		return false;
	}
	
	if (parts [1].length < 4 || parts [1] < 1783) {
		window.addMessage("ERROR", msg);
		return false;
	}
	return true;
}

///////////////////////////////////////////////////////////////////////
// Funcao: Verifica se um valor e valido com relacao a expressao regular informada.
// Caso seja negativo, uma mensagem sera informada.             
/////////////////////////////////////////////////////////////////////// 
Validator.isValidRegex = function(regex, value, errorMessage){

	if(!regex.test(value)){
		//window.addMessage("ERROR", errorMessage);
		return false;
	}
	return true;
}
	
Validator.prototype = {
	regex: function(regex, value, errorMessage){		
		if(!regex.test(value)){
			window.addMessage("ERROR", errorMessage);
			return false;
		}
		return true;
	},
	
	date: function(data, errorMessage){
		var data = retiraEspacos(data);
		if(!data.match(/^\d{1,2}\D\d{1,2}\D\d{2,4}$/)){
			return false;
		}
		var d = data.match(/\D/);
		var i1 = data.indexOf(d);
		var i2 = data.indexOf(d, i1 + 1);
		var dia = parseInt(data.substring(0,i1));	
		var mes = parseInt(data.substring(i1 + 1, i2));
		var ano = parseInt(data.substring(i2 + 1));	
		var date = new Date(ano, mes - 1, dia);
		if(date.getDate() != dia || (date.getMonth() + 1) != mes){
			window.addMessage("ERROR", errorMessage);
			return false;
		}
		return true;
	}
};