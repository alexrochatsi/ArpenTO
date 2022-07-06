package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Email;

@Entity
@Table(name = "usuarioadministrador")
public class UsuarioAdministrador extends EntidadePadrao {

	@Transient
	private static final long serialVersionUID = 134092810403057623L;

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 100, nullable = false)
	private String nome;
	@Column(unique=true)
	private String nomeLogin;
	@Column (length = 60, nullable = false)
	private String senha;
	@Email
	private String email;
	private Boolean flagAtivo;
	private Date dataUltimoLogin;
	private Date dataCadastro;
	private String UID;

	/* Getters e Setters */
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

   public UsuarioAdministrador() {
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

   public String getNomeLogin() {
      return nomeLogin;
   }

   public void setNomeLogin(String nomeLogin) {
      this.nomeLogin=nomeLogin;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email=email;
   }
}