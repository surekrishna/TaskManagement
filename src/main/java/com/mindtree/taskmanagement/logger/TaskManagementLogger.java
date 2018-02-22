package com.mindtree.taskmanagement.logger;

import org.apache.commons.logging.Log;

/**
 * 
 * @author M1033511
 *
 */
public interface TaskManagementLogger extends Log {

	/**
	 * Marks the entry into a debug method.
	 * 
	 * @return start time in milliseconds
	 */
	long logMethodEntry();

	/**
	 * Marks the exit point from a debug method.
	 * 
	 * @param startTime
	 *            start time of method entry
	 */
	void logMethodExit(long startTime);
}
