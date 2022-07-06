package br.com.datasind.dataModel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.datasind.entidade.Perfil;

public class PerfilDataModel extends ListDataModel<Perfil> implements SelectableDataModel<Perfil>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1157441751317201830L;

	public PerfilDataModel() {

	}

	public PerfilDataModel(List<Perfil> perfis) {
		super(perfis);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Perfil getRowData(String arg0) {
		List<Perfil> perfis = (List<Perfil>) getWrappedData();

		for (Perfil perfilIt : perfis) {
			if (perfilIt.getId().toString().equals(arg0))
				return perfilIt;
		}

		return null;
	}

	@Override
	public Object getRowKey(Perfil arg0) {
		return arg0.getId();
	}

}
