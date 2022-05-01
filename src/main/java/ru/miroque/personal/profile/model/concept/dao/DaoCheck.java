package ru.miroque.personal.profile.model.concept.dao;

import ru.miroque.personal.profile.model.concept.entity.Check;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

public interface DaoCheck {

	void createOrUpdate(Check item) throws ExceptionNotPersisted;

	void createOrUpdate(Knowledge box, Check item) throws ExceptionNotPersisted;

	Check findById(long id) throws ExceptionBadWorkWithXml;
}
