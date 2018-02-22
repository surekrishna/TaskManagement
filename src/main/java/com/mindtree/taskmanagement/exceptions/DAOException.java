package com.mindtree.taskmanagement.exceptions;

/**
 * This exception class which is extending the BaseException has been used in
 * the DAO layer code to throw only dao layer exceptions.
 * 
 * @author M1033511
 *
 */
public class DAOException extends BaseException {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private String errorCode = "Unknown_Dao_Exception";

	public DAOException() {
		super();
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
