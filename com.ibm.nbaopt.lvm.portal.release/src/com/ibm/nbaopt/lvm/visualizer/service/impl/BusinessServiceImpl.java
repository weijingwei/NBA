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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.nbaopt.lvm.visualizer.dao.BusinessDao;
import com.ibm.nbaopt.lvm.visualizer.domain.Business;
import com.ibm.nbaopt.lvm.visualizer.domain.SegmentTransition;
import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;
import com.ibm.nbaopt.lvm.visualizer.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {
	
	private BusinessDao dao = new BusinessDao();

	@Override
	public String getBusinessChartJson(Business business) throws ApplicationException {
		try {
			dao.getBusinessInsight(business);
			return buildD3Data(business);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("BusinessServiceImpl", e);
		}
	}
	
	private String buildD3Data(Business business) throws ApplicationException {
//		sortSegmentTransitions(business);
		Collections.sort(business.getSegmentTransitions());
		try {
			JSONObject d3Data = new JSONObject();
			JSONArray links = generateLinks(business);
			d3Data.put("links", links);
			Map<Integer, JSONObject> segments = getSegments(links);
			JSONObject category = generateCategory(segments);
			d3Data.put("category", category);
			resetNodeColor(category, links);
			searchSegment(business, links, category);
			return d3Data.serialize();
		} catch (IOException e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	
	private void sortSegmentTransitions(Business business) {
		List<SegmentTransition> segmentTransitions = business.getSegmentTransitions();
		List<SegmentTransition> results = new ArrayList<SegmentTransition>();
		Map<Integer, List<SegmentTransition>> segmentTransitionMap = new TreeMap<Integer, List<SegmentTransition>>();
		for (SegmentTransition segmentTransition : segmentTransitions) {
			int level = sortSegmentTransitions(segmentTransitions, segmentTransition, 0);
			if (segmentTransitionMap.get(level) == null) {
				segmentTransitionMap.put(level, new ArrayList<SegmentTransition>());
			}
			segmentTransitionMap.get(level).add(segmentTransition);
		}
		List<Integer> keys = new ArrayList<Integer>(segmentTransitionMap.keySet());
		Collections.sort(keys);
		for (Integer key : keys) {
			List<SegmentTransition> value = segmentTransitionMap.get(key);
			Collections.sort(value);
			results.addAll(value);
		}
		business.setSegmentTransitions(results);
	}
	
	private int sortSegmentTransitions (List<SegmentTransition> segmentTransitions, SegmentTransition currentSegmentTransition, int level) {
		int currentLevel = level;
		for (SegmentTransition segmentTransition : segmentTransitions) {
			if (segmentTransition.getSegmentId_to() == currentSegmentTransition.getSegmentId_from()) {
				int l = sortSegmentTransitions(segmentTransitions, segmentTransition, level + 1);
				if (l > currentLevel) {
					currentLevel = l;
				}
			}
		}
		return currentLevel;
	}

	private JSONObject generateCategory(Map<Integer, JSONObject> segments) {
		/*2% 18% 20% 30% 30%*/
		JSONObject category = new JSONObject();
		JSONArray great = new JSONArray();
		JSONArray good = new JSONArray();
		JSONArray medium = new JSONArray();
		JSONArray worse = new JSONArray();
		JSONArray poor = new JSONArray();
		category.put("great", great);
		category.put("good", good);
		category.put("medium", medium);
		category.put("worse", worse);
		category.put("poor", poor);
		category.put("greatPercent", "2%");
		category.put("goodPercent", "18%");
		category.put("mediumPercent", "20%");
		category.put("worsePercent", "30%");
		category.put("poorPercent", "30%");
		int count = 0;
		for (JSONObject segment : segments.values()) {
			count += Integer.parseInt(segment.get("count").toString());
		}
		int countSum = 0;
		for (JSONObject segment : segments.values()) {
			if (countSum + Integer.parseInt(segment.get("count").toString()) <= count * 0.02) {
				great.add(segment);
			} else if (countSum + Integer.parseInt(segment.get("count").toString()) <= count * 0.2) {
				good.add(segment);
			} else if (countSum + Integer.parseInt(segment.get("count").toString()) <= count * 0.4) {
				medium.add(segment);
			} else if (countSum + Integer.parseInt(segment.get("count").toString()) <= count * 0.7) {
				worse.add(segment);
			} else {
				poor.add(segment);
			}
			countSum += Integer.parseInt(segment.get("count").toString());
		}
		return category;
	}

	private JSONArray generateLinks(Business business) {
		JSONArray links = new JSONArray();
		for (SegmentTransition segmentTransition : business.getSegmentTransitions()) {
			JSONObject link = new JSONObject();
			links.add(link);
			link.put("linkColor", business.getLinkColor(segmentTransition.getProb()));
			link.put("sourceNodeSize", business.getNodeSize(segmentTransition.getFrom_segmentCount()));
			link.put("targetNodeSize", business.getNodeSize(segmentTransition.getTo_segmentCount()));
			link.put("sourceNodeColor", business.getNodeColor(segmentTransition.getFrom_LTV()));
			link.put("targetNodeColor", business.getNodeColor(segmentTransition.getTo_LTV()));
			link.put("model", segmentTransition.getModel());
			link.put("iteration", segmentTransition.getIteration());
			link.put("segmentId_from", segmentTransition.getSegmentId_from());
			link.put("segmentId_to", segmentTransition.getSegmentId_to());
			link.put("avg_LTV", segmentTransition.getAvg_LTV());
			link.put("from_LTV", segmentTransition.getFrom_LTV());
			link.put("to_LTV", segmentTransition.getTo_LTV());
			link.put("from_segmentCount", segmentTransition.getFrom_segmentCount());
			link.put("to_segmentCount", segmentTransition.getTo_segmentCount());
			link.put("prob", segmentTransition.getProb());
			link.put("gain", segmentTransition.getGain());
		}
		return links;
	}

	@SuppressWarnings("unchecked")
	private void resetNodeColor(JSONObject category, JSONArray links) {
		JSONArray greats = (JSONArray) category.get("great");
		JSONArray goods = (JSONArray) category.get("good");
		JSONArray mediums = (JSONArray) category.get("medium");
		JSONArray worses = (JSONArray) category.get("worse");
		JSONArray poors = (JSONArray) category.get("poor");
		Iterator<JSONObject> linkIterator = null;
		
		if (greats.size() > 0) {
			double maxLTV = (Double)((JSONObject)greats.get(0)).get("LTV");
			double minLTV = (Double)((JSONObject)greats.get(greats.size() - 1)).get("LTV");
			double interval = (maxLTV - minLTV) / 101;
			Iterator<JSONObject> iterator = greats.iterator();
			while (iterator.hasNext()) {
				JSONObject great = iterator.next();
				double ltv = (Double) great.get("LTV");
				String color = "";
				if (interval == 0) {
					color = "#00ff00";
				} else {
					color = Integer.toHexString((int) Math.floor((maxLTV - ltv) / interval));
					if (color.length() < 2) {
						color = "0" + color;
					}
					color = "#" + color + "ff00";
				}
				great.put("color", color);
				linkIterator = links.iterator();
				while (linkIterator.hasNext()) {
					JSONObject link = linkIterator.next();
					if (link.get("segmentId_from").toString().equals(great.get("id").toString())) {
						link.put("sourceNodeColor", color);
					}
					if (link.get("segmentId_to").toString().equals(great.get("id").toString())) {
						link.put("targetNodeColor", color);
					}
				}
			}
		}
		if (goods.size() > 0) {
			double maxLTV = (Double)((JSONObject)goods.get(0)).get("LTV");
			double minLTV = (Double)((JSONObject)goods.get(goods.size() - 1)).get("LTV");
			double interval = (maxLTV - minLTV) / 101;
			Iterator<JSONObject> iterator = goods.iterator();
			while (iterator.hasNext()) {
				JSONObject good = iterator.next();
				double ltv = (Double) good.get("LTV");
				String color = "";
				if (interval == 0) {
					color = "#98ff00";
				} else {
					color = "#" + Integer.toHexString((int) Math.floor((maxLTV - ltv) / interval) + 102) + "ff00";
				}
				good.put("color", color);
				linkIterator = links.iterator();
				while (linkIterator.hasNext()) {
					JSONObject link = linkIterator.next();
					if (link.get("segmentId_from").toString().equals(good.get("id").toString())) {
						link.put("sourceNodeColor", color);
					}
					if (link.get("segmentId_to").toString().equals(good.get("id").toString())) {
						link.put("targetNodeColor", color);
					}
				}
			}
		}
		if (mediums.size() > 0) {
			double maxLTV = (Double)((JSONObject)mediums.get(0)).get("LTV");
			double minLTV = (Double)((JSONObject)mediums.get(mediums.size() - 1)).get("LTV");
			double interval = (maxLTV - minLTV) / 103;
			Iterator<JSONObject> iterator = mediums.iterator();
			while (iterator.hasNext()) {
				JSONObject medium = iterator.next();
				double ltv = (Double) medium.get("LTV");
				String color = "";
				if (interval == 0) {
					color = "#ffff00";
				} else {
					if (Math.floor((maxLTV - ltv) / interval) < 52) {
						color = "#" + color + Integer.toHexString((int) Math.floor((maxLTV - ltv) / interval) + 204) + "ff00";
					} else {
						color = "#" + color + "ff" + Integer.toHexString((int) Math.floor(((ltv - minLTV) / interval)) + 204) + "00";
					}
				}
				medium.put("color", color);
				linkIterator = links.iterator();
				while (linkIterator.hasNext()) {
					JSONObject link = linkIterator.next();
					if (link.get("segmentId_from").toString().equals(medium.get("id").toString())) {
						link.put("sourceNodeColor", color);
					}
					if (link.get("segmentId_to").toString().equals(medium.get("id").toString())) {
						link.put("targetNodeColor", color);
					}
				}
			}
		}
		if (worses.size() > 0) {
			double maxLTV = (Double)((JSONObject)worses.get(0)).get("LTV");
			double minLTV = (Double)((JSONObject)worses.get(worses.size() - 1)).get("LTV");
			double interval = (maxLTV - minLTV) / 101;
			Iterator<JSONObject> iterator = worses.iterator();
			while (iterator.hasNext()) {
				JSONObject worse = iterator.next();
				double ltv = (Double) worse.get("LTV");
				String color = "";
				if (interval == 0) {
					color = "#ff9800";
				} else {
					color = "#ff" + Integer.toHexString((int) Math.floor((ltv - minLTV) / interval) + 102) + "00";
				}
				worse.put("color", color);
				linkIterator = links.iterator();
				while (linkIterator.hasNext()) {
					JSONObject link = linkIterator.next();
					if (link.get("segmentId_from").toString().equals(worse.get("id").toString())) {
						link.put("sourceNodeColor", color);
					}
					if (link.get("segmentId_to").toString().equals(worse.get("id").toString())) {
						link.put("targetNodeColor", color);
					}
				}
			}
		}
		if (poors.size() > 0) {
			double maxLTV = (Double)((JSONObject)poors.get(0)).get("LTV");
			double minLTV = (Double)((JSONObject)poors.get(poors.size() - 1)).get("LTV");
			double interval = (maxLTV - minLTV) / 101;
			Iterator<JSONObject> iterator = poors.iterator();
			while (iterator.hasNext()) {
				JSONObject poor = iterator.next();
				double ltv = (Double) poor.get("LTV");
				String color = "";
				if (interval == 0) {
					color = "#ff0000";
				} else {
					color = Integer.toHexString((int) Math.floor((ltv - minLTV) / interval)) + "00";
					if (color.length() < 4) {
						color = "0" + color;
					}
					color = "#ff" + color;
				}
				poor.put("color", color);
				linkIterator = links.iterator();
				while (linkIterator.hasNext()) {
					JSONObject link = linkIterator.next();
					if (link.get("segmentId_from").toString().equals(poor.get("id").toString())) {
						link.put("sourceNodeColor", color);
					}
					if (link.get("segmentId_to").toString().equals(poor.get("id").toString())) {
						link.put("targetNodeColor", color);
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void searchSegment(Business business, JSONArray links, JSONObject category) {
		String searchSegment = business.getSearchSegment().trim();
		String[] ids = searchSegment.split(",");
		List<String> idList = Arrays.asList(ids);
		Iterator iterator = links.iterator();
		if (idList.size() > 0 && !idList.get(0).isEmpty()) {
			while(iterator.hasNext()) {
				JSONObject link = (JSONObject) iterator.next();
				if (!idList.contains(link.get("segmentId_from").toString()) && !idList.contains(link.get("segmentId_to").toString())) {
					iterator.remove();
				}
			}
		}
		
		double LTV = 0;
		Iterator greats = ((JSONArray)category.get("great")).iterator();
		boolean isExist = false;
		while(greats.hasNext()) {
			JSONObject great = (JSONObject) greats.next();
			isExist = false;
			iterator = links.iterator();
			while(iterator.hasNext()) {
				JSONObject link = (JSONObject) iterator.next();
				if (link.get("segmentId_from").toString().equals(great.get("id").toString()) || link.get("segmentId_to").toString().equals(great.get("id").toString())) {
					isExist = true;
				}
			}
			if (!isExist) {
				greats.remove();
			} else {
				LTV += Double.parseDouble(great.get("LTV").toString());
			}
		}
		if (LTV == 0) {
			category.put("greatAvgLTV", "");
		} else {
			category.put("greatAvgLTV", String.format("%.3f", LTV / ((JSONArray)category.get("great")).size()));
		}
		
		LTV = 0;
		Iterator goods = ((JSONArray)category.get("good")).iterator();
		while(goods.hasNext()) {
			JSONObject good = (JSONObject) goods.next();
			isExist = false;
			iterator = links.iterator();
			while(iterator.hasNext()) {
				JSONObject link = (JSONObject) iterator.next();
				if (link.get("segmentId_from").toString().equals(good.get("id").toString()) || link.get("segmentId_to").toString().equals(good.get("id").toString())) {
					isExist = true;
				}
			}
			if (!isExist) {
				goods.remove();
			} else {
				LTV += Double.parseDouble(good.get("LTV").toString());
			}
		}
		if (LTV == 0) {
			category.put("goodAvgLTV", "");
		} else {
			category.put("goodAvgLTV", String.format("%.3f", LTV / ((JSONArray)category.get("good")).size()));
		}
		
		LTV = 0;
		Iterator mediums = ((JSONArray)category.get("medium")).iterator();
		while(mediums.hasNext()) {
			JSONObject medium = (JSONObject) mediums.next();
			isExist = false;
			iterator = links.iterator();
			while(iterator.hasNext()) {
				JSONObject link = (JSONObject) iterator.next();
				if (link.get("segmentId_from").toString().equals(medium.get("id").toString()) || link.get("segmentId_to").toString().equals(medium.get("id").toString())) {
					isExist = true;
				}
			}
			if (!isExist) {
				mediums.remove();
			} else {
				LTV += Double.parseDouble(medium.get("LTV").toString());
			}
		}
		if (LTV == 0) {
			category.put("mediumAvgLTV", "");
		} else {
			category.put("mediumAvgLTV", String.format("%.3f", LTV / ((JSONArray)category.get("medium")).size()));
		}
		
		LTV = 0;
		Iterator worses = ((JSONArray)category.get("worse")).iterator();
		while(worses.hasNext()) {
			JSONObject worse = (JSONObject) worses.next();
			isExist = false;
			iterator = links.iterator();
			while(iterator.hasNext()) {
				JSONObject link = (JSONObject) iterator.next();
				if (link.get("segmentId_from").toString().equals(worse.get("id").toString()) || link.get("segmentId_to").toString().equals(worse.get("id").toString())) {
					isExist = true;
				}
			}
			if (!isExist) {
				worses.remove();
			} else {
				LTV += Double.parseDouble(worse.get("LTV").toString());
			}
		}
		if (LTV == 0) {
			category.put("worseAvgLTV", "");
		} else {
			category.put("worseAvgLTV", String.format("%.3f", LTV / ((JSONArray)category.get("worse")).size()));
		}
		
		LTV = 0;
		Iterator poors = ((JSONArray)category.get("poor")).iterator();
		while(poors.hasNext()) {
			JSONObject poor = (JSONObject) poors.next();
			isExist = false;
			iterator = links.iterator();
			while(iterator.hasNext()) {
				JSONObject link = (JSONObject) iterator.next();
				if (link.get("segmentId_from").toString().equals(poor.get("id").toString()) || link.get("segmentId_to").toString().equals(poor.get("id").toString())) {
					isExist = true;
				}
			}
			if (!isExist) {
				poors.remove();
			} else {
				LTV += Double.parseDouble(poor.get("LTV").toString());
			}
		}
		if (LTV == 0) {
			category.put("poorAvgLTV", "");
		} else {
			category.put("poorAvgLTV", String.format("%.3f", LTV / ((JSONArray)category.get("poor")).size()));
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<Integer, JSONObject> getSegments(JSONArray links) {
		Map<Integer, JSONObject> segments = new LinkedHashMap<Integer, JSONObject>();
		Iterator iterator = links.iterator();
		while (iterator.hasNext()) {
			JSONObject link = (JSONObject) iterator.next();
			if (!segments.containsKey(link.get("segmentId_from"))) {
				JSONObject segment = new JSONObject();
				segment.put("id", link.get("segmentId_from"));
				segment.put("LTV", link.get("from_LTV"));
				segment.put("count", link.get("from_segmentCount"));
				segment.put("color", link.get("sourceNodeColor"));
				segments.put((Integer) link.get("segmentId_from"), segment);
			}
			if (!segments.containsKey(link.get("segmentId_to"))) {
				JSONObject segment = new JSONObject();
				segment.put("id", link.get("segmentId_to"));
				segment.put("LTV", link.get("to_LTV"));
				segment.put("count", link.get("to_segmentCount"));
				segment.put("color", link.get("targetNodeColor"));
				segments.put((Integer) link.get("segmentId_to"), segment);
			}
		}
		
		List<Map.Entry<Integer, JSONObject>> mapList = new ArrayList<Map.Entry<Integer,JSONObject>>(segments.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<Integer, JSONObject>>() {
			@Override
			public int compare(Entry<Integer, JSONObject> o1, Entry<Integer, JSONObject> o2) {
				return ((Double)o2.getValue().get("LTV")).compareTo(((Double)o1.getValue().get("LTV")));
			}

		});
		segments.clear();
		for (Entry<Integer, JSONObject> entry : mapList) {
			segments.put(entry.getKey(), entry.getValue());
		}
		return segments;
	}

	@Override
	public String getSegmentInfo(String model, String iteration, String segment) throws ApplicationException {
		try {
			JSONObject result = new JSONObject();
			StringBuffer sb = new StringBuffer();
			List<Map<String, Object>> segmentRules = dao.getSegmentRules(model, iteration, segment);
			for (Map<String, Object> map : segmentRules) {
				sb.append(map.get("EXPRESSION") + "<br/>");
			}
			result.put("rule", sb.toString());
			List<Map<String, Object>> segmentAction = dao.getAction(model, iteration, segment);
			JSONArray jsonAction = new JSONArray();
			for (Map<String, Object> map : segmentAction) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("action", map.get("ACTION"));
				jsonObject.put("coefficient", map.get("ACTION_COEFF"));
				jsonObject.put("actualCount", map.get("ACTION_COUNT"));
				jsonObject.put("allocationCount", map.get("ALLOCATED_COUNT"));
				jsonAction.add(jsonObject);
			}
			result.put("action", jsonAction);
			JSONArray jsonSegment = new JSONArray();
			List<Map<String, Object>> segmentInfo = dao.getSegmentStats(model, iteration, segment);
			for (Map<String, Object> map : segmentInfo) {
				for (String key : map.keySet()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put(key, map.get(key));
					jsonSegment.add(jsonObject);
				}
			}
			result.put("info", jsonSegment);
			return result.serialize();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("BusinessServiceImpl", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException("BusinessServiceImpl", e);
		}
	}

}
