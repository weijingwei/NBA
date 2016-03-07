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

import com.ibm.nbaopt.lvm.visualizer.domain.Optimizer;
import com.ibm.nbaopt.lvm.visualizer.service.OptService;
import com.ibm.nbaopt.lvm.visualizer.service.impl.OptServiceImpl;

@Path("optimizer")
public class OptResources {
	private static final Logger log = Logger.getLogger(OptResources.class);
	private OptService service = new OptServiceImpl();

	@GET
	@Path("{MODEL_NAME}/{ITERATION}/actionPie")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getActionAllocationPie(
			@PathParam("MODEL_NAME") String modelName,
			@PathParam("ITERATION") int iteration,
			@Context HttpServletRequest request) {

		try {
			log.info("Get action allocation pie");
			return Response
					.ok()
					.entity(service.getActionAllocationPie(modelName,
							iteration, Optimizer.OPT_ACTION)).build();
		} catch (Exception e) {
			log.error("Get action allocation pie error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}

	@GET
	@Path("{MODEL_NAME}/{ITERATION}/resourceAllocation")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getResourcesAllocation(
			@PathParam("MODEL_NAME") String modelName,
			@PathParam("ITERATION") int iteration) {
		try {
			log.info("Get resource allocation list");
			return Response
					.ok()
					.entity(service.getResourcesAllocation(modelName,
							iteration, Optimizer.OPT_RESOURCE)).build();
		} catch (Exception e) {
			log.error("Get resource list error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
}
