package com.mindtree.taskmanagement.constants;

/**
 * This class is used for Database Query constants
 * @author M1033511
 *
 */
public class QueryConstants {

	public static final String LOAD_PROJECT = "from Employee where projectName = ?";
	public static final String LOAD_EMPLOYEES = "from Employee where PROJECT_ID = ?";
	public static final String BULK_UPDATE_EMPLOYEE = "UPDATE Employee SET DESCRIPTION = ?, START_DATE = ?, END_DATE =? WHERE EMP_ID = ?";
	public static final String ASSIGNED_PROJECT_EMPLOYEES = "from Project where PROJECT_NAME = ?";
	private QueryConstants(){}
}
