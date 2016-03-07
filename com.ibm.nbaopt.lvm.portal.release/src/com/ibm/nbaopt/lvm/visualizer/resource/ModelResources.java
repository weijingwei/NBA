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

import com.ibm.nbaopt.lvm.visualizer.service.ModelService;
import com.ibm.nbaopt.lvm.visualizer.service.impl.ModelServiceImpl;

@Path("model")
public class ModelResources {
	private static final Logger log = Logger.getLogger(ModelResources.class);

	private static ModelService service = new ModelServiceImpl();

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllModelChart(@Context HttpServletRequest request) {
		try {
			log.info("Get all model information");
			return Response.ok()
					.entity(service.getAllModelJson()).build();
		} catch (Exception e) {
			log.error("Get all model chart error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}

	}
	
	@GET
	@Path("{MODEL_NAME}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSpecifiedModelChart(
			@PathParam("MODEL_NAME") String modelName,
			@Context HttpServletRequest request) {
		try {
			log.info("Get spefic model chart");
			ModelService service = new ModelServiceImpl();
			return Response.ok()
					.entity(service.getSpecifiedModelJson(modelName)).build();
		} catch (Exception e) {
			log.error("Get spefic model chart error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}

	}
	

}
