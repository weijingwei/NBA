package com.ibm.nbaopt.lvm.visualizer.service;

import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;

public interface FeatureService {

	public String getIterationFeatureCount(String modelName, int iteration) throws ApplicationException;
	
	public String getIterationFeatureMostUsed(String modelName, int iteration) throws ApplicationException;

}
