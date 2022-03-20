package ru.miroque.personal.profile.model.concept.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledgeXml;

class ServiceKnowledgeTest {
	ServiceKnowledge service;
	
	@BeforeAll
	void init() {
		service = new ServiceKnowledgeXml(new DaoKnowledgeXml());
	}
	
	@Test
	void testGetKnowledge() {
		service.getKnowledge();
		fail("Not yet implemented");
	}

	@Test
	void testGetParent() {
		fail("Not yet implemented");
	}

	@Test
	void testGetChildren() {
		fail("Not yet implemented");
	}

}
