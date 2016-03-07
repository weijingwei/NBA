package com.ibm.nbaopt.lvm.visualizer.service.impl;

import java.util.List;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.nbaopt.lvm.visualizer.dao.FeatureDao;
import com.ibm.nbaopt.lvm.visualizer.domain.FeatureCount;
import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;
import com.ibm.nbaopt.lvm.visualizer.service.FeatureService;

public class FeatureServiceImpl implements FeatureService{

	@Override
	public String getIterationFeatureCount(String modelName, int iteration)
			throws ApplicationException {
		try {
			FeatureDao dao = new FeatureDao();
			List<FeatureCount> features = dao.getFeatureCount(modelName, iteration);
			JSONArray ja = new JSONArray();
			for(FeatureCount fc : features){
				JSONObject o = new JSONObject();
				o.put("featureName", fc.getFeature());
				o.put("featureCount", fc.getUse_count());
				ja.add(o);
			}
			return ja.serialize();
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	
	@Override
	public String getIterationFeatureMostUsed(String modelName, int iteration)
			throws ApplicationException {
		try {
			FeatureDao dao = new FeatureDao();
			List<FeatureCount> features = dao.getFeatureCount(modelName, iteration);
			int length=features.size();
			int mostUsedCount=(int)Math.ceil(length*1);
			JSONArray featureRoot=new JSONArray();
			JSONArray countRoot=new JSONArray();
			JSONObject root=new JSONObject();
			int index=1;
			for(int i=mostUsedCount-1;i>=0;i--){
				JSONArray feaCount = new JSONArray();
				JSONArray feaName = new JSONArray();
				feaCount.add(features.get(i).getUse_count());
				feaCount.add(index);
				feaName.add(index);
				feaName.add(features.get(i).getFeature());
				featureRoot.add(feaName);
				countRoot.add(feaCount);
				index++;
			}
			root.put("Name", featureRoot);
			root.put("Count", countRoot);
			return root.serialize();
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
	}

}
