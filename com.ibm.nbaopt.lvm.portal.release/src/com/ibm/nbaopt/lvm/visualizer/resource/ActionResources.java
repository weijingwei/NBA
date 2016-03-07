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

import com.ibm.nbaopt.lvm.visualizer.service.ActionService;
import com.ibm.nbaopt.lvm.visualizer.service.impl.ActionServiceImpl;

@Path("action")
public class ActionResources {

	private static final Logger log = Logger.getLogger(ActionResources.class);
	private ActionService service = new ActionServiceImpl();
	@GET
	@Path("{MODEL_NAME}/advantage")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getModelAdvantageChart(
			@PathParam("MODEL_NAME") String modelName,
			@Context HttpServletRequest request) {
		try {
			log.info("Get spefic model advantage chart");
			return Response.ok()
					.entity(service.getModelAdvantageJson(modelName)).build();
		} catch (Exception e) {
			log.error("Get spefic model advantage chart error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}

	}
	
	@GET
	@Path("{MODELNAME}/{ITERATION}/actionstat")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getModelActionAllocation(@PathParam("MODELNAME") String modelName,
			@PathParam("ITERATION") int iteration, @Context HttpServletRequest request) {
		try {
			log.info("Get action allocation stat");
			return Response.ok()
					.entity(service.getModelActionAllocation(modelName, iteration)).build();

		} catch (Exception e) {
			log.error("Get action allocation error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
	
	@GET
	@Path("{MODELNAME}/actionstat")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUniqueActions(@PathParam("MODELNAME") String modelName,@Context HttpServletRequest request) {
		try {
			log.info("Get distinct actions ");
			return Response.ok()
					.entity(service.getUniqueActions(modelName)).build();

		} catch (Exception e) {
			log.error("Get distinct actions  error", e);
			e.printStackTrace();
			return Response.serverError().entity("error").build();
		}
	}
	
	
}
