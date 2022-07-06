package br.com.datasind.gerente;

import br.com.datasind.aplicacao.ApplicationException;



public class SenhaInvalidaException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public SenhaInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SenhaInvalidaException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SenhaInvalidaException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	public SenhaInvalidaException() {
		super();
	}

}
