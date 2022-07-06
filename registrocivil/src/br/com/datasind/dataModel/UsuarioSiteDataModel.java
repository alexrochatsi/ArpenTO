package br.com.datasind.dataModel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.datasind.entidade.UsuarioSite;

public class UsuarioSiteDataModel extends ListDataModel<UsuarioSite> implements SelectableDataModel<UsuarioSite>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4902413323333963759L;

	public UsuarioSiteDataModel() {
	}

	public UsuarioSiteDataModel(List<UsuarioSite> usuariosSite) {
		super(usuariosSite);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UsuarioSite getRowData(String arg0) {

		List<UsuarioSite> usuariosSite = (List<UsuarioSite>) getWrappedData();

		for (UsuarioSite usuatioIt : usuariosSite) {
			if (usuatioIt.getId().toString().equals(arg0))
				return usuatioIt;
		}

		return null;
	}

	@Override
	public Object getRowKey(UsuarioSite arg0) {
		return arg0.getId();
	}

}