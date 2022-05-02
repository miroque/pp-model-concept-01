package ru.miroque.personal.profile.model.concept.dao;

import ru.miroque.personal.profile.model.concept.entity.Check;
import ru.miroque.personal.profile.model.concept.entity.Question;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

public interface DaoQuestion {

	void createOrUpdate(Question item) throws ExceptionNotPersisted;

	void createOrUpdate(Check box, Question item) throws ExceptionNotPersisted;

	Question findById(long id) throws ExceptionBadWorkWithXml;
}
