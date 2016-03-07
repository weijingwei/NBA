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
import java.util.Map;

import com.ibm.nbaopt.lvm.visualizer.domain.ActionStat;

public class ActionDao extends BaseDao {

	private static final String advantageSql = "SELECT ITERATION as iteration, SUM(ACTION_COEFF * ALLOCATED_COUNT) as advantage "
			+ "FROM "
			+ m_schemaName
			+ ".ACTION_STAT "
			+ "WHERE model=?"
			+ " GROUP BY iteration ORDER BY iteration";

	private final String m_sql_actionCountForSingleSegment = " SELECT ACTION,ACTION_COUNT AS actionCount, ALLOCATED_COUNT as allocatedCount, AVERAGEREWARD AS avgReward "
			+ "FROM "
			+ m_schemaName
			+ ".ACTION_STAT "
			+ " WHERE MODEL=? AND ITERATION =? AND SEGMENTID =? "
			+ " AND (ACTION_COUNT >0 OR ALLOCATED_COUNT >0) ";

	private String uniqueActionSql = "SELECT distinct action " + "FROM "
			+ m_schemaName + ".ACTION_STAT "
			+ "WHERE model=? "+ "ORDER BY action";

	private String coefficientsSql = "SELECT segmentid, action, action_coeff "
			+ "FROM " + m_schemaName + ".ACTION_STAT "
			+ "WHERE model=? and iteration=? " + "ORDER BY segmentid, action";

	private String allocationsSql = "SELECT segmentid, action, allocated_count "
			+ "FROM " + m_schemaName + ".ACTION_STAT "
			+ "WHERE model=? and iteration=? "+ "ORDER BY segmentid, action";

	/**
	 * @author Cruise
	 * @param modelName
	 * @return
	 * @throws SQLException
	 */

	public List<Map<String, Object>> getModelAdvantage(String modelName)
			throws SQLException {
		return find(advantageSql, modelName);
	}

	public List<ActionStat> getSegementActions(String modelName, int iteration,
			int segmentId) throws SQLException {
		return find(m_sql_actionCountForSingleSegment, ActionStat.class,
				modelName, iteration, segmentId);

	}

	public List<ActionStat> getUniqueActions(String modelName)
			throws SQLException {
		return find(uniqueActionSql, ActionStat.class,modelName);
	}

	public List<ActionStat> getCoefficientsList(String runID, String modelID)
			throws SQLException {
		return find(coefficientsSql, ActionStat.class, runID, modelID);

	}

	public List<ActionStat> getActionAllocationsList(String modelName,
			int iteration) throws SQLException {
		return find(allocationsSql, ActionStat.class, modelName, iteration);
	}
}
