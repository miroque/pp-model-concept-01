package ru.miroque.personal.profile.model.concept.exception;

public class ExceptionNotPersisted extends Exception {
	private static final long serialVersionUID = 1L;

	public ExceptionNotPersisted() {
		super();
	}

	public ExceptionNotPersisted(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExceptionNotPersisted(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceptionNotPersisted(String message) {
		super(message);
	}

	public ExceptionNotPersisted(Throwable cause) {
		super(cause);
	}

	
}
