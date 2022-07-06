package br.com.datasind.gerente;

import br.com.datasind.aplicacao.ApplicationException;


public class UsuarioNaoEncontradoException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public UsuarioNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public UsuarioNaoEncontradoException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UsuarioNaoEncontradoException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	public UsuarioNaoEncontradoException() {
		super();
	}

}

