package ru.miroque.personal.profile.model.concept.service;

import ru.miroque.personal.profile.model.concept.entity.Answer;
import ru.miroque.personal.profile.model.concept.entity.Question;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

public interface ServiceAnswer {

	void set(Answer item) throws ExceptionNotPersisted;

	void set(Question box, Answer item) throws ExceptionNotPersisted;

	Answer findById(long id) throws ExceptionBadWorkWithXml;
}
