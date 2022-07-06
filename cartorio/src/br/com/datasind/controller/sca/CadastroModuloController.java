package br.com.datasind.controller.sca;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.gerente.GerenteControleAcesso;
import br.com.datasind.validacao.ValidacaoException;

@ManagedBean(name = "cadastroModulo")
@ViewScoped
public class CadastroModuloController extends CadastroControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5769511329818350742L;

	private Modulo modulo;

	private List<Modulo> listaModulos;

	private Modulo moduloSelecionado;

	public void acaoIncluir() {
		GerenteControleAcesso gerenteCadastro = getFabricaGerente().getGerenteControleAcesso();
		try {

			if (getModulo().getDescricao() != null) {
				gerenteCadastro.incluirEntidade(getModulo());
				// acaoLimpar();
				setModulo(null);
			} else {
				mensageiro(ERROR, "Nao foi possivel cadastra Atividade. Verifique campo descricao!");
			}
		} catch (ApplicationException e) {
			addMessage(ERROR_MESSAGE, "Nao foi possivel cadastrar Atividade.");
			LOGGER.error(e);
			return;
		} catch (ValidacaoException e) {
			addMessage(WARNING_MESSAGE, e.getMessage());
			LOGGER.equals(e);
			return;
		}
		mensageiro(INFO, "Atividade cadastrada com sucesso.");
		addMessage(INFO_MESSAGE, "Atividade cadastrada com sucesso.");
	}

	public void acaoAlterar() {
		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();

		try {
			Modulo atvtemp = new Modulo();
			atvtemp = (gerenteControleAcesso.obterModuloPorId(modulo));
			getModulo().setDescricao(atvtemp.getDescricao());
			gerenteControleAcesso.atualizarEntidade(getModulo());
			acaoLimpar();
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel atualizar a Atividade.");
			addMessage(ERROR_MESSAGE, "Nao foi possivel atualizar a Atividade.");
			LOGGER.error(e);
			return;
		} catch (ValidacaoException e) {
			mensageiro(WARNING, e.getMessage());
			addMessage(WARNING_MESSAGE, e.getMessage());
			LOGGER.error(e);
			return;
		}
		mensageiro(INFO, "Cadastro de Atividade alterada com sucesso.");
		addMessage(INFO_MESSAGE, "Cadastro de Atividade alterada com sucesso.");
	}

	public Modulo getObterModuloPorId(Integer id) {
		GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
		Modulo atvtemp = new Modulo();
		try {

			atvtemp = (gerenteControleAcesso.obterModuloPorId(id));
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel localizar.");
			LOGGER.error(e);
			return atvtemp;
		}
		return atvtemp;
	}

	public void acaoExcluir() {

		GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();

		try {
			gerente.excluirEntidade(getModulo());
			acaoLimpar();
		} catch (ApplicationException e) {
			mensageiro(ERROR, "Nao foi possivel excluir Atividade.");
			addMessage(ERROR_MESSAGE, "Nao foi possivel excluir Atividade.");
			LOGGER.error(e);
			return;
		}
		mensageiro(INFO, "Atividade excluida com sucesso.");
		addMessage(INFO_MESSAGE, "Atividade excluida com sucesso.");
	}

	public void acaoLimpar() {

		setModulo(null);
		// setListaAtividades(null);

	}

	public void onRowSelect(SelectEvent event) {

		setModulo((Modulo) event.getObject());
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

	public List<Modulo> getListaModulos() {
		if (listaModulos == null) {
			try {
				GerenteControleAcesso gerenteControleAcesso = getFabricaGerente().getGerenteControleAcesso();
				listaModulos = new ArrayList<Modulo>();

				listaModulos = gerenteControleAcesso.obterModulos();
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
		return listaModulos;
	}

	public void setListaModulos(List<Modulo> listaModulos) {
		this.listaModulos = listaModulos;
	}

	public Modulo getModuloSelecionado() {
		if (moduloSelecionado == null) {
			moduloSelecionado = new Modulo();
		}
		return moduloSelecionado;
	}

	public void setModuloSelecionado(Modulo moduloSelecionado) {
		this.moduloSelecionado = moduloSelecionado;
	}

}
