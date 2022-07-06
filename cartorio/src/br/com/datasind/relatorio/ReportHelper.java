
package br.com.datasind.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRProperties;

import org.apache.log4j.Logger;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.pdf.Files;
import org.jrimum.bopepo.view.BoletoViewer;
import org.ujac.print.DocumentPrinter;
import org.ujac.util.io.FileResourceLoader;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Arquivo;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;

@SuppressWarnings({"rawtypes", "deprecation"})
public class ReportHelper extends CadastroControler{

   /**
    * 
    */
   private static final long serialVersionUID=9128205293298211230L;

   static final Logger LOGGER=Logger.getLogger(ReportHelper.class);

   public static final int FORMATO_CAMINHO_RELATIVO=0;

   public static final int FORMATO_CAMINHO_ABSOLUTO=1;

   private static String compileReportClasspath;

   public static String getCompileReportClasspath() {
	  if(compileReportClasspath == null) {
		 compileReportClasspath=System.getProperty("java.class.path") + File.pathSeparator;

		 compileReportClasspath+=ReportHelper.class.getResource("/").getFile() + File.pathSeparator;

		 File lib=new File(new File(ReportHelper.class.getResource("/").getFile()).getParent() + File.separator + "lib");

		 String[] jars=lib.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
			   return name.endsWith(".jar");
			}
		 });

		 for(int i=0; i < jars.length; i++) {
			compileReportClasspath+=lib.getAbsolutePath() + File.separator + jars[i] + File.pathSeparator;
		 }
	  }
	  return compileReportClasspath;
   }

   public static String gerarRelatorio(Collection dados, String jasper, Map parametros) throws ApplicationException {
	  try {
		 return gerarRelatorioSemTentarCompilar(dados , jasper , parametros);

	  } catch (ApplicationException e) {
		 try {
			compilaRelatorio(jasper.replaceAll("\\.jasper" , ".jrxml"));
			return gerarRelatorioSemTentarCompilar(dados , jasper , parametros);

		 } catch (JRException e1) {
			throw new ApplicationException(e1);
		 }
	  }
   }

   /**
    * Metodo sobrecarregado para receber um ResultSet como fonte dos dados
    * 
    * @param dados
    * @param jasper
    * @param parametros
    * @return
    * @throws ApplicationException
    */

   public static String gerarRelatorio(ResultSet dados, String jasper, Map parametros) throws ApplicationException {
	  try {
		 LOGGER.debug("Tentando gerar relatorio sem compilar");
		 return gerarRelatorioSemTentarCompilar(dados , jasper , parametros);

	  } catch (ApplicationException e) {
		 LOGGER.debug("Problemas gerando relatorio sem compilar. Tentando compilar...");
		 try {
			compilaRelatorio(jasper.replaceAll("\\.jasper" , ".jrxml"));
			return gerarRelatorioSemTentarCompilar(dados , jasper , parametros);

		 } catch (JRException e1) {
			throw new ApplicationException(e1);
		 }
	  }
   }

   @SuppressWarnings("unchecked")
   public static String gerarRelatorioSemTentarCompilar(Collection dados, String jasper, Map parametros) throws ApplicationException {
	  LOGGER.debug("Gerando relatorio sem compilar");
	  try {
		 LOGGER.debug("Criando JRBeanCollectionDataSource com " + dados);
		 JRBeanCollectionDataSource dataSource=null;
		 dataSource=new JRBeanCollectionDataSource(dados);
		 System.out.println(ReportHelper.class.getResource(""));
		 LOGGER.debug("Lendo jasper " + ReportHelper.class.getResource(jasper));
		 InputStream stream=ReportHelper.class.getResourceAsStream(jasper);
		 if(stream == null) {
			LOGGER.debug("Jasper " + ReportHelper.class.getResource(jasper) + " nao encontrado.");
			throw new ApplicationException(".jasper nao encontrado");
		 }
		 JasperPrint jasperPrint=null;
		 try {
			LOGGER.debug("Gerando relatorio fillReport(" + stream + ", " + parametros + ", " + dataSource + ")");
			jasperPrint=JasperFillManager.fillReport(stream , parametros , dataSource);
		 } catch (Exception e) {
			LOGGER.debug("Problemas gerando relatorio" , e);
			throw new ApplicationException("Problemas exibindo o relatorio", e);
		 }

		 String name=TempFileNameHelper.getName("rep") + ".pdf";
		 String path=TempFileNameHelper.getBaseAbsoluta() + "/" + name;

		 LOGGER.debug("Exportando arquivo pdf para " + path);
		 JasperExportManager.exportReportToPdfFile(jasperPrint , path);

		 path=TempFileNameHelper.getBaseRelativa() + "/" + name;
		 LOGGER.debug("Retornando resultado " + path);

		 return path;

	  } catch (JRException e) {
		 LOGGER.debug("Problemas..  " , e);
		 throw new ApplicationException(e);
	  }
   }

   /**
    * Metodo sobrecarregado para receber um ResultSet como fonte dos dados
    * 
    * @param dados
    * @param jasper
    * @param parametros
    * @return
    * @throws ApplicationException
    */
   @SuppressWarnings("unchecked")
   public static String gerarRelatorioSemTentarCompilar(ResultSet dados, String jasper, Map parametros) throws ApplicationException {
	  try {
		 JRResultSetDataSource dataSource=null;
		 dataSource=new JRResultSetDataSource(dados);

		 InputStream stream=ReportHelper.class.getResourceAsStream(jasper);
		 if(stream == null) {
			throw new ApplicationException(".jasper nao encontrado");
		 }

		 JasperPrint jasperPrint=null;
		 try {
			jasperPrint=JasperFillManager.fillReport(stream , parametros , dataSource);

		 } catch (Exception e) {
			throw new ApplicationException("Problemas exibindo o relatorio", e);
		 }

		 String name=TempFileNameHelper.getName("rep") + ".pdf";

		 JasperExportManager.exportReportToPdfFile(jasperPrint , TempFileNameHelper.getBaseAbsoluta() + "/" + name);

		 return TempFileNameHelper.getBaseRelativa() + "/" + name;

	  } catch (JRException e) {
		 throw new ApplicationException(e);
	  }
   }

   @SuppressWarnings("unchecked")
   public static String gerarRelatorioSemTentarCompilar2(Collection dados,Arquivo arquivo, Map parametros) throws ApplicationException {
	  try {
		 InputStream stream = arquivo.getStream();
		 JRBeanCollectionDataSource dataSource=null;
		 dataSource=new JRBeanCollectionDataSource(dados);
		 if(stream == null) {
			throw new ApplicationException(".jasper nao encontrado");
		 }
		 JasperPrint jasperPrint=null;
		 try {
			jasperPrint=JasperFillManager.fillReport(stream , parametros,dataSource);

		 } catch (Exception e) {
			throw new ApplicationException("Problemas exibindo o relatorio", e);
		 }
		 String name=TempFileNameHelper.getName("rep") + ".pdf";
		 
		 JasperExportManager.exportReportToPdfFile(jasperPrint , TempFileNameHelper.getBaseAbsoluta() + "/" + name);
		 return TempFileNameHelper.getBaseRelativa() + "/" + name;

	  } catch (JRException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @SuppressWarnings("unchecked")
   public static String gerarPDFSemDataSource(Arquivo arquivo, Map parametros, String matricula) throws ApplicationException {
	  try {
		 InputStream stream = arquivo.getStream();
		 if(stream == null) {
			throw new ApplicationException(".jasper nao encontrado");
		 }
		 JasperPrint jasperPrint=null;
		 try {
			jasperPrint=JasperFillManager.fillReport(stream , parametros, new JREmptyDataSource());

		 } catch (Exception e) {
			throw new ApplicationException("Problemas exibindo o relatorio", e);
		 }
		 String name=matricula + ".pdf";
		 
		 JasperExportManager.exportReportToPdfFile(jasperPrint , TempFileNameHelper.getBaseAbsoluta() + "/" + name);
		 return TempFileNameHelper.getBaseRelativa() + "/" + name;

	  } catch (JRException e) {
		 throw new ApplicationException(e);
	  }
   }

   public static String gerarRelatorio(Collection dados, String jasper, Map parametros, String[] reportSources) throws ApplicationException {
	  LOGGER.debug("Gerando relatorio");
	  try {
		 LOGGER.debug("Tentando gerar relatorio sem compilar. gerarRelatorioSemTentarCompilar(" + dados + ", " + jasper + ", " + parametros + ")");
		 return gerarRelatorioSemTentarCompilar(dados , jasper , parametros);

	  } catch (ApplicationException e) {
		 LOGGER.warn(e);
		 LOGGER.debug("Problemas gerando o relatorio sem compilar. Tentando compilar.");
		 try {
			for(int i=0; i < reportSources.length; i++) {
			   LOGGER.debug("Tentando compilar " + reportSources[i]);
			   compilaRelatorio(reportSources[i]);
			}
		 } catch (JRException e1) {
			LOGGER.debug("Problemas compilando " , e1);
			throw new ApplicationException(e1);
		 }

		 LOGGER.debug("Compilacao terminada. Gerando o relatorio.");
		 return gerarRelatorioSemTentarCompilar(dados , jasper , parametros);
	  }
   }

   private static void compilaRelatorio(String jrxml) throws JRException {
	  try {
		 // jasper.reports.compile.class.path
		 String compilerClasspath=getCompileReportClasspath();
		 LOGGER.debug("Ajustando o classpath de compilacao. " + compilerClasspath);

		 JRProperties.setProperty(JRProperties.COMPILER_CLASSPATH , compilerClasspath);

		 LOGGER.debug("Tentando compilar relatorio" + ReportHelper.class.getResource(jrxml));
		 JasperCompileManager.compileReportToFile(ReportHelper.class.getResource(jrxml).getFile());

	  } catch (Exception e) {
		 LOGGER.error("Nao foi possivel compilar o relatorio " + jrxml , e);
	  }
   }

   public static String gerarDocumento(Map parametros, String template) throws ApplicationException {
	  System.out.println("ENTREI NO METODO QUE GERA O DOCUMENTO");
	  return gerarDocumento(parametros , template , FORMATO_CAMINHO_RELATIVO);
   }

   public static String gerarDocumento(Map parametros, String template, int formatoCaminho) throws ApplicationException {

	  try {

		 InputStream templateStream=ReportHelper.class.getResourceAsStream(template);

		 DocumentPrinter documentPrinter=new DocumentPrinter(templateStream, parametros);

		 documentPrinter.setResourceLoader(new FileResourceLoader(ReportHelper.class.getResource("/").getFile()));

		 String name=TempFileNameHelper.getName("rep") + ".pdf";
		 FileOutputStream pdfStream=new FileOutputStream(TempFileNameHelper.getBaseAbsoluta() + "/" + name);

		 documentPrinter.printDocument(pdfStream);

		 if(formatoCaminho == FORMATO_CAMINHO_RELATIVO) {
			return TempFileNameHelper.getBaseRelativa() + "/" + name;

		 } else {
			return TempFileNameHelper.getBaseAbsoluta() + "/" + name;
		 }

	  } catch (Exception e) {

		 throw new ApplicationException(e);
	  }
   }

   /**
    * Concatena dois ou mais arquivos pdf em um novo arquivo pdf.
    * 
    * @param arquivosOrigem
    *           array contendo os arquivos a serem concatenados
    * @param arquivoDestino
    *           caminho para o novo pdf a ser gerado
    * @throws FileNotFoundException
    *            caso algum dos arquivos de origem nao exista ou nao seja um arquivo valido
    * @throws IOException
    *            caso haja falha na leitura de algum dos arquivos ou na escrita do novo arquivo
    * @throws DocumentException
    *            caso haja falha ao montar no novo documento pdf.
    * @throws IllegalArgumentException
    *            caso algum argumento seja <code>null</code> ou nao existam pelo menos dois arquivos a serem
    *            concatenados.
    * @author osmar (baseado no tutorial do iText)
    * @since 28/10/2011
    */
   @SuppressWarnings("unchecked")
   public static void concatenarPdfs(File[] arquivosOrigem, File arquivoDestino) throws FileNotFoundException, IOException, DocumentException, IllegalArgumentException {
	  if(arquivosOrigem == null) {
		 throw new IllegalArgumentException("O array de arquivos de origem nao pode ser null");
	  } else if(arquivosOrigem.length < 2) {
		 throw new IllegalArgumentException("O array de arquivos de origem nao pode ter menos que 2 elementos");
	  } else if(arquivoDestino == null) {
		 throw new IllegalArgumentException("O arquivo de destino nao pode ser null");
	  }

	  int pageOffset=0;
	  ArrayList master=new ArrayList();
	  String outFile=arquivoDestino.getAbsolutePath();
	  Document document=null;
	  PdfCopy writer=null;
	  for(int f=0; f < arquivosOrigem.length; f++) {
		 LOGGER.debug("Processando arquivo: " + arquivosOrigem[f].getAbsolutePath());

		 if( !arquivosOrigem[f].exists()) {
			throw new FileNotFoundException("Impossivel encontrar arquivo: " + arquivosOrigem[f].getAbsolutePath());
		 } else if( !arquivosOrigem[f].isFile()) {
			throw new FileNotFoundException("Arquivo invalido: " + arquivosOrigem[f].getAbsolutePath());
		 }

		 // Pegamos o Reader para um dos documentos de entrada
		 PdfReader reader=new PdfReader(arquivosOrigem[f].getAbsolutePath());
		 reader.consolidateNamedDestinations();
		 // Descobrimos o numero total de paginas
		 int n=reader.getNumberOfPages();
		 List bookmarks=SimpleBookmark.getBookmark(reader);
		 if(bookmarks != null) {
			if(pageOffset != 0) {
			   SimpleBookmark.shiftPageNumbers(bookmarks , pageOffset , null);
			}
			master.addAll(bookmarks);
		 }
		 pageOffset+=n;

		 LOGGER.debug("Existem " + n + " paginas em: " + arquivosOrigem[f].getName());

		 if(f == 0) {
			// Passo 1: Criacao de um objeto-documento
			document=new Document(reader.getPageSizeWithRotation(1));
			// Passo 2: Criamos um writer que escute o documento
			writer=new PdfCopy(document, new FileOutputStream(outFile));
			// Passo 3: Abrimos o documento
			document.open();
		 }
		 // Passo 4: Adicionamos conteudo
		 PdfImportedPage page;
		 for(int i=0; i < n;) {
			++i;
			page=writer.getImportedPage(reader , i);
			writer.addPage(page);

			LOGGER.debug("Pagina processada: " + i + ", pdf: " + arquivosOrigem[f].getName());

		 }

		 reader.close();

		 LOGGER.debug("Arquivo processado: " + arquivosOrigem[f].getAbsolutePath() + "\n----------");

	  }
	  if(master.size() > 0) {
		 writer.setOutlines(master);
	  }
	  // Passo 5: Fechamos o documento
	  document.close();
   }

   @SuppressWarnings("unchecked")
   public static void concatenarPdfs(ArrayList<File> arquivosOrigem, File arquivoDestino) throws FileNotFoundException, IOException, DocumentException, IllegalArgumentException {
	  if(arquivosOrigem == null) {
		 throw new IllegalArgumentException("O array de arquivos de origem nao pode ser null");
	  } else if(arquivosOrigem.size() < 2) {
		 throw new IllegalArgumentException("O array de arquivos de origem nao pode ter menos que 2 elementos");
	  } else if(arquivoDestino == null) {
		 throw new IllegalArgumentException("O arquivo de destino nao pode ser null");
	  }

	  int pageOffset=0;
	  ArrayList master=new ArrayList();
	  String outFile=arquivoDestino.getAbsolutePath();
	  Document document=null;
	  PdfCopy writer=null;
	  int f=0;
	  for(File file : arquivosOrigem) {

		 LOGGER.debug("Processando arquivo: " + file.getAbsolutePath());

		 if( !file.exists()) {
			throw new FileNotFoundException("Impossivel encontrar arquivo: " + file.getAbsolutePath());
		 } else if( !file.isFile()) {
			throw new FileNotFoundException("Arquivo invalido: " + file.getAbsolutePath());
		 }

		 // Pegamos o Reader para um dos documentos de entrada
		 PdfReader reader=new PdfReader(file.getAbsolutePath());
		 reader.consolidateNamedDestinations();
		 // Descobrimos o numero total de paginas
		 int n=reader.getNumberOfPages();
		 List bookmarks=SimpleBookmark.getBookmark(reader);
		 if(bookmarks != null) {
			if(pageOffset != 0) {
			   SimpleBookmark.shiftPageNumbers(bookmarks , pageOffset , null);
			}
			master.addAll(bookmarks);
		 }
		 pageOffset+=n;

		 LOGGER.debug("Existem " + n + " paginas em: " + file.getName());

		 if(f == 0) {
			// Passo 1: Criacao de um objeto-documento
			document=new Document(reader.getPageSizeWithRotation(1));
			// Passo 2: Criamos um writer que escute o documento
			writer=new PdfCopy(document, new FileOutputStream(outFile));
			// Passo 3: Abrimos o documento
			document.open();
		 }
		 // Passo 4: Adicionamos conteudo
		 PdfImportedPage page;
		 for(int i=0; i < n;) {
			++i;
			page=writer.getImportedPage(reader , i);
			writer.addPage(page);

			LOGGER.debug("Pagina processada: " + i + ", pdf: " + file.getName());

		 }

		 reader.close();

		 LOGGER.debug("Arquivo processado: " + file.getAbsolutePath() + "\n----------");
		 f+=1;
	  }
	  if(master.size() > 0) {
		 writer.setOutlines(master);
	  }
	  // Passo 5: Fechamos o documento
	  document.close();
   }

   @SuppressWarnings("unchecked")
   public static String gerarRelatorioSemTentarCompilarComRealPath(Collection dados, String jasper, Map parametros, Arquivo arquivo) throws ApplicationException {
	  LOGGER.debug("Gerando relatorio sem compilar");
	  try {

		 LOGGER.debug("Criando JRBeanCollectionDataSource com " + dados);
		 JRBeanCollectionDataSource dataSource=null;
		 dataSource=new JRBeanCollectionDataSource(dados);
		 // System.out.println(jasper);

		 InputStream stream=arquivo.getStream();

		 LOGGER.debug("Lendo jasper " + jasper);

		 if(stream == null) {
			LOGGER.debug("Jasper " + ReportHelper.class.getResource(jasper) + " nao encontrado.");
			throw new ApplicationException(".jasper nao encontrado");
		 }

		 JasperPrint jasperPrint=null;
		 try {
			LOGGER.debug("Gerando relatorio fillReport(" + stream + ", " + parametros + ", " + dataSource + ")");

			jasperPrint=JasperFillManager.fillReport(stream , parametros , dataSource);

			String image=(String) parametros.get("Imagem");
			jasperPrint.setProperty("ireport.background.image" , image);
			jasperPrint.setProperty("ireport.background.image.properties" , "true, true, 0.25, -8, -8, 0, 0, 611, 126");

		 } catch (Exception e) {
			LOGGER.debug("Problemas gerando relatorio" , e);

			throw new ApplicationException("Problemas exibindo o relatorio", e);
		 }

		 String name=TempFileNameHelper.getName("rep") + ".pdf";

		 String path=TempFileNameHelper.getBaseAbsoluta() + "/" + name;

		 LOGGER.debug("Exportando arquivo pdf para " + path);
		 JasperExportManager.exportReportToPdfFile(jasperPrint , path);

		 path=TempFileNameHelper.getBaseRelativa() + "/" + name;
		 LOGGER.debug("Retornando resultado " + path);

		 return path;

	  } catch (JRException e) {
		 LOGGER.debug("Problemas..  " , e);
		 throw new ApplicationException(e);
	  }
   }

   @SuppressWarnings("unchecked")
   public static String gerarRelatorioSemTentarCompilarComRealPath(Map parametros, Arquivo arquivo) throws ApplicationException {
	  LOGGER.debug("Gerando relatorio sem compilar");
	  try {

		 // LOGGER.debug("Criando JRBeanCollectionDataSource com " + dados);
		 // JRBeanCollectionDataSource dataSource = null;
		 // dataSource = new JRBeanCollectionDataSource(new ArrayList());
		 // System.out.println(jasper);

		 InputStream stream=arquivo.getStream();

		 LOGGER.debug("Lendo jasper ");

		 if(stream == null) {
			LOGGER.debug("Jasper nao encontrado.");
			throw new ApplicationException(".jasper nao encontrado");
		 }

		 JasperPrint jasperPrint=null;
		 try {
			LOGGER.debug("Gerando relatorio fillReport(" + stream + ", " + parametros);

			jasperPrint=JasperFillManager.fillReport(stream , parametros);

			String image=(String) parametros.get("Imagem");
			jasperPrint.setProperty("ireport.background.image" , image);
			jasperPrint.setProperty("ireport.background.image.properties" , "true, true, 0.25, -8, -8, 0, 0, 611, 126");

		 } catch (Exception e) {
			LOGGER.debug("Problemas gerando relatorio" , e);

			throw new ApplicationException("Problemas exibindo o relatorio", e);
		 }

		 String name=TempFileNameHelper.getName("rep") + ".pdf";

		 String path=TempFileNameHelper.getBaseAbsoluta() + "/" + name;

		 LOGGER.debug("Exportando arquivo pdf para " + path);
		 JasperExportManager.exportReportToPdfFile(jasperPrint , path);

		 path=TempFileNameHelper.getBaseRelativa() + "/" + name;
		 LOGGER.debug("Retornando resultado " + path);

		 return path;

	  } catch (JRException e) {
		 LOGGER.debug("Problemas..  " , e);
		 throw new ApplicationException(e);
	  }
   }

   public static byte[] mergeFilesInPages(List<byte[]> pdfFilesAsByteArray) throws DocumentException, IOException {

	  Document document=new Document();
	  ByteArrayOutputStream byteOS=new ByteArrayOutputStream();

	  PdfWriter writer=PdfWriter.getInstance(document , byteOS);

	  document.open();

	  PdfContentByte cb=writer.getDirectContent();
	  float positionAnterior=0;

	  // Para cada arquivo da lista, cria-se um PdfReader, responsavel por ler
	  // o arquivo PDF e recuperar informacoes dele.
	  for(byte[] pdfFile : pdfFilesAsByteArray) {

		 PdfReader reader=new PdfReader(pdfFile);

		 // Faz o processo de mesclagem por pagina do arquivo, comecando pela
		 // de numero 1.
		 for(int i=1; i <= reader.getNumberOfPages(); i++) {

			float documentHeight=cb.getPdfDocument().getPageSize().getHeight();

			// Importa a pagina do PDF de origem
			PdfImportedPage page=writer.getImportedPage(reader , i);
			float pagePosition=positionAnterior;

			/*
			 * Se a altura restante no documento de destino form menor que a altura do documento, cria-se uma nova
			 * pagina no documento de destino.
			 */
			if((documentHeight - positionAnterior) <= page.getHeight()) {
			   document.newPage();
			   pagePosition=0;
			   positionAnterior=0;
			}

			// Adiciona a pagina ao PDF destino
			cb.addTemplate(page , 0 , pagePosition);

			positionAnterior+=page.getHeight();
		 }
	  }

	  byteOS.flush();
	  document.close();

	  byte[] arquivoEmBytes=byteOS.toByteArray();
	  byteOS.close();

	  return arquivoEmBytes;
   }

   public static String geraGuiaPdfUnicoArquivo(BoletoViewer boletoViewer, String outFile, File file, List<Boleto> boletos, String context) {

	  String prefix=TempFileNameHelper.getName("guia");
	  String sufix=".pdf";
	  String name=prefix + sufix;
	  // String path = TempFileNameHelper.getBaseAbsoluta() + "/" + name;

	  @SuppressWarnings("static-access")
	  File arquivoPdf=boletoViewer.groupInOnePdfWithTemplate(boletos , outFile + "/" + name , file);
	  File arquivoTemp=new File(context + "tmp/" + name);
	  try {
		 InputStream in=new FileInputStream(arquivoPdf);
		 OutputStream out;

		 out=new FileOutputStream(arquivoTemp);
		 // Transferindo bytes de entrada para saida
		 byte[] buf=new byte[1024];
		 int len;
		 while ((len=in.read(buf)) > 0) {
			out.write(buf , 0 , len);
		 }
		 in.close();
		 out.close();
	  } catch (FileNotFoundException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  } catch (IOException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  }
	  // return arquivoTemp.getPath();
	  return TempFileNameHelper.getBaseRelativa() + "/" + name;
   }

   public static String geraGuiaPdf(BoletoViewer boletoViewer, String realPath) {

	  String name=TempFileNameHelper.getName("guia") + ".pdf";

	  File arquivoPdf=boletoViewer.getPdfAsFile(realPath + "/" + name);
	  return TempFileNameHelper.getBaseRelativa() + "/" + arquivoPdf.getName();
   }

   @SuppressWarnings("static-access")
   public static String geraGuiaPdfUnicoArquivoConcatenado(BoletoViewer boletoViewer, File file, List<Boleto> boletos, String context) {
	  String nome=buscaNome("guia");
	  try {
		 ArrayList<File> listaArquivos=new ArrayList<File>();
		 boletoViewer.setTemplate(file);

		 File arquivoPdf=new File(context + "tmp/" + nome);
		 listaArquivos.addAll(boletoViewer.onePerPDF(boletos , context + "/tmp" , buscaNome("GuiaUnica") , file));

		 if(listaArquivos.size() > 1) {
			concatenarPdfs(listaArquivos , arquivoPdf);
		 } else {
			// System.out.println(listaArquivos.get(0).getPath());
			// System.out.println(listaArquivos.get(0).getAbsolutePath());
			// System.out.println(listaArquivos.get(0).getCanonicalPath());
			// System.out.println(listaArquivos.get(0).getName());
			return "tmp/" + listaArquivos.get(0).getName();
		 }

	  } catch (FileNotFoundException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  } catch (IOException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  } catch (IllegalArgumentException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  } catch (DocumentException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  }
	  // return arquivoTemp.getPath();
	  return TempFileNameHelper.getBaseRelativa() + "/" + nome;
   }

   public static String buscaNome(String prefixo) {
	  return TempFileNameHelper.getName(prefixo);
   }

   public static String groupInPages(List<Boleto> boletos, File file, File templatePersonalizado) {

	  File arq=null;
	  BoletoViewer boletoViewer=new BoletoViewer(boletos.get(0));
	  boletoViewer.setTemplate(templatePersonalizado);

	  List<byte[]> boletosEmBytes=new ArrayList<byte[]>(boletos.size());

	  // Adicionando os PDF, em forma de array de bytes, na lista.
	  for(Boleto bop : boletos) {
		 boletosEmBytes.add(boletoViewer.setBoleto(bop).getPdfAsByteArray());
	  }

	  try {

		 // Criando o arquivo com os boletos da lista
		 arq=Files.bytesToFile(file , mergeFilesInPages(boletosEmBytes));

	  } catch (Exception e) {
		 throw new IllegalStateException("Erro durante geração do PDF! Causado por " + e.getLocalizedMessage(), e);
	  }

	  return "tmp/" + arq.getName();
   }

}
