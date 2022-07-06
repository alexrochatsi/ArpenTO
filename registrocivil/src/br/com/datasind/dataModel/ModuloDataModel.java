package br.com.datasind.dataModel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.datasind.entidade.Modulo;

public class ModuloDataModel extends ListDataModel<Modulo> implements SelectableDataModel<Modulo>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3745443916323051283L;

	public ModuloDataModel() {
	}

	public ModuloDataModel(List<Modulo> modulos) {
		super(modulos);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Modulo getRowData(String arg0) {

		List<Modulo> modulos = (List<Modulo>) getWrappedData();

		for (Modulo moduloIt : modulos) {
			if (moduloIt.getId().toString().equals(arg0))
				return moduloIt;
		}

		return null;
	}

	@Override
	public Object getRowKey(Modulo arg0) {
		return arg0.getId();
	}

}
