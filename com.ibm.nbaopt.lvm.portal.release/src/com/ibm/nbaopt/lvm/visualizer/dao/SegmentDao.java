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

import com.ibm.nbaopt.lvm.visualizer.domain.Segment;
import com.ibm.nbaopt.lvm.visualizer.domain.SegmentNodes;

public class SegmentDao extends BaseDao {

	private String segment_stat_sql = "SELECT SEGMENTID as segmentId, SAMPLE_SIZE as sampleSize, MATCHED_SAMPLE_SIZE as matchedSampleSize,SEGMENTCOUNT as segmentCount,V_ACTUAL as vActual, Q_EST_VALUE_MODEL as qEstValueModel, GAIN, AVG_LTV as avgLTV, AVG_REWARD as avgReward "
			+ "FROM "
			+ m_schemaName
			+ ".SEGMENT_STAT "
			+ "WHERE model=? and iteration=? ";
	
	private String segment_sequence_sql = "SELECT SUM(T1 * T1SIZE) as t1Value, SUM(T2 * T2SIZE) as t2Value, SUM(T3 * T3SIZE) as t3Value "
	        + "FROM "
			+ m_schemaName
			+ ".SEQUENCE "
			+ "WHERE model=? and iteration=?";

	private String segmentTreeSql = "SELECT PARENTID, NODEID, EXPRESSION, LEVELNUM,SEGMENT "
			+ "FROM "
			+ m_schemaName
			+ ".SEGMENT_NODES "
			+ "WHERE model=? and iteration=? " + "ORDER BY PARENTID";

	private static final String FILTER_SQLL = " AND ( T1>0 or T2 > 0 or T3 >0 ) AND (T1<>T2 OR T2 <>T3) ";

	private static final String m_sql_segmentOverTime = "SELECT  V_ACTUAL as vActual, AVG_REWARD as avgReward, T1,T2,T3, A.SEGMENTID as segmentId,SAMPLE_SIZE as sampleSize, rule "
			+ " FROM "
			+ m_schemaName
			+ ".SEQUENCE A, "
			+ m_schemaName
			+ ".SEGMENT_STAT B,"
			+ m_schemaName
			+ ".RULES C"
			+ " WHERE A.MODEL=? AND  A.ITERATION=?  "
			+ " AND A.MODEL = C.MODEL AND A.ITERATION = C.ITERATION  AND A.SEGMENTID = C.SEGMENTID "
			+ " AND A.MODEL = B.MODEL AND A.ITERATION = B.ITERATION AND A.SEGMENTID= B.SEGMENTID ";

	private final String m_sql_segMigration = " SELECT SEGMENTID_TO AS SEGMENTIDTO, PROB, SEGMENTCOUNT, V_ACTUAL AS VACTUAL  , RULE , GAIN "
			+ "FROM "
			+ m_schemaName
			+ ".TRANSITION A,  "
			+ m_schemaName
			+ ".SEGMENT_STAT B, "
			+ m_schemaName
			+ ".RULES C"
			+ " WHERE A.MODEL=? AND A.ITERATION=? AND A.SEGMENTID_FROM =? "
			+ " AND A.MODEL = B.MODEL AND A.ITERATION = B.ITERATION AND A.SEGMENTID_TO = B.SEGMENTID "
			+ " AND A.MODEL=C.MODEL AND A.ITERATION =C.ITERATION AND A.SEGMENTID_TO = C.SEGMENTID "
			+ "   ORDER BY SEGMENTID_TO";

	private final String m_sql_segment = " SELECT A.SEGMENTID AS SEGMENTID, A.SEGMENTCOUNT AS SEGMENTCOUNT ,A.V_ACTUAL AS VACTUAL, RULE, GAIN "
			+ "FROM "
			+ m_schemaName
			+ ".SEGMENT_STAT A, "
			+ m_schemaName
			+ ".RULES C"
			+ " WHERE "
			+ " A.MODEL=? AND A.ITERATION=? AND A.SEGMENTID=? "
			+ " AND A.MODEL=C.MODEL AND A.ITERATION=C.ITERATION  AND A.SEGMENTID = C.SEGMENTID ";

	private final String m_sql_sequence = " SELECT MODEL, ITERATION, SEGMENTID, T1, T2, T3 "
			+ "FROM "
			+ m_schemaName
			+ ".SEQUENCE "
			+ " WHERE "
			+ " MODEL=? AND ITERATION=? AND SEGMENTID=? ";

	private final String m_sql_trans = "SELECT A.MODEL AS MODEL, A.ITERATION AS ITERATION, A.SEGMENTID AS SEGMENTID, T1, T2, T3, T1SIZE, T2SIZE, T3SIZE , q_est_value_model AS qEstValueModel FROM "
			+ m_schemaName
			+ ".SEQUENCE A,"
			+ m_schemaName
			+ ".SEGMENT_STAT B WHERE A.MODEL=? AND A.ITERATION=? AND A.MODEL=B.MODEL AND A.ITERATION=B.ITERATION AND A.SEGMENTID=B.SEGMENTID ORDER BY q_est_value_model";

	private final String m_sql_single_trans = "SELECT SEGMENTID_TO AS SEGMENTIDTO, PROB "
			+ "FROM "
			+ m_schemaName
			+ ".TRANSITION "
			+ "WHERE "
			+ "MODEL=? AND ITERATION=? AND SEGMENTID_FROM=? ";

	private final String m_sql_sequence_sum = " SELECT sum(T1) as T1, sum(T2) as T2, sum(T3) as T3 "
			+ "FROM "
			+ m_schemaName
			+ ".SEQUENCE "
			+ " WHERE "
			+ " MODEL=? AND ITERATION=?";

	public List<Segment> getSegmentsOverTime(String modelName, int iteration,
			boolean filter) throws SQLException {
		String sql = m_sql_segmentOverTime;
		if (filter) {
			sql += FILTER_SQLL;
		}
		// System.out.println(sql);
		return find(sql, Segment.class, modelName, iteration);
	}

	public Segment getSegementSequenceSum(String modelName, int iteration)
			throws SQLException {
		return getSingleResult(m_sql_sequence_sum, Segment.class, modelName,
				iteration);
	}

	public Segment getSegement(String modelName, int iteration, int segmentId)
			throws SQLException {
		return getSingleResult(m_sql_segment, Segment.class, modelName,
				iteration, segmentId);

	}

	public List<Segment> getSegementMigrations(String modelName, int iteration,
			int segmentId) throws SQLException {
		return find(m_sql_segMigration, Segment.class, modelName, iteration,
				segmentId);

	}

	public List<Segment> getSegementTrans(String modelName, int iteration)
			throws SQLException {
		return find(m_sql_trans, Segment.class, modelName, iteration);

	}

	public List<Segment> getSingleSegmentTransition(String modelName,
			int iteration, int segmentID) throws SQLException {
		return find(m_sql_single_trans, Segment.class, modelName, iteration,
				segmentID);
	}

	public Segment getSegementSequence(String modelName, int iteration,
			int segmentId) throws SQLException {
		return getSingleResult(m_sql_sequence, Segment.class, modelName,
				iteration, segmentId);

	}

	public List<SegmentNodes> getSegmentTreeData(String modelName, int iteration)
			throws SQLException {
		return find(segmentTreeSql, SegmentNodes.class, modelName, iteration);

	}

	public List<Segment> getIterationSegmentStat(String modelName,
			String iteration) throws SQLException {
		return find(segment_stat_sql, Segment.class, modelName, iteration);
	}

	public Map<String, Object> getIterationSegmentTransaction(String modelName,
			String iteration) throws SQLException{
		return getSingleResult(segment_sequence_sql, modelName, iteration);
	}
}
