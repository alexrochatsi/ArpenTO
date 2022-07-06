package br.com.datasind.conversao.util;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Cidade;
import br.com.datasind.gerente.GerenteControleAcesso;

@FacesConverter(value = "cidadeConverter", forClass = Cidade.class)
public class CidadeConverter extends ConverterPadrao implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		try {
			Integer codigo = Integer.valueOf(value);
			GerenteControleAcesso gerente = getFabricaGerente().getGerenteControleAcesso();
			Cidade object = gerente.obterCidadePorId(codigo);
			return object;

		} catch (NumberFormatException ex) {
			return null;

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mensageiro(ERROR, "Erro ao converter cidade");
		}
		return value;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {

		if (value != null && value instanceof Cidade) {

			Cidade entity = (Cidade) value;
			if (entity.getId() == null) {
				return null;
			}

			// adiciona item como atributo do componente
			this.addAttribute(component, entity);

			Integer codigo = entity.getId();
			if (codigo != null) {
				return String.valueOf(codigo);
			}
		}
		return null;
	}

	protected void addAttribute(UIComponent component, Cidade o) {
		String key = o.getId().toString(); // codigo da empresa como chave neste
											// caso
		this.getAttributesFrom(component).put(key, o);
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}

}