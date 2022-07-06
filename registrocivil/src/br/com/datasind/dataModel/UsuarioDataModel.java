package br.com.datasind.dataModel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.datasind.entidade.Usuario;

public class UsuarioDataModel extends ListDataModel<Usuario> implements SelectableDataModel<Usuario>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4902413323333963759L;

	public UsuarioDataModel() {
	}

	public UsuarioDataModel(List<Usuario> usuarios) {
		super(usuarios);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getRowData(String arg0) {

		List<Usuario> usuarios = (List<Usuario>) getWrappedData();

		for (Usuario usuatioIt : usuarios) {
			if (usuatioIt.getId().toString().equals(arg0))
				return usuatioIt;
		}

		return null;
	}

	@Override
	public Object getRowKey(Usuario arg0) {
		return arg0.getId();
	}

}