package ru.miroque.personal.profile.model.concept.service;

import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;

public interface ServiceKnowledge {

	Collection<Knowledge> findByName(String value) throws XPathExpressionException;

	void set(Knowledge item) throws ExceptionNotPersisted;

	void set(Knowledge parent, Knowledge item) throws ExceptionNotPersisted;

    Collection<Knowledge> findAllAtRoot();

	Collection<Knowledge> findAllInStorage();

    Collection<Knowledge> findAllAtBranch(Long id);

}
