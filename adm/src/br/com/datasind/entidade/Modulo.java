package br.com.datasind.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "modulo")
public class Modulo extends EntidadePadrao {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -9155912684665278717L;

	@Id
	@GeneratedValue
	private Integer id;
	private String descricao;
	private String nomeInterno;

	public Modulo() {
		super();
	}

	public Modulo(Integer id, String descricao, String nomeInterno) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.nomeInterno = nomeInterno;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Modulo other = (Modulo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getNomeInterno() {
		return nomeInterno;
	}

	public void setNomeInterno(String nomeInterno) {
		this.nomeInterno = nomeInterno;
	}

	/*
	 * protected void setId(Integer id) { this.id = id; }
	 */

	public void setId(Integer id) {
		this.id = id;
	}

	public Object clone() throws CloneNotSupportedException {
		Modulo modulo = new Modulo();
		modulo.setDescricao(getDescricao());
		modulo.setNomeInterno(getNomeInterno());
		modulo.setId(getId());

		return modulo;
	}

	@Override
	public String toString() {
		return "[Id=" + getId() + ", Descricao=" + getDescricao()
				+ ", NomeInterno=" + getNomeInterno() + "]";
	}
}
