package br.com.datasind.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author alex
 * @since 04/07/2016
 *
 **/
@Entity
@Table(name = "ibgeuf")
public class IbgeUF extends EntidadePadrao {

	private static final long serialVersionUID = -3365744760972456235L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(length=2)
	private String uf;
	
	@Column(length=50, name="nomeuf")
	private String nomeUF;
	
	@Column(name="codigomunicipio", length=7)
	private String codigoMunicipio;

	@Column(name="nomemunicipio")
	private String nomeMunicipio;
	
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNomeUF() {
		return nomeUF;
	}

	public void setNomeUF(String nomeUF) {
		this.nomeUF = nomeUF;
	}

	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
