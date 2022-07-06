package br.com.datasind.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author Alex_Rocha
 * @since 16/09/2016
 *
 **/
@Entity
@Table (name="banco")
public class Banco extends EntidadePadrao{
   
	private static final long serialVersionUID = -3307611524635565529L;
	@Id
	@GeneratedValue
	private Integer id;
    private String nome;
    private Integer codigo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
}
