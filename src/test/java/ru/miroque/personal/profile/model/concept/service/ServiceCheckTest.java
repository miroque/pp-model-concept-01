package ru.miroque.personal.profile.model.concept.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.miroque.personal.profile.model.concept.dao.DaoCheckXml;
import ru.miroque.personal.profile.model.concept.dao.DaoKnowledgeXml;
import ru.miroque.personal.profile.model.concept.entity.Check;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceCheckTest {
	ServiceCheck service;
	ServiceCheck serviceCorrupted;

	@BeforeEach
	void init() throws Exception {
		service = new ServiceCheckXml(new DaoCheckXml(new File("src/test/resources/empty.ppml")));
		serviceCorrupted = new ServiceCheckXml(new DaoCheckXml(new File("src/test/resources/empty-corrupted.ppml")));
	}

	/**
	 * Просто найти одно Знание, оно должно быть одно.
	 */
	@Disabled
	@Test
	void testGetCheckById() throws ExceptionBadWorkWithXml {
		Check check = service.findById(2L);
		System.out.println("check = " + check);
	}

	/**
	 * Протестировать на ошибки, много нашло, или вообще не нашло..
	 * но тут я уже тестировал, получилось, что если узлов с однинаковым айди, много.
	 * то по режиму выбора Узла, он достает самый первый который нашел.
	 */
	@Disabled
	@Test
	void testGetCheckByIdWithError() throws ExceptionBadWorkWithXml {
		long id = 3L;
		ExceptionBadWorkWithXml thrown = assertThrows(ExceptionBadWorkWithXml.class,
				() -> serviceCorrupted.findById(id),
				"Expected doThing() to throw, but it didn't");
		assertTrue(thrown.getMessage().contains("Проверка не найдена id[" + id + "]"));
	}

	/**
	 * Проверка создания/обновления Проверки в Знании
	 *
	 * @throws ExceptionNotPersisted
	 */
	@Test
	void testCreateCheckinKnowledge() throws ExceptionNotPersisted {
		service.set(new Knowledge(1L, null), new Check(1L, "тестовая проверка"));
	}

	/**
	 * Провека ОБНОВЛЕНИЯ конкретной Проверки
	 */
	@Disabled
	@Test
	void testUpdateCheck() throws ExceptionNotPersisted {
		service.set(new Check(8L, "Я забыл что тут было, но однако хочется проверить."));
	}

	@Disabled
	@Test
	void testCreateKnowledgeInPersonError() {
		ExceptionBadWorkWithXml thrown = assertThrows(ExceptionBadWorkWithXml.class,
				() -> new ServiceKnowledgeXml(
						new DaoKnowledgeXml(new File("src/test/resources/pp-simple-data-error.xml"))),
				"Expected doThing() to throw, but it didn't");
		assertTrue(thrown.getMessage().contains("тут больше элементов Дата, чем нужно"));
	}

}
