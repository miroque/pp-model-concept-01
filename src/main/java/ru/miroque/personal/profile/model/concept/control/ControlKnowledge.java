package ru.miroque.personal.profile.model.concept.control;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.RestPath;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/knowledge")
public interface ControlKnowledge {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			operationId = "getAll",
			summary = "Get all Knowledges",
			description = "Getting all existing Knowledges in Storage"
	)
	@APIResponse(
			responseCode = "200",
			description = "some resource gettig",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Knowledge.class))
	)
	Response items();

	// @GET
	@Path("/{id:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			operationId = "getSome",
			summary = "Get some Resource",
			description = "getting some test resource"
	)
	@APIResponse(
			responseCode = "200",
			description = "some resource gettig",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Knowledge.class))
	)
	Response item(@PathParam("id") Long id);

	// @GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			operationId = "getSome",
			summary = "Get some Resource",
			description = "getting some test resource"
	)
	@APIResponse(
			responseCode = "200",
			description = "some resource gettig",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Knowledge.class))
	)
	Response item(@PathParam("name") String value);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			operationId = "set Knowledge",
			summary = "Set New or Update Knowledge",
			description = "Set New or Update Knowledge desc"
	)
	@APIResponse(
			responseCode = "200",
			description = "setting end return updated Knowledge",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Knowledge.class))
	)
	Response set(Knowledge item);

	@POST
	@Path("/{id:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			operationId = "set Knowledge with parent",
			summary = "Set New or Update Knowledge with parent",
			description = "Set New or Update Knowledge desc with parent"
	)
	@APIResponse(
			responseCode = "200",
			description = "setting end return updated Knowledge with parent",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Knowledge.class))
	)
	Response set(@RestPath Long id , Knowledge item);
}
