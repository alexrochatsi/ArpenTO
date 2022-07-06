package br.com.datasind.dataModel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.datasind.entidade.PermissaoAcesso;

public class PermissaoAcessoDataModel extends ListDataModel<PermissaoAcesso> implements SelectableDataModel<PermissaoAcesso>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2381147319090747707L;

	public PermissaoAcessoDataModel() {
	}

	public PermissaoAcessoDataModel(List<PermissaoAcesso> permissaoAcessos) {
		super(permissaoAcessos);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PermissaoAcesso getRowData(String arg0) {

		List<PermissaoAcesso> permissaoAcessos = (List<PermissaoAcesso>) getWrappedData();

		for (PermissaoAcesso permissaoAcessoIt : permissaoAcessos) {
			if (permissaoAcessoIt.getId().toString().equals(arg0))
				return permissaoAcessoIt;
		}

		return null;
	}

	@Override
	public Object getRowKey(PermissaoAcesso arg0) {
		return arg0.getId();
	}

}
