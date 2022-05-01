package ru.miroque.personal.profile.model.concept.service;

import ru.miroque.personal.profile.model.concept.dao.DaoCheck;
import ru.miroque.personal.profile.model.concept.entity.Check;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

public class ServiceCheckXml implements ServiceCheck {
	private final DaoCheck dao;

	public ServiceCheckXml(DaoCheck dao) {
		this.dao = dao;
	}

	@Override
	public void set(Check item) throws ExceptionNotPersisted {
		dao.createOrUpdate(item);
	}

	@Override
	public void set(Knowledge box, Check item) throws ExceptionNotPersisted {
		dao.createOrUpdate(box, item);
	}

	@Override
	public Check findById(long id) throws ExceptionBadWorkWithXml {
		return dao.findById(id);
	}
}
