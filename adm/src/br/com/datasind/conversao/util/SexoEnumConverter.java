package br.com.datasind.conversao.util;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import br.com.datasind.entidade.SexoEnum;

@FacesConverter(value="sexoEnumConverter")
public class SexoEnumConverter extends EnumConverter {
   public SexoEnumConverter() {
      super(SexoEnum.class);
  }
}
