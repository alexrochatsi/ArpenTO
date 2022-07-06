package br.com.datasind.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.conversao.util.Codificador;
import br.com.datasind.entidade.Usuario;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

/**
 * 
 * @author OsmarJunior
 * @since 23/07/2011
 */
@ManagedBean(name = "alterarSenha")
@RequestScoped
public class AlterarSenhaControler extends CadastroControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8450412054665500601L;

	private Usuario usuarioSenha;

	private String senhaConfirmacao;

	private String senhaNova;

	private String senhaAtual;

	public AlterarSenhaControler() {
		super();
	}

	public String getSenhaConfirmacao() {
		if (senhaConfirmacao == null) {
			senhaConfirmacao = new String();
		}
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public String getSenhaNova() {
		if (senhaNova == null) {
			senhaNova = new String();
		}
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getSenhaAtual() {
		if (senhaAtual == null) {
			senhaAtual = new String();
		}
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public boolean verificaSenhasIguais(String senha) {
		return getSenhaNova().toString().equals(senha) ? true : false;
	}

	public Usuario getUsuarioSenha() {
		if (usuarioSenha == null) {
			usuarioSenha = getUsuarioSessao();
		}
		return usuarioSenha;
	}

	public void setUsuarioSenha(Usuario usuarioSenha) {
		this.usuarioSenha = usuarioSenha;
	}

	public String enviarAction(ActionEvent event) throws ValidacaoException, ApplicationException {

		if (getSenhaAtual().equalsIgnoreCase("")) {
			mensageiro(ERROR, "O campo 'SENHA ATUAL' deve ser preenchido.");
			return CURRENT_PAGE;
		}

		// checa se campos foram preenchidos
		if (getSenhaConfirmacao().equalsIgnoreCase("") || getSenhaNova().equalsIgnoreCase("")) {
			mensageiro(ERROR, "Os campos 'NOVA SENHA' e 'CONFIRMACAO SENHA' devem ser preenchidos.");
			return CURRENT_PAGE;
		}
		// checa se campos tem o tamanho minimo
		if (getSenhaNova().length() < Usuario.TAMANHO_MINIMO_SENHA) {
			mensageiro(ERROR, "O campo 'NOVA SENHA' deve ter no minimo " + Usuario.TAMANHO_MINIMO_SENHA + " caracteres.");
			return CURRENT_PAGE;
		}
		// checa se senha nova e de confirmacao sao identicas
		if (!verificaSenhasIguais(getSenhaConfirmacao().toString())) {
			mensageiro(ERROR, "Os campos 'NOVA SENHA' e 'CONFIRMACAO SENHA' devem ser iguais.");
			return CURRENT_PAGE;
		}
		// checa se senha atual(+MD5) e a mesma do usuario da sessao
		if (!getUsuarioSessao().getSenha().equalsIgnoreCase(Codificador.codificaSenhaMD5(getSenhaAtual()))) {
			mensageiro(ERROR, "Senha atual nao confere.");
			return CURRENT_PAGE;
		}
		// senha nova nao pode ser a mesma da atual
		if (verificaSenhasIguais(getSenhaAtual().toString())) {
			mensageiro(ERROR, "Os campos 'SENHA ATUAL' e 'NOVA SENHA' devem ser diferentes.");
			return CURRENT_PAGE;
		}

		// caso a validacao seja ok, atualizar entidade usuario com senha
		// confirmada(+MD5)

		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

		try {

			getUsuarioSenha().setSenha(Codificador.codificaSenhaMD5(getSenhaConfirmacao().toString()));
			gerenteControleAcesso.atualizarEntidade(getUsuarioSenha());

		} catch (ApplicationException e) {
			mensageiro(WARNING, "Nao foi possivel atualizar a senha do usuario.");
			LOGGER.error(e);
			return CURRENT_PAGE;
		}

		// addMessage(INFO_MESSAGE, "Senha alterada com sucesso.");
		return CURRENT_PAGE;

	}

	/**
	 * Metodo Botao Cancelar o envio do arquivo
	 * 
	 */
	public String cancelarAction() {
		return "cancelar";
	}

}
