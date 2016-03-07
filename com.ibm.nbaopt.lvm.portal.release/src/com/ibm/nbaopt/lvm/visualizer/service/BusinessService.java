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

import com.ibm.nbaopt.lvm.visualizer.domain.Business;
import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;

public interface BusinessService {
	public String getBusinessChartJson(Business business) throws ApplicationException;
	public String getSegmentInfo(String model, String iteration, String segment) throws ApplicationException;
}
