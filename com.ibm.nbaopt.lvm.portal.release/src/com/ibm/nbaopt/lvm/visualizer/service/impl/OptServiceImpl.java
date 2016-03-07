package com.ibm.nbaopt.lvm.visualizer.service.impl;

import java.util.List;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.nbaopt.lvm.visualizer.dao.OptDao;
import com.ibm.nbaopt.lvm.visualizer.domain.Optimizer;
import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;
import com.ibm.nbaopt.lvm.visualizer.service.OptService;

public class OptServiceImpl implements OptService {

	private OptDao dao = new OptDao();

	@Override
	public String getActionAllocationPie(String modelName, int iteration,
			String boundType) throws ApplicationException {
		try {
			List<Optimizer> optimizers = dao.getAllocation(modelName,
					iteration, boundType);
			JSONArray rootJson = new JSONArray();
			for (Optimizer opt : optimizers) {
				JSONObject item = new JSONObject();
				item.put("data", opt.getModelAlloc());
				item.put("label", opt.getBoundValue());
				rootJson.add(item);
			}
			return rootJson.serialize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("OptServiceImpl",e);
		}
	}

	@Override
	public String getResourcesAllocation(String modelName, int iteration,
			String boundType) throws ApplicationException {
		try {
			List<Optimizer> optimizers = dao.getAllocation(modelName,
					iteration, boundType);
			JSONArray rootJson = new JSONArray();
			for (Optimizer opt : optimizers) {
				JSONObject item = new JSONObject();
				item.put("resourceName", opt.getBoundValue());
				item.put("allocation", opt.getModelAlloc());
				item.put("ratio", opt.getRatio());
				rootJson.add(item);
			}
			return rootJson.serialize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("OptServiceImpl",e);
		}
	}

}
