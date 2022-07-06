package br.com.datasind.validacao;

import java.util.ArrayList;
import java.util.List;

import br.com.datasind.Mensageiro.MensageiroException;

public class ValidacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> messages = new ArrayList<String>();

	private String idUIComponent;

	public ValidacaoException() {
		super();
	}

	public ValidacaoException(String message, Throwable cause) {
		super(message, cause);
		messages.add(message);
		MensageiroException.errorMsg(message);

	}

	public ValidacaoException(String message, String idUIComponent) {
		super(message);
		messages.add(message);
		MensageiroException.errorMsg(message);
		this.idUIComponent = idUIComponent;
	}

	public ValidacaoException(String message) {
		super(message);
		// messages.add(message);
		MensageiroException.errorMsg(message);
	}

	public ValidacaoException(Throwable cause) {
		super(cause);
	}

	public void addMessage(String message) {
		if (!messages.contains(message)) {
			messages.add(message);
			MensageiroException.errorMsg(message);
		}
	}

	public List<String> getMessages() {
		return messages;
	}

	public String getIdUIComponent() {
		return idUIComponent;
	}

	public void setarMessage(String mensagem) {

	}

}
