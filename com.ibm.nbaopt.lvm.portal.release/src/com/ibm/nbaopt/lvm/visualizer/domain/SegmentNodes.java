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

public class SegmentNodes {

	private String model;

	private int iteration;

	private int nodeId;

	private int parentId;

	private int segment;

	private String expression;

	private int levelNum;

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

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getparentId() {
		return parentId;
	}

	public void setparentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSgment() {
		return segment;
	}

	public void setSegment(int segment) {
		this.segment = segment;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public int getlevelNum() {
		return levelNum;
	}

	public void setlevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
}
