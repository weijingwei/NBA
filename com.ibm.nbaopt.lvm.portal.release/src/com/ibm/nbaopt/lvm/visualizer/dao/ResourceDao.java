package com.ibm.nbaopt.lvm.visualizer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibm.nbaopt.lvm.visualizer.domain.Resource;

public class ResourceDao extends BaseDao {
	
	private static final String m_sql_resource = "Select RESOURCE_URI_PATTERN as resource_uri_pattern, RESOURCE_ID as resource_id from " + m_schemaName + ".RESOURCE";
	private static final String m_sql_resource_auth_code = "Select AUTH_CODE as auth_code from " + m_schemaName + ".RESOURCE_CODE Where RESOURCE_ID = ?";

	public List<Resource> getResources() throws SQLException{
		String sql = m_sql_resource;
		List<Resource> resources = find(sql, Resource.class);
		sql = m_sql_resource_auth_code;
		if(null != resources){
			for(Resource resource : resources){
				List<Map<String, Object>> raw_auth_codes = find(sql, resource.getResource_id());
				List<String> auth_codes = new ArrayList<String>();
				if(null!=raw_auth_codes){
					for(Map<String, Object> raw_auth_code : raw_auth_codes){
						auth_codes.add(raw_auth_code.get("auth_code").toString());
					}
				}
				
				resource.setAuth_codes(auth_codes);
			}
		}
		
		return resources;
	}
}
