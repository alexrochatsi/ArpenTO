package br.com.datasind.relatorio;

/**
 * 
 */


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import br.com.datasind.entidade.Arquivo;


/**
 * @author ccosta
 * 
 */
public class TempFileNameHelper {
	public static final String BASE = "tmp";

	/**
	 * 
	 */
	public TempFileNameHelper() {
		super();
	}

	public static String getBase(String pasta) {
		File file;
		try {
			file = new File(URLDecoder.decode(TempFileNameHelper.class.getResource("").getFile(), "UTF-8"));

			while (!file.getParent().endsWith("WEB-INF")) {
				file = file.getParentFile();
			}
			// WEB-INF
			file = file.getParentFile();

			// contextRoot
			file = file.getParentFile();

			String base = file.getAbsolutePath() + File.separator + pasta;
			File baseFile = new File(base);
			baseFile.mkdirs();
			return base;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getBaseAbsoluta() {
		try {
			String baseAbsoluta = new File(URLDecoder.decode(TempFileNameHelper.class.getResource("/").getFile(), "UTF-8")).getParentFile().getParentFile()
					.getAbsolutePath()
					+ File.separator + BASE;
			File baseAbsolutaFile = new File(baseAbsoluta);
			baseAbsolutaFile.mkdirs();

			return baseAbsoluta;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getBaseRelativa() {
		return BASE;
	}

	public static String getName(String base) {
		return base + System.currentTimeMillis();
	}

	public static String gerarArquivoTemporario(Arquivo arquivo) throws IOException {
		String name = TempFileNameHelper.getName("doc") + arquivo.getSufixo();

		File file = new File(TempFileNameHelper.getBaseAbsoluta() + "/" + name);
		file.createNewFile();
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));

		byte[] bytes = new byte[arquivo.getStream().available()];

		arquivo.getStream().read(bytes);
		outputStream.write(bytes);
		outputStream.flush();
		outputStream.close();

		return name;
	}

}
