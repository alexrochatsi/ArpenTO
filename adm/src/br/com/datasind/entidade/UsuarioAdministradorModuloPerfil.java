package br.com.datasind.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="usuarioadministradormoduloperfil")
public class UsuarioAdministradorModuloPerfil extends EntidadePadrao {
	
	@Transient
	private static final long serialVersionUID = 3256999939178639664L;
	@Id
	@GeneratedValue
	private Integer id;
	private Integer flagGestor;
	@ManyToOne (fetch=FetchType.LAZY,  cascade = CascadeType.MERGE )
	private Perfil perfil;

	
	public UsuarioAdministradorModuloPerfil() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public Integer getFlagGestor() {
		return flagGestor;
	}
	
	public Perfil getPerfil() {
		if(perfil == null){
			perfil = new Perfil();
		}
		return perfil;
	}
	
	public void setFlagGestor(Integer flagGestor) {
		this.flagGestor = flagGestor;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	protected void setId(Integer id) {
		this.id = id;
	}
}