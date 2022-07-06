package br.com.datasind.controller.sca;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.dataModel.PerfilDataModel;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Perfil;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name = "cadastroPerfil")
@ViewScoped
public class CadastroPerfilController extends CadastroControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5769511329818350742L;

	private Perfil perfil;

	private Perfil perfilSelecionado;

	private SelectItem moduloSelect;

	private List<SelectItem> listaModuloSelect;

	private List<Perfil> listaPerfis;

	private PerfilDataModel perfilDM;

	public void acaoIncluir(ActionEvent event) {
		GerenteControleAcesso gerenteCadastro = getFabricaGerente().getGerenteControleAcesso();
		try {
			mensageiro(INFO, "Preparando. ");
			if (getPerfil().getModulo().getId() == null || "".equals(getPerfil().getModulo().getId())) {
				mensageiro(ERROR, "Nao foi possivel cadastra perfil. Selecione um Modulo");
				listaPerfis = null;
				return;
			}

			if (getPerfil().getDescricao() != null && !"".equals(getPerfil().getDescricao())) {
				gerenteCadastro.incluirEntidade(getPerfil());
				setPerfil(null);
			} else {
				mensageiro(ERROR, "Nao foi possivel cadastra perfil. Verifique campo descricao!");
				listaPerfis = null;
				return;
			}
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel cadastrar perfil.");
			LOGGER.error(e);
			return;
		} catch (ValidacaoException e) {
			mensageiro(WARNING, e.getMessage());
			LOGGER.equals(e);
			return;
		}
		mensageiro(INFO, "Perfil cadastrada com sucesso.");

	}

	public void acaoAlterar(ActionEvent event) {
		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

		try {
			if (getPerfil().getId() == null || (getPerfil().getId() == 0)) {
				mensageiro(WARNING, "Selecione um perfil!");
				return;
			}
			// Perfil temp = new Perfil();
			// temp = (gerenteControleAcesso.obterPerfilPorId(perfil));
			// getPerfil().setDescricao(temp.getDescricao());
			gerenteControleAcesso.atualizarEntidade(getPerfil());
			acaoLimpar();
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel atualizar a perfil.");
			LOGGER.error(e);
			return;
		} catch (ValidacaoException e) {
			mensageiro(WARNING, e.getMessage());
			LOGGER.error(e);
			return;
		}
		mensageiro(INFO, "Cadastro de perfil alterada com sucesso.");

	}

	public void acaoExcluir(ActionEvent event) {

		GerenteControleAcesso gerenteCadastro = getFabricaGerente().getGerenteControleAcesso();

		try {
			gerenteCadastro.excluirEntidade(getPerfil());
			acaoLimpar();
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel excluir perfil.");
			LOGGER.error(e);
			return;
		}
		mensageiro(INFO, "Perfil excluido com sucesso.");

	}

	public void acaoBuscar() {
		listaPerfis = null;
	}

	public void acaoLimpar() {
		setPerfil(null);
		setListaperfils(null);
		setListaModuloSelect(null);
		setPerfilDM(null);

	}

	public void onRowSelect(SelectEvent event) {

		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

		setPerfil((Perfil) event.getObject());

		// System.out.println(getPerfil().getModulo().getDescricao());
		try {
			// FacesContext context = FacesContext.getCurrentInstance();
			// context.getApplication().
			getPerfil().setModulo(gerenteControleAcesso.obterModuloPorId(getPerfil().getModulo().getId()));

		} catch (ApplicationException e) {
			mensageiro(ERROR, "Erro ao atualizar Modulo");
		}
		mensageiro(WARNING, "perfil " + ((Perfil) event.getObject()).getDescricao());

	}

	public Perfil getPerfil() {
		if (perfil == null) {
			perfil = new Perfil();
		}
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@SuppressWarnings("unchecked")
	public List<Perfil> getListaPerfis() {

		if (listaPerfis == null) {

			listaPerfis = new ArrayList<Perfil>();
			GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
			try {
				listaPerfis = gerenteControleAcesso.obterTodos(Perfil.class);

			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
		return listaPerfis;
	}

	public void setListaperfils(List<Perfil> listaperfils) {
		this.listaPerfis = listaperfils;
	}

	public Perfil getPerfilSelecionado() {
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}

	public List<SelectItem> getListaModuloSelect() throws Exception {

		if (listaModuloSelect == null) {
			listaModuloSelect = new ArrayList<SelectItem>();
			List<Modulo> modulos = new ArrayList<Modulo>();
			GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
			try {
				modulos = gerenteControleAcesso.obterModulos();
			} catch (ApplicationException e) {
				mensageiro(ERROR, "Erro ao listar Modulos");
			}
			this.listaModuloSelect.add(new SelectItem(null, "Selecione"));
			for (Modulo modulo : modulos) {
				listaModuloSelect.add(new SelectItem(modulo, modulo.getDescricao()));
			}
		}

		return listaModuloSelect;
	}

	public void setListaModuloSelect(List<SelectItem> listaModuloSelect) {
		this.listaModuloSelect = listaModuloSelect;
	}

	public SelectItem getModuloSelect() {
		if (moduloSelect == null) {
			this.moduloSelect = new SelectItem();
		}
		return moduloSelect;
	}

	public void setModuloSelect(SelectItem moduloSelect) {
		this.moduloSelect = moduloSelect;
	}

	public void moduloAlterado(ValueChangeEvent event) {
		if (event.getNewValue() != null) {
			mensageiro(INFO, "Selecionado " + ((Modulo) event.getNewValue()).getDescricao());
		}
	}

	public Perfil getObterPerfilPorId(Integer id) throws ValidacaoException {
		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
		Perfil atvtemp = new Perfil();
		try {

			atvtemp = (gerenteControleAcesso.obterPerfilPorId(id));
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel localizar.");
			LOGGER.error(e);
			return atvtemp;
		}
		return atvtemp;
	}

	public PerfilDataModel getPerfilDM() {
		if (perfilDM == null) {
			perfilDM = new PerfilDataModel(getListaPerfis());
		}

		return perfilDM;
	}

	public void setPerfilDM(PerfilDataModel perfilDM) {
		this.perfilDM = perfilDM;
	}

}
