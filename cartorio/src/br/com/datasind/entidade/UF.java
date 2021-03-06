package br.com.datasind.entidade;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

/**
 * @Author osmar
 * @since 01/06/2011
 *
 **/
@Entity
@Table (name="uf")
public class UF extends EntidadePadrao{
	
	
	
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -3039858524024581887L;

	@Id
	@GeneratedValue
	private Integer id;
	private String descUF;
	private String siglaUF;
	@OneToMany(mappedBy="uf", fetch = FetchType.LAZY)
	@Cascade (org.hibernate.annotations.CascadeType.ALL)
	private List<Cidade> listaCidades;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescUF() {
		return descUF;
	}

	public void setDescUF(String descUF) {
		this.descUF = descUF;
	}

	public String getSiglaUF() {
		return siglaUF;
	}

	public void setSiglaUF(String siglaUF) {
		this.siglaUF = siglaUF;
	}

	public List<Cidade> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Cidade> listaCidades) {
		this.listaCidades = listaCidades;
	}
	
	
}
