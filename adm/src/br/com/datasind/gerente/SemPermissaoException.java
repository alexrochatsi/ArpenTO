package br.com.datasind.gerente;

import br.com.datasind.cadastro.CadastroException;

/**
 * 
 * @author OsmarJunior
 * @since 16/05/2014
 */

public class SemPermissaoException extends CadastroException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7272396045210964375L;

	/**
	 * @param message
	 * @param cause
	 */
	public SemPermissaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SemPermissaoException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SemPermissaoException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	// public SemPermissaoException() {
	// super();
	// }

}
