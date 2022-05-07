package ru.miroque.personal.profile.model.concept.dao;

import ru.miroque.personal.profile.model.concept.entity.Answer;
import ru.miroque.personal.profile.model.concept.entity.Question;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

public interface DaoAnswer {

	void createOrUpdate(Answer item) throws ExceptionNotPersisted;

	void createOrUpdate(Question box, Answer item) throws ExceptionNotPersisted;

	Answer findById(long id) throws ExceptionBadWorkWithXml;
}
