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

package com.ibm.nbaopt.lvm.visualizer.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.nbaopt.lvm.visualizer.dao.ActionDao;
import com.ibm.nbaopt.lvm.visualizer.domain.ActionStat;
import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;
import com.ibm.nbaopt.lvm.visualizer.service.ActionService;

public class ActionServiceImpl implements ActionService {

	private static final Logger log = Logger.getLogger(ActionServiceImpl.class);
	private ActionDao actionDao = new ActionDao();

	@Override
	public String getModelAdvantageJson(String modelName)
			throws ApplicationException {
		try {
			List<Map<String, Object>> advantagesList = actionDao.getModelAdvantage(modelName);
			JSONArray advantageArray = new JSONArray();
			for (Map<String, Object> m : advantagesList) {
				Object[]  values = m.values().toArray(); 
				JSONArray iterationArray = new JSONArray();			
				for (Object value : values) {
					iterationArray.add(value);
					
				}
				advantageArray.add(iterationArray);
				
			}
			return advantageArray.serialize();

		} catch (Exception e) {
			throw new ApplicationException("Get Model Advantage exception:", e);
		}
	}

	@Override
	public String getModelActionAllocation(String modelName, int iteration)
			throws ApplicationException {
		try {
			log.info("Get action allocation stat");
			List<ActionStat> actionAllocList = actionDao.getActionAllocationsList(modelName,iteration);
			JSONArray items = new JSONArray();
			JSONObject segObj=new JSONObject();
			JSONArray alloArr=new JSONArray();
			String segmentId=null;
			for(ActionStat action:actionAllocList)
			{
				if(!action.getSegmentid().equalsIgnoreCase(segmentId)){
					segmentId=action.getSegmentid();
					 alloArr=new JSONArray();
					 segObj=new JSONObject();
					 segObj.put("segmentId",segmentId);
					 segObj.put("allocatedCount", alloArr);
					 alloArr.add(segmentId);
					 items.add(segObj);
				}
				alloArr.add(action.getAllocated_count());
			}
			return items.serialize();

		} catch (Exception e) {
			throw new ApplicationException("Get action allocation exception:", e);
		}
	}

	@Override
	public String getUniqueActions(String modelName) throws ApplicationException {
		try{
			log.info("Get Unique Actions");
			List<ActionStat> list=actionDao.getUniqueActions(modelName);
			JSONArray rootJson=new JSONArray();
			for(ActionStat action:list)
			{
				rootJson.add(action.getAction());
			}
			
			return rootJson.serialize();
		}catch(Exception e){
			throw new ApplicationException("Get Unique Actions exception:",e);
		}
	}

}
