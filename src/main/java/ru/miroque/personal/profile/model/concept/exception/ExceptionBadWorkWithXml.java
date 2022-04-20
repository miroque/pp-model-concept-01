package ru.miroque.personal.profile.model.concept.exception;

public class ExceptionBadWorkWithXml extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionBadWorkWithXml() {
	}

	public ExceptionBadWorkWithXml(String message) {
		super(message);
	}

	public ExceptionBadWorkWithXml(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceptionBadWorkWithXml(Throwable cause) {
		super(cause);
	}

	public ExceptionBadWorkWithXml(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
