package ru.miroque.personal.profile.model.concept.service;

import ru.miroque.personal.profile.model.concept.entity.Knowledge;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;

public interface ServiceKnowledge {

	Collection<Knowledge>  findByName(String name) throws XPathExpressionException;

}
