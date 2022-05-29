package ru.miroque.personal.profile.model.concept.control;

import org.jboss.logging.Logger;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.service.ServiceKnowledge;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.xml.xpath.XPathExpressionException;

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
		Knowledge item;
		try {
			return Response.status(200).entity(service.findByName(value)).build();
		} catch (XPathExpressionException e) {
			log.error(e);
			return Response.serverError().build();
		}
	}
}
