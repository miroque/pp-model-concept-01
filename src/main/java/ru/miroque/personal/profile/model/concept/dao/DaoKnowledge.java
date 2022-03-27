package ru.miroque.personal.profile.model.concept.dao;

import ru.miroque.personal.profile.model.concept.entity.Knowledge;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;

public interface DaoKnowledge {

    Collection<Knowledge> findByName(String name) throws XPathExpressionException;
    void persist(Knowledge item);

}
