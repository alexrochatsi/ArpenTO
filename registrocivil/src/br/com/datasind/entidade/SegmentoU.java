package br.com.datasind.entidade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "segmentou")
public class SegmentoU extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3815636413933434289L;

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 2)
	private String codigoSegmento;
	@Column(length = 4)
	private String codigoMovimentoRetorno;
	@Column(length = 20)
	private BigDecimal vlrJurosMulta;
	@Column(length = 20)
	private BigDecimal vlrDesconto;
	@Column(length = 20)
	private BigDecimal vlrAbatimento;
	@Column(length = 20)
	private BigDecimal vlrIOFRecolhido;
	@Column(length = 20)
	private BigDecimal vlrPago;
	@Column(length = 20)
	private BigDecimal vlrASerCreditado;
	@Column(length = 20)
	private BigDecimal vlrOutrasDespesas;
	@Column(length = 20)
	private BigDecimal vlrOutrosCreditos;
	private Date dataOcorrencia;
	private Date dataEfetivacaoCredito;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoSegmento() {
		return codigoSegmento;
	}

	public void setCodigoSegmento(String codigoSegmento) {
		this.codigoSegmento = codigoSegmento;
	}

	public String getCodigoMovimentoRetorno() {
		return codigoMovimentoRetorno;
	}

	public void setCodigoMovimentoRetorno(String codigoMovimentoRetorno) {
		this.codigoMovimentoRetorno = codigoMovimentoRetorno;
	}

	public BigDecimal getVlrJurosMulta() {
		return vlrJurosMulta;
	}

	public void setVlrJurosMulta(BigDecimal vlrJurosMulta) {
		this.vlrJurosMulta = vlrJurosMulta;
	}

	public BigDecimal getVlrDesconto() {
		return vlrDesconto;
	}

	public void setVlrDesconto(BigDecimal vlrDesconto) {
		this.vlrDesconto = vlrDesconto;
	}

	public BigDecimal getVlrAbatimento() {
		return vlrAbatimento;
	}

	public void setVlrAbatimento(BigDecimal vlrAbatimento) {
		this.vlrAbatimento = vlrAbatimento;
	}

	public BigDecimal getVlrIOFRecolhido() {
		return vlrIOFRecolhido;
	}

	public void setVlrIOFRecolhido(BigDecimal vlrIOFRecolhido) {
		this.vlrIOFRecolhido = vlrIOFRecolhido;
	}

	public BigDecimal getVlrPago() {
		return vlrPago;
	}

	public void setVlrPago(BigDecimal vlrPago) {
		this.vlrPago = vlrPago;
	}

	public BigDecimal getVlrASerCreditado() {
		return vlrASerCreditado;
	}

	public void setVlrASerCreditado(BigDecimal vlrASerCreditado) {
		this.vlrASerCreditado = vlrASerCreditado;
	}

	public BigDecimal getVlrOutrasDespesas() {
		return vlrOutrasDespesas;
	}

	public void setVlrOutrasDespesas(BigDecimal vlrOutrasDespesas) {
		this.vlrOutrasDespesas = vlrOutrasDespesas;
	}

	public BigDecimal getVlrOutrosCreditos() {
		return vlrOutrosCreditos;
	}

	public void setVlrOutrosCreditos(BigDecimal vlrOutrosCreditos) {
		this.vlrOutrosCreditos = vlrOutrosCreditos;
	}

	public Date getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(Date dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public Date getDataEfetivacaoCredito() {
		return dataEfetivacaoCredito;
	}

	public void setDataEfetivacaoCredito(Date dataEfetivacaoCredito) {
		this.dataEfetivacaoCredito = dataEfetivacaoCredito;
	}

}
