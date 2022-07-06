package br.com.datasind.gerente;

import br.com.datasind.aplicacao.ApplicationException;



public class PrimeiraSenhaException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public PrimeiraSenhaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public PrimeiraSenhaException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PrimeiraSenhaException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	public PrimeiraSenhaException() {
		super();
	}

}
