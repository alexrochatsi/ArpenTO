package br.com.datasind.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name="objetosessao")
public class ObjetoSessao extends EntidadePadrao implements Cloneable {

	

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -6171808142513093032L;

	
	@Id
	@GeneratedValue
	private Integer id;
	private String chave;
	
	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	
	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Object clone() throws CloneNotSupportedException {
		ObjetoSessao cln = new ObjetoSessao();
		cln.setChave(getChave());
		cln.setId(getId());
		return cln;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ObjetoSessao)) {
			return false;
		}

		ObjetoSessao o = (ObjetoSessao) obj;
		return this.chave == null ? o.chave == null : this.chave.equals(o.chave);
	}
	
	@Override
	public int hashCode() {
		return (this.chave == null) ? 0 : this.chave.hashCode();
	}

}
