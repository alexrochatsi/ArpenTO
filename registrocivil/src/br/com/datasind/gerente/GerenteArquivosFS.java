package br.com.datasind.gerente;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.aplicacao.Configuracao;
import br.com.datasind.criptografia.AssistenteCriptografia;
import br.com.datasind.criptografia.AssistenteCriptografiaImpl;
import br.com.datasind.criptografia.Criptografador;
import br.com.datasind.criptografia.FalhaCriptografiaException;
import br.com.datasind.entidade.Arquivo;
import br.com.datasind.relatorio.TempFileNameHelper;
import br.com.datasind.util.FiltroNome;

/**
 * 
 * @author OsmarJunior
 * @since 17/09/2011
 */
public class GerenteArquivosFS implements GerenteArquivos {
	private File diretorioBase;

	private String context;

	private static final Logger logger = Logger.getLogger(GerenteArquivosFS.class);

	/**
	 * @throws ApplicationException
	 * 
	 */
	public synchronized String salvar(Arquivo arquivo) throws ApplicationException {
		logger.debug("Salvando arquivo " + arquivo);
		File diretorioAtual = getDiretorioAtual();

		logger.debug("Salvando no diretorio " + diretorioAtual);
		String[] files = diretorioAtual.list();

		int numFiles = (files == null ? 0 : files.length);
		logger.debug("Quantidade de arquivos no diretorio " + numFiles);

		try {
			File file = new File(diretorioAtual.getAbsolutePath() + "\\" + preencheComZeros(numFiles, 5) + arquivo.getSufixo());

			logger.debug("Tentando salvar no arquivo " + file);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));

			byte[] bytes = new byte[arquivo.getStream().available()];
			arquivo.getStream().read(bytes);

			bufferedOutputStream.write(bytes);

			bufferedOutputStream.flush();
			bufferedOutputStream.close();

			String id = getIdForFile(file);
			logger.debug("Arquivo salvo id:" + id);
			return id;

		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * @throws ApplicationException
	 * 
	 */
	public Arquivo abrir(String id) throws ArquivoNaoEncontradoException, ApplicationException {

		id = id.trim();
		File file = getFileForId(id);
		logger.debug("Abrindo arquivo id= " + id);
		logger.debug("Abrindo arquivo em " + file.getAbsolutePath());

		if (!file.exists()) {
			throw new ArquivoNaoEncontradoException("Nao foi possivel encontrar o arquivo com id " + id);
		}

		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

			Arquivo arquivo = new Arquivo();
			arquivo.setStream(bufferedInputStream);
			arquivo.setSufixo(file.getName().substring(file.getName().lastIndexOf('.')));

			return arquivo;

		} catch (FileNotFoundException e) {
			throw new ArquivoNaoEncontradoException(e);
		}
	}

	private String getIdForFile(File file) {
		StringBuffer id = new StringBuffer();
		id.insert(0, file.getName().substring(0, file.getName().lastIndexOf('.')));

		File parent;

		// dia
		parent = file.getParentFile();
		id.insert(0, parent.getName());

		// mes
		parent = parent.getParentFile();
		id.insert(0, parent.getName());

		// ano
		parent = parent.getParentFile();
		id.insert(0, parent.getName());

		return id.toString();
	}

	private File getFileForId(String id) throws ApplicationException {
		StringBuffer path = new StringBuffer(id.substring(0, 8));
		path.insert(4, '\\');
		path.insert(7, '\\');

		File dir = new File(getDiretorioBase() + "\\" + path.toString());
		String[] nomes = dir.list(new FiltroNome(id.substring(8)));
		if (!(nomes.length > 0)) {
			throw new ApplicationException("Nao foi possivel encontrar o arquivo com id " + id);
		}
		return new File(getDiretorioBase() + "\\" + path.toString() + "\\" + nomes[0]);
	}

	/**
	 * @return
	 * @throws ApplicationException
	 */
	private File getDiretorioAtual() throws ApplicationException {
		Calendar calendar = Calendar.getInstance();

		String path = preencheComZeros(calendar.get(Calendar.YEAR), 4) + "\\" + preencheComZeros(calendar.get(Calendar.MONTH) + 1, 2) + "\\"
				+ preencheComZeros(calendar.get(Calendar.DAY_OF_MONTH), 2);

		File diretorioAtual = new File(getDiretorioBase().getAbsolutePath() + "\\" + path);

		if (!diretorioAtual.exists()) {
			diretorioAtual.mkdirs();
		}

		return diretorioAtual;
	}

	private String preencheComZeros(int valor, int tamanho) {
		String resultado = String.valueOf(valor);
		while (resultado.length() < tamanho) {
			resultado = "0" + resultado;
		}

		return resultado;
	}

	public String getContext() throws ApplicationException {

		if (context == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) fc.getExternalContext().getContext();
			context = servletContext.getRealPath("/");
		}

		return context;
	}

	private File getDiretorioBase() throws ApplicationException {
		if (diretorioBase == null) {

			Configuracao configuracao = Configuracao.getInstancia();
			// diretorioBase = new File(String.valueOf(configuracao
			// .get("sgi.ged.base")));
			// System.out.println(getContext());
			diretorioBase = new File(getContext() + String.valueOf(configuracao.get("sgi.tmp")));
			if (!diretorioBase.exists()) {
				diretorioBase.mkdirs();
			}
		}
		return diretorioBase;
	}

	@Override
	public File diretorioRetorno() throws ApplicationException {
		// diretorioBase = new File(getContext() );
		Configuracao configuracao = Configuracao.getInstancia();
		diretorioBase = new File(getContext() + String.valueOf(configuracao.get("sgi.retorno")));
		if (!diretorioBase.exists()) {
			diretorioBase.mkdirs();
		}
		return diretorioBase;

		// return new File(getContext() + ".");
	}

	public String salvarArquivoComCriptografia(Arquivo arquivo, Criptografador criptografador) throws ApplicationException {
		try {
			AssistenteCriptografia assistente = new AssistenteCriptografiaImpl(criptografador);
			arquivo = assistente.criptografar(arquivo);
		} catch (FalhaCriptografiaException e) {
			throw new ApplicationException(e);
		}
		return salvar(arquivo);
	}

	public Arquivo abrirArquivoComCriptografia(String id, Criptografador criptografador) throws ApplicationException {
		Arquivo arquivo;
		try {
			AssistenteCriptografia assistente = new AssistenteCriptografiaImpl(criptografador);
			arquivo = assistente.descriptografar(abrir(id));
		} catch (FalhaCriptografiaException e) {
			throw new ApplicationException(e);
		} catch (ArquivoNaoEncontradoException e) {
			throw new ApplicationException(e);
		}
		return arquivo;
	}

	public synchronized String salvarImagem(Arquivo arquivo, String nome, String path) throws ApplicationException {

		logger.debug("Salvando arquivo " + arquivo);

		try {

			File retorno = getDiretorioImagens();

			if (!retorno.exists()) {
				retorno.mkdirs();
			}

			logger.debug("Tentando salvar no arquivo " + nome);
			nome = TempFileNameHelper.getName("img") + arquivo.getSufixo();
			retorno = new File(retorno + "/" + nome);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(retorno));

			byte[] bytes = new byte[arquivo.getStream().available()];
			arquivo.getStream().read(bytes);

			bufferedOutputStream.write(bytes);

			bufferedOutputStream.flush();
			bufferedOutputStream.close();

			// String id = getIdForFile(file);
			// logger.debug("Arquivo salvo id:" + id);
			return nome;

		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Arquivo abrirImagem(String nome) throws ArquivoNaoEncontradoException, ApplicationException {
		nome = nome.trim();
		logger.debug("Procurando arquivo: " + nome);
		File file = getImagemForId(nome, getDiretorioImagens().getPath());
		logger.debug("Abrindo arquivo id= " + nome);
		logger.debug("Abrindo arquivo em " + file.getAbsolutePath());

		if (!file.exists()) {
			throw new ArquivoNaoEncontradoException("Nao foi possivel encontrar o arquivo com id " + nome);
		}

		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

			Arquivo arquivo = new Arquivo();
			arquivo.setStream(bufferedInputStream);
			arquivo.setSufixo(file.getName().substring(file.getName().lastIndexOf('.')));

			return arquivo;

		} catch (FileNotFoundException e) {
			throw new ArquivoNaoEncontradoException(e);
		}
	}

	private File getImagemForId(String nome, String path) throws ApplicationException {

		// File dir = new File(path+"\\resources\\images\\images");
		// System.out.println(dir.toString());
		// String[] nomes = dir.list(new FiltroNome(nome.substring(10)));
		/*
		 * if (!(nomes.length > 0)) { throw new ApplicationException(
		 * "Nao foi possivel encontrar imagem com nome " + nome); }
		 */
		return new File(path + "/" + nome);

	}

	@Override
	public Boolean verificaExistenciaImagem(String nome) throws ApplicationException {

		if (nome == null || "".equals(nome)) {
			return Boolean.FALSE;
		}

		nome = nome.trim();
		logger.debug("Procurando existencia do arquivo: " + nome);

		File file = getImagemForId(nome, getDiretorioImagensString());
		logger.debug("Procurando arquivo em " + file.getAbsolutePath());

		if (!file.exists()) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public Arquivo abrirArquivoPorRealPath(String nome, String path) throws ArquivoNaoEncontradoException, ApplicationException {
		nome = nome.trim();
		logger.debug("Procurando arquivo: " + nome);
		File file = new File(path + "resources/reports/" + nome);
		if (!file.exists()) {
			throw new ArquivoNaoEncontradoException("Nao foi possivel encontrar o arquivo com id " + nome);
		}

		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

			Arquivo arquivo = new Arquivo();
			arquivo.setStream(bufferedInputStream);
			arquivo.setSufixo(file.getName().substring(file.getName().lastIndexOf('.')));

			return arquivo;

		} catch (FileNotFoundException e) {
			throw new ArquivoNaoEncontradoException(e);
		}
	}

	@Override
	public Arquivo abrirArquivoPorPath(String nome, String path) throws ArquivoNaoEncontradoException, ApplicationException {
		nome = nome.trim();
		logger.debug("Procurando arquivo: " + nome);
		File file = new File(path + nome);
		if (!file.exists()) {
			throw new ArquivoNaoEncontradoException("Nao foi possivel encontrar o arquivo com id " + nome);
		}

		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

			Arquivo arquivo = new Arquivo();
			arquivo.setStream(bufferedInputStream);
			arquivo.setSufixo(file.getName().substring(file.getName().lastIndexOf('.')));

			return arquivo;

		} catch (FileNotFoundException e) {
			throw new ArquivoNaoEncontradoException(e);
		}
	}

	@Override
	public String pathGuiasPorEmpresa(String empresa) throws ApplicationException {
		File diretorioAtual = new File(getDiretorioBase().getAbsolutePath() + "/" + empresa);

		if (!diretorioAtual.exists()) {
			diretorioAtual.mkdirs();
		}

		return diretorioAtual.getAbsolutePath();
	}

	@Override
	public String pathSgiTemp() throws ApplicationException {
		File diretorioAtual = new File(getDiretorioBase().getAbsolutePath() + "/");

		if (!diretorioAtual.exists()) {
			diretorioAtual.mkdirs();
		}

		return diretorioAtual.getAbsolutePath();
	}

	@Override
	public String getDiretorioLayoutCEFSindical() throws ApplicationException {
		Configuracao configuracao = Configuracao.getInstancia();

		String localLayout = String.valueOf(configuracao.get("sgi.importacao.cef.layoutSindical"));

		return localLayout;
	}

	@Override
	public String getDiretorioLayoutCEFSIGCB240() throws ApplicationException {
		Configuracao configuracao = Configuracao.getInstancia();

		String localLayout = String.valueOf(configuracao.get("sgi.importacao.cef.layoutSIGCB240"));

		return localLayout;
	}

	@Override
	public String getDiretorioLayoutSicoob240() throws ApplicationException {
		Configuracao configuracao = Configuracao.getInstancia();

		String localLayout = String.valueOf(configuracao.get("sgi.importacao.sicoob.layoutSicoob240"));

		return localLayout;
	}

	@Override
	public String getDiretorioLayoutItau240() throws ApplicationException {
		Configuracao configuracao = Configuracao.getInstancia();

		String localLayout = String.valueOf(configuracao.get("sgi.importacao.sicoob.layoutItau240"));

		return localLayout;
	}

	public File getDiretorioRetornoSindical() throws ApplicationException {

		Configuracao configuracao = Configuracao.getInstancia();
		File diretorioRetornoSindical = new File(getContext() + String.valueOf(configuracao.get("sgi.importacao.cef.diretorioRetornoSindical")));
		if (!diretorioRetornoSindical.exists()) {
			diretorioRetornoSindical.mkdirs();
		}

		return diretorioRetornoSindical;
	}

	public File getDiretorioRetornoDiversas() throws ApplicationException {

		Configuracao configuracao = Configuracao.getInstancia();

		File diretorioRetornoSindical = new File(getContext() + String.valueOf(configuracao.get("sgi.importacao.cef.diretorioRetornoDiversas")));
		if (!diretorioRetornoSindical.exists()) {
			diretorioRetornoSindical.mkdirs();
		}

		return diretorioRetornoSindical;
	}

	public File getDiretorioImagens() throws ApplicationException {

		Configuracao configuracao = Configuracao.getInstancia();
		File diretorioRetornoSindical = new File(getContext() + String.valueOf(configuracao.get("sgi.arquivos.imagens")));
		if (!diretorioRetornoSindical.exists()) {
			diretorioRetornoSindical.mkdirs();
		}

		return diretorioRetornoSindical;
	}

	public String getDiretorioImagensString() throws ApplicationException {

		Configuracao configuracao = Configuracao.getInstancia();
		File diretorioRetornoSindical = new File(getContext() + String.valueOf(configuracao.get("sgi.arquivos.imagens")));
		if (!diretorioRetornoSindical.exists()) {
			diretorioRetornoSindical.mkdirs();
		}

		return diretorioRetornoSindical.getPath() + "/";
	}

	public String getDiretorioArquivosParametrizados() throws ApplicationException {

		Configuracao configuracao = Configuracao.getInstancia();
		File diretorioRetornoSindical = new File(getContext() + String.valueOf(configuracao.get("sgi.arquivos.parametros")));
		if (!diretorioRetornoSindical.exists()) {
			diretorioRetornoSindical.mkdirs();
		}

		return diretorioRetornoSindical.getPath() + "/";
	}

	@Override
	public File salvarRetornoSindical(UploadedFile file) throws ApplicationException {
		logger.debug("Salvando arquivo " + file.getFileName());
		File retorno = getDiretorioRetornoSindical();
		try {
			Arquivo arquivo = new Arquivo();
			arquivo.setStream(file.getInputstream());
			arquivo.setSufixo(file.getFileName());

			if (!retorno.exists()) {
				retorno.mkdirs();
			}
			retorno = new File(retorno + "/" + file.getFileName());
			logger.debug("Tentando salvar no arquivo " + file);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(retorno));

			byte[] bytes = new byte[arquivo.getStream().available()];
			arquivo.getStream().read(bytes);

			bufferedOutputStream.write(bytes);

			bufferedOutputStream.flush();
			bufferedOutputStream.close();

			// String id = getIdForFile(file);
			// logger.debug("Arquivo salvo id:" + id);
			return retorno;

		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);

		} catch (IOException e) {
			throw new ApplicationException(e);
		}

	}

	@Override
	public File salvarRetornoDiversas(UploadedFile file) throws ApplicationException {
		logger.debug("Salvando arquivo " + file.getFileName());
		File retorno = getDiretorioRetornoDiversas();
		try {
			Arquivo arquivo = new Arquivo();
			arquivo.setStream(file.getInputstream());
			arquivo.setSufixo(file.getFileName());

			if (!retorno.exists()) {
				retorno.mkdirs();
			}
			retorno = new File(retorno + "/" + file.getFileName());
			logger.debug("Tentando salvar no arquivo " + file);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(retorno));

			byte[] bytes = new byte[arquivo.getStream().available()];
			arquivo.getStream().read(bytes);

			bufferedOutputStream.write(bytes);

			bufferedOutputStream.flush();
			bufferedOutputStream.close();

			// String id = getIdForFile(file);
			// logger.debug("Arquivo salvo id:" + id);
			return retorno;

		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public String salvarImagemCaptcha(BufferedImage image) throws ApplicationException {
		String nomeArquivo = new Date().getTime() + ".jpg";

		File file = new File(getContext() + "/tmp" + "/" + nomeArquivo);

		if (!file.exists()) {
			file.mkdirs();
		}

		try {
			if (image != null) {
				ImageIO.write(image, "jpg", file);
			} else {
				return null;
			}

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
		return "../tmp/" + nomeArquivo;
	}

}
