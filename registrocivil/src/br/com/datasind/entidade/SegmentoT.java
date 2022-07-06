package br.com.datasind.entidade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "segmentot")
public class SegmentoT extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1233379774116705082L;

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 4)
	private String codigoBanco;
	@Column(length = 6)
	private String numSequencialRegLote;
	@Column(length = 2)
	private String codigoSegmento;
	@Column(length = 4)
	private String codigoMevimentoRetorno;
	@Column(length = 30)
	private String nossoNumero;
	@Column(length = 4)
	private Integer codigoCarteira;
	@Column(length = 20)
	private String numeroDocCobranca;
	private Date vencimentoTitulo;
	@Column(length = 20)
	private BigDecimal valorNominalTitulo;
	@Column(length = 4)
	private String bancoCobRecebimento;
	@Column(length = 5)
	private String agCobRecebimento;
	@Column(length = 2)
	private String dvAgenciaCobRecebimento;
	@Column(length = 30)
	private String identificacaoTituloEmpresa;
	@Column(length = 4)
	private String codigoMoeda;
	@Column(length = 15)
	private String numeroInscricao;
	@Column(length = 50)
	private String nome;
	@Column(length = 15)
	private String numeroContrato;
	@Column(length = 20)
	private BigDecimal valorTarifaCustas;
	@Column(length = 15)
	private String identificacaoRejeicao;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Retorno retorno;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private SegmentoU segmentoU;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getNumSequencialRegLote() {
		return numSequencialRegLote;
	}

	public void setNumSequencialRegLote(String numSequencialRegLote) {
		this.numSequencialRegLote = numSequencialRegLote;
	}

	public String getCodigoSegmento() {
		return codigoSegmento;
	}

	public void setCodigoSegmento(String codigoSegmento) {
		this.codigoSegmento = codigoSegmento;
	}

	public String getCodigoMevimentoRetorno() {
		return codigoMevimentoRetorno;
	}

	public void setCodigoMevimentoRetorno(String codigoMevimentoRetorno) {
		this.codigoMevimentoRetorno = codigoMevimentoRetorno;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Integer getCodigoCarteira() {
		return codigoCarteira;
	}

	public void setCodigoCarteira(Integer codigoCarteira) {
		this.codigoCarteira = codigoCarteira;
	}

	public String getNumeroDocCobranca() {
		return numeroDocCobranca;
	}

	public void setNumeroDocCobranca(String numeroDocCobranca) {
		this.numeroDocCobranca = numeroDocCobranca;
	}

	public Date getVencimentoTitulo() {
		return vencimentoTitulo;
	}

	public void setVencimentoTitulo(Date vencimentoTitulo) {
		this.vencimentoTitulo = vencimentoTitulo;
	}

	public BigDecimal getValorNominalTitulo() {
		return valorNominalTitulo;
	}

	public void setValorNominalTitulo(BigDecimal valorNominalTitulo) {
		this.valorNominalTitulo = valorNominalTitulo;
	}

	public String getBancoCobRecebimento() {
		return bancoCobRecebimento;
	}

	public void setBancoCobRecebimento(String bancoCobRecebimento) {
		this.bancoCobRecebimento = bancoCobRecebimento;
	}

	public String getAgCobRecebimento() {
		return agCobRecebimento;
	}

	public void setAgCobRecebimento(String agCobRecebimento) {
		this.agCobRecebimento = agCobRecebimento;
	}

	public String getDvAgenciaCobRecebimento() {
		return dvAgenciaCobRecebimento;
	}

	public void setDvAgenciaCobRecebimento(String dvAgenciaCobRecebimento) {
		this.dvAgenciaCobRecebimento = dvAgenciaCobRecebimento;
	}

	public String getIdentificacaoTituloEmpresa() {
		return identificacaoTituloEmpresa;
	}

	public void setIdentificacaoTituloEmpresa(String identificacaoTituloEmpresa) {
		this.identificacaoTituloEmpresa = identificacaoTituloEmpresa;
	}

	public String getCodigoMoeda() {
		return codigoMoeda;
	}

	public void setCodigoMoeda(String codigoMoeda) {
		this.codigoMoeda = codigoMoeda;
	}

	public String getNumeroInscricao() {
		return numeroInscricao;
	}

	public void setNumeroInscricao(String numeroInscricao) {
		this.numeroInscricao = numeroInscricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public BigDecimal getValorTarifaCustas() {
		return valorTarifaCustas;
	}

	public void setValorTarifaCustas(BigDecimal valorTarifaCustas) {
		this.valorTarifaCustas = valorTarifaCustas;
	}

	public String getIdentificacaoRejeicao() {
		return identificacaoRejeicao;
	}

	public void setIdentificacaoRejeicao(String identificacaoRejeicao) {
		this.identificacaoRejeicao = identificacaoRejeicao;
	}

	public Retorno getRetorno() {
		if (retorno == null) {
			retorno = new Retorno();
		}
		return retorno;
	}

	public void setRetorno(Retorno retorno) {
		this.retorno = retorno;
	}

	public SegmentoU getSegmentoU() {
		if (segmentoU == null) {
			segmentoU = new SegmentoU();
		}
		return segmentoU;
	}

	public void setSegmentoU(SegmentoU segmentoU) {
		this.segmentoU = segmentoU;
	}

}
