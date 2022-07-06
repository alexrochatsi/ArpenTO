package br.com.datasind.conversao.util;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import br.com.datasind.entidade.TipoDocumentoEnum;

@FacesConverter(value="tipoDocumentoEnumConverter")
public class TipoDocumentoEnumConverter extends EnumConverter {
   public TipoDocumentoEnumConverter() {
      super(TipoDocumentoEnum.class);
  }
}
