package com.ibm.nbaopt.lvm.visualizer.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibm.nbaopt.lvm.visualizer.domain.Optimizer;

public class OptDao extends BaseDao{

	private static String m_sql_modelopts = "SELECT model, iteration,bound_type as boundType, bound_name as boundName, bound_value as boundValue, "
			+ " lower, upper, model_alloc as modelAlloc, ratio, lower_relax as lowerRelax, upper_relax as upperRelax "
			+ "FROM "
			+ m_schemaName
			+ ".OPT WHERE MODEL=? and ITERATION=? and bound_type=? order by bound_value ";
	
	private static String m_sql_modelopts_group = "SELECT model, iteration,bound_type as boundType, bound_name as boundName, bound_value as boundValue, "
			+ " lower, upper, model_alloc as modelAlloc, ratio, lower_relax as lowerRelax, upper_relax as upperRelax "
			+ "FROM "
			+ m_schemaName
			+ ".OPT WHERE MODEL=? and ITERATION=? and bound_type=? and bound_name=? order by bound_value ";

	
	
	public List<Optimizer> getOpts(String modelName, int iteration, String group,
			String boundType) throws SQLException
	{
		return find(m_sql_modelopts_group, Optimizer.class, modelName, iteration,
				boundType, group);
	}

	public List<Optimizer> getAllocation(String modelName, int iteration,
			String boundType) throws SQLException {
		return find(m_sql_modelopts, Optimizer.class, modelName, iteration,
				boundType);
	}

}
