package com.ibm.nbaopt.lvm.visualizer.service;

import com.ibm.nbaopt.lvm.visualizer.exception.ApplicationException;

public interface OptService {

	public String getActionAllocationPie(String modelName, int iteration,
			String boundType) throws ApplicationException;

	public String getResourcesAllocation(String modelName, int iteration,
			String boundType) throws ApplicationException;

}
