package com.mindtree.taskmanagement.exceptions;

/**
 * Used for exception implementation.
 * 
 * @author M1033511
 *
 */
public class BaseException extends Exception {

	/**
	 * declaring serialVersionUID.
	 */
	private static final long serialVersionUID = -6173445095797188369L;

	/**
	 * the error code to be displayed.
	 */
	private String errorCode;
	/**
	 * The error description to be be displayed.
	 */
	private String errorDesc;

	/* Constructors */
	/**
	 * Default constructor for the Domain Exception.
	 * 
	 */
	public BaseException() {
	}

	/**
	 * @param errorCode
	 *            - error code id for specific error
	 * @param errorDesc
	 *            - error description for specific error
	 * @param httpStatus
	 *            - httpstatus
	 * @param correlationId
	 *            - to track the request
	 */
	public BaseException(final String errorCode, final String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	/**
	 * Parameterized constructor for the Domain Exception.
	 * 
	 * @param errCode
	 *            The error code against which a proper error message is to be
	 *            logged.
	 * @param correlationId
	 *            - to track the request
	 */
	public BaseException(final String errCode) {
		super(errCode);
		this.errorCode = errCode;
	}

	/**
	 * Parameterized constructor for the Domain Exception.
	 * 
	 * @param cause
	 *            The cause that triggered the Adapter Exception.
	 * @param errCode
	 *            The error code against which a proper error message is to be
	 *            logged.
	 * @param correlationId
	 *            - to track the request
	 */
	public BaseException(final Exception cause, final String errCode) {
		super(cause.getMessage(), cause);
		this.errorCode = errCode;
	}

	/* General getters and setters */
	/**
	 * Sets the errorCode value.
	 * 
	 * @param errCode
	 *            Error code
	 */
	public final void setErrorCode(final String errCode) {
		this.errorCode = errCode;
	}

	/**
	 * Gets the errorCode value.
	 * 
	 * @return The error code value
	 */
	public final String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * Gets the error description.
	 * 
	 * @return The error description
	 */
	public final String getErrorDesc() {
		return this.errorDesc;
	}

	/**
	 * Sets the error description.
	 * 
	 * @param errDesc
	 *            Error description
	 */
	public final void setErrorDesc(final String errDesc) {
		this.errorDesc = errDesc;
	}

}
