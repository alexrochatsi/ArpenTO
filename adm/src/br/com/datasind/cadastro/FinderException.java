package br.com.datasind.cadastro;



public class FinderException extends CadastroException {

	private static final long serialVersionUID = 3257570585596015673L;

	/**
	 * @param message
	 */
	public FinderException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FinderException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FinderException(String message, Throwable cause) {
		super(message, cause);
	}
}

