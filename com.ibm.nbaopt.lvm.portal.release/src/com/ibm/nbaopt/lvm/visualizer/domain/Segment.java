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

/**
 * 2014.12.22  20:22:23
 * @author Cruise
 *
 */
public class Segment {
	private String model;

	private int iteration;
	
	private int segmentId;
	
	private int segmentCount;
	
	private double avgLTV;

	private double avgReward;
	
	private double qEstValueModel;
	
	private double vActual;
	
	private double gain;
	
	private int sampleSize;
	
	private int matchedSampleSize;
	
	private double t1;

	private double t2;

	private double t3;
	
	private int t1size;
	
	private int t2size;
	
	private int t3size;

	private String rule;

	private int segmentIdTo;

	private double prob;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	public int getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}

	public int getSegmentCount() {
		return segmentCount;
	}

	public void setSegmentCount(int segmentCount) {
		this.segmentCount = segmentCount;
	}

	public double getAvgLTV() {
		return avgLTV;
	}

	public void setAvgLTV(double avgLTV) {
		this.avgLTV = avgLTV;
	}

	public double getAvgReward() {
		return avgReward;
	}

	public void setAvgReward(double avgReward) {
		this.avgReward = avgReward;
	}

	public double getqEstValueModel() {
		return qEstValueModel;
	}

	public void setqEstValueModel(double qEstValueModel) {
		this.qEstValueModel = qEstValueModel;
	}

	public double getvActual() {
		return vActual;
	}

	public void setvActual(double vActual) {
		this.vActual = vActual;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	public int getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}

	public int getMatchedSampleSize() {
		return matchedSampleSize;
	}

	public void setMatchedSampleSize(int matchedSampleSize) {
		this.matchedSampleSize = matchedSampleSize;
	}

	public double getT1() {
		return t1;
	}

	public void setT1(double t1) {
		this.t1 = t1;
	}

	public double getT2() {
		return t2;
	}

	public void setT2(double t2) {
		this.t2 = t2;
	}

	public double getT3() {
		return t3;
	}

	public void setT3(double t3) {
		this.t3 = t3;
	}

	public int getT1size() {
		return t1size;
	}

	public void setT1size(int t1size) {
		this.t1size = t1size;
	}

	public int getT2size() {
		return t2size;
	}

	public void setT2size(int t2size) {
		this.t2size = t2size;
	}

	public int getT3size() {
		return t3size;
	}

	public void setT3size(int t3size) {
		this.t3size = t3size;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public int getSegmentIdTo() {
		return segmentIdTo;
	}

	public void setSegmentIdTo(int segmentIdTo) {
		this.segmentIdTo = segmentIdTo;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

}
