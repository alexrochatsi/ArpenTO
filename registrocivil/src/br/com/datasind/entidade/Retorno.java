package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "retorno")
public class Retorno extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1506529036915117686L;
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private String hdNomeArquivo;
	private String hdCodigoBanco;
	private String hdEmpresaCodigoConvenio;
	private Integer hdEmpresaAgenciaNumero;
	private Integer hdEmpresaAgenciaDv;
	private String hdEmpresaContaCorrenteNumero;
	private String hdEmpresaContaCorrenteDv;
	private String hdEmpresaDvAgenciaConta;
	private Integer hdArquivoSequenciaNumero;
	private Date hdDataGeracao;
	private Integer hdArquivoLayoutVersao;
	private String hlMsg1;
	private String hlMsg2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHdNomeArquivo() {
		return hdNomeArquivo;
	}

	public void setHdNomeArquivo(String hdNomeArquivo) {
		this.hdNomeArquivo = hdNomeArquivo;
	}

	public String getHdCodigoBanco() {
		return hdCodigoBanco;
	}

	public void setHdCodigoBanco(String hdCodigoBanco) {
		this.hdCodigoBanco = hdCodigoBanco;
	}

	public String getHdEmpresaCodigoConvenio() {
		return hdEmpresaCodigoConvenio;
	}

	public void setHdEmpresaCodigoConvenio(String hdEmpresaCodigoConvenio) {
		this.hdEmpresaCodigoConvenio = hdEmpresaCodigoConvenio;
	}

	public Integer getHdEmpresaAgenciaNumero() {
		return hdEmpresaAgenciaNumero;
	}

	public void setHdEmpresaAgenciaNumero(Integer hdEmpresaAgenciaNumero) {
		this.hdEmpresaAgenciaNumero = hdEmpresaAgenciaNumero;
	}

	public Integer getHdEmpresaAgenciaDv() {
		return hdEmpresaAgenciaDv;
	}

	public void setHdEmpresaAgenciaDv(Integer hdEmpresaAgenciaDv) {
		this.hdEmpresaAgenciaDv = hdEmpresaAgenciaDv;
	}

	public String getHdEmpresaContaCorrenteNumero() {
		return hdEmpresaContaCorrenteNumero;
	}

	public void setHdEmpresaContaCorrenteNumero(String hdEmpresaContaCorrenteNumero) {
		this.hdEmpresaContaCorrenteNumero = hdEmpresaContaCorrenteNumero;
	}

	public String getHdEmpresaContaCorrenteDv() {
		return hdEmpresaContaCorrenteDv;
	}

	public void setHdEmpresaContaCorrenteDv(String hdEmpresaContaCorrenteDv) {
		this.hdEmpresaContaCorrenteDv = hdEmpresaContaCorrenteDv;
	}

	public String getHdEmpresaDvAgenciaConta() {
		return hdEmpresaDvAgenciaConta;
	}

	public void setHdEmpresaDvAgenciaConta(String hdEmpresaDvAgenciaConta) {
		this.hdEmpresaDvAgenciaConta = hdEmpresaDvAgenciaConta;
	}

	public Integer getHdArquivoSequenciaNumero() {
		return hdArquivoSequenciaNumero;
	}

	public void setHdArquivoSequenciaNumero(Integer hdArquivoSequenciaNumero) {
		this.hdArquivoSequenciaNumero = hdArquivoSequenciaNumero;
	}

	public Date getHdDataGeracao() {
		return hdDataGeracao;
	}

	public void setHdDataGeracao(Date hdDataGeracao) {
		this.hdDataGeracao = hdDataGeracao;
	}

	public Integer getHdArquivoLayoutVersao() {
		return hdArquivoLayoutVersao;
	}

	public void setHdArquivoLayoutVersao(Integer hdArquivoLayoutVersao) {
		this.hdArquivoLayoutVersao = hdArquivoLayoutVersao;
	}

	public String getHlMsg1() {
		return hlMsg1;
	}

	public void setHlMsg1(String hlMsg1) {
		this.hlMsg1 = hlMsg1;
	}

	public String getHlMsg2() {
		return hlMsg2;
	}

	public void setHlMsg2(String hlMsg2) {
		this.hlMsg2 = hlMsg2;
	}

}