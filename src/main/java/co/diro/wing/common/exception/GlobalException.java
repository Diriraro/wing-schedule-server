package co.diro.wing.common.exception;

public class GlobalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5721197713752101561L;

	/**
	 * 
	 */
	public GlobalException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GlobalException(String message, Throwable cause) {
		super(message, cause);
		cause.printStackTrace();
	}

	/**
	 * @param message
	 */
	public GlobalException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public GlobalException(Throwable cause) {
		super(cause);
		cause.printStackTrace();
	}

	
}
