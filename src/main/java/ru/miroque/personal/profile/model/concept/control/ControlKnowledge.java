package ru.miroque.personal.profile.model.concept.control;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

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

	@GET
	@Path("/r")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			operationId = "getAllAtRoot",
			summary = "Get all Knowledges at Root Level",
			description = "Getting all existing Knowledges at Root Level"
	)
	@APIResponse(
			responseCode = "200",
			description = "some resource gettig",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Knowledge.class))
	)
	Response itemsAtRoot();

	@GET
	@Path("/b/{id:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			operationId = "getAllBranch",
			summary = "Get all Knowledges from Chosen Knowledge",
			description = "Getting all existing Knowledges from chosen Knowledge"
	)
	@APIResponse(
			responseCode = "200",
			description = "some resource gettig",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Knowledge.class))
	)
	Response itemsAtBranch(@PathParam("id") Long id);

	// @GET
	@Path("/{nid:\\d+}")
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
	Response item(@PathParam("nid") Long nid);

	/**
	 * Странно конечно, что оно не позволило зацепиться на один и тот же "ендпоинт".
	 * Возможно я что-то упустил в "придумывании" "архитектуры".
	 * Пока для поиска по названию будет использоваться этот "эндпоинт".
	 * Дальше возможно это переделаю.
	 * 
	 * @param name
	 * @return
	 */
	@GET
	@Path("/find")
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
	Response findBy(@RestQuery String name);

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
