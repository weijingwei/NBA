package com.ibm.nbaopt.lvm.visualizer.domain;

public class FeatureCount {

	private String model;

	private int iteration;

	private String feature;

	private int use_count;

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

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public int getUse_count() {
		return use_count;
	}

	public void setUse_count(int use_count) {
		this.use_count = use_count;
	}

	
}
