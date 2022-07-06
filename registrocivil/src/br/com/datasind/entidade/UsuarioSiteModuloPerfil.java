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
@Table(name="usuariositemoduloperfil")
public class UsuarioSiteModuloPerfil extends EntidadePadrao {
	
	@Transient
	private static final long serialVersionUID = 3256999939178639664L;
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne (fetch=FetchType.LAZY,  cascade = CascadeType.MERGE )
	private UsuarioSite usuarioSite;
	private Integer flagGestor;
	@ManyToOne (fetch=FetchType.LAZY,  cascade = CascadeType.MERGE )
	private Perfil perfil;

	public UsuarioSiteModuloPerfil() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public UsuarioSite getUsuarioSite() {
		if(usuarioSite == null){
			usuarioSite = new UsuarioSite();
		}
		return usuarioSite;
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

	public void setUsuarioSite(UsuarioSite usuarioSite) {
		this.usuarioSite = usuarioSite;
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