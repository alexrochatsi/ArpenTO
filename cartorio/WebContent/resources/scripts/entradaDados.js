function atualizaPosicaoDiv(objeto) {
	if (document.cookie.length > 0) {
		begin = document.cookie.indexOf(objeto + "=");
		if (begin != -1) {
			begin += objeto.length + 1;
			end = document.cookie.indexOf(";", begin);
			if (end == -1) {
				end = document.cookie.length;
			}
			document.getElementById(objeto).scrollTop = unescape(document.cookie
					.substring(begin, end));
			// alert("document.getElementById(objeto).scrollTop = " +
			// unescape(document.cookie.substring(begin, end)));
		}
	}
}

function guardaPosicaoDiv(objeto) {
	var intY = document.getElementById(objeto).scrollTop;
	var ExpireDate = new Date();
	ExpireDate.setTime(ExpireDate.getTime() + (24 * 3600 * 1000));

	document.cookie = objeto + "=" + escape(intY) + "; expires="
			+ ExpireDate.toGMTString();
	// alert(objeto + "=" + escape(intY) + "; expires=" +
	// ExpireDate.toGMTString());
}

// Ex.: onkeyup="ContaCaracteres(this,60)"
function ContaCaracteres(campo, quantidade) {
	var val = campo.value;
	var intCaracteres = quantidade - val.length;
	var pHTML = "";
	if (intCaracteres <= 0) {
		campo.value = val.substr(0, quantidade);
		alert("Quantidade maxima de " + quantidade + " caracteres alcancada.");
		return false;
	}
}

function validaHora(obj) {
	var horario = obj.value.split(":");

	if (horario[0] < 0 || horario[0] > 23) {
		window.alert("Hora invalida.");
		obj.value = "";
		return false;
	}

	if (horario[1] < 0 || horario[1] > 59) {
		window.alert("Minuto invalido.");
		obj.value = "";
		return false;
	}

	return true;
}

function validaHoraCompleta(obj) {
	var horario = obj.value.split(":");

	if (horario[0] < 0 || horario[0] > 23) {
		window.alert("Hora invalida.");
		obj.value = "";
		return false;
	}

	if (horario[1] < 0 || horario[1] > 59) {
		window.alert("Minuto invalido.");
		obj.value = "";
		return false;
	}

	if (horario[2] < 0 || horario[2] > 59) {
		window.alert("Segundo invalido.");
		obj.value = "";
		return false;
	}

	return true;
}

function validaMesAno(obj) {
	if (obj.value.length <= 0)
		return true;

	// formato mm/yyyy
	var horario = obj.value.split("/");

	if (horario[0] < 1 || horario[0] > 12) {
		window.alert("Mes invalido.");
		obj.value = "";
		return false;
	}

	if (horario[1].length < 4) {
		window.alert("Ano invalido.");
		return false;
	}

	return true;
}

function bloquearCaracteres(e) {
	var key;
	var keychar;
	var reg;

	if (window.event) {
		// for IE, e.keyCode or window.event.keyCode can be used
		key = e.keyCode;
	} else if (e.which) {
		// netscape
		key = e.which;
	} else {
		// no event, so pass through
		return true;
	}

	// Se for backspace deixa passar.
	// Alterado para permitir que a virgula seja digitada
	if (key == 8) {
		return true;
	}

	keychar = String.fromCharCode(key);
	reg = /\D/;
	return !reg.test(keychar);
}

function bloquearLetras(e) {
	var key;
	var keychar;
	var reg;

	if (window.event) {
		// for IE, e.keyCode or window.event.keyCode can be used
		key = e.keyCode;
	} else if (e.which) {
		// netscape
		key = e.which;
	} else {
		// no event, so pass through
		return true;
	}

	// Se for backspace deixa passar.
	// Alterado para permitir que a virgula seja digitada
	if ((key == 8) || (key == 44) || (key == 46)) {
		return true;
	}

	keychar = String.fromCharCode(key);
	reg = /\D/;
	return !reg.test(keychar);
}

function bloquearLetras2(e) {
	// N?o permitir q virgula e ponto sejam digitados
	var key;
	var keychar;
	var reg;

	if (window.event) {
		// for IE, e.keyCode or window.event.keyCode can be used
		key = e.keyCode;
	} else if (e.which) {
		// netscape
		key = e.which;
	} else {
		// no event, so pass through
		return true;
	}

	// Se for backspace deixa passar.
	if (key == 8) {
		return true;
	}

	keychar = String.fromCharCode(key);
	reg = /\D/;
	return !reg.test(keychar);
}

function bloqueiaCaracteresParaDecimal(e) {
	// Permitir backspace e ponto.
	var key;
	var keychar;
	var reg;

	if (window.event) {
		key = e.keyCode;
	} else if (e.which) {
		key = e.which;
	} else {
		return true;
	}

	if ((key == 8) || (key == 46)) {
		return true;
	}

	keychar = String.fromCharCode(key);
	reg = /\D/;
	return !reg.test(keychar);
}

function formataData(field, e) {
	// 99/99/9999
	var tecla = e.keyCode;
	vr = field.value;
	var tamOrig = vr.length;

	vr = vr.replace(".", "");
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		if (tamOrig > 10)
			vr = vr.substr(0, vr.length - 1);
		if (tam > 2 && tam < 5)
			field.value = vr.substr(0, tam - 2) + '/' + vr.substr(tam - 2, tam);
		if (tam >= 5 && tam <= 10)
			field.value = vr.substr(0, 2) + '/' + vr.substr(2, 2) + '/'
					+ vr.substr(4, 4);
	}
}

function formataDataHora(field, e) {
	// 99/99/9999 99:99
	var tecla = e.keyCode;
	vr = field.value;
	var tamOrig = vr.length;

	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		if (tamOrig > 15) {
			field.value = vr.substr(0, vr.length - 1);
			return;
		}
		// pos 3
		if (tam > 2 && tam < 4)
			field.value = vr.substr(0, tam - 1) + '/' + vr.substr(tam - 1, tam);
		// pos 6
		if (tam > 5 && tam < 7) {
			field.value = vr.substr(0, tam - 1) + '/' + vr.substr(tam - 1, tam);
		}
		// pos 11
		if (tam > 10 && tam < 12)
			field.value = vr.substr(0, tam - 1) + ' ' + vr.substr(tam - 1, tam);
		// pos 14
		if (tam > 13 && tam < 15) {
			field.value = vr.substr(0, tam - 1) + ':' + vr.substr(tam - 1, tam);
		}
	}
}

function formataDiaMes(field, e) {
	// 99/99
	var tecla = e.keyCode;
	vr = field.value;
	var tamOrig = vr.length;

	vr = vr.replace(".", "");
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		if (tamOrig >= 5)
			vr = vr.substr(0, vr.length - 1);
		if (tam > 2 && tam < 5)
			field.value = vr.substr(0, tam - 2) + '/' + vr.substr(tam - 2, tam);
		if (tam >= 5)
			field.value = vr.substr(0, 2) + '/' + vr.substr(2, 2);
	}
}

function formataMesAno(field, e) {
	// 99/9999
	var tecla = e.keyCode;
	vr = field.value;
	var tamOrig = vr.length;

	vr = vr.replace(".", "");
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		if (tamOrig >= 7)
			vr = vr.substr(0, vr.length - 1);
		if (tam > 2 && tam < 7)
			field.value = vr.substr(0, tam - 4) + '/' + vr.substr(tam - 4, tam);
		if (tam >= 7)
			field.value = vr.substr(0, 2) + '/' + vr.substr(2, 4);
	}
}

function formataNotaDotacao(field, e) {
	// 0000ND00000
	var tecla = e.keyCode;
	vr = field.value;
	tam = vr.length + 1;
	if (tecla != 9 && tecla != 8) {
		// pos 4
		if (tam > 4 && tam < 6)
			field.value = vr.substr(0, tam - 1) + 'ND'
					+ vr.substr(tam - 1, tam);
	}
}

function formataOrdemBancaria(field, e) {
	// 0000OB00000
	var tecla = e.keyCode;
	vr = field.value;
	tam = vr.length + 1;
	if (tecla != 9 && tecla != 8) {
		// pos 4
		if (tam > 4 && tam < 6)
			field.value = vr.substr(0, tam - 1) + 'OB'
					+ vr.substr(tam - 1, tam);
	}
}

function formataProgrmaDesembolso(field, e) {
	// 0000PD00000
	var tecla = e.keyCode;
	vr = field.value;
	tam = vr.length + 1;
	if (tecla != 9 && tecla != 8) {
		// pos 4
		if (tam > 4 && tam < 6)
			field.value = vr.substr(0, tam - 1) + 'PD'
					+ vr.substr(tam - 1, tam);
	}
}

function formataNotaLancamento(field, e) {
	// 0000PD00000
	var tecla = e.keyCode;
	vr = field.value;
	tam = vr.length + 1;
	if (tecla != 9 && tecla != 8) {
		// pos 4
		if (tam > 4 && tam < 6)
			field.value = vr.substr(0, tam - 1) + 'NL'
					+ vr.substr(tam - 1, tam);
	}
}

function formataNotaEmpenho(field, e) {
	// 0000NE00000
	var tecla = e.keyCode;
	vr = field.value;
	tam = vr.length + 1;
	if (tecla != 9 && tecla != 8) {
		// pos 4
		if (tam > 4 && tam < 6)
			field.value = vr.substr(0, tam - 1) + 'NE'
					+ vr.substr(tam - 1, tam);
	}
}

function formataCPF(field, e) {
	// 000.000.000-00
	var tecla = e.keyCode;
	vr = field.value;
	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		// pos 4
		if (tam > 3 && tam < 5)
			field.value = vr.substr(0, tam - 1) + '.' + vr.substr(tam - 1, tam);
		// pos 8
		if (tam > 7 && tam < 9)
			field.value = vr.substr(0, tam - 1) + '.' + vr.substr(tam - 1, tam);
		// pos 12
		if (tam >= 12 && tam < 14)
			field.value = vr.substr(0, tam - 1) + '-' + vr.substr(tam - 1, tam);

	}
}

function formataCNPJ(field, e) {
	// 00.000.000/0000-00
	var tecla = e.keyCode;
	vr = field.value;
	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		// pos 3
		if (tam > 2 && tam < 4)
			field.value = vr.substr(0, tam - 1) + '.' + vr.substr(tam - 1, tam);
		// pos 7
		if (tam > 6 && tam < 8)
			field.value = vr.substr(0, tam - 1) + '.' + vr.substr(tam - 1, tam);
		// pos 11
		if (tam > 10 && tam < 12)
			field.value = vr.substr(0, tam - 1) + '/' + vr.substr(tam - 1, tam);
		// pos 16
		if (tam >= 16 && tam < 18)
			field.value = vr.substr(0, tam - 1) + '-' + vr.substr(tam - 1, tam);

	}
}

function validaDataHora(field) {
	var checkstr = "0123456789";
	var DateField = field;
	var Datevalue = "";
	var TimeValue = "";
	var DateTemp = "";
	var TimeTemp = "";
	var separator = "/";
	var separatorTime = ":";
	var day;
	var month;
	var year;
	var hour;
	var min;
	var leap = 0;
	var err = 0;
	var i;
	err = 0;
	DateValue = DateField.value.substr(0, tam - 6);
	TimeValue = DateField.value.substr(tam - 6);

	if (DateValue.length > 0 && DateValue.length < 10) {
		alert("A data deve ter o formato: DD/MM/AAAA.");
		DateField.select();
		return false;
	}

	if (TimeValue.length > 0 && TimeValue.length < 5) {
		alert("A hora deve ter o formato: HH:MM.");
		DateField.select();
		return false;
	}

	/* Delete all chars except 0..9 */
	for (i = 0; i < DateValue.length; i++) {
		if (checkstr.indexOf(DateValue.substr(i, 1)) >= 0) {
			DateTemp = DateTemp + DateValue.substr(i, 1);
		}
	}
	for (i = 0; i < TimeValue.length; i++) {
		if (checkstr.indexOf(TimeValue.substr(i, 1)) >= 0) {
			TimeTemp = TimeTemp + TimeValue.substr(i, 1);
		}
	}

	DateValue = DateTemp;
	TimeValue = TimeTemp;

	/* Hora */
	hour = TimeValue.substr(0, 2);
	/* Minuto */
	min = TimeValue.substr(2, 2);

	/* Always change date to 8 digits - string */
	/* if year is entered as 2-digit / always assume 20xx */
	if (DateValue.length == 6) {
		DateValue = DateValue.substr(0, 4) + '20' + DateValue.substr(4, 2);
	}
	if (DateValue.length != 8) {
		err = 19;
	}
	/* year is wrong if year = 0000 */
	year = DateValue.substr(4, 4);

	// if (year == 0 || year > 9999 || year <= 0000) {
	if (year == 0 || year > 9999) {
		err = 20;
	}
	/* Validation of month */
	month = DateValue.substr(2, 2);
	if ((month < 1) || (month > 12)) {
		err = 21;
	}
	/* Validation of day */
	day = DateValue.substr(0, 2);
	if (day < 1) {
		err = 22;
	}
	/* Validation leap-year / february / day */
	if ((year % 4 == 0) || (year % 100 == 0) || (year % 400 == 0)) {
		leap = 1;
	}
	if ((month == 2) && (leap == 1) && (day > 29)) {
		err = 23;
	}
	if ((month == 2) && (leap != 1) && (day > 28)) {
		err = 24;
	}
	/* Validation of other months */
	if ((day > 31)
			&& ((month == "01") || (month == "03") || (month == "05")
					|| (month == "07") || (month == "08") || (month == "10") || (month == "12"))) {
		err = 25;
	}
	if ((day > 30)
			&& ((month == "04") || (month == "06") || (month == "09") || (month == "11"))) {
		err = 26;
	}
	/* if 00 ist entered, no error, deleting the entry */
	if ((day == 0) && (month == 0) && (year == 00)) {
		err = 0;
		day = "";
		month = "";
		year = "";
		seperator = "";
	}
	if (hour < 0 || hour > 23) {
		err = 27;
	}
	if (min < 0 || min > 59) {
		err = 28;
	}
	/*
	 * if no error, write the completed date to Input-Field (e.g. 13.12.2001)
	 */
	if (err == 0) {
		if (DateField.value.length > 0)
			DateField.value = day + separator + month + separator + year + ' '
					+ hour + separatorTime + min;
		return true;
	}
	/* Error-message if err != 0 */
	else {
		DateField.select();
		field.value = "";
		alert("Data/Hora invalida.");
		return false;
	}
}

function validaData(field) {
	var checkstr = "0123456789";
	var DateField = field;
	var Datevalue = "";
	var DateTemp = "";
	var seperator = "/";
	var day;
	var month;
	var year;
	var leap = 0;
	var err = 0;
	var i;
	err = 0;
	DateValue = DateField.value;

	if (DateValue.length > 0 && DateValue.length < 10) {
		alert("O campo da data deve ter o formato: DD/MM/AAAA.");
		DateField.select();
		return false;
	}

	/* Delete all chars except 0..9 */
	for (i = 0; i < DateValue.length; i++) {
		if (checkstr.indexOf(DateValue.substr(i, 1)) >= 0) {
			DateTemp = DateTemp + DateValue.substr(i, 1);
		}
	}
	DateValue = DateTemp;
	/* Always change date to 8 digits - string */
	/* if year is entered as 2-digit / always assume 20xx */
	if (DateValue.length == 6) {
		DateValue = DateValue.substr(0, 4) + '20' + DateValue.substr(4, 2);
	}
	if (DateValue.length != 8) {
		err = 19;
	}
	/* year is wrong if year = 0000 */
	year = DateValue.substr(4, 4);

	// if (year == 0 || year > 9999 || year <= 0000) {
	if (year == 0 || year > 9999) {
		err = 20;
	}
	/* Validation of month */
	month = DateValue.substr(2, 2);
	if ((month < 1) || (month > 12)) {
		err = 21;
	}
	/* Validation of day */
	day = DateValue.substr(0, 2);
	if (day < 1) {
		err = 22;
	}
	/* Validation leap-year / february / day */
	if ((year % 4 == 0) || (year % 100 == 0) || (year % 400 == 0)) {
		leap = 1;
	}
	if ((month == 2) && (leap == 1) && (day > 29)) {
		err = 23;
	}
	if ((month == 2) && (leap != 1) && (day > 28)) {
		err = 24;
	}
	/* Validation of other months */
	if ((day > 31)
			&& ((month == "01") || (month == "03") || (month == "05")
					|| (month == "07") || (month == "08") || (month == "10") || (month == "12"))) {
		err = 25;
	}
	if ((day > 30)
			&& ((month == "04") || (month == "06") || (month == "09") || (month == "11"))) {
		err = 26;
	}
	/* if 00 ist entered, no error, deleting the entry */
	if ((day == 0) && (month == 0) && (year == 00)) {
		err = 0;
		day = "";
		month = "";
		year = "";
		seperator = "";
	}
	/*
	 * if no error, write the completed date to Input-Field (e.g. 13.12.2001)
	 */
	if (err == 0) {
		DateField.value = day + seperator + month + seperator + year;

		return true;

	}
	/* Error-message if err != 0 */
	else {
		DateField.select();
		field.value = "";

		alert("Data invalida.");
		return false;
	}
}

function verificaDataInicialFinal(campoInicial, campoFinal) {
	var partesDaData = campoInicial.value.split("/");
	var dtInicial = new Date(), dtFinal = new Date();
	dtInicial
			.setFullYear(partesDaData[2], partesDaData[1] - 1, partesDaData[0]);

	partesDaData = campoFinal.value.split("/");
	dtFinal.setFullYear(partesDaData[2], partesDaData[1] - 1, partesDaData[0]);

	if (dtInicial.getTime() > dtFinal.getTime()) {
		alert("A data final deve ser maior ou igual a data inicial.");
		campoFinal.select();
		return false;
	}
	return true;
}

function formataHora(field, e) {

	var tecla = e.keyCode;
	vr = field.value;
	var tamOrig = vr.length;
	vr = vr.replace(".", "");
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(":", "");
	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		if (tamOrig >= 5)
			vr = vr.substr(0, vr.length - 1);
		if (tam > 2 && tam < 5)
			field.value = vr.substr(0, tam - 2) + ':' + vr.substr(tam - 2, tam);
		if (tam >= 5)
			field.value = vr.substr(0, 2) + ':' + vr.substr(2, 2);
	}
}

function formataHoraCompleta(field, e) {

	var tecla = e.keyCode;
	vr = field.value;
	var tamOrig = vr.length;
	vr = vr.replace(".", "");
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(":", "");
	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		if (tamOrig > 8)
			vr = vr.substr(0, vr.length - 1);
		if (tam > 2 && tam < 5)
			field.value = vr.substr(0, tam - 2) + ':' + vr.substr(tam - 2, tam);
		if (tam >= 6 && tam <= 8)
			field.value = vr.substr(0, 2) + ':' + vr.substr(2, 2) + ':'
					+ vr.substr(2, 2);
	}
}

// function formataValor(field,tammax,e) {
function formataValor(field, e) {

	var tecla = e.keyCode;
	var tammax = 100;

	vr = field.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}

	if (tecla == 8) {
		tam = tam - 1;
	}

	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {
		if (tam <= 2) {
			field.value = vr;
		}
		if ((tam > 2) && (tam <= 5)) {
			field.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 6) && (tam <= 8)) {
			field.value = vr.substr(0, tam - 5) + '.' + vr.substr(tam - 5, 3)
					+ ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 9) && (tam <= 11)) {
			field.value = vr.substr(0, tam - 8) + '.' + vr.substr(tam - 8, 3)
					+ '.' + vr.substr(tam - 5, 3) + ','
					+ vr.substr(tam - 2, tam);
		}
		if ((tam >= 12) && (tam <= 14)) {
			field.value = vr.substr(0, tam - 11) + '.' + vr.substr(tam - 11, 3)
					+ '.' + vr.substr(tam - 8, 3) + '.' + vr.substr(tam - 5, 3)
					+ ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 15) && (tam <= 17)) {
			field.value = vr.substr(0, tam - 14) + '.' + vr.substr(tam - 14, 3)
					+ '.' + vr.substr(tam - 11, 3) + '.'
					+ vr.substr(tam - 8, 3) + '.' + vr.substr(tam - 5, 3) + ','
					+ vr.substr(tam - 2, tam);
		}
	}

}

function formataQuantidade(field, e) {

	var tecla = e.keyCode;
	var tammax = 100;

	vr = field.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}

	if (tecla == 7) {
		tam = tam - 1;
	}

	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {
		if (tam <= 2) {
			field.value = vr;
		}
		if (tam > 2 && tam <= 5) {
			field.value = vr.substr(0, tam - 2) + '.' + vr.substr(tam - 2, tam);
		}
		if (tam > 5 && tam < 8) {
			field.value = vr.substr(0, tam - 2) + '.' + vr.substr(tam - 2, tam);
		}
	}
}

function formataValorUnitario(field, e) {

	var tecla = e.keyCode;
	var tammax = 100;

	vr = field.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}

	if (tecla == 8) {
		tam = tam - 1;
	}

	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {
		if (tam <= 4) {
			field.value = vr;
		}
		if ((tam > 4) && (tam <= 8)) {
			field.value = vr.substr(0, tam - 4) + ',' + vr.substr(tam - 4, tam);
		}
		if (tam >= 9) {
			field.value = vr.substr(0, tam - 4) + ',' + vr.substr(tam - 4, tam);
		}
	}
}

// ncd = Numero de Casas Descimais
function formataVlrUnit(field, e, ncd) {

	var tecla = e.keyCode;
	var tammax = 100;

	vr = field.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}

	if (tecla == 8) {
		tam = tam - 1;
	}

	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {
		if (tam <= ncd) {
			field.value = vr;
		}
		if ((tam > ncd) && (tam <= (2 * ncd))) {
			field.value = vr.substr(0, tam - ncd) + ','
					+ vr.substr(tam - ncd, tam);
		}
		if (tam >= ((ncd * 2) + 1)) {
			field.value = vr.substr(0, tam - ncd) + ','
					+ vr.substr(tam - ncd, tam);
		}
	}
}

/*
 * Funcao para formatacao de valores no formato (999.999.999,99*), onde pode ser
 * especificada a quantidade de casas decimais. Os parametros sao: field - o
 * objeto do formulario (this) e - o evento (event) decimal - a quantidade de
 * casas decimais
 */
function formataValor(field, e, decimal) {

	var tecla = e.keyCode;
	var tammax = field.maxlength;

	vr = field.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	if (tam == tammax)
		return false;

	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}

	if (tecla == 8) {
		// tam = tam - 1 ;
	}

	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {
		var valorAux = '';
		if (tam < decimal) {
			field.value = vr;
			// alert('tam:' + tam + ' decimal:' + decimal + ' vr:' +vr);
		} else {
			digitos = vr.split(/\s*/);
			var j = 1;
			var limite = (digitos.length - decimal) - 1;
			var i = (digitos.length - 1);
			for (i; i >= 0; i--) {
				if (i == limite) {
					valorAux = '.' + valorAux;
				} else if (i < limite) {
					if (j % 3 == 0) {
						valorAux = '.' + valorAux;
					}
					j++;
				}
				valorAux = digitos[i] + valorAux;
				// alert( 'laco ' + i + ': vr=' + vr + ', j=' + j + ', i=' + i +
				// ', valorAux=' + valorAux);
			}
			field.value = valorAux;
		}
	}
}

function formataValorBigDecimal(field, e, decimal) {

	var tecla = e.keyCode;
	var tammax = field.maxlength;

	vr = field.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	if (tam == tammax)
		return false;

	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}

	if (tecla == 8) {
		// tam = tam - 1 ;
	}

	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {
		var valorAux = '';
		if (tam < decimal) {
			field.value = vr;
			// alert('tam:' + tam + ' decimal:' + decimal + ' vr:' +vr);
		} else {
			digitos = vr.split(/\s*/);
			var j = 1;
			var limite = (digitos.length - decimal) - 1;
			var i = (digitos.length - 1);
			for (i; i >= 0; i--) {
				if (i == limite) {
					valorAux = '.' + valorAux;
				} else if (i < limite) {

					j++;
				}
				valorAux = digitos[i] + valorAux;
				// alert( 'laco ' + i + ': vr=' + vr + ', j=' + j + ', i=' + i +
				// ', valorAux=' + valorAux);
			}
			field.value = valorAux;
		}
	}
}

function formataValorDecimal(field, decimal) {
	vr = field.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	if (tam == decimal) {
		field.value = '0,' + vr;
	}
}

function FormataValorNotaServidor(campo, e) {
	// UTILIZAR:
	// onKeyPress="return valida_num(event);"
	// alert("entrou");
	if (document.all) { // Internet Explorer
		var tecla = event.keyCode;
	} else if (document.layers) // Nestcape
		var tecla = e.which;

	if (tecla > 47 && tecla < 58) { // numeros de 0 a 9
		if (campo.value == '')
			return true;
		else if (campo.value == 1 && tecla == 48)
			return true;
		else
			return false;
	} else {
		if ((tecla != 8) || (key != 44)) // Backspace ou Virgula
			event.keyCode = 0;
		// return false;
		else
			return true;
	}
}

function FormataValorNota(campo, tammax, teclapres) {
	var tecla = teclapres.keyCode;
	if (tecla == 9)
		return;

	vr = campo.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}
	if (tecla == 8) {
		tam = tam - 1;
	}
	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {
		if (tam == 5)
			return false;
		if (tam <= 2) {
			campo.value = vr;
		}
		if ((tam > 2) && (tam <= 5)) {
			campo.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 6) && (tam <= 8)) {
			campo.value = vr.substr(0, tam - 5) + '.' + vr.substr(tam - 5, 3)
					+ ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 9) && (tam <= 11)) {
			campo.value = vr.substr(0, tam - 8) + '.' + vr.substr(tam - 8, 3)
					+ '.' + vr.substr(tam - 5, 3) + ','
					+ vr.substr(tam - 2, tam);
		}
		if ((tam >= 12) && (tam <= 14)) {
			campo.value = vr.substr(0, tam - 11) + '.' + vr.substr(tam - 11, 3)
					+ '.' + vr.substr(tam - 8, 3) + '.' + vr.substr(tam - 5, 3)
					+ ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 15) && (tam <= 17)) {
			campo.value = vr.substr(0, tam - 14) + '.' + vr.substr(tam - 14, 3)
					+ '.' + vr.substr(tam - 11, 3) + '.'
					+ vr.substr(tam - 8, 3) + '.' + vr.substr(tam - 5, 3) + ','
					+ vr.substr(tam - 2, tam);
		}
	} else {
		return false;
	}
}

function valida_num(e) {
	// UTILIZAR:
	// onKeyPress="return valida_num(event);"
	// alert("entrou");
	if (document.all) // Internet Explorer
		var tecla = event.keyCode;
	else if (document.layers) // Nestcape
		var tecla = e.which;
	if (tecla > 47 && tecla < 58) // numeros de 0 a 9
		return true;
	else {
		if ((tecla != 8) || (key != 44)) // Backspace ou Virgula
			event.keyCode = 0;
		// return false;
		else
			return true;
	}
}

function valida_numComVirgula(e) {
	// UTILIZAR:
	// onKeyPress="return valida_num(event);"
	// alert("entrou");
	if (document.all) // Internet Explorer
		var tecla = event.keyCode;
	else if (document.layers) // Nestcape
		var tecla = e.which;
	if ((tecla > 47 && tecla < 58) || (tecla == 44)) // numeros de 0 a 9
		return true;
	else {
		if ((tecla != 8)) // Backspace ou Virgula
			event.keyCode = 0;
		// return false;
		else
			return true;
	}
}

function validaCNPJ(field) {
	var checkstr = "0123456789";
	var cnpjvalue = "";
	var cnpjTemp = "";

	cnpjValue = field.value;
	/* Delete all chars except 0..9 */
	for (i = 0; i < cnpjValue.length; i++) {
		if (checkstr.indexOf(cnpjValue.substr(i, 1)) >= 0) {
			cnpjTemp = cnpjTemp + cnpjValue.substr(i, 1);
		}
	}

	if (cnpjTemp.length == 14) {
		var a = [];
		var b = new Number;
		var c = [ 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 ];
		for (i = 0; i < 12; i++) {
			a[i] = cnpjTemp.charAt(i);
			b += a[i] * c[i + 1];
		}
		if ((x = b % 11) < 2) {
			a[12] = 0
		} else {
			a[12] = 11 - x
		}
		b = 0;
		for (y = 0; y < 13; y++) {
			b += (a[y] * c[y]);
		}
		if ((x = b % 11) < 2) {
			a[13] = 0;
		} else {
			a[13] = 11 - x;
		}
		if ((cnpjTemp.charAt(12) != a[12]) || (cnpjTemp.charAt(13) != a[13])) {
			field.select();
			alert("Digito verificador do CNPJ invalido.");
			return false;
		} else {
			return true;
		}
	} else if (cnpjTemp.length >= 1) {
		field.select();
		alert("CNPJ deve conter 14 digitos numericos.");
		return false;
	}
}

function validaCPF(field) {
	var checkstr = "0123456789";
	var cpfvalue = "";
	var cpfTemp = "";

	cpfValue = field.value;
	/* Delete all chars except 0..9 */
	for (i = 0; i < cpfValue.length; i++) {
		if (checkstr.indexOf(cpfValue.substr(i, 1)) >= 0) {
			cpfTemp = cpfTemp + cpfValue.substr(i, 1);
		}
	}

	if (cpfTemp.length == 11) {
		if (cpfTemp == "00000000000" || cpfTemp == "11111111111"
				|| cpfTemp == "22222222222" || cpfTemp == "33333333333"
				|| cpfTemp == "44444444444" || cpfTemp == "55555555555"
				|| cpfTemp == "66666666666" || cpfTemp == "77777777777"
				|| cpfTemp == "88888888888" || cpfTemp == "99999999999") {
			field.select();
			alert("CPF Invalido.");
			return false;
		} else {

			var a = [];
			var b = new Number;
			var c = 11;
			for (i = 0; i < 11; i++) {
				a[i] = cpfTemp.charAt(i);
				if (i < 9)
					b += (a[i] * --c);
			}
			if ((x = b % 11) < 2) {
				a[9] = 0
			} else {
				a[9] = 11 - x
			}
			b = 0;
			c = 11;
			for (y = 0; y < 10; y++)
				b += (a[y] * c--);
			if ((x = b % 11) < 2) {
				a[10] = 0;
			} else {
				a[10] = 11 - x;
			}
			if ((cpfTemp.charAt(9) != a[9]) || (cpfTemp.charAt(10) != a[10])) {
				field.select();
				alert("Digito verificador do CPF invalido.");
				return false;
			} else {
				return true;
			}
		}
	} else if (cpfTemp.length >= 1) {
		field.select();
		alert("CPF deve conter 11 digitos numericos.");
		return false;
	}

}

function validaEMAIL(field) {
	var email = "";
	email = field.value;
	if (email.length >= 1) {
		if (!email.match(/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+.[a-zA-Z0-9._-]+)/gi)) {
			field.select();
			alert("Informe um e-mail valido.\n Exemplo: e-mail@dominio.ext");
			return false
		}
	} else
		return true;
}

function formataCEP(field, e) {
	// 00.000-000
	var tecla = e.keyCode;
	vr = field.value;
	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		// pos 2
		if (tam > 2 && tam < 5)
			field.value = vr.substr(0, tam - 1) + '.' + vr.substr(tam - 1, tam);
		// pos 4
		else if (tam > 6 && tam < 8)
			field.value = vr.substr(0, tam - 1) + '-' + vr.substr(tam - 1, tam);

	}
}

function validaCEP(field) {
	var checkstr = "0123456789";
	var cepValue = "";
	cepValue = field.value;
	var cepTemp = "";
	/* Delete all chars except 0..9 */
	for (i = 0; i < cepValue.length; i++) {
		if (checkstr.indexOf(cepValue.substr(i, 1)) >= 0) {
			cepTemp = cepTemp + cepValue.substr(i, 1);
		}
	}
	if (cepTemp.length >= 1) {
		if (cepTemp.length < 8) {
			field.select();
			alert("CEP deve conter 8 digitos numericos.");
			return false;
		} else
			return true;
	}
}

function SoNumero(e) {
	var isIE = (navigator.appName.indexOf('Internet Explorer') != -1);
	var keyNumber = (isIE) ? event.keyCode : e.which;
	if (((keyNumber < 48) || (keyNumber > 57)) && (keyNumber != 13)
			&& (keyNumber != "0") && (keyNumber != 8)) {
		if (isIE)
			event.keyCode = 0;
		return false;
	}
}

function FormataPlaca(Campo, teclapres) {
	strtext = Campo.value;
	var pos = strtext.indexOf('-');
	if (pos != (-1))
		strtext = strtext.substring(0, pos)
				+ strtext.substring(pos + 1, strtext.length);
	var tam = (strtext.length);
	if (tam == 6) {
		Campo.value = strtext.substring(0, 3) + '-'
				+ strtext.substring(3, strtext.length);
	}

}

function mostrarMsg(idForm, idDiv, idHMessages) {
	var divMessages = document.getElementById(idForm + ":" + idDiv);
	var messageList = document.getElementById(idForm + ":" + idHMessages);
	var linkFecharMsg = criarLinkFechar(idForm, divMessages);

	if (messageList != null) {
		configurarContainerMsgs(divMessages);
		var widthHMsgs = messageList.tBodies[0].rows[0].childNodes[0].childNodes[0].style.width
				.replace("px", "");
		configurarLinkFecharMsgs(divMessages, linkFecharMsg,
				(parseInt(widthHMsgs) - 615));

		if (messageList.tBodies[0].rows.length > 1) {
			divMessages.style.height = "54px";
			if (messageList.tBodies[0].rows.length > 2)
				divMessages.style.width = (parseInt(widthHMsgs) + 25) + "px";
		}

		divMessages.style.display = "inline";
		linkFecharMsg.style.display = "inline";
	}
}

function criarLinkFechar(idForm, divMessages) {
	var linkFecharMsg = document.createElement("span");
	linkFecharMsg.id = idForm + ":linkFecharMsg";
	linkFecharMsg.className = "linkFechar";
	linkFecharMsg.innerHTML = "[x]";

	linkFecharMsg.onclick = function() {
		linkFecharMsg.style.display = "none";
		divMessages.style.display = "none";
	};

	divMessages.parentNode.insertBefore(linkFecharMsg, divMessages);
	return linkFecharMsg;
}

function configurarContainerMsgs(containerMsgs) {
	containerMsgs.style.position = "absolute";
	containerMsgs.style.height = "30px";
	containerMsgs.style.overflow = "auto";
	containerMsgs.style.border = "1px solid #2FA653";
	containerMsgs.style.backgroundColor = "#FFFFFF";
	containerMsgs.style.zIndex = "5";
	containerMsgs.style.display = "none";
}

function configurarLinkFecharMsgs(divMessages, linkFecharMsg, variacaoPos) {
	var aux = divMessages.style.top.toString().replace("px", "");
	linkFecharMsg.style.top = (parseInt(aux) + 7) + "px";
	aux = divMessages.style.left.toString().replace("px", "");
	linkFecharMsg.style.left = (parseInt(aux) + (595 + (variacaoPos))) + "px";
}

function setFocus(values, throwError) {
	try {
		values = values.split("/");
		var idForm = document.forms[0].id;

		/* values: Array { componentId, styleName, changeState } */
		var component = document.getElementById(idForm + ":" + values[0]);
		if (component == null)
			component = document.getElementById(values[0]);

		if (!component.disabled) {
			if (values.length > 1 && new Boolean(values[2]) && values[1] != "")
				component.className = values[1];

			if (component.tagName == "INPUT")
				component.focus();
		}

	} catch (error) {
		if (throwError)
			throw error;
	}
}

function disableAllInputs(throwError) {
	var inputs = document.getElementsByTagName("input");

	try {
		for ( var i = 0; i < inputs.length; i++)
			inputs[i].disabled = "disabled";
	} catch (error) {
		if (throwError)
			throw error;
	}
}

function disableInputs(type, throwError) {
	var inputs = document.getElementsByTagName("input");

	try {
		for ( var i = 0; i < inputs.length; i++)
			if (inputs[i].type == type)
				inputs[i].disabled = "disabled";
	} catch (error) {
		if (throwError)
			throw error;
	}
}
function removeCaracEspeciaisExcetoPonto() {
	if (window.event.keyCode == 46)
		return true
	if (window.event.keyCode >= 48 && window.event.keyCode <= 57)
		return true
	if (window.event.keyCode >= 65 && window.event.keyCode <= 90)
		return true
	else if (window.event.keyCode >= 97 && window.event.keyCode <= 122)
		return true
	if (window.event.keyCode == 32)
		return true
	if (window.event.keyCode >= 192 && window.event.keyCode <= 252
			&& window.event.keyCode != 197 && window.event.keyCode != 198
			&& window.event.keyCode != 208 && window.event.keyCode != 209
			&& window.event.keyCode != 215 && window.event.keyCode != 216
			&& window.event.keyCode != 221 && window.event.keyCode != 222
			&& window.event.keyCode != 223 && window.event.keyCode != 229
			&& window.event.keyCode != 230 && window.event.keyCode != 240
			&& window.event.keyCode != 241 && window.event.keyCode != 247
			&& window.event.keyCode != 248 && window.event.keyCode != 247
			&& window.event.keyCode != 248)
		return true
	else
		window.event.keyCode = 0;
}

function mascara(o, f) {
	v_obj = o;
	v_fun = f;
	setTimeout("execmascara()", 1);
}

function execmascara() {
	v_obj.value = v_fun(v_obj.value);
}

function valor(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/[0-9]{15}/, "inválido");
	v = v.replace(/(\d{1})(\d{11})$/, "$1.$2"); // coloca ponto antes dos
	// últimos 11 digitos
	v = v.replace(/(\d{1})(\d{8})$/, "$1.$2"); // coloca ponto antes dos
	// últimos 8 digitos
	v = v.replace(/(\d{1})(\d{5})$/, "$1.$2"); // coloca ponto antes dos
	// últimos 5 digitos
	v = v.replace(/(\d{1})(\d{1,2})$/, "$1,$2"); // coloca virgula antes dos
	// últimos 2 digitos
	return v;
}

// Recebe a mascara e formata
function formataDiversos(src, mask) {
	var i = src.value.length;
	var saida = mask.substring(0, 1);
	var texto = mask.substring(i);
	if (texto.substring(0, 1) != saida) {
		src.value += texto.substring(0, 1);
	}
}

var isNN = (navigator.appName.indexOf("Netscape") != -1);
function autoTab(input, len, e) {

	var keyCode = (isNN) ? e.which : e.keyCode;
	var filter = (isNN) ? [ 0, 8, 9 ] : [ 0, 8, 9, 16, 17, 18, 37, 38, 39, 40,
			46 ];
	if (input.value.length >= len && !containsElement(filter, keyCode)) {
		input.value = input.value.slice(0, len);
		input.form[(getIndex(input) + 1) % input.form.length].focus();
	}
	return true;
}
function containsElement(arr, ele) {
	var found = false, index = 0;
	while (!found && index < arr.length)
		if (arr[index] == ele) {
			found = true;
		} else {
			index++;
		}
	return found;
}
function getIndex(input) {
	var index = -1, i = 0, found = false;
	while (i < input.form.length && index == -1)
		if (input.form[i] == input) {
			index = i;
		} else {
			i++;
		}
	return index;
}

function mascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e) {
	var sep = 0;
	var key = '';
	var i = j = 0;
	var len = len2 = 0;
	var strCheck = '0123456789';
	var aux = aux2 = '';
	var whichCode = (window.Event) ? e.which : e.keyCode;
	if (whichCode == 13)
		return true;
	key = String.fromCharCode(whichCode); // Valor para o codigo da Chave
	if (strCheck.indexOf(key) == -1)
		return false; // Chave invalida
	len = objTextBox.value.length;
	for (i = 0; i < len; i++)
		if ((objTextBox.value.charAt(i) != '0')
				&& (objTextBox.value.charAt(i) != SeparadorDecimal))
			break;
	aux = '';
	for (; i < len; i++)
		if (strCheck.indexOf(objTextBox.value.charAt(i)) != -1)
			aux += objTextBox.value.charAt(i);
	aux += key;
	len = aux.length;
	if (len == 0)
		objTextBox.value = '';
	if (len == 1)
		objTextBox.value = '0' + SeparadorDecimal + '0' + aux;
	if (len == 2)
		objTextBox.value = '0' + SeparadorDecimal + aux;
	if (len > 2) {
		aux2 = '';
		for (j = 0, i = len - 3; i >= 0; i--) {
			if (j == 3) {
				aux2 += SeparadorMilesimo;
				j = 0;
			}
			aux2 += aux.charAt(i);
			j++;
		}
		objTextBox.value = '';
		len2 = aux2.length;
		for (i = len2 - 1; i >= 0; i--)
			objTextBox.value += aux2.charAt(i);
		objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
	}
	return false;
}


