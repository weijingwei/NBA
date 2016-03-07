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

package com.ibm.nbaopt.lvm.visualizer.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.ibm.nbaopt.lvm.visualizer.domain.Business;
import com.ibm.nbaopt.lvm.visualizer.service.BusinessService;
import com.ibm.nbaopt.lvm.visualizer.service.impl.BusinessServiceImpl;

@Path("business")
public class BusinessResources {
	private static final Logger log = Logger.getLogger(BusinessResources.class);
	private BusinessService service = new BusinessServiceImpl();

	@GET
	@Path("{MODELNAME}/{ITERATION}/{DATATYPE}/{FILTER}/businessChart")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBusinessChartJson(@PathParam("MODELNAME") String modelName, @PathParam("ITERATION") String iteration,
			@PathParam("DATATYPE") String dataType, @PathParam("FILTER") String filter, @Context HttpServletRequest request) {
		try {
			log.info("Get Business Insight Chart");
			Business business = new Business();
			business.setModelName(modelName);
			business.setIteration(iteration);
			business.setDataType(dataType);
			String[] filters = filter.split("::");
			for (String str : filters) {
				if (!str.isEmpty()) {
					String[] value = str.split(":");
					if (value[0].equals("selfConnection")) {
						business.setShowSelfConnection(value[1].equals("showing"));
					} else if (value[0].equals("searchSegment")) {
						business.setSearchSegment(value[1]);
					} else {
						// ...
					}
				}
			}
			return Response.ok().entity(service.getBusinessChartJson(business)).build();
		} catch (Exception e) {
			log.error("Get business insight chart error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
	
	@GET
	@Path("{MODELNAME}/{ITERATION}/{SEGMENT}/segmentInfo")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSegment(@PathParam("MODELNAME") String modelName, @PathParam("ITERATION") String iteration,
			@PathParam("SEGMENT") String segment, @Context HttpServletRequest request) {
		try {
			log.info("Get Segment informations");
			return Response.ok().entity(service.getSegmentInfo(modelName, iteration, segment)).build();
		} catch (Exception e) {
			log.error("Get Segment informations failure", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
}
