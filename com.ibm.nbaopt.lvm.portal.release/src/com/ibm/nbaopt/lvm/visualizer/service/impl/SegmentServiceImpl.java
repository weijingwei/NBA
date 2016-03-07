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
package com.ibm.nbaopt.lvm.visualizer.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.nbaopt.lvm.visualizer.dao.SegmentDao;
import com.ibm.nbaopt.lvm.visualizer.domain.Segment;
import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;
import com.ibm.nbaopt.lvm.visualizer.service.SegmentService;

public class SegmentServiceImpl implements SegmentService {
	
	private SegmentDao dao = new SegmentDao();

	@Override
	public String getIterationSegmentStat(String modelName, String iteration)
			throws ApplicationException {
		try {
			List<Segment> segments = dao.getIterationSegmentStat(modelName, iteration);
			JSONObject rootJson = new JSONObject();
			JSONArray items = new JSONArray();
			DecimalFormat df=new DecimalFormat("#.####");
			for (Segment s : segments) {
				JSONObject item = new JSONObject();
				item.put("segmentID", s.getSegmentId());
				item.put("segmentSize", s.getSampleSize());
//				item.put("averageReward", df.format(s.getAvgReward()));
				item.put("matchedSampleSize", s.getMatchedSampleSize());
				item.put("segmentReward", s.getSegmentCount()*s.getAvgReward());
				item.put("vActual", df.format(s.getvActual()));// v_actual
				item.put("qEstValueModel", df.format(s.getqEstValueModel()));// q_est_value_model
				item.put("gain", s.getGain());
				items.add(item);
			}
			rootJson.put("items", items);
			rootJson.put("label", "Segment stat");

			return rootJson.serialize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("SegmentServiceImpl", e);
		}
	}

	@Override
	public String getIterationSegmentTransaction(String modelName,
			String iteration) throws ApplicationException {
		try {
			Map<String,Object> sequenceMap = dao.getIterationSegmentTransaction(modelName, iteration);
			JSONObject sequence = new JSONObject();
			double t1_value = ((BigDecimal) sequenceMap.get("t1Value")).doubleValue();
			double t2_value = ((BigDecimal) sequenceMap.get("t2Value")).doubleValue();
			double t3_value = ((BigDecimal) sequenceMap.get("t3Value")).doubleValue();
			
			sequence.put("T2-T1",t2_value - t1_value);
			sequence.put("T3-T2",t3_value - t2_value);
			return sequence.serialize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("SegmentServiceImpl", e);
		}
	}

}
