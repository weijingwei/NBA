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

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.nbaopt.lvm.visualizer.domain.Model;

/**
 * @author hammer
 * @date Aug 8, 2013 Description:
 */
public class ModelDao extends BaseDao {
	private static final Logger log = Logger.getLogger(ModelDao.class);

	private static String m_sql_modellist = "SELECT model, iteration, description, v_Value, q_Value, model_date as modelDate, model_starttime as modelStartTime, model_endtime as modelEndTime, model_timecost as modelTimeCost FROM " + m_schemaName + ".MODELS ORDER BY MODEL";

//	private static String m_sql_modelrewards = "SELECT *  FROM "
//			+ m_schemaName + ".MODELS WHERE MODEL=?   ORDER BY ITERATION ";
	private static String m_sql_modelrewards = "SELECT model, iteration, description, v_Value, q_Value, model_date as modelDate, model_starttime as modelStartTime, model_endtime as modelEndTime, model_timecost as modelTimeCost  FROM "
			+ m_schemaName + ".MODELS WHERE MODEL=?   ORDER BY ITERATION ";
	 

	
	public List<Model> getAllModels() throws SQLException {
		return find(m_sql_modellist, Model.class);
	}

	
	public List<Model> getModelByName(String modelName) throws SQLException {
		return find(m_sql_modelrewards, Model.class, modelName);
	}

	
}
