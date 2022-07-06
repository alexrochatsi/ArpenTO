
package br.com.datasind.relatorio;

import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;

public class RelatorioBuilder{

   /**
    * RelatorioBuilder e um singletron
    */
   private RelatorioBuilder() {
	  logger=Logger.getLogger(getClass());

   }

   private static RelatorioBuilder instancia;

   private String lookupPath="resources\\relatorios";

   private String lookupPathPDF="resources\\relatorios\\gerados";

   private Logger logger;

   protected transient ServletContext servletContext;

   public void setLookupPath(String lookupPath) {
	  this.lookupPath=lookupPath;
   }

   public String getLookupPath() {
	  return lookupPath;
   }

   public String getLookupPathPDF() {
	  return lookupPathPDF;
   }

   public void setLookupPathPDF(String lookupPathPDF) {
	  this.lookupPathPDF=lookupPathPDF;
   }

   /**
    * Este metodo gera o relatorio em pdf com base nos parametros de relatorio e escreve o resultado no outputStream
    * 
    * @param relatorio
    * @param outputStream
    */
   public void printTo(Relatorio relatorio, OutputStream outputStream) {
	  printTo(relatorio , outputStream , true);
   }

   @SuppressWarnings({"rawtypes", "unchecked"})
   public void printTo(Relatorio relatorio, OutputStream outputStream, boolean compile) {
	  relatorio.setLookupPath(lookupPath);

	  Map parametros=relatorio.getParametros();
	  JRDataSource dataSource=relatorio.getDataSource();
	  try {
		 ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

		 String caminho=classLoader.getResource(lookupPath + File.separator + relatorio.getFontesRelatorio()[0] + ".jasper").getFile();
		 // parametros.put("caminhoRelatorios", lookupPath);

		 JasperPrint jasperPrint=JasperFillManager.fillReport(caminho , parametros , dataSource);

		 JasperExportManager.exportReportToPdfStream(jasperPrint , outputStream);

	  } catch (Exception e) {
		 if(compile) {
			try {
			   compile(relatorio);
			   printTo(relatorio , outputStream , false);

			} catch (JRException e1) {
			   throw new RuntimeException(e1);
			}
		 } else {
			throw new RuntimeException(e);
		 }
	  }
   }

   @SuppressWarnings({"unchecked", "rawtypes"})
   public void printTo(Relatorio relatorio, boolean compile) {
	  relatorio.setLookupPath(lookupPath);

	  Map parametros=relatorio.getParametros();
	  JRDataSource dataSource=relatorio.getDataSource();

	  Connection conn=relatorio.getConnection();

	  try {
		 /*
		  * ClassLoader classLoader = Thread.currentThread() .getContextClassLoader();
		  */

		 String caminhoJasper=getRealPathPath(lookupPath + File.separator + relatorio.getFontesRelatorio()[0] + ".jasper");

		 String caminhoPDF=getRealPathPath(lookupPathPDF + File.separator + relatorio.getIdSessao() + File.separator + relatorio.getFontesRelatorio()[0]);

		 String caminhoDiretorio=getRealPathPath(lookupPathPDF + File.separator + relatorio.getIdSessao());

		 SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "br"));

		 logger.info("INICIANDO O RELATORIO - " + formato.format(new Date()));

		 JasperPrint jasperPrint=new JasperPrint();

		 // verifica se tem dataSource ou conexao
		 if(dataSource != null) {
			jasperPrint=JasperFillManager.fillReport(caminhoJasper , parametros , dataSource);
		 } else {
			jasperPrint=JasperFillManager.fillReport(caminhoJasper , parametros , conn);

		 }

		 relatorio.setPathPDF(caminhoPDF + ((int) (10 * Math.random())) + ".pdf");

		 File file=new File(caminhoDiretorio);
		 file.mkdirs();

		 JasperExportManager.exportReportToPdfFile(jasperPrint , relatorio.getPathPDF());

		 logger.info("TERMINANDO O RELATORIO - " + formato.format(new Date()));

	  } catch (Exception e) {
		 logger.error(e , e);
		 if(compile) {
			try {
			   compile(relatorio);
			   printTo(relatorio , false);

			} catch (JRException e1) {
			   logger.error(e1 , e1);
			   throw new RuntimeException(e1.getMessage(), e1.getCause());
			}
		 } else {
			throw new RuntimeException(e.getMessage(), e.getCause());

		 }
	  }
   }

   private void compile(Relatorio relatorio) throws JRException {
	  String[] fontes=relatorio.getFontesRelatorio();
	  for(int i=0; i < fontes.length; i++) {
		 /*
		  * ClassLoader classLoader = Thread.currentThread() .getContextClassLoader();
		  */

		 JasperCompileManager.compileReportToFile(getRealPathPath(lookupPath + File.separator + fontes[i] + ".jrxml") , getRealPathPath(lookupPath + File.separator + fontes[i] + ".jasper"));

		 logger.info("Relatorio Compilado com sucesso.");
	  }
   }

   public synchronized static RelatorioBuilder getInstancia() {
	  if(instancia == null) {
		 instancia=new RelatorioBuilder();
	  }
	  return instancia;
   }

   public ServletContext getServletContext() {
	  if(servletContext == null) {
		 servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	  }
	  return servletContext;
   }

   public void setServletContext(ServletContext servletContext) {
	  this.servletContext=servletContext;
   }

   public String getRealPathPath(String absolutePath) {
	  return getServletContext().getRealPath(absolutePath);
   }
}
