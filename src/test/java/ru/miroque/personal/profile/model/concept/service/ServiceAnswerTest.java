package ru.miroque.personal.profile.model.concept.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.miroque.personal.profile.model.concept.dao.DaoAnswerXml;
import ru.miroque.personal.profile.model.concept.dao.DaoKnowledgeXml;
import ru.miroque.personal.profile.model.concept.dao.DaoQuestionXml;
import ru.miroque.personal.profile.model.concept.entity.Answer;
import ru.miroque.personal.profile.model.concept.entity.Check;
import ru.miroque.personal.profile.model.concept.entity.Question;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceAnswerTest {
	ServiceAnswer service;
	ServiceAnswer serviceCorrupted;

	@BeforeEach
	void init() throws Exception {
		service = new ServiceAnswerXml(new DaoAnswerXml(new File("src/test/resources/empty.ppml")));
		serviceCorrupted = new ServiceAnswerXml(new DaoAnswerXml(new File("src/test/resources/empty-corrupted.ppml")));
	}

	/**
	 * Просто найти один Вопрос, оно должно быть одно.
	 */
	@Disabled
	@Test
	void testGetAnswerById() throws ExceptionBadWorkWithXml {
		Answer item = service.findById(1L);
		System.out.println(item);
	}

	/**
	 * Провека ОБНОВЛЕНИЯ конкретного Вопроса
	 */
	@Disabled
	@Test
	void testUpdateAnswer() throws ExceptionNotPersisted {
		service.set(new Answer(1L, "Это контракт описания свойств и поведения всех объектов обладающих этими свойствами."));
	}

	/**
	 * Протестировать на ошибки, много нашло, или вообще не нашло..
	 * но тут я уже тестировал, получилось, что если узлов с однинаковым айди, много.
	 * то по режиму выбора Узла, он достает самый первый который нашел.
	 */
	@Disabled
	@Test
	void testGetAnswerByIdWithError() throws ExceptionBadWorkWithXml {
		long id = 3L;
		ExceptionBadWorkWithXml thrown = assertThrows(ExceptionBadWorkWithXml.class,
				() -> serviceCorrupted.findById(id),
				"Expected doThing() to throw, but it didn't");
		assertTrue(thrown.getMessage().contains("Проверка не найдена id[" + id + "]"));
	}

	/**
	 * Проверка обновления Вопроса в Проверке
	 *
	 * @throws ExceptionNotPersisted
	 */
	@Test
	void testUpdateAnswerInQuestion() throws ExceptionNotPersisted {
		service.set(new Question(1L, null), new Answer(1L, "Проверка--обновление"));
	}

	/**
	 * Проверка создания Проверки в Знании
	 *
	 * @throws ExceptionNotPersisted
	 */
	@Test
	void testCreateAnswerInQuestion() throws ExceptionNotPersisted {
		service.set(new Question(800_000_000L, null), new Answer(800_000_000L, "проверка--создание 2 2"));
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
