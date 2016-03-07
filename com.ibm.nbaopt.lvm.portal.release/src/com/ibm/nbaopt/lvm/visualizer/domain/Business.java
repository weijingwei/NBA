package com.ibm.nbaopt.lvm.visualizer.domain;

import java.util.ArrayList;
import java.util.List;

public class Business {
	public static final String PROB = "prob";
	public static final String LTV = "LTV";
	public static final String ALL = "all";
	private List<Filter> filters = new ArrayList<Filter>();
	private List<SegmentTransition> segmentTransitions = new ArrayList<SegmentTransition>();
	private String dataType;
	private String searchSegment = "";
	private String modelName;
	private String iteration;
	private boolean showSelfConnection = false;
	private double maxProb = 0;
	private double minProb = 0;
	private double maxLTV = 0;
	private double minLTV = 0;
	private int maxCount = 0;
	private int minCount = 0;
	private int topNum = 1;
	
	public String getNodeColor(double ltv) {
		if (Math.floor((ltv - getMinLTV()) / getBaseLTV()) < 256) {
			String result = Integer.toHexString((int) Math.floor((ltv - getMinLTV()) / getBaseLTV())) + "00";
			if (result.length() < 4) {result = "0" + result;}
			return "#ff" + result;
		} else {
			String result = "ff00";
			result = Integer.toHexString((int) (511 - Math.floor((ltv - getMinLTV()) / getBaseLTV()))) + result;
			if (result.length() < 6) {result = "0" + result;}
			return "#" + result;
		}
	}
	
	public int getNodeSize(int segmentCount) {
		return (int) Math.ceil((segmentCount - getMinCount()) / getBaseNodeCount()) + 1;
	}
	
	public String getLinkColor(double prob) {
		int index = (int) (230 - (prob - getMinProb()) / getBaseProb());
		String result = Integer.toHexString(index);
		if (result.length() < 2) {result = "0" + result;}
		return "#" + result + result + result;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getIteration() {
		return iteration;
	}

	public void setIteration(String iteration) {
		this.iteration = iteration;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public List<SegmentTransition> getSegmentTransitions() {
		return segmentTransitions;
	}

	public void setSegmentTransitions(List<SegmentTransition> segmentTransitions) {
		this.segmentTransitions = segmentTransitions;
		for (SegmentTransition segmentTransition : segmentTransitions) {
			if (getMaxLTV() < segmentTransition.getFrom_LTV()) {
				setMaxLTV(segmentTransition.getFrom_LTV());
			}
			if (getMinLTV() > segmentTransition.getFrom_LTV() || getMinLTV() == 0) {
				setMinLTV(segmentTransition.getFrom_LTV());
			}
			if (getMaxLTV() < segmentTransition.getTo_LTV()) {
				setMaxLTV(segmentTransition.getTo_LTV());
			}
			if (getMinLTV() > segmentTransition.getTo_LTV() || getMinLTV() == 0) {
				setMinLTV(segmentTransition.getTo_LTV());
			}
			if (getMaxProb() < segmentTransition.getProb()) {
				setMaxProb(segmentTransition.getProb());
			}
			if (getMinProb() > segmentTransition.getProb() || getMinProb() == 0) {
				setMinProb(segmentTransition.getProb());
			}
			if (getMaxCount() < segmentTransition.getFrom_segmentCount()) {
				setMaxCount(segmentTransition.getFrom_segmentCount());
			}
			if (getMaxCount() < segmentTransition.getTo_segmentCount()) {
				setMaxCount(segmentTransition.getTo_segmentCount());
			}
			if (getMinCount() > segmentTransition.getFrom_segmentCount() || getMinCount() == 0) {
				setMinCount(segmentTransition.getFrom_segmentCount());
			}
			if (getMinCount() > segmentTransition.getTo_segmentCount() || getMinCount() == 0) {
				setMinCount(segmentTransition.getTo_segmentCount());
			}
		}
	}

	public double getMaxProb() {
		return maxProb;
	}

	public void setMaxProb(double maxProb) {
		this.maxProb = maxProb;
	}
	
	public double getMinProb() {
		return minProb;
	}

	public void setMinProb(double minProb) {
		this.minProb = minProb;
	}

	public double getMaxLTV() {
		return maxLTV;
	}

	public void setMaxLTV(double maxLTV) {
		this.maxLTV = maxLTV;
	}

	public double getMinLTV() {
		return minLTV;
	}

	public void setMinLTV(double minLTV) {
		this.minLTV = minLTV;
	}
	
	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getMinCount() {
		return minCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}
	
	private double getBaseProb() {
		return (getMaxProb() - getMinProb()) / 230;
	}
	
	public double getBaseLTV() {
		return (getMaxLTV() - getMinLTV()) / 511;
	}
	
	public double getBaseNodeCount() {
		return (getMaxCount() - getMinCount()) / 19;
	}

	public boolean isShowSelfConnection() {
		return showSelfConnection;
	}

	public void setShowSelfConnection(boolean showSelfConnection) {
		this.showSelfConnection = showSelfConnection;
	}

	public String getSearchSegment() {
		return searchSegment;
	}

	public void setSearchSegment(String searchSegment) {
		this.searchSegment = searchSegment;
	}

	public String getTopNum() {
		return topNum + "";
	}

	public void setTopNum(int topNum) {
		this.topNum = topNum;
	}

	public class Filter {
		private int from;
		private int to;

		public int getFrom() {
			return from;
		}

		public void setFrom(int from) {
			this.from = from;
		}

		public int getTo() {
			return to;
		}

		public void setTo(int to) {
			this.to = to;
		}
	}

}