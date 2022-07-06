package br.com.datasind.teste;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Cidade;
import br.com.datasind.entidade.UF;
import br.com.datasind.gerente.GerenteControleAcesso;

@ManagedBean(name = "ufCidade")
@SessionScoped
public class TesteCidadeEstado extends CadastroControler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UF uf;

	private Cidade cidade;

	private List<UF> ufs;

	private List<Cidade> cidades;

	public UF getUf() {
		if (uf == null) {
			uf = new UF();
		}
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public Cidade getCidade() {
		if (cidade == null) {
			cidade = new Cidade();
		}
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@SuppressWarnings("unchecked")
   public List<UF> getUfs() {
		if (ufs == null) {
			GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
			try {
				ufs = gerente.obterTodos(UF.class);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
		return ufs;
	}

	public void setUfs(List<UF> ufs) {
		this.ufs = ufs;
	}

	public List<Cidade> getCidades() {
		if (cidades == null) {
			cidades = new ArrayList<Cidade>();
			if (getUf().getId() != null) {
				GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
				try {
					cidades = gerente.obterCidadesPorUF(getUf());
					// for (Cidade cidade : cidades) {
					// setCidade(cidade);
					// return cidades;
					// }
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public void handleCityChange(AjaxBehaviorEvent event) {
		@SuppressWarnings("unused")
	  String idEstado = event.getComponent().getAttributes().get("value").toString();
		setCidade(null);
		setCidades(null);
		if (uf != null && !uf.getId().equals("")) {
			System.out.println(getUf().getId());
		}

	}

}
