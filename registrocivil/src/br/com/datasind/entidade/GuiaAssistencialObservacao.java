package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "guiaassistencialobservacao")
public class GuiaAssistencialObservacao extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4509934268183043743L;

	@Id
	@GeneratedValue
	private Integer id;
	@Lob
	private String observacao;
	private Date data;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Usuario usuario;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private GuiaAssistencial guiaAssistencial;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public GuiaAssistencial getGuiaAssistencial() {
		if (guiaAssistencial == null) {
			guiaAssistencial = new GuiaAssistencial();
		}
		return guiaAssistencial;
	}

	public void setGuiaAssistencial(GuiaAssistencial guiaAssistencial) {
		this.guiaAssistencial = guiaAssistencial;
	}

}
