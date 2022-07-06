
package br.com.datasind.conversao.util;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("termoConverter")
public class TermoConverter extends ConverterPadrao implements Converter{

   @Override
   public Object getAsObject(FacesContext arg0, UIComponent arg1, String termo) {
	  if(termo.trim().equals("")) {
		 return null;
	  } else {
		 termo=termo.replace("_" , "");
		 DecimalFormat df=new DecimalFormat("0000000");
		 termo = df.format(Integer.parseInt(termo.toString()));
		 return termo;
	  }
   }

   @Override
   public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
	  return null;
   }

}
