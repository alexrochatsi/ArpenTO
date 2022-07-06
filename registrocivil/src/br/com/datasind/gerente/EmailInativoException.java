package br.com.datasind.gerente;

import br.com.datasind.aplicacao.ApplicationException;


public class EmailInativoException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public EmailInativoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public EmailInativoException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public EmailInativoException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	public EmailInativoException() {
		super();
	}

}

