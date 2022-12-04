package ru.miroque.personal.profile.model.concept.control.implementation;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import ru.miroque.personal.profile.model.concept.control.ControlKnowledge;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;
import ru.miroque.personal.profile.model.concept.service.ServiceKnowledge;

public class ControlKnowledgeDefault implements ControlKnowledge {
	@Inject
	Logger log;

	@Inject
	ServiceKnowledge service;

	@Override
	public Response items() {
		try {
			return Response.status(200).entity(service.findAllInStorage()).build();
		} catch (Exception e) {
			log.error(e);
//			throw new ExceptionBadWorkWithXml(String.format(bundle.getString("error.answer.not-found"), id));
			return Response.serverError().build();
		}
	}

	@Override
	public Response itemsAtRoot() {
		try {
			return Response.status(200).entity(service.findAllAtRoot()).build();
		} catch (Exception e) {
			log.error(e);
//			throw new ExceptionBadWorkWithXml(String.format(bundle.getString("error.answer.not-found"), id));
			return Response.serverError().build();
		}
	}
	
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
		log.infov("Simple set of One Knowledge. <>> item::{0}::", item);
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
		log.infov("Set of One Knowledge AND PARENT. <>> id::{0}::item::{1}::", id, item);
		// log.debugv("Set of One Knowledge AND PARENT. <>> id::{0}::item::{1}::", id, item);
		// log.tracev("Set of One Knowledge AND PARENT. <>> id::{0}::item::{1}::", id, item);
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
