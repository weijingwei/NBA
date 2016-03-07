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
package com.ibm.nbaopt.lvm.visualizer.service;

import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;

public interface SegmentService {

	public String getIterationSegmentStat(String modelName, String iteration)
			throws ApplicationException;

	public String getIterationSegmentTransaction(String modelName,
			String iteration) throws ApplicationException;

}
