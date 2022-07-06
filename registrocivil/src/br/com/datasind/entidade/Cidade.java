package br.com.datasind.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author osmar
 * @since 01/06/2011
 *
 **/
@Entity
@Table (name="cidade")
public class Cidade extends EntidadePadrao {
	
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -4903756795856631339L;

	@Id
	@GeneratedValue
	private Integer id;
	private String descCidade;
	@ManyToOne (fetch=FetchType.EAGER,  cascade = CascadeType.ALL )
	private UF uf;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescCidade() {
		return descCidade;
	}

	public void setDescCidade(String descCidade) {
		this.descCidade = descCidade;
	}

	public UF getUf() {
		if(uf == null){
			uf = new UF();
		}
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}
	
	
}
