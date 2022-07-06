package br.com.datasind.cadastro;

public class CadastroException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public CadastroException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CadastroException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CadastroException(String message, Throwable cause) {
		super(message, cause);
	}
}