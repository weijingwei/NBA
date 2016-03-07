/**********************************************************
* Licensed Materials - Property of IBM
* 6949 - 002
* (c) Copyright IBM Corp. 2012, 2013 All Rights Reserved.
***********************************************************/
package com.ibm.nbaopt.lvm.visualizer.localization;

import org.eclipse.osgi.util.NLS;

public class ResourceLabel extends NLS
{
	private static final String BUNDLE_NAME = "com.ibm.nbaopt.lvm.visualizer.localization.resource";

	static
	{
		NLS.initializeMessages(BUNDLE_NAME, ResourceLabel.class);
	}
	
	// general
	public static String IDENTIFIER;
	public static String ITEMS;
	public static String LABEL;
	
	// Segments overview
	public static String SEGMENT_ID;
	public static String STATISTICS;
	public static String COEFFICIENT;
	public static String ALLOCATION;
}
