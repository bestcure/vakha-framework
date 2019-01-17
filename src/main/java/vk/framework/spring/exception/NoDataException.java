package vk.framework.spring.exception;

public class NoDataException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoDataException() {
	}

	public NoDataException(String message) {
		super(message);
	}

	public NoDataException(Throwable cause) {
		super(cause);
	}

	public NoDataException(String message, Throwable cause) {
		super(message, cause);
	}
}