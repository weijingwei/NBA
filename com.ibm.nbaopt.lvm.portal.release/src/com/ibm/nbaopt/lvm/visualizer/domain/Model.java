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

package com.ibm.nbaopt.lvm.visualizer.domain;

import java.util.Date;

/**
 * @author hammer
 * @date Aug 12, 2013
 * Description:
 */
public class Model
{
	private String model;

	private int iteration;

	private String description;

	private double v_Value;

	private double q_Value;

	private double gain;

	private Date modelDate;
	
	private String modelStartTime;
	
	private String modelEndTime;
	
	private String modelTimeCost;

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public int getIteration()
	{
		return iteration;
	}

	public void setIteration(int iteration)
	{
		this.iteration = iteration;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setGain(double gain)
	{
		this.gain = gain;
	}

	public Date getModelDate()
	{
		return modelDate;
	}

	public void setModelDate(Date modelDate)
	{
		this.modelDate = modelDate;
	}
	
	public String getModelStartTime() {
		return modelStartTime;
	}

	public void setModelStartTime(String modelStartTime) {
		this.modelStartTime = modelStartTime;
	}

	public String getModelEndTime() {
		return modelEndTime;
	}

	public void setModelEndTime(String modelEndTime) {
		this.modelEndTime = modelEndTime;
	}
	
	public String getModelTimeCost() {
		return modelTimeCost;
	}

	public void setModelTimeCost(String modelTimeCost) {
		this.modelTimeCost = modelTimeCost;
	}

	public double getV_Value()
	{
		return v_Value;
	}

	public void setV_Value(double v_Value)
	{
		this.v_Value = v_Value;
	}

	public double getQ_Value()
	{
		return q_Value;
	}

	public void setQ_Value(double q_Value)
	{
		this.q_Value = q_Value;
	}

	public double getGain()
	{
		return gain;
	}

}
