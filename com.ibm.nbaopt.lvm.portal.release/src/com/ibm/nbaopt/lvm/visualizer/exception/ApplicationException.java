/***********************************************
 * Licensed Materials - Property of IBM
 * 
 * 6949-31G
 * 
 * (C) Copyright IBM Corp. 2007 All Rights Reserved. (C) Copyright State of New
 * York 2002 All Rights Reserved.
 * 
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 ***********************************************/

package com.ibm.nbaopt.lvm.visualizer.exception;

/**
 * @author wli
 * @date Aug 16, 2013
 * Description:
 */
public class ApplicationException extends Exception
{

	private static final long serialVersionUID = 704659942531552071L;

	 

	public ApplicationException()
	{
		super();
	}
	
	public ApplicationException(String msg)
	{
		super(msg);
	}
	
	public ApplicationException(String msg, Exception e)
	{
		super(msg, e);
	}
	
}
