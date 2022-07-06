package br.com.datasind.gerente;

import java.awt.image.BufferedImage;
import java.io.File;

import org.primefaces.model.UploadedFile;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.criptografia.Criptografador;
import br.com.datasind.entidade.Arquivo;

public interface GerenteArquivos extends Gerente {
	public String salvar(Arquivo arquivo) throws ApplicationException;

	public Arquivo abrir(String id) throws ArquivoNaoEncontradoException, ApplicationException;

	public String salvarArquivoComCriptografia(Arquivo arquivo, Criptografador criptografador) throws ApplicationException;

	public Arquivo abrirArquivoComCriptografia(String id, Criptografador criptografador) throws ApplicationException;

	public String salvarImagem(Arquivo arquivo, String nome, String path) throws ApplicationException;

	public Arquivo abrirImagem(String nome) throws ArquivoNaoEncontradoException, ApplicationException;

	public Arquivo abrirArquivoPorRealPath(String nome, String path) throws ArquivoNaoEncontradoException, ApplicationException;

	public Arquivo abrirArquivoPorPath(String nome, String path) throws ArquivoNaoEncontradoException, ApplicationException;

	public Boolean verificaExistenciaImagem(String nome) throws ApplicationException;

	public String pathGuiasPorEmpresa(String empresa) throws ApplicationException;

	public String pathSgiTemp() throws ApplicationException;

	public String getDiretorioLayoutCEFSindical() throws ApplicationException;

	public String getDiretorioLayoutCEFSIGCB240() throws ApplicationException;

	public String getDiretorioLayoutSicoob240() throws ApplicationException;

	public String getDiretorioLayoutItau240() throws ApplicationException;

	public String getDiretorioImagensString() throws ApplicationException;

	public String getDiretorioArquivosParametrizados() throws ApplicationException;

	public File salvarRetornoSindical(UploadedFile file) throws ApplicationException;

	public File salvarRetornoDiversas(UploadedFile file) throws ApplicationException;

	public File diretorioRetorno() throws ApplicationException;

	public String salvarImagemCaptcha(BufferedImage image) throws ApplicationException;

}
