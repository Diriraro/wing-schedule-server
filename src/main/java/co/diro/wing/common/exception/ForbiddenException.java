package co.diro.wing.common.exception;

public class ForbiddenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5721197713752101561L;

	/**
	 * 
	 */
	public ForbiddenException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ForbiddenException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ForbiddenException(Throwable cause) {
		super(cause);
	}

	
}
