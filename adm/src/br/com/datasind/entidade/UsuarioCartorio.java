package br.com.datasind.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Email;

@Entity
@Table(name = "usuariocartorio")
public class UsuarioCartorio extends EntidadePadrao {

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
	private Boolean flagAtivo;
	private Date dataUltimoLogin;
	private Date dataCadastro;
	private String UID;
	@Column(unique=true)
	private String nomeLogin;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Cartorio cartorio;
	@Column(unique = true)
	private String cpf;
	private Date validadeCertificado;

	/* Getters e Setters */
	
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

	public Cartorio getCartorio() {
      return cartorio;
   }

   public void setCartorio(Cartorio cartorio) {
      this.cartorio=cartorio;
   }

   public UsuarioCartorio() {
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

   public String getCpf() {
      return cpf;
   }

   public void setCpf(String cpf) {
      this.cpf=cpf;
   }

   public Date getValidadeCertificado() {
      return validadeCertificado;
   }

   public void setValidadeCertificado(Date validadeCertificado) {
      this.validadeCertificado=validadeCertificado;
   }
}