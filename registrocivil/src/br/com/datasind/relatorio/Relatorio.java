package br.com.datasind.relatorio;

import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

public interface Relatorio {

	/**
	 * @return
	 */
	String[] getFontesRelatorio();

	@SuppressWarnings("rawtypes")
	Map getParametros();

	JRDataSource getDataSource();

	String getPathPDF();

	String getIdSessao();

	void setPathPDF(String pathPDF);

	void setLookupPath(String path);

	Connection getConnection();

}
