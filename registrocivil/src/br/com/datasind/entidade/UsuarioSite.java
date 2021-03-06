package br.com.datasind.entidade;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Email;

@Entity
@Table(name = "usuariosite")
public class UsuarioSite extends EntidadePadrao {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 134092810403057623L;

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 100, nullable = false)
	private String nome;
	@Email
	@Column(length = 150, nullable = false)
	private String email;
	@Column (length = 60, nullable = false)
	private String senha;
	private boolean perfilAtivo;
	private Boolean flagAtivo;
	private Date dataUltimoLogin;
	private Date dataCadastro;
	private Boolean validaEmail;
	@Column(unique=true)
	private String UID;
	@OneToOne
	private UsuarioSitePerfil usuarioSitePerfil;
	@OneToMany(mappedBy = "usuarioSite", targetEntity = CertidaoPedido.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<CertidaoPedido> certidaoPedidos;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public UsuarioSite() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
		boolean foundLowerCase = false;
		for (int i = 0; i < senha.length() && !foundLowerCase; i++) {
			if (!Character.isUpperCase(senha.charAt(i))) {
				foundLowerCase = true;
			}
		}

		if (!foundLowerCase) {
			System.out.println(senha);
		}
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Date getDataUltimoLogin() {
		return dataUltimoLogin;
	}

	public void setDataUltimoLogin(Date dataUltimoLogin) {
		this.dataUltimoLogin = dataUltimoLogin;
	}

	public Boolean getValidaEmail() {
		return validaEmail;
	}

	public void setValidaEmail(Boolean validaEmail) {
		this.validaEmail = validaEmail;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

   public boolean isPerfilAtivo() {
      return perfilAtivo;
   }

   public void setPerfilAtivo(boolean perfilAtivo) {
      this.perfilAtivo=perfilAtivo;
   }

   public UsuarioSitePerfil getUsuarioSitePerfil() {
      return usuarioSitePerfil;
   }

   public void setUsuarioSitePerfil(UsuarioSitePerfil usuarioSitePerfil) {
      this.usuarioSitePerfil=usuarioSitePerfil;
   }

   public List<CertidaoPedido> getCertidaoPedidos() {
      return certidaoPedidos;
   }

   public void setCertidaoPedidos(List<CertidaoPedido> certidaoPedidos) {
      this.certidaoPedidos=certidaoPedidos;
   }

}