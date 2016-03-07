package com.ibm.nbaopt.lvm.visualizer.domain;

public class ActionStat {
	
	private String model;

	private String interation;

	private String segmentid;

	private String action;

	private Double action_coeff;

	private int action_count;

	private int allocated_count;

	private Double averagereward;

	private Double q_est_value_model;

	private int samplesize;

	private int matchedsamplesize;

	private int valid_count;

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public String getInteration()
	{
		return interation;
	}

	public void setInteration(String interation)
	{
		this.interation = interation;
	}

	public String getSegmentid()
	{
		return segmentid;
	}

	public void setSegmentid(String segmentid)
	{
		this.segmentid = segmentid;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public Double getAction_coeff()
	{
		return action_coeff;
	}

	public void setAction_coeff(Double action_coeff)
	{
		this.action_coeff = action_coeff;
	}

	public int getAction_count()
	{
		return action_count;
	}

	public void setAction_count(int action_count)
	{
		this.action_count = action_count;
	}

	public int getAllocated_count()
	{
		return allocated_count;
	}

	public void setAllocated_count(int allocated_count)
	{
		this.allocated_count = allocated_count;
	}

	public Double getAveragereward()
	{
		return averagereward;
	}

	public void setAveragereward(Double averagereward)
	{
		this.averagereward = averagereward;
	}

	public Double getQ_est_value_model()
	{
		return q_est_value_model;
	}

	public void setQ_est_value_model(Double q_est_value_model)
	{
		this.q_est_value_model = q_est_value_model;
	}

	public int getSamplesize()
	{
		return samplesize;
	}

	public void setSamplesize(int samplesize)
	{
		this.samplesize = samplesize;
	}

	public int getMatchedsamplesize()
	{
		return matchedsamplesize;
	}

	public void setMatchedsamplesize(int matchedsamplesize)
	{
		this.matchedsamplesize = matchedsamplesize;
	}

	public int getValid_count()
	{
		return valid_count;
	}

	public void setValid_count(int valid_count)
	{
		this.valid_count = valid_count;
	}
}
