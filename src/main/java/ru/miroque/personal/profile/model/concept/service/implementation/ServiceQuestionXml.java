package ru.miroque.personal.profile.model.concept.service.implementation;

import ru.miroque.personal.profile.model.concept.dao.DaoQuestion;
import ru.miroque.personal.profile.model.concept.entity.Check;
import ru.miroque.personal.profile.model.concept.entity.Question;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;
import ru.miroque.personal.profile.model.concept.service.ServiceQuestion;

public class ServiceQuestionXml implements ServiceQuestion {
	private final DaoQuestion dao;

	public ServiceQuestionXml(DaoQuestion dao) {
		this.dao = dao;
	}

	@Override
	public void set(Question item) throws ExceptionNotPersisted {
		dao.createOrUpdate(item);
	}

	@Override
	public void set(Check box, Question item) throws ExceptionNotPersisted {
		dao.createOrUpdate(box, item);
	}

	@Override
	public Question findById(long id) throws ExceptionBadWorkWithXml {
		return dao.findById(id);
	}
}
