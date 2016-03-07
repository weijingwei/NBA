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

import com.ibm.nbaopt.lvm.visualizer.service.FeatureService;
import com.ibm.nbaopt.lvm.visualizer.service.impl.FeatureServiceImpl;




@Path("feature")
public class FeatureResources
{
	private static final Logger log = Logger.getLogger(FeatureResources.class);
	
	@GET
	@Path("{MODEL_NAME}/{ITERATION}/featureCount")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIterationFeatureCount(@PathParam("MODEL_NAME") String modelName, @PathParam("ITERATION") int iteration, @Context HttpServletRequest request){
		
		try
		{
			log.info("Get Iteration Feature Count.");
			FeatureService service = new FeatureServiceImpl();
			return Response
					.ok()
					.entity(service.getIterationFeatureCount(modelName,
							iteration)).build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
	
	@GET
	@Path("{MODEL_NAME}/{ITERATION}/featureMostUsed")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIterationFeatureMostUsed(@PathParam("MODEL_NAME") String modelName, @PathParam("ITERATION") int iteration, @Context HttpServletRequest request){
		
		try
		{
			log.info("Get Iteration Feature Most Usage.");
			FeatureService service = new FeatureServiceImpl();
			return Response
					.ok()
					.entity(service.getIterationFeatureMostUsed(modelName,
							iteration)).build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
}
