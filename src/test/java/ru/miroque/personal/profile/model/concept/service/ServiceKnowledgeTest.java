package ru.miroque.personal.profile.model.concept.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledgeXml;

class ServiceKnowledgeTest {
	ServiceKnowledge service;

	@BeforeEach
	void init() throws Exception {
		service = new ServiceKnowledgeXml(new DaoKnowledgeXml(new File("src/test/resources/pp-miroque.xml")));
	}

	@Test
	void testGetKnowledge() {
//		service.getKnowledge();
		System.out.println("foo");
		fail("Not yet implemented");
	}

	@Disabled
	@Test
	void testGetParent() {
		fail("Not yet implemented");
	}

	@Disabled
	@Test
	void testGetChildren() {
		fail("Not yet implemented");
	}

}
