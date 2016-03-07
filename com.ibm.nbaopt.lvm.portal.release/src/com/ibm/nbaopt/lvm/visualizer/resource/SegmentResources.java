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

import com.ibm.nbaopt.lvm.visualizer.service.SegmentService;
import com.ibm.nbaopt.lvm.visualizer.service.impl.SegmentServiceImpl;

@Path("segment")
public class SegmentResources {
	private static final Logger log = Logger.getLogger(SegmentResources.class);
	private SegmentService service = new SegmentServiceImpl();

	@GET
	@Path("{MODELNAME}/{ITERATION}/segmentstat")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIterationSegmentStat(@PathParam("MODELNAME") String modelName,
			@PathParam("ITERATION") String iteration, @Context HttpServletRequest request) {
		try {
			log.info("Get segment stat");
			return Response.ok().entity(service.getIterationSegmentStat(modelName, iteration))
					.build();

		} catch (Exception e) {
			log.error("Get segment stat error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
	
	@GET
	@Path("{MODELNAME}/{ITERATION}/sequence")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIterationSegmentTransaction(@PathParam("MODELNAME") String modelName,
			@PathParam("ITERATION") String iteration, @Context HttpServletRequest request) {
		try {
			log.info("Get segment sequence");
			return Response.ok().entity(service.getIterationSegmentTransaction(modelName, iteration))
					.build();

		} catch (Exception e) {
			log.error("Get segment sequence error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
	
}
