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
@Table(name="usuariocartoriomoduloperfil")
public class UsuarioCartorioModuloPerfil extends EntidadePadrao {
	
	@Transient
	private static final long serialVersionUID = 3256999939178639664L;
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne (fetch=FetchType.LAZY,  cascade = CascadeType.MERGE )
	private UsuarioCartorio usuarioCartorio;
	private Integer flagGestor;
	@ManyToOne (fetch=FetchType.LAZY,  cascade = CascadeType.MERGE )
	private Perfil perfil;

	public UsuarioCartorioModuloPerfil() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public UsuarioCartorio getUsuarioCartorio() {
		if(usuarioCartorio == null){
			usuarioCartorio = new UsuarioCartorio();
		}
		return usuarioCartorio;
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

	public void setUsuarioCartorio(UsuarioCartorio usuarioCartorio) {
		this.usuarioCartorio = usuarioCartorio;
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