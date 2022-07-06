package br.com.datasind.controller.sca;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.conversao.util.Codificador;
import br.com.datasind.dataModel.UsuarioDataModel;
import br.com.datasind.entidade.Usuario;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name = "cadastroUsuario")
@ViewScoped
public class CadastroUsuarioController extends CadastroControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1893513995855967747L;

	private Usuario usuario;

	private List<Usuario> listaUsuarios;

	private Usuario usuarioSelecionado;

	private UsuarioDataModel usuarioDM;

	public void acaoIncluir(ActionEvent event) {
		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
		try {

			if ((gerenteControleAcesso.obterUsuarioPorLogin(getUsuario().getLogin()).size() > 0)) {
				mensageiro(WARNING, "Nome de usu�rio j� esxite em nossa base.");
				return;
			}

			getUsuario().setSenha(Codificador.codificaSenhaMD5(Usuario.PRIMEIRA_SENHA));
			gerenteControleAcesso.incluirEntidade(getUsuario());
			// listaUsuarios.add(usuario);
			acaoLimpar();
			setUsuario(null);
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel cadastrar o usuario.");
			LOGGER.error(e);
			return;
		} catch (ValidacaoException e) {
			mensageiro(ERROR, e.getMessage());
			LOGGER.equals(e);
			return;
		}
		mensageiro(INFO, "Usuario cadastrado com sucesso. O usuario devera alterar sua senha ao efetuar o proximo logon no sistema (senha padrao: '" + Usuario.PRIMEIRA_SENHA
				+ "').");
	}

	public void acaoAlterar(ActionEvent event) {
		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

		try {
			Usuario usutemp = new Usuario();
			usutemp = (gerenteControleAcesso.obterUsuarioPorId(usuario));
			getUsuario().setSenha(usutemp.getSenha());
			getUsuario().setDataUltimoLogin(usutemp.getDataUltimoLogin());

			gerenteControleAcesso.atualizarEntidade(getUsuario());
			acaoLimpar();
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel atualizar o cadastro do usuario.");
			LOGGER.error(e);
			acaoLimpar();
			return;
		} catch (ValidacaoException e) {
			mensageiro(WARNING, e.getMessage().toString());
			LOGGER.error(e);
			acaoLimpar();
			return;
		}

		mensageiro(INFO, "Usuario alterado com sucesso.");
	}

	public void acaoExcluir(ActionEvent event) {

		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

		try {
			gerenteControleAcesso.excluirEntidade(getUsuario());
			acaoLimpar();
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel excluir usuario.");
			LOGGER.error(e);
			return;
		}

		mensageiro(INFO, "Usuario excluido com sucesso.");
	}

	/**
	 * Acao Limpar usuarioCadastro.
	 */
	public void acaoLimpar() {

		setUsuario(null);
		setListaUsuarios(null);
		setUsuarioDM(null);
	}

	public void onRowSelect(SelectEvent event) {

		setUsuario((Usuario) event.getObject());
		mensageiro(WARNING, "Usuario " + ((Usuario) event.getObject()).getNomeCompleto());

	}

	public void acaoAtribuirSenha() {

		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

		try {
			Usuario usutemp = new Usuario();
			usutemp = (gerenteControleAcesso.obterUsuarioPorId(usuario));
			getUsuario().setDataUltimoLogin(usutemp.getDataUltimoLogin());
			getUsuario().setSenha(Codificador.codificaSenhaMD5(Usuario.PRIMEIRA_SENHA));
			gerenteControleAcesso.atualizarEntidade(getUsuario());

		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel alterar a senha do usuario.");
			LOGGER.error(e);
			return;

		} catch (ValidacaoException e) {
			mensageiro(WARNING, e.getMessage());
			LOGGER.error(e);
			return;
		}
		acaoLimpar();
		mensageiro(INFO, "Senha alterada com sucesso. O usuario devera alterar sua senha ao efetuar o proximo logon no sistema (senha padrao: '" + Usuario.PRIMEIRA_SENHA + "').");
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setFlagAtivo(Boolean.TRUE);
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() {
		if (listaUsuarios == null) {
			try {
				GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
				listaUsuarios = new ArrayList<Usuario>();

				listaUsuarios = gerenteControleAcesso.obterListaUsuarios();
			} catch (ApplicationException e) {
				mensageiro(ERROR, "Erro ao buscar lista de Usuarios");
			}

		}
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuarioSelecionado() {
		if (usuarioSelecionado == null) {
			usuarioSelecionado = new Usuario();
		}
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public UsuarioDataModel getUsuarioDM() {
		if (usuarioDM == null) {
			usuarioDM = new UsuarioDataModel(getListaUsuarios());
		}
		return usuarioDM;
	}

	public void setUsuarioDM(UsuarioDataModel usuarioDM) {
		this.usuarioDM = usuarioDM;
	}

	public void adicionar(ActionEvent event) {
		System.out.println(usuario.getNomeCompleto());
	}

}
