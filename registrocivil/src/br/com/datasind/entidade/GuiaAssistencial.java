package br.com.datasind.entidade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "guiaassistencial")
public class GuiaAssistencial extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3637983449002250602L;

	@Id
	@GeneratedValue
	private Integer id;
	private Date dataVencimento;
	private String exercicio;
	private String mes;
	private String nossoNumero;
	private String localPagamento;
	private Date dataDocumento;
	private String numeroDocumento;
	private String especieDocumento;
	private String aceite;
	private Date dataProcessamento;
	private String usuBanco;
	private String carteira;
	private String especie;
	private String quantidade;
	private BigDecimal valorDocumento;
	private BigDecimal descontosAbatimentos;
	private BigDecimal outrasDeducoes;
	private BigDecimal moraMulta;
	private BigDecimal outrosAcrescimos;
	private BigDecimal valorCobrado;
	private BigDecimal valor;
	private String codigoBarras;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private TipoGuia tipoGuia;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
	private SegmentoT segmentoT;

	@OneToMany(mappedBy = "guiaAssistencial", fetch = FetchType.LAZY)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<GuiaAssistencialObservacao> observacoes;

	private Integer status;

	@Lob
	private String instrucoesProtocolo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Usuario usuario;

	private String nossoNumeroAnterior;

	private Boolean incluirEscritorio;

	private Integer statusRemessa;

	private Integer codigoMovimento;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Remessa remessa;

	public static final int CT_ATIVA = 0;

	public static final int CT_PAGA = 1;

	public static final int CT_BAIXADA = 2;

	public static final int CT_EXCLUIDA = 3;

	public static final String descStatus[] = { "ATIVA", "PAGA", "BAIXADA", "EXCLUIDA" };

	public static final int RM_SEM_ENVIAR = 0;

	public static final int RM_ENVIADA = 1;

	public static final int RM_ALTERADA_SEM_ENVIO = 2;

	public static final int RM_ALTERADA_ENVIADA = 3;

	public static final int RM_CANCELADA_SEM_ENVIO = 4;

	public static final int RM_CANCELADA_ENVIADA = 5;

	public static final String descStatusRemessa[] = { "SEM ENVIAR", "ENVIADA" };

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getExercicio() {
		return exercicio;
	}

	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getLocalPagamento() {
		return localPagamento;
	}

	public void setLocalPagamento(String localPagamento) {
		this.localPagamento = localPagamento;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getEspecieDocumento() {
		return especieDocumento;
	}

	public void setEspecieDocumento(String especieDocumento) {
		this.especieDocumento = especieDocumento;
	}

	public String getAceite() {
		return aceite;
	}

	public void setAceite(String aceite) {
		this.aceite = aceite;
	}

	public Date getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public String getUsuBanco() {
		return usuBanco;
	}

	public void setUsuBanco(String usuBanco) {
		this.usuBanco = usuBanco;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorDocumento() {
		return valorDocumento;
	}

	public void setValorDocumento(BigDecimal valorDocumento) {
		this.valorDocumento = valorDocumento;
	}

	public BigDecimal getDescontosAbatimentos() {
		return descontosAbatimentos;
	}

	public void setDescontosAbatimentos(BigDecimal descontosAbatimentos) {
		this.descontosAbatimentos = descontosAbatimentos;
	}

	public BigDecimal getOutrasDeducoes() {
		return outrasDeducoes;
	}

	public void setOutrasDeducoes(BigDecimal outrasDeducoes) {
		this.outrasDeducoes = outrasDeducoes;
	}

	public BigDecimal getMoraMulta() {
		return moraMulta;
	}

	public void setMoraMulta(BigDecimal moraMulta) {
		this.moraMulta = moraMulta;
	}

	public BigDecimal getOutrosAcrescimos() {
		return outrosAcrescimos;
	}

	public void setOutrosAcrescimos(BigDecimal outrosAcrescimos) {
		this.outrosAcrescimos = outrosAcrescimos;
	}

	public BigDecimal getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(BigDecimal valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public TipoGuia getTipoGuia() {
		if (tipoGuia == null) {
			tipoGuia = new TipoGuia();
		}
		return tipoGuia;
	}

	public void setTipoGuia(TipoGuia tipoGuia) {
		this.tipoGuia = tipoGuia;
	}

	public SegmentoT getSegmentoT() {
		if (segmentoT == null) {
			segmentoT = new SegmentoT();
		}
		return segmentoT;
	}

	public void setSegmentoT(SegmentoT segmentoT) {
		this.segmentoT = segmentoT;
	}

	public List<GuiaAssistencialObservacao> getObservacoes() {
		if (observacoes == null) {
			observacoes = new ArrayList<GuiaAssistencialObservacao>();
		}
		return observacoes;
	}

	public void setObservacoes(List<GuiaAssistencialObservacao> observacoes) {
		this.observacoes = observacoes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInstrucoesProtocolo() {
		return instrucoesProtocolo;
	}

	public void setInstrucoesProtocolo(String instrucoesProtocolo) {
		this.instrucoesProtocolo = instrucoesProtocolo;
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNossoNumeroAnterior() {
		return nossoNumeroAnterior;
	}

	public void setNossoNumeroAnterior(String nossoNumeroAnterior) {
		this.nossoNumeroAnterior = nossoNumeroAnterior;
	}

	public Boolean getIncluirEscritorio() {
		if (incluirEscritorio == null) {
			incluirEscritorio = false;
		}
		return incluirEscritorio;
	}

	public void setIncluirEscritorio(Boolean incluirEscritorio) {
		this.incluirEscritorio = incluirEscritorio;
	}

	public Integer getStatusRemessa() {
		return statusRemessa;
	}

	public void setStatusRemessa(Integer statusRemessa) {
		this.statusRemessa = statusRemessa;
	}

	public Remessa getRemessa() {
		if (remessa == null) {
			remessa = new Remessa();
		}
		return remessa;
	}

	public void setRemessa(Remessa remessa) {
		this.remessa = remessa;
	}

	public Integer getCodigoMovimento() {
		return codigoMovimento;
	}

	public void setCodigoMovimento(Integer codigoMovimento) {
		this.codigoMovimento = codigoMovimento;
	}

}
