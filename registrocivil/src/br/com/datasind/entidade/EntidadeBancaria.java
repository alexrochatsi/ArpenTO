package br.com.datasind.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @Author osmar
 * @since 16/08/2011
 * 
 **/
@Entity
@Table(name = "entidadebancaria")
public class EntidadeBancaria extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5281801528120411428L;

	@Id
	@GeneratedValue
	private Integer id;
	private String agencia;
	private String dvAgencia;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Banco banco;
	private String cedente;
	private String dvCedente;
	private String operacao;
	private String nome;
	private String endereco;
	private String bairro;
	private String cep;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Cidade cidade;
	private String fone;
	private String cnpj;
	private String responsavel;
	private String email;
	private String site;
	private String observacoes;
	private String carteira;
	private Boolean status;
	private Double saldoInicial;
	private Double saldo;
	private Boolean registro;
	private String tipoArquivo;
	@Enumerated(EnumType.STRING)
	private TipoConta tipoConta;
	private String conta;
	private String dvConta;
	private Boolean testeProducao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getDvAgencia() {
		return dvAgencia;
	}

	public void setDvAgencia(String dvAgencia) {
		this.dvAgencia = dvAgencia;
	}

	public Banco getBanco() {
		if (banco == null) {
			banco = new Banco();
		}
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getCedente() {
		return cedente;
	}

	public void setCedente(String cedente) {
		this.cedente = cedente;
	}

	public String getDvCedente() {
		return dvCedente;
	}

	public void setDvCedente(String dvCedente) {
		this.dvCedente = dvCedente;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Cidade getCidade() {
		if (cidade == null) {
			cidade = new Cidade();
		}
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Boolean getRegistro() {
		if (registro == null) {
			registro = false;
		}
		return registro;
	}

	public void setRegistro(Boolean registro) {
		this.registro = registro;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getDvConta() {
		return dvConta;
	}

	public void setDvConta(String dvConta) {
		this.dvConta = dvConta;
	}

	public Boolean getTesteProducao() {
		return testeProducao;
	}

	public void setTesteProducao(Boolean testeProducao) {
		this.testeProducao = testeProducao;
	}
}
