package ru.miroque.personal.profile.model.concept.service.implementation;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledge;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;
import ru.miroque.personal.profile.model.concept.service.ServiceKnowledge;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;

@Default
@ApplicationScoped
public class ServiceKnowledgeXml implements ServiceKnowledge {

	private final DaoKnowledge dao;

	@Inject
	public ServiceKnowledgeXml(DaoKnowledge dao) {
		this.dao = dao;
	}

	@Override
	public Collection<Knowledge> findByName(String value) throws XPathExpressionException {
		return dao.findByName(value);
	}

	@Override
	public void set(Knowledge item) throws ExceptionNotPersisted {
		dao.createOrUpdate(item);
	}

	@Override
	public void set(Knowledge parent, Knowledge item) throws ExceptionNotPersisted {
		dao.createOrUpdate(parent, item);
	}

	@Override
	public Collection<Knowledge> findAllAtRoot() {
		return dao.findAllAtRoot();
	}

	@Override
	public Collection<Knowledge> findAllInStorage() {
		return dao.finAllInStorage();
	}

	@Override
	public Collection<Knowledge> findAllAtBranch(Long id) {
		return dao.findAllAtBranch(id);
	}
}