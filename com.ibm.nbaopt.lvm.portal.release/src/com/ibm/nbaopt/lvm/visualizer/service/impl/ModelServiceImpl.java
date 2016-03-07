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

import org.apache.log4j.Logger;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.nbaopt.lvm.visualizer.dao.ModelDao;
import com.ibm.nbaopt.lvm.visualizer.domain.Model;
import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;
import com.ibm.nbaopt.lvm.visualizer.service.ModelService;

public class ModelServiceImpl implements ModelService {

	private static final Logger log = Logger.getLogger(ModelServiceImpl.class);

	
	private ModelDao modelDao = new ModelDao();
	
	@Override
	public String getAllModelJson() throws ApplicationException {
		try {
			List<Model> models = modelDao.getAllModels();
			JSONArray ja = new JSONArray();
			String modelName = null;
			JSONObject jo = new JSONObject();
			JSONArray vArr = new JSONArray();
			JSONArray qArr = new JSONArray();
			int index = 0;
			for(Model model : models){
				if(!model.getModel().equalsIgnoreCase(modelName)){
					modelName = model.getModel();
					if(index != 0){
						jo.put("iterationCount", index);
						jo = new JSONObject();
						vArr = new JSONArray();
						qArr = new JSONArray();
						index = 0;
					}
					jo.put("name", modelName);
					jo.put("description", model.getDescription());
					jo.put("startTime", model.getModelStartTime());
					jo.put("endTime", model.getModelEndTime());
					jo.put("timeCost", model.getModelTimeCost());
					jo.put("q", qArr);
					jo.put("v", vArr);
					ja.add(jo);
				}
				JSONArray iterationQ = new JSONArray();
				JSONArray ierationV = new JSONArray();
				index = index + 1;
				ierationV.add(index);
				ierationV.add(model.getV_Value()); 
				iterationQ.add(index);
				iterationQ.add(model.getQ_Value());
				vArr.add(ierationV);
				qArr.add(iterationQ);
			}
			jo.put("iterationCount", index);
			return ja.serialize();
		} catch (Exception e) {
			throw new ApplicationException(
					"Get All Model exception:", e);
		}
	}
	
	@Override
	public String getSpecifiedModelJson(String modelName)
			throws ApplicationException {
		try {
			log.info("Get a specified model");
			List<Model> models = modelDao.getModelByName(modelName);
			JSONObject jo = new JSONObject();
			jo.put("name", modelName );
			JSONArray vArr = new JSONArray();
			JSONArray qArr = new JSONArray();
			int index = 1;
			for (Model model : models) {
				JSONArray q_Arr = new JSONArray();
				JSONArray v_Arr = new JSONArray();

				v_Arr.add(index);
				q_Arr.add(index);

				v_Arr.add(model.getV_Value());
				q_Arr.add(model.getQ_Value());
				vArr.add(v_Arr);
				qArr.add(q_Arr);
				index++;
			}
			jo.put("v", vArr);
			jo.put("q", qArr);
			jo.put("name", modelName);
			jo.put("description", models.get(0).getDescription());
			jo.put("startTime", models.get(0).getModelStartTime());
			jo.put("endTime", models.get(0).getModelEndTime());
			jo.put("timeCost", models.get(0).getModelTimeCost());
			jo.put("iterationCount", index-1);
			return jo.serialize();
		} catch (Exception e) {
			throw new ApplicationException(
					"Get Specified ModelJson exception:", e);
		}
	}

}
