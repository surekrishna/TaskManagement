package com.mindtree.taskmanagement.exceptions;

/**
 * This exception class which is extending the BaseException has been used in
 * the service layer code to throw only service layer exceptions.
 * 
 * @author M1033511
 *
 */
public class ServiceException extends BaseException {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private String errorCode = "Unknown_Service_Exception";

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
