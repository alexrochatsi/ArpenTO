package br.com.certisign;

import java.io.Serializable;


public class CertificadoBean implements Serializable {

	private static final long serialVersionUID = -4713516070205384338L;

	// emissor: Certisign Autoridade Certificadora TESTE,
	// nome: "PROPRIETARIO DO CERTIFICADO DE TESTE",
	// cpf: "001.002.003-44",
	// cnpj: "00000000000000000",
	// OAB: "00000000000",
	// validade: 01/01/2015,
	// certificado: "JKKJASDF8Q783E......OUQWEYOFCYASDOUIYFQ780"

	private String serial;
	private String emissor;
	private String nome;
	private String cpf;
	private String cnpj;
	private String oab;
	private String validade;
	private String emissao;
	private String email;
	private String certificadoBase64;
	private String tituloEleitor;
	private String valido;

	public CertificadoBean() {
	};

	public CertificadoBean(String serial, String emissor, String nome, String cpf, String cnpj, String oab, String validade, String emissao, String email,
			String certificadoBase64, String tituloEleitor, String valido) {
		super();
		this.serial = serial;
		this.emissor = emissor;
		this.nome = nome;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.oab = oab;
		this.validade = validade;
		this.emissao = emissao;
		this.email = email;
		this.certificadoBase64 = certificadoBase64;
		this.tituloEleitor = tituloEleitor;
		this.valido = valido;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getOab() {
		return oab;
	}

	public void setOab(String oab) {
		this.oab = oab;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCertificadoBase64() {
		return certificadoBase64;
	}

	public void setCertificadoBase64(String certificadoBase64) {
		this.certificadoBase64 = certificadoBase64;
	}

	public String getEmissao() {
		return emissao;
	}

	public void setEmissao(String emissao) {
		this.emissao = emissao;
	}

	public String getTituloEleitor() {
		return tituloEleitor;
	}

	public void setTituloEleitor(String tituloEleitor) {
		this.tituloEleitor = tituloEleitor;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getValido() {
		return valido;
	}

	public void setValido(String valido) {
		this.valido = valido;
	}

	@Override
	public String toString() {
		return "CertificadoBean [serial=" + serial + ", emissor=" + emissor
				+ ", nome=" + nome + ", cpf=" + cpf + ", cnpj=" + cnpj
				+ ", oab=" + oab + ", validade=" + validade + ", emissao="
				+ emissao + ", email=" + email + ", certificadoBase64="
				+ certificadoBase64 + ", tituloEleitor=" + tituloEleitor
				+ ", valido=" + valido + "]";
	}

}
