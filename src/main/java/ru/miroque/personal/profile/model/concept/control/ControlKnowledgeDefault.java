package ru.miroque.personal.profile.model.concept.control;

import org.jboss.logging.Logger;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;
import ru.miroque.personal.profile.model.concept.service.ServiceKnowledge;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class ControlKnowledgeDefault implements ControlKnowledge {
	@Inject
	Logger log;

	@Inject
	ServiceKnowledge service;

	@Override
	public Response item(Long id) {
		//TODO: implement in SERVICE
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@Override
	public Response item(String value) {
		try {
			return Response.status(200).entity(service.findByName(value)).build();
		} catch (Exception e) {
			log.error(e);
//			throw new ExceptionBadWorkWithXml(String.format(bundle.getString("error.answer.not-found"), id));
			return Response.serverError().build();
		}
	}

	@Override
	public Response set(Knowledge item) {
		log.info("Simple set of One Knowledge");
		log.infof("<^> item: {%s}", item);
		try {
			service.set(item);
			return Response.ok("{\"status\":\"saved\"}").build();
		} catch (ExceptionNotPersisted e) {
			log.error(e);
			return Response.serverError().build();
		}
	}

	@Override
	public Response set(Long id, Knowledge item) {
		log.info("Set of One Knowledge AND PARENT");
		log.infof("<^> id: {%s}, item: {%s}", id, item);
		try {
			var dumb = new Knowledge(id, null);
			service.set(dumb, item);
			return Response.ok("{\"status\":\"saved\"}").build();
		} catch (ExceptionNotPersisted e) {
			log.error(e);
			return Response.serverError().build();
		}
	}
}
