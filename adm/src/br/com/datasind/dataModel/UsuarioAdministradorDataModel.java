package br.com.datasind.dataModel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.datasind.entidade.UsuarioAdministrador;

public class UsuarioAdministradorDataModel extends ListDataModel<UsuarioAdministrador> implements SelectableDataModel<UsuarioAdministrador>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4902413323333963759L;

	public UsuarioAdministradorDataModel() {
	}

	public UsuarioAdministradorDataModel(List<UsuarioAdministrador> usuariosAdministrador) {
		super(usuariosAdministrador);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UsuarioAdministrador getRowData(String arg0) {

		List<UsuarioAdministrador> usuariosAdministrador = (List<UsuarioAdministrador>) getWrappedData();

		for (UsuarioAdministrador usuatioIt : usuariosAdministrador) {
			if (usuatioIt.getId().toString().equals(arg0))
				return usuatioIt;
		}

		return null;
	}

	@Override
	public Object getRowKey(UsuarioAdministrador arg0) {
		return arg0.getId();
	}

}