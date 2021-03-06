package ru.miroque.personal.profile.model.concept.dao;

import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;

public interface DaoKnowledge {

	Collection<Knowledge> findByName(String name) throws XPathExpressionException;

	void createOrUpdate(Knowledge item) throws ExceptionNotPersisted;

	void createOrUpdate(Knowledge parent, Knowledge item) throws ExceptionNotPersisted;

}
