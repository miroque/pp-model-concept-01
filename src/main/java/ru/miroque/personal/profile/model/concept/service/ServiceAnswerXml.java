package ru.miroque.personal.profile.model.concept.service;

import ru.miroque.personal.profile.model.concept.dao.DaoAnswer;
import ru.miroque.personal.profile.model.concept.entity.Answer;
import ru.miroque.personal.profile.model.concept.entity.Question;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

public class ServiceAnswerXml implements ServiceAnswer {
	private final DaoAnswer dao;

	public ServiceAnswerXml(DaoAnswer dao) {
		this.dao = dao;
	}

	@Override
	public void set(Answer item) throws ExceptionNotPersisted {
		dao.createOrUpdate(item);
	}

	@Override
	public void set(Question box, Answer item) throws ExceptionNotPersisted {
		dao.createOrUpdate(box, item);
	}

	@Override
	public Answer findById(long id) throws ExceptionBadWorkWithXml {
		return dao.findById(id);
	}
}
