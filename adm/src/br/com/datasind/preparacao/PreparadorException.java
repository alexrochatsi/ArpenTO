package br.com.datasind.preparacao;

public class PreparadorException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PreparadorException() {
		super();
	}

	/**
	 * @param message
	 */
	public PreparadorException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PreparadorException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PreparadorException(String message, Throwable cause) {
		super(message, cause);
	}

}
