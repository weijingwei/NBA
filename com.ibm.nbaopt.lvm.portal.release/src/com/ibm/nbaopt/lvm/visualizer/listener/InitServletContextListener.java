/***********************************************
 * Licensed Materials - Property of IBM
 * 
 * 6949-31G
 *
 * (C) Copyright IBM Corp. 2007 All Rights Reserved.
 * (C) Copyright State of New York 2013 All Rights Reserved.
 * 
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ***********************************************/

package com.ibm.nbaopt.lvm.visualizer.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ibm.nbaopt.lvm.visualizer.dao.BaseDao;

/**
 * @author hammer
 * @date Aug 9, 2013 Description:
 */
public class InitServletContextListener implements ServletContextListener
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		destroy();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event)
	{

		ServletContext context = event.getServletContext();
		String schemaName = context.getInitParameter("DBSchema");
		String jndiName = context.getInitParameter("JNDIName");
		init(schemaName, jndiName);

	}
	
	/**
	 * @author hammer
	 * @param schemaName
	 * @param jndiName
	 */
	private void init(String schemaName, String jndiName)
	{
		BaseDao.init(schemaName, jndiName);
	}
	
	/**
	 * @author hammer
	 */
	private void destroy()
	{
		BaseDao.destroy();
	}

}
