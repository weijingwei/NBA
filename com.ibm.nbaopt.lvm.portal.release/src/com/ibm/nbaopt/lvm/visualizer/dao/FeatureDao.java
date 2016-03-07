package com.ibm.nbaopt.lvm.visualizer.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibm.nbaopt.lvm.visualizer.domain.FeatureCount;

public class FeatureDao extends BaseDao {
		
	public String m_sql_modelfeatures = "SELECT DISTINCT FEATURE, USE_COUNT FROM " 
			+ m_schemaName
			+ ".FEATURE_COUNT "
			+ "WHERE model=? and iteration =? and USE_COUNT <> 0 ORDER BY USE_COUNT DESC";
	
	public List<FeatureCount> getFeatureCount(String modelName, int iteration)
			throws SQLException
	{

		return find(m_sql_modelfeatures, FeatureCount.class, modelName, iteration); 
	
	}


}
