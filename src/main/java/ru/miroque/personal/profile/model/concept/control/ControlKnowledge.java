package ru.miroque.personal.profile.model.concept.control;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/knowledge")
public interface ControlKnowledge {

	@GET
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
	public Response item(@PathParam("id")Long id);

	@GET
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
	public Response item(@PathParam("name")String value);
}
