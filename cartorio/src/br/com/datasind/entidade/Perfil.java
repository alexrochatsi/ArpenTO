package br.com.datasind.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

@Entity
@Table(name = "perfil")
public class Perfil extends EntidadePadrao {

	@Transient
	private static Logger LOGGER = Logger.getLogger(Perfil.class);

	@Transient
	private static final long serialVersionUID = 3258689897106520113L;

	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Modulo modulo;
	private String descricao;

	public Perfil() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public Modulo getModulo() {
		if (modulo == null) {
			modulo = new Modulo();
		}
		return modulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
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
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Object clone() throws CloneNotSupportedException {
		Perfil perfil = new Perfil();
		perfil.setId(getId());
		perfil.setDescricao(getDescricao());
		perfil.setModulo(getModulo());
		return perfil;
	}

}