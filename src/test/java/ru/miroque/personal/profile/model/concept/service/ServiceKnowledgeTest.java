package ru.miroque.personal.profile.model.concept.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledgeXml;

import javax.xml.xpath.XPathExpressionException;

class ServiceKnowledgeTest {
	ServiceKnowledge service;

	@BeforeEach
	void init() throws Exception {
		service = new ServiceKnowledgeXml(new DaoKnowledgeXml(new File("src/test/resources/pp-miroque.xml")));
	}


	/**
	 * Так тут основной тест, в котором я хочу найти определенное "знание"
	 * по имени....
	 * Но раз по имени тогда получается 2 варианта:
	 * - ни чего нет
	 * - одно совпадение
	 * - несколько совпадений
	 * И каждый этот случай надо будет протестировать и описать....
	 */
	@Test
	void testGetKnowledge() throws XPathExpressionException {
//		service.findByName("java programmer");
		service.findByName("data engineer");

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
