package ru.miroque.personal.profile.model.concept.service;

import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;

public interface ServiceKnowledge {
	
	void set(Knowledge item) throws ExceptionNotPersisted;
	
	void set(Knowledge parent, Knowledge item) throws ExceptionNotPersisted;
	
    Collection<Knowledge> findAllAtRoot();
	
	Collection<Knowledge> findAllInStorage();
	
    Collection<Knowledge> findAllAtBranch(Long id);
	
	Collection<Knowledge> findByName(String value) throws XPathExpressionException;
	
	Knowledge findByNid(Long value) throws ExceptionBadWorkWithXml;
}
