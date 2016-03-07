/***********************************************
 * Licensed Materials - Property of IBM
 * 
 * 6949-31G
 *
 * (C) Copyright IBM Corp. 2007 All Rights Reserved.
 * (C) Copyright State of New York 2002 All Rights Reserved.
 * 
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ***********************************************/

package com.ibm.nbaopt.lvm.visualizer.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * @author wli
 * @date Aug 16, 2013 
 * Description: Application Exception handler, to handle system exception by logg4j component
 */
public class Log
{

	private final static int INIT_BUFFER_SIZE = 1024;

	protected Logger logger;

	private static Log log;
    /**
     * 
     * @param log4jLogger
     */
	private Log(Logger log4jLogger)
	{

		logger = log4jLogger;
	}

	/**
     * 
     */
	public static Log getLogger(Class classObject)
	{
		if (log != null)
			return log;
		else
			return new Log(org.apache.log4j.Logger.getLogger(classObject));

	}

	public void error(String classname, Throwable e)
	{

		logger.error(getExceptionLog(e));
	}

	public void debug(String classname, Throwable e)
	{

		logger.debug(getExceptionLog(e));
	}

	public void info(String classname, Throwable e)
	{

		logger.info(getExceptionLog(e));
	}

	public void warn(String classname, Throwable e)
	{

		logger.warn(getExceptionLog(e));
	}

	private static String getExceptionLog(Throwable e)
	{
		String exceptionName = getExceptionName(e);
		String detailMessage = null;
		if (e instanceof ApplicationException)
		{
			detailMessage = getDetailMessage((ApplicationException) e);
		}
		else
		{
			detailMessage = getDetailMessage(e);
		}
		StringBuffer log = new StringBuffer(INIT_BUFFER_SIZE);
		log.append("Exception Name: ");
		log.append(exceptionName);
		log.append("\n");
		log.append("Exception Detail: ");
		log.append(detailMessage);
		log.append("\n");
		log.append("-----------------------------------------------------------------------------------------\n");
		return log.toString();
	}

	private static String getDetailMessage(ApplicationException e)
	{
		StringBuffer msg = new StringBuffer(INIT_BUFFER_SIZE);

		if (e.getMessage() != null)
		{
			msg.append("Message : ");
			msg.append(e.getMessage());
			msg.append("\n");
		}

		msg.append("Exception Stack Trace\n");
		try
		{
			StringWriter sw = new StringWriter(INIT_BUFFER_SIZE);
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			msg.append(sw.toString());
			sw.close();
		}
		catch (Exception ex)
		{
			msg.append(ex.toString());
		}
		Throwable rootCause = e.getCause();
		if (rootCause != null)
		{
			msg.append("\n Root Exception Stack Trace : ");
			msg.append(rootCause.toString());
			msg.append("\n");
			try
			{
				StringWriter sw = new StringWriter(INIT_BUFFER_SIZE);
				PrintWriter pw = new PrintWriter(sw);
				rootCause.printStackTrace(pw);
				msg.append(sw.toString());
				sw.close();
			}
			catch (Exception ex)
			{
				msg.append(rootCause.toString());
			}
		}
		return msg.toString();
	}

	private static String getDetailMessage(Throwable e)
	{
		StringBuffer msg = new StringBuffer();

		msg.append("Message : ");
		msg.append(e.getMessage());
		msg.append("\n");
		msg.append("Exception Stack Trace\n");
		try
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			msg.append(sw.toString());
			sw.close();
		}
		catch (Exception ex)
		{
			msg.append(e.toString());
		}
		return msg.toString();
	}

	private static String getExceptionName(Throwable e)
	{
		String name = e.getClass().getName();
		String result = name;
		if (e instanceof ApplicationException)
		{
			int index = name.lastIndexOf(".");
			result = name.substring(index + 1);
		}
		return result;
	}

}
