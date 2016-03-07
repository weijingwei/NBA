package com.ibm.nbaopt.lvm.visualizer.domain;

public class Optimizer {
	public static final String OPT_ACTION = "Action";

	public static final String OPT_ACTION_GROUP = "ActionGroup";

	public static final String OPT_RESOURCE = "Resource";

	private String model;
	private int iteration;
	private String boundType;
	private String boundName;
	private String boundValue;
	private double lower;
	private double upper;
	private double modelAlloc;
	private double ratio;
	private double lowerRelax;
	private double upperRelax;

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

	public String getBoundType() {
		return boundType;
	}

	public void setBoundType(String boundType) {
		this.boundType = boundType;
	}

	public String getBoundName() {
		return boundName;
	}

	public void setBoundName(String boundName) {
		this.boundName = boundName;
	}

	public String getBoundValue() {
		return boundValue;
	}

	public void setBoundValue(String boundValue) {
		this.boundValue = boundValue;
	}

	public double getLower() {
		return lower;
	}

	public void setLower(double lower) {
		this.lower = lower;
	}

	public double getUpper() {
		return upper;
	}

	public void setUpper(double upper) {
		this.upper = upper;
	}

	public double getModelAlloc() {
		return modelAlloc;
	}

	public void setModelAlloc(double modelAlloc) {
		this.modelAlloc = modelAlloc;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public double getLowerRelax() {
		return lowerRelax;
	}

	public void setLowerRelax(double lowerRelax) {
		this.lowerRelax = lowerRelax;
	}

	public double getUpperRelax() {
		return upperRelax;
	}

	public void setUpperRelax(double upperRelax) {
		this.upperRelax = upperRelax;
	}

}
