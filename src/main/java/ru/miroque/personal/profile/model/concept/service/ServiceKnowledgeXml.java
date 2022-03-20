package ru.miroque.personal.profile.model.concept.service;

import java.util.List;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledge;

public class ServiceKnowledgeXml implements ServiceKnowledge {
	
	private DaoKnowledge dao;
	
	public ServiceKnowledgeXml(DaoKnowledge dao) {
		this.dao = dao;
	}

	public Object getKnowledge(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getParent(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getChildren(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

}
