package br.com.datasind.aplicacao;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
//		Throwable lastCause = cause;
//		while(lastCause.getCause() != lastCause) {
//			lastCause = lastCause.getCause();
//		}
//		if(cause.getMessage().indexOf("ORA-02292") > -1) {
//			
//		}
	}

	public ApplicationException() {
		super();
	}

}

