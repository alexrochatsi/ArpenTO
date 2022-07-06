package br.com.datasind.controller.sca;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.dataModel.ModuloDataModel;
import br.com.datasind.dataModel.PerfilDataModel;
import br.com.datasind.dataModel.PermissaoAcessoDataModel;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Perfil;
import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name = "cadastroPermissaoAcesso")
@ViewScoped
public class CadastroPermissaoAcessoController extends CadastroControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5769511329818350742L;

	private PermissaoAcesso permissaoAcesso;

	private PermissaoAcesso permissaoAcessoSelecionado;

	private List<PermissaoAcesso> listaTodasPermissoesAcessoModulo;

	private List<Modulo> listaModulo;

	private ModuloDataModel moduloDM;

	private Modulo modulo;

	private PerfilDataModel perfilDM;

	private List<Perfil> perfis;

	private PermissaoAcessoDataModel permissaoAcessoDM;

	private Modulo moduloSelecionado;

	public void acaoIncluir(ActionEvent event) {
		GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
		try {

			if (getPermissaoAcesso().getId() == null || getPermissaoAcesso().getId() == 0) {
				gerente.incluirEntidade(getPermissaoAcesso());
				// acaoLimpar();
				PermissaoAcesso temp = getPermissaoAcesso();
				acaoLimpar(null);
				getPermissaoAcesso().setPerfil(temp.getPerfil());
				// setModulo((Modulo)
				// gerenteCadastro.obterEntidadePelaClasse(Modulo.class,
				// temp.getPerfil().getModulo().getId()));
				setModulo(temp.getPerfil().getModulo());
				mensageiro(INFO, "PermissaoAcesso cadastrada com sucesso.");
			} else {
				mensageiro(ERROR, "Nao foi possivel cadastra PermissaoAcesso. Verifique campo descricao!");
			}
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel cadastrar PermissaoAcesso.");
			LOGGER.error(e);
			return;
		} catch (ValidacaoException e) {
			mensageiro(WARNING, e.getMessage());
			LOGGER.equals(e);
			return;
		}

	}

	public void acaoAlterar(ActionEvent event) {
		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

		try {
			PermissaoAcesso atvtemp = new PermissaoAcesso();
			atvtemp = (gerenteControleAcesso.obterPermissaoAcessoPorId(permissaoAcesso));
			getPermissaoAcesso().setId(atvtemp.getId());
			gerenteControleAcesso.atualizarEntidade(getPermissaoAcesso());
			acaoLimpar(null);
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel atualizar a PermissaoAcesso.");
			LOGGER.error(e);
			return;
		} catch (ValidacaoException e) {
			mensageiro(WARNING, e.getMessage());
			LOGGER.error(e);
			return;
		}
		mensageiro(INFO, "Cadastro de PermissaoAcesso alterada com sucesso.");

	}

	public void acaoExcluir(ActionEvent event) {

		GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();

		try {
			gerente.excluirEntidade(getPermissaoAcesso());
			acaoLimpar(null);
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel excluir PermissaoAcesso.");
			LOGGER.error(e);
			return;
		}
		mensageiro(INFO, "PermissaoAcesso excluida com sucesso.");

	}

	public void acaoLimpar(ActionEvent event) {
		setListaModulo(null);
		setListaTodasPermissoesAcessoModulo(null);
		setModulo(null);
		setModuloDM(null);
		setModuloSelecionado(null);
		setPerfilDM(null);
		setPerfis(null);
		setPermissaoAcesso(null);
		setPermissaoAcessoDM(null);
		setPermissaoAcessoSelecionado(null);
	}

	public void onRowSelect(SelectEvent event) {

		setPermissaoAcesso((PermissaoAcesso) event.getObject());
		GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
		try {
			setPermissaoAcesso((PermissaoAcesso) gerente.obterEntidadePelaClasse(PermissaoAcesso.class, permissaoAcesso.getId()));
			gerente.inicializa("inicializadorPermissaoAcesso", getPermissaoAcesso());
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Erro ao buscar Permissao de Acesso");
		}
		mensageiro(WARNING, "PermissaoAcesso " + ((PermissaoAcesso) event.getObject()).getId());

	}

	public PermissaoAcesso getPermissaoAcesso() {
		if (permissaoAcesso == null) {
			permissaoAcesso = new PermissaoAcesso();
		}
		return permissaoAcesso;
	}

	public void setPermissaoAcesso(PermissaoAcesso permissaoAcesso) {
		this.permissaoAcesso = permissaoAcesso;
	}

	public void perfilAlterado(ValueChangeEvent event) {
		Integer id = (Integer) event.getNewValue();
		GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
		Perfil perfil = null;
		try {
			perfil = (Perfil) gerente.obterEntidadePelaClasse(Perfil.class, id);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mensageiro(INFO, "Selecionado " + perfil.getDescricao());
		// System.out.println("/n/n/n/n teste");
	}

	public List<PermissaoAcesso> getListaTodasPermissoesAcessoModulo() {
		if (listaTodasPermissoesAcessoModulo == null && getPermissaoAcesso().getPerfil().getModulo().getId() != null && getPermissaoAcesso().getPerfil().getId() != null) {

			GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

			try {
				listaTodasPermissoesAcessoModulo = gerenteControleAcesso.obterPermissoesAcessoModulo(getPermissaoAcesso().getPerfil());

			} catch (ApplicationException e) {
				mensageiro(ERROR, "Falha ao tentar obter lista de permissoes de acesso do modulo.");
				LOGGER.error(e);
			}
		} else {
			listaTodasPermissoesAcessoModulo = new ArrayList<PermissaoAcesso>();
		}
		return listaTodasPermissoesAcessoModulo;
	}

	public void setListaTodasPermissoesAcessoModulo(List<PermissaoAcesso> listaTodasPermissoesAcessoModulo) {
		this.listaTodasPermissoesAcessoModulo = listaTodasPermissoesAcessoModulo;
	}

	public List<Modulo> getListaModulo() {
		if (listaModulo == null) {
			GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
			listaModulo = new ArrayList<Modulo>();
			try {
				listaModulo = gerente.obterListaModulos();
			} catch (ApplicationException e) {
				mensageiro(ERROR, "Erro ao obter lista de Modulos");
			}

		}
		return listaModulo;
	}

	public void setListaModulo(List<Modulo> listaModulo) {
		this.listaModulo = listaModulo;
	}

	public ModuloDataModel getModuloDM() {
		if (moduloDM == null) {
			moduloDM = new ModuloDataModel(getListaModulo());
		}
		return moduloDM;
	}

	public void setModuloDM(ModuloDataModel moduloDM) {
		this.moduloDM = moduloDM;
	}

	public void onRowModuloSelect(SelectEvent event) {
		setModulo((Modulo) event.getObject());
		setPerfis(null);
		setPerfilDM(null);
		getPermissaoAcesso().setPerfil(null);
		mensageiro(WARNING, "Modulo " + ((Modulo) event.getObject()).getDescricao());

	}

	public Modulo getModulo() {
		if (modulo == null) {
			modulo = new Modulo();
		}
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<Perfil> getPerfis() {
		if (perfis == null && getModulo().getId() != null) {
			GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
			try {
				perfis = gerente.obterListaPerfilPorModulo(getModulo());
			} catch (ApplicationException e) {
				e.printStackTrace();
				mensageiro(ERROR, "Erro ao buscar lista de Perfis");
			}
		}
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public PerfilDataModel getPerfilDM() {
		if (perfilDM == null) {
			perfilDM = new PerfilDataModel(getPerfis());
		}
		return perfilDM;
	}

	public void setPerfilDM(PerfilDataModel perfilDM) {
		this.perfilDM = perfilDM;
	}

	public void onRowPerfilSelect(SelectEvent event) {
		getPermissaoAcesso().setPerfil((Perfil) event.getObject());
		setListaTodasPermissoesAcessoModulo(null);
		setPermissaoAcessoDM(null);
		mensageiro(WARNING, "Perfil " + ((Perfil) event.getObject()).getDescricao());

	}

	public PermissaoAcessoDataModel getPermissaoAcessoDM() {
		if (permissaoAcessoDM == null) {
			permissaoAcessoDM = new PermissaoAcessoDataModel(getListaTodasPermissoesAcessoModulo());
		}
		return permissaoAcessoDM;
	}

	public void setPermissaoAcessoDM(PermissaoAcessoDataModel permissaoAcessoDM) {
		this.permissaoAcessoDM = permissaoAcessoDM;
	}

	public PermissaoAcesso getPermissaoAcessoSelecionado() {
		return permissaoAcessoSelecionado;
	}

	public void setPermissaoAcessoSelecionado(PermissaoAcesso permissaoAcessoSelecionado) {
		this.permissaoAcessoSelecionado = permissaoAcessoSelecionado;
	}

	public Modulo getModuloSelecionado() {
		return moduloSelecionado;
	}

	public void setModuloSelecionado(Modulo moduloSelecionado) {
		this.moduloSelecionado = moduloSelecionado;
	}

}
