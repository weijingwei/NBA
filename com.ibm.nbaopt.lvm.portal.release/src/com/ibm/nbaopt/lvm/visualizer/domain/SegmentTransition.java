package com.ibm.nbaopt.lvm.visualizer.domain;

public class SegmentTransition implements Comparable<SegmentTransition>{
	private String model;
	private int iteration;
	private int segmentId_from;
	private int segmentId_to;
	private double avg_LTV;
	private double from_LTV; // source node color
	private double to_LTV; // target node color
	private int from_segmentCount; //source node size
	private int to_segmentCount; // target node size
	private double prob; // link color
	private double gain;
	
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
	
	public int getSegmentId_from() {
		return segmentId_from;
	}
	
	public void setSegmentId_from(int segmentId_from) {
		this.segmentId_from = segmentId_from;
	}
	
	public double getAvg_LTV() {
		return avg_LTV;
	}
	
	public void setAvg_LTV(double avg_LTV) {
		this.avg_LTV = avg_LTV;
	}
	
	public int getSegmentId_to() {
		return segmentId_to;
	}
	
	public void setSegmentId_to(int segmentId_to) {
		this.segmentId_to = segmentId_to;
	}
	
	public double getProb() {
		return prob;
	}
	
	public void setProb(double prob) {
		this.prob = prob;
	}
	
	public double getFrom_LTV() {
		return from_LTV;
	}
	
	public void setFrom_LTV(double from_LTV) {
		this.from_LTV = from_LTV;
	}
	
	public double getTo_LTV() {
		return to_LTV;
	}
	
	public void setTo_LTV(double to_LTV) {
		this.to_LTV = to_LTV;
	}
	
	public double getGain() {
		return gain;
	}
	
	public void setGain(double gain) {
		this.gain = gain;
	}
	
	public int getFrom_segmentCount() {
		return from_segmentCount;
	}
	
	public void setFrom_segmentCount(int from_segmentCount) {
		this.from_segmentCount = from_segmentCount;
	}
	
	public int getTo_segmentCount() {
		return to_segmentCount;
	}
	
	public void setTo_segmentCount(int to_segmentCount) {
		this.to_segmentCount = to_segmentCount;
	}

	@Override
	public int compareTo(SegmentTransition o) {
		/*if (o.getTo_LTV() == this.getTo_LTV()) {
			return (int) (o.getFrom_LTV() - this.getFrom_LTV());
		}
		return (int) (o.getTo_LTV() - this.getTo_LTV());*/
		if (o.getFrom_LTV() == this.getFrom_LTV()) {
			return (int) (o.getTo_LTV() - this.getTo_LTV());
		}
		return (int) (o.getFrom_LTV() - this.getFrom_LTV());
	}

	@Override
	public String toString() {
		return "From " + getSegmentId_from() + " to " + getSegmentId_to();
	}
	
}
