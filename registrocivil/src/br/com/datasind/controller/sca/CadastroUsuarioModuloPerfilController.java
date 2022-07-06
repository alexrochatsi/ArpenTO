package br.com.datasind.controller.sca;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.dataModel.UsuarioDataModel;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Perfil;
import br.com.datasind.entidade.Usuario;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.entidade.UsuarioSiteModuloPerfil;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name = "cadUsuarioModuloPerfil")
@ViewScoped
public class CadastroUsuarioModuloPerfilController extends CadastroControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3733201569292011482L;

	private UsuarioSiteModuloPerfil usuarioModuloPerfil;

	private Usuario usuario;
	
	private UsuarioSite usuarioSite;

	private List<Perfil> listaPerfil;

	private List<UsuarioSiteModuloPerfil> listaUsuarioModuloPerfil;

	private UsuarioDataModel usuarioDM;

	private List<Usuario> listaUsuarios;

	private List<Modulo> modulos;

	private TreeNode root;

	private TreeNode[] selectedNodes;

	public UsuarioSiteModuloPerfil getUsuarioModuloPerfil() {
		if (usuarioModuloPerfil == null) {
			usuarioModuloPerfil = new UsuarioSiteModuloPerfil();
		}
		return usuarioModuloPerfil;
	}

	public void setUsuarioModuloPerfil(UsuarioSiteModuloPerfil usuarioModuloPerfil) {
		this.usuarioModuloPerfil = usuarioModuloPerfil;
	}

	public void moduloAlterado(ValueChangeEvent event) {
		mensageiro(INFO, "Selecionado " + ((Modulo) event.getNewValue()).getDescricao());

	}

	public void onRowSelect(SelectEvent event) {

		setUsuario((Usuario) event.getObject());
		mensageiro(WARNING, "Usuario " + ((Usuario) event.getObject()).getNomeCompleto());

	}

	public void onRowSelectDataTable(SelectEvent event) {

		setUsuarioModuloPerfil((UsuarioSiteModuloPerfil) event.getObject());
		GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
		try {
			setUsuarioModuloPerfil(gerente.obterUsuarioModuloPerfilPorId(usuarioModuloPerfil));
			gerente.inicializa("inicializadorUsuarioModuloPerfil", getUsuarioModuloPerfil());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("");

	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String acaoIncluir(ActionEvent event) {

		// if (getSelectedNodes().length < 1) {
		// mensageiro(WARNING, "Nenhum perfil selecionado");
		// return null;
		// }
		if (getUsuario().getId() == null) {
			mensageiro(WARNING, "Nenhum usuario selecionado");
			return null;
		}
		try {
			GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
			List<Integer> perfisSelecionados = new ArrayList<Integer>();
			List<Integer> perfisPermitidos = new ArrayList<Integer>();

			for (UsuarioSiteModuloPerfil umf : getListaUsuarioModuloPerfil()) {
				// Perfil perfil =
				// gerenteControleAcesso.obterPerfilPorId(umf.getPerfil());
				// gerenteControleAcesso.inicializa("inicializadorPerfil",
				// umf.getPerfil());
				System.out.println(umf.getPerfil().getDescricao());
				perfisPermitidos.add(umf.getPerfil().getId());
			}

			for (TreeNode node : getSelectedNodes()) {
				Perfil perfil = (Perfil) node.getData();
				gerenteControleAcesso.inicializa("inicializadorPerfil", perfil);
				System.out.println(perfil.getId() + " " + perfil.getDescricao());
				perfisSelecionados.add(perfil.getId());
				if (!perfisPermitidos.contains(perfil.getId())) {
					UsuarioSiteModuloPerfil umfTemp = new UsuarioSiteModuloPerfil();
					umfTemp.setUsuarioSite(getUsuarioSite());
					umfTemp.setPerfil(perfil);
					gerenteControleAcesso.incluirEntidade(umfTemp);

				}
			}

			for (Integer perfilId : perfisPermitidos) {
				if (!perfisSelecionados.contains(perfilId)) {
					UsuarioSiteModuloPerfil umf = (UsuarioSiteModuloPerfil) gerenteControleAcesso.obterUsuarioModuloPerfilPorIdUsuarioIdPerfil(getUsuario().getId(), perfilId);
					if (umf != null) {
						gerenteControleAcesso.excluirEntidade(umf);
					}
				}

			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mensageiro(ERROR, "Erro ao alterar permissoes - ApplicationException");
			return CURRENT_PAGE;
		} catch (ValidacaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mensageiro(ERROR, "Erro ao alterar permissoes - Validacao Exception");
			return CURRENT_PAGE;
		}

		acaoLimpar(null);
		mensageiro(INFO, "Permissoes alteradas com sucesso");
		return CURRENT_PAGE;
	}

	public void acaoDeletar() {
		if (usuarioModuloPerfil.getId() == null || "".equals(usuarioModuloPerfil.getId())) {
			mensageiro(WARNING, "Selecione um Perfil para o Usuario");
			return;
		} else {
			GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
			mensageiro(INFO, "Deletado com sucesso!");
			try {
				gerente.excluirEntidade(getUsuarioModuloPerfil());
			} catch (ApplicationException e) {
				mensageiro(ERROR, "Erro ao excluir!");
			}
		}
	}

	public void acaoLimpar(ActionEvent event) {
		setUsuario(null);
		setUsuarioDM(null);
		setUsuarioModuloPerfil(null);
		setListaPerfil(null);
		setListaUsuarioModuloPerfil(null);
		setRoot(null);
		setModulos(null);
		setSelectedNodes(null);
	}

	public Boolean getBuscarPerfil() {
		Boolean value = getBuscarPerfil();
		return value;
	}

	@SuppressWarnings("unchecked")
	public List<Perfil> getListaPerfil() {
		if (listaPerfil == null) {
			listaPerfil = new ArrayList<Perfil>();
			GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
			try {
				listaPerfil = gerente.obterTodos(Perfil.class);
			} catch (ApplicationException e) {
				mensageiro(ERROR, "Erro ao buscar Modulos");
			}
		}
		return listaPerfil;
	}

	public List<Perfil> getListaPerfil(Modulo modulo) {

		listaPerfil = new ArrayList<Perfil>();
		GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
		try {
			listaPerfil = gerente.obterListaPerfilPorModulo(modulo);
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Erro ao buscar Modulos");
		}

		return listaPerfil;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public List<UsuarioSiteModuloPerfil> getListaUsuarioModuloPerfil() {
		if (getUsuario().getId() != null) {
			listaUsuarioModuloPerfil = new ArrayList<UsuarioSiteModuloPerfil>();
			GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
			try {
				listaUsuarioModuloPerfil = gerenteControleAcesso.obterListaPerfilPorUsuario(getUsuario());
			} catch (ApplicationException e) {
				mensageiro(ERROR, "Erro ao Listar Modulo/Perfil");
			}
		}
		return listaUsuarioModuloPerfil;
	}

	public void setListaUsuarioModuloPerfil(List<UsuarioSiteModuloPerfil> listaUsuarioModuloPerfil) {
		this.listaUsuarioModuloPerfil = listaUsuarioModuloPerfil;
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

	public TreeNode getRoot() {
		if (getUsuario().getId() != null) {
			root = new DefaultTreeNode("Root", null);
			for (Modulo modulo : getModulos()) {
				TreeNode node0 = new DefaultTreeNode(modulo, root);

				for (Perfil perfil : getListaPerfil(modulo)) {
					TreeNode node01 = new DefaultTreeNode(perfil, node0);
					for (UsuarioSiteModuloPerfil umf : getListaUsuarioModuloPerfil()) {
						if (perfil.getId() == umf.getPerfil().getId()) {
							node01.setSelected(true);
						}
					}
				}

				// TreeNode node01 = new DefaultTreeNode("Teste1", node0);
				// TreeNode node02 = new DefaultTreeNode("Teste2", node0);

				// if (getModulosPermitidos().contains(modulo)) {
				// // node0.setSelected(true);
				// node0.setExpanded(true);
				// }

			}
		} else {
			root = new DefaultTreeNode("Root", null);
			for (Modulo modulo : getModulos()) {
				TreeNode node0 = new DefaultTreeNode(modulo, root);

				for (Perfil perfil : getListaPerfil(modulo)) {
					@SuppressWarnings("unused")
					TreeNode node01 = new DefaultTreeNode(perfil, node0);

				}

			}

		}
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	@SuppressWarnings("unchecked")
	public List<Modulo> getModulos() {

		GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
		try {
			modulos = gerente.obterTodos(Modulo.class);
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Erro ao buscar Modulos");
		}

		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	public UsuarioSite getUsuarioSite() {
		return usuarioSite;
	}

	public void setUsuarioSite(UsuarioSite usuarioSite) {
		this.usuarioSite = usuarioSite;
	}

}
