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
package com.ibm.nbaopt.lvm.visualizer.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

/**
 * @author hammer
 * @date Aug 5, 2013
 * Description:
 */
public class BaseDao
{
	private static DataSource ds;

	protected static QueryRunner run;

	protected static String m_schemaName = "";

	private static String JNDI_AME = "";
	private static final Logger LOGGER = Logger.getLogger(BaseDao.class);

	/**
	 * @author hammer
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException
	{
		return ds.getConnection();
	}
	
	/**
	 * @author hammer
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> find(String sql, Class<T> clazz, Object... params)
			throws SQLException
	{
		LOGGER.debug(sql);
		List<T> list = null;
		list = (List<T>) run.query(sql, new BeanListHandler(clazz), params);

		return list;
	}

	/**
	 * @author hammer
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> find(String sql, Object... params)
			throws SQLException
	{
		LOGGER.debug(sql);
		List<Map<String, Object>> list = null;
		list = run.query(sql, new MapListHandler(), params);
		return list;
	}
	
	/**
	 * @author hammer
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> T getSingleResult(String sql, Class<T> clazz, Object... params)
			throws SQLException
	{
		LOGGER.debug(sql);
		T t = null;
		t = (T) run.query(sql, new BeanHandler<T>(clazz), params);

		return t;
	}
	
	/**
	 * @author hammer
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> getSingleResult(String sql, Object... params)
			throws SQLException
	{
		LOGGER.debug(sql);
		Map<String, Object> m = null;
		m = (Map<String, Object>) run.query(sql, new MapHandler(), params);

		return m;
	}

	/**
	 * @author hammer
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public int getCount(String tableName) throws SQLException
	{
		
		int count = 0;
		String field = "_COUNT";
		String sql = "SELECT COUNT(*) AS " + field + " FROM " + tableName;
		Map<String, Object> m = getSingleResult(sql);
		count = Integer.parseInt(m.get(field).toString());

		return count;
	}
	
	/**
	 * @author hammer
	 * @param schemaName
	 * @param jndiName
	 */
	public static void init(String schemaName, String jndiName)
	{
		m_schemaName = schemaName;
		JNDI_AME = jndiName;
		LOGGER.info("Initializing DATASOURCE\tjndiname:"+ JNDI_AME + "\tschema name:" + m_schemaName);
		try
		{
			ds = (DataSource) new InitialContext().lookup(JNDI_AME);
			run = new QueryRunner(ds);
		}
		catch (NamingException e)
		{
			LOGGER.error("initialize DATASOURCE FAILURE", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * @author hammer
	 */
	public static void destroy()
	{

	}
}
