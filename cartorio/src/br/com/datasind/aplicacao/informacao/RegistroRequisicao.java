package br.com.datasind.aplicacao.informacao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;



public class RegistroRequisicao implements RegistroInformacao {
	private static final Logger logger = Logger
			.getLogger(RegistroRequisicao.class);
	
	DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

	public void execute(Map<String, Object> parameters) {
		StringBuffer registro = new StringBuffer();		
		registro.append("'" + parameters.get("modulo") + "';");
		registro.append("'" + parameters.get("url") + "';");
		registro.append("'" + dateFormat.format((Date) parameters.get("data")) + "'");
		
		logger.debug(registro.toString());
	}
}

