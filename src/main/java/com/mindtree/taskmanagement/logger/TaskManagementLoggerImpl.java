package com.mindtree.taskmanagement.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @author M1033511
 *
 */
public class TaskManagementLoggerImpl implements TaskManagementLogger {

	private static final int GET_CALLING_METHOD_NAME_DEPTH = 2;
	Logger log4JLogger;// = new Log4JLogger();
	
	public TaskManagementLoggerImpl() {
		log4JLogger = Logger.getRootLogger();
	}
	
	public TaskManagementLoggerImpl(String className) {
		log4JLogger = Logger.getLogger(className);
	}
	
	@Override
	public void debug(Object arg0) {
		log4JLogger.debug(arg0);
	}

	@Override
	public void debug(Object arg0, Throwable arg1) {
		log4JLogger.debug(arg0, arg1);
	}

	@Override
	public void error(Object arg0) {
		log4JLogger.error(arg0);
	}

	@Override
	public void error(Object arg0, Throwable arg1) {
		log4JLogger.error(arg0, arg1);
	}

	@Override
	public void fatal(Object arg0) {
		log4JLogger.fatal(arg0);
	}

	@Override
	public void fatal(Object arg0, Throwable arg1) {
		log4JLogger.fatal(arg0, arg1);
	}

	@Override
	public void info(Object arg0) {
		log4JLogger.info(arg0);
	}

	@Override
	public void info(Object arg0, Throwable arg1) {
		log4JLogger.info(arg0, arg1);
	}

	@Override
	public boolean isDebugEnabled() {
		return log4JLogger.isEnabledFor(Level.DEBUG);
	}

	@Override
	public boolean isErrorEnabled() {
		return log4JLogger.isEnabledFor(Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return log4JLogger.isEnabledFor(Level.FATAL);
	}

	@Override
	public boolean isInfoEnabled() {
		return log4JLogger.isEnabledFor(Level.INFO);
	}

	@Override
	public boolean isTraceEnabled() {
		return log4JLogger.isEnabledFor(Level.TRACE);
	}

	@Override
	public boolean isWarnEnabled() {
		return log4JLogger.isEnabledFor(Level.WARN);
	}

	@Override
	public void trace(Object arg0) {
		log4JLogger.trace(arg0);
	}

	@Override
	public void trace(Object arg0, Throwable arg1) {
		log4JLogger.trace(arg0, arg1);
	}

	@Override
	public void warn(Object arg0) {
		log4JLogger.warn(arg0);
	}

	@Override
	public void warn(Object arg0, Throwable arg1) {
		log4JLogger.warn(arg0, arg1);
	}

	@Override
	public long logMethodEntry() {
		final long startTime = System.currentTimeMillis();
		if (this.isDebugEnabled()) {
			this.debug("Entering : " + getCallingMethodName()
					+ " Entry Time in msec : " + startTime);
		}
		return startTime;
	}

	@Override
	public void logMethodExit(long startTime) {
		final long endTime = System.currentTimeMillis();
		if (this.isDebugEnabled()) {
			this.debug("Exiting : " + getCallingMethodName()
					+ " Exit Time in msec : " + endTime + " Time Taken :"
					+ (endTime - startTime));
		}

	}

	private static String getCallingMethodName() {
		return Thread.currentThread().getStackTrace()[GET_CALLING_METHOD_NAME_DEPTH]
				.getMethodName() + "()";
	}
}
