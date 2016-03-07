package com.ibm.nbaopt.lvm.visualizer.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibm.nbaopt.lvm.visualizer.domain.Business;
import com.ibm.nbaopt.lvm.visualizer.domain.SegmentTransition;

public class BusinessDao extends BaseDao {
	private final String ISSHOWSELFCONNECTION = "ISSHOWSELFCONNECTION";
	private final String SEARCHSEGMENT = "SEARCHSEGMENT";
	private final String HIDESELFCONNECTION = "and a.segmentid_from!=a.segmentid_to ";
	private final String TOPNUM = "TOPNUM";
	@SuppressWarnings("unused")
	private String searchSegment(String searchSegment) {
		String condition = "and (1<>1 ";
		String[] ids = searchSegment.split(",");
		for (String id : ids) {
			if (!id.trim().isEmpty())
				condition += "or (a.segmentid_from=" + id.trim() +" or a.segmentid_to=" + id.trim() + ") ";
		}
		condition += ")";
		return condition;
	}
	private String m_sql_segmentTransition_LTV = "select model,iteration,segmentid_from,from_segmentcount,to_segmentcount,FROM_LTV,segmentid_to,prob,TO_LTV,gain "
			+ "from (select a.model,a.iteration,a.segmentid_from,a.segmentcount as from_segmentcount,b.segmentcount as to_segmentcount,a.avg_LTV as FROM_LTV,a.segmentid_to,a.prob,b.avg_LTV as TO_LTV,bgain as gain, "
			+ "rank()over(partition by a.model,a.iteration,a.segmentid_from order by b.avg_LTV desc) as row_id "
			+ "from (select a.model,a.iteration,a.segmentid_from,b.segmentcount,b.avg_LTV,a.segmentid_to,a.prob, b.gain as bgain "
			+ "from " + m_schemaName + ".TRANSITION a," + m_schemaName + ".SEGMENT_STAT b "
			+ "where a.model=? "
			+ "and b.iteration=? "
			+ "and a.model=b.model "
			+ "and a.iteration=b.iteration "
			+ "and a.segmentid_from=b.segmentid "
			+ SEARCHSEGMENT
			+ ISSHOWSELFCONNECTION // hideSelfConnection
			+ ") a, "
			+ m_schemaName + ".SEGMENT_STAT b where "
			+ "a.model=b.model "
//			+ "and a.avg_LTV <= b.avg_LTV "
			+ "and a.iteration=b.iteration "
			+ "and a.segmentid_to=b.segmentid) where row_id<=" + TOPNUM + " and FROM_LTV <= TO_LTV";
	
	private String m_sql_segmentTransition_prob = "select model,iteration,segmentid_from,from_segmentcount,to_segmentcount,FROM_LTV,segmentid_to,prob,TO_LTV,gain "
			+ "from (select a.model,a.iteration,a.segmentid_from,a.segmentcount as from_segmentcount,b.segmentcount as to_segmentcount,a.avg_LTV as FROM_LTV,a.segmentid_to,a.prob,b.avg_LTV as TO_LTV,bgain as gain, "
			+ "rank()over(partition by a.model,a.iteration,a.segmentid_from order by a.prob desc) as row_id "
			+ "from (select a.model,a.iteration,a.segmentid_from,b.segmentcount,b.avg_LTV,a.segmentid_to,a.prob, b.gain as bgain "
			+ "from " + m_schemaName + ".TRANSITION a," + m_schemaName + ".SEGMENT_STAT b "
			+ "where a.model=? "
			+ "and b.iteration=? "
			+ "and a.model=b.model "
			+ "and a.iteration=b.iteration "
			+ "and a.segmentid_from=b.segmentid "
			+ SEARCHSEGMENT
			+ ISSHOWSELFCONNECTION // hideSelfConnection
			+ ") a, "
			+ m_schemaName + ".SEGMENT_STAT b where "
			+ "a.model=b.model "
//			+ "and a.avg_LTV <= b.avg_LTV "
			+ "and a.iteration=b.iteration "
			+ "and a.segmentid_to=b.segmentid) where row_id<=" + TOPNUM + " and FROM_LTV <= TO_LTV";
	
	private String m_sql_segmentTransition_all = "select a.model,a.iteration,a.segmentid_from,a.segmentcount as from_segmentcount,b.segmentcount as to_segmentcount,a.avg_LTV as FROM_LTV,a.segmentid_to,a.prob,b.avg_LTV as TO_LTV, bgain as gain "
			+ "from (select a.model,a.iteration,a.segmentid_from,b.segmentcount,b.avg_LTV,a.segmentid_to,a.prob , b.gain as bgain "
			+ "from " + m_schemaName + ".TRANSITION a," + m_schemaName + ".SEGMENT_STAT b "
			+ "where a.model=? "
			+ "and b.iteration=? "
			+ "and a.model=b.model "
			+ "and a.iteration=b.iteration "
			+ "and a.segmentid_from=b.segmentid "
			+ SEARCHSEGMENT
			+ ISSHOWSELFCONNECTION // hideSelfConnection
			+ ") a, "
			+ m_schemaName + ".SEGMENT_STAT b where "
			+ "a.model=b.model "
			+ "and a.iteration=b.iteration "
			+ "and a.segmentid_to=b.segmentid and a.avg_LTV <= b.avg_LTV";
	
	private String m_sql_segmentRules = "with " + m_schemaName + ".report(nodeid,parentid,expression,segment) as "
			+ "(select a.nodeid,a.parentid,a.expression,a.segment from " + m_schemaName + ".segment_nodes a,(select parentid from " + m_schemaName + ".segment_nodes where model=? and iteration=? and segment=?) b "
			+ "where a.model=? and a.iteration=? and a.nodeid=b.parentid "
			+ "union all "
			+ "select b.nodeid,b.parentid,b.expression,b.segment from " + m_schemaName + ".report a," + m_schemaName + ".segment_nodes b where b.model=? and b.iteration=? and b.nodeid=a.parentid) " 
			+ "select * from " + m_schemaName + ".report";
	
	private String m_sql_action = "select model,iteration,segmentid,Action,Action_Coeff,Action_Count,Allocated_Count from " + m_schemaName + ".ACTION_STAT where model=? and iteration=? and segmentid=?";
	
	private String m_sql_segmentStats = "select model,iteration,segmentid,V_Actual,Q_EST_VALUE_MODEL,GAIN,AVG_LTV,AVG_REWARD from " + m_schemaName + ".SEGMENT_STAT where model=? and iteration=? and segmentid=?";
	
	private String getSQL(Business business) {
		String sql = "";
		if (business.getDataType().equals(Business.PROB)) {
			sql = m_sql_segmentTransition_prob;
		} else if (business.getDataType().equals(Business.LTV)) {
			sql = m_sql_segmentTransition_LTV;
		} else if (business.getDataType().equals(Business.ALL)) {
			sql = m_sql_segmentTransition_all;
		}
		sql = sql.replace(TOPNUM, business.getTopNum());
		return sql.replace(ISSHOWSELFCONNECTION, business.isShowSelfConnection() ? "" : HIDESELFCONNECTION)
				.replace(SEARCHSEGMENT, "");
//				.replace(SEARCHSEGMENT, business.getSearchSegment().isEmpty() ? "" : searchSegment(business.getSearchSegment()));
	}
	
	public void getBusinessInsight(Business business) throws SQLException {
		business.setSegmentTransitions(find(getSQL(business), SegmentTransition.class, business.getModelName(), business.getIteration()));
	}
	
	public List<Map<String, Object>> getSegmentRules(String model, String iteration, String segment) throws SQLException {
		return find(m_sql_segmentRules, model, iteration, segment, model, iteration, model, iteration);
	}
	
	public List<Map<String, Object>> getAction(String model, String iteration, String segment)  throws SQLException {
		return find(m_sql_action, model, iteration, segment);
	}
	
	public List<Map<String, Object>> getSegmentStats(String model, String iteration, String segment)  throws SQLException {
		return find(m_sql_segmentStats, model, iteration, segment);
	}
	
}