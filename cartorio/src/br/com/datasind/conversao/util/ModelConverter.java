package br.com.datasind.conversao.util;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.datasind.controller.sca.CadastroModuloController;
import br.com.datasind.entidade.Modulo;

@ManagedBean(name = "modelConverter")
@FacesConverter(value = "modelConverter", forClass = Modulo.class)
public class ModelConverter implements Converter {

	private CadastroModuloController cadastro;

	public CadastroModuloController getCadastro() {
		if (cadastro == null) {
			cadastro = new CadastroModuloController();
		}
		return cadastro;
	}

	public void setCadastro(CadastroModuloController cadastro) {
		this.cadastro = cadastro;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		try {
			Integer codigo = Integer.valueOf(value);

			Modulo object = getCadastro().getObterModuloPorId(codigo);
			return object;

		} catch (NumberFormatException ex) {
			return null;

		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {

		if (value != null && !"".equals(value)) {

			Modulo entity = (Modulo) value;

			// adiciona item como atributo do componente

			// this.addAttribute(component, entity);

			Integer codigo = entity.getId();
			if (codigo != null) {
				this.addAttribute(component, entity);
				return String.valueOf(codigo);
			}
		}
		return null;
	}

	protected void addAttribute(UIComponent component, Modulo o) {
		String key = o.getId().toString(); // codigo da empresa como chave neste
											// caso
		this.getAttributesFrom(component).put(key, o);
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}

}