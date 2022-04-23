package ru.miroque.personal.profile.model.concept.service;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledge;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;

public class ServiceKnowledgeXml implements ServiceKnowledge {

	private final DaoKnowledge dao;

	public ServiceKnowledgeXml(DaoKnowledge dao) {
		this.dao = dao;
	}

	@Override
	public Collection<Knowledge> findByName(String name) throws XPathExpressionException {
		return dao.findByName(name);
	}

	@Override
	public void set(Knowledge item) throws ExceptionNotPersisted {
		dao.createOrUpdate(item);
	}

	@Override
	public void set(Knowledge parent, Knowledge item) throws ExceptionNotPersisted {
		dao.createOrUpdate(parent, item);
	}
}
