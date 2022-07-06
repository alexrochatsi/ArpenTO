package br.com.datasind.dataModel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.datasind.entidade.UsuarioCartorio;

public class UsuarioCartorioDataModel extends ListDataModel<UsuarioCartorio> implements SelectableDataModel<UsuarioCartorio>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4902413323333963759L;

	public UsuarioCartorioDataModel() {
	}

	public UsuarioCartorioDataModel(List<UsuarioCartorio> usuariosCartorio) {
		super(usuariosCartorio);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UsuarioCartorio getRowData(String arg0) {

		List<UsuarioCartorio> usuariosCartorio = (List<UsuarioCartorio>) getWrappedData();

		for (UsuarioCartorio usuatioIt : usuariosCartorio) {
			if (usuatioIt.getId().toString().equals(arg0))
				return usuatioIt;
		}

		return null;
	}

	@Override
	public Object getRowKey(UsuarioCartorio arg0) {
		return arg0.getId();
	}

}