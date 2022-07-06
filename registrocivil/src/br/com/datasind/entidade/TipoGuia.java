package br.com.datasind.entidade;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tipoguia")
public class TipoGuia extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7755097108365160507L;

	@Id
	@GeneratedValue
	private Integer id;
	private String descricao;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private EntidadeBancaria entidadeBancaria;
	@Lob
	private String instrucoesProtocolo;
	private String instrucoes1;
	private String instrucoes2;
	private String instrucoes3;
	private String instrucoes4;
	private String instrucoes5;
	private String localPagamento;
	private BigDecimal multa;
	private BigDecimal juros;
	
	/**** modulo caixa ***/
	@Column(length=10)
	private String tipoCategoria;
	private Boolean status;
	/*se é uma categoria para o modulo caixa*/
	private Boolean isCaixa;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EntidadeBancaria getEntidadeBancaria() {
		if (entidadeBancaria == null) {
			entidadeBancaria = new EntidadeBancaria();
		}
		return entidadeBancaria;
	}

	public void setEntidadeBancaria(EntidadeBancaria entidadeBancaria) {
		this.entidadeBancaria = entidadeBancaria;
	}

	public String getInstrucoesProtocolo() {
		return instrucoesProtocolo;
	}

	public void setInstrucoesProtocolo(String instrucoesProtocolo) {
		this.instrucoesProtocolo = instrucoesProtocolo;
	}

	public String getInstrucoes1() {
		return instrucoes1;
	}

	public void setInstrucoes1(String instrucoes1) {
		this.instrucoes1 = instrucoes1;
	}

	public String getInstrucoes2() {
		return instrucoes2;
	}

	public void setInstrucoes2(String instrucoes2) {
		this.instrucoes2 = instrucoes2;
	}

	public String getInstrucoes3() {
		return instrucoes3;
	}

	public void setInstrucoes3(String instrucoes3) {
		this.instrucoes3 = instrucoes3;
	}

	public String getInstrucoes4() {
		return instrucoes4;
	}

	public void setInstrucoes4(String instrucoes4) {
		this.instrucoes4 = instrucoes4;
	}

	public String getInstrucoes5() {
		return instrucoes5;
	}

	public void setInstrucoes5(String instrucoes5) {
		this.instrucoes5 = instrucoes5;
	}

	public String getLocalPagamento() {
		return localPagamento;
	}

	public void setLocalPagamento(String localPagamento) {
		this.localPagamento = localPagamento;
	}

	public BigDecimal getMulta() {
		if (multa == null || multa.equals(new BigDecimal(0))) {
			multa = new BigDecimal(0.00);
		}
		multa = multa.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	public BigDecimal getJuros() {
		if (juros == null || juros.equals(new BigDecimal(0))) {
			juros = new BigDecimal(0.00);
		}
		juros = juros.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}
	
	public String getTipoCategoria() {
	   return tipoCategoria;
	}
	
	public void setTipoCategoria(String tipoCategoria) {
	   this.tipoCategoria=tipoCategoria;
	}

	public Boolean getStatus() {
	   return status;
	}
	
	public void setStatus(Boolean status) {
	   this.status=status;
	}

   public Boolean getIsCaixa() {
      return isCaixa;
   }

   public void setIsCaixa(Boolean isCaixa) {
      this.isCaixa=isCaixa;
   }
	
}
