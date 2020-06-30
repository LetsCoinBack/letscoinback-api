package br.com.model.exception;

import br.com.model.util.Translator;

public class EmailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailException(String message, Throwable cause) {
		super(Translator.toLocale(message), cause);
	}

	public EmailException(String message) {
		super(Translator.toLocale(message));
	}

}