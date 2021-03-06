
package br.com.datasind.entidade;

import java.io.InputStream;
import java.io.Serializable;

/**
 * 
 * @author OsmarJunior
 * @since 16/09/2011
 */
public class Arquivo implements Serializable{

   private static final long serialVersionUID= -3455914492431340372L;

   private InputStream stream;
   private String sufixo;

   public Arquivo() {
	  super();
   }

   public InputStream getStream() {
	  return stream;
   }

   public void setStream(InputStream stream) {
	  this.stream=stream;
   }

   public String getSufixo() {
	  return sufixo;
   }

   public void setSufixo(String sufixo) {
	  this.sufixo=sufixo;
   }

   public static String getSufixoPeloNome(String nome) {
	  if(nome == null) {
		 return null;
	  }
	  return nome.substring(nome.lastIndexOf('.'));
   }
}
