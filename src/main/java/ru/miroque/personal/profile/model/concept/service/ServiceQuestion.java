package ru.miroque.personal.profile.model.concept.service;

import ru.miroque.personal.profile.model.concept.entity.Check;
import ru.miroque.personal.profile.model.concept.entity.Question;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

public interface ServiceQuestion {

	void set(Question item) throws ExceptionNotPersisted;

	void set(Check box, Question item) throws ExceptionNotPersisted;

	Question findById(long id) throws ExceptionBadWorkWithXml;
}
