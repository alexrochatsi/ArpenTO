package br.com.datasind.util;
/**
 * @Author osmar
 * @since 15/02/2012
 *
 **/
public class FormatoInadequadoException extends Exception{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5628868078159441761L;
	
	
	public FormatoInadequadoException(){
		super();
	 }
	 public FormatoInadequadoException(String msg){
	        super(msg);
	 }
	 
	 public FormatoInadequadoException(String message, Throwable cause) {
			super(message, cause);
		}
}
