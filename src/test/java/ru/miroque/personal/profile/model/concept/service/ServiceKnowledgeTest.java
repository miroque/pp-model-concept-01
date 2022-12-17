// package ru.miroque.personal.profile.model.concept.service;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.fail;

// import java.io.File;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Disabled;
// import   org.junit.jupiter.api.Test;

// import ru.miroque.personal.profile.model.concept.dao.implementation.DaoKnowledgeXml;
// import ru.miroque.personal.profile.model.concept.entity.Knowledge;
// import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
// import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;
// import ru.miroque.personal.profile.model.concept.service.implementation.ServiceKnowledgeXml;

// import javax.xml.xpath.XPathExpressionException;

// class ServiceKnowledgeTest {
// 	ServiceKnowledge service;

// 	@BeforeEach
// 	void init() throws Exception {
// //		service = new ServiceKnowledgeXml(new DaoKnowledgeXml(new File("src/test/resources/pp-simple-data.xml")));
// 		service = new ServiceKnowledgeXml(new DaoKnowledgeXml(new File("src/test/resources/empty.ppml")));
// 	}

// 	/**
// 	 * Так тут основной тест, в котором я хочу найти определенное "знание" по
// 	 * имени.... Но раз по имени тогда получается 2 варианта: - ни чего нет - одно
// 	 * совпадение - несколько совпадений И каждый этот случай надо будет
// 	 * протестировать и описать....
// 	 */
// 	@Disabled
// 	@Test
// 	void testGetKnowledge() throws XPathExpressionException {
// 		service.findByName("java programmer");
// //		service.findByName("data engineer");

// 	}

// 	/**
// 	 * Проверка когда мы просто набрасываем знание, в персону. т.е. в первичный
// 	 * уровень, без знания в какое "знание"
// 	 * 
// 	 * И опять же, подход, такой, что я не знаю есть это знание или его нету в
// 	 * Хранилище. Я просто его кладу в хранилище, а магия путь будет там где-то.
// 	 * 
// 	 * @throws ExceptionNotPersisted
// 	 */
// 	@Test
// 	void testCreateKnowledgeInPerson() throws ExceptionNotPersisted {
// 		service.set(new Knowledge(1l, "Test 0"));
// 		service.set(new Knowledge(2l, "Test 1"));
// 		service.set(new Knowledge(3l, "Test 2"));
// 		service.set(new Knowledge(4l, "Test 3"));
// 		service.set(new Knowledge(5l, "Test 4"));

// 	}
// 	@Test
// 	void testCreateKnowledgeInKnowledge() throws ExceptionNotPersisted {
// //		var root = new Knowledge(999l, "Test nine nine nine");
// 		var root = new Knowledge(6l, "Test nine nine nine");
// //		service.set(root);
// 		service.set(root, new Knowledge(7l, "Некое знание которое я забыл, но которое необходимо \uD83C\uDF89 \uD83C\uDFC0"));
// 	}

// 	@Test
// 	void testCreateKnowledgeInPersonError() {
// 		ExceptionBadWorkWithXml thrown = assertThrows(ExceptionBadWorkWithXml.class,
// 				() -> new ServiceKnowledgeXml(
// 						new DaoKnowledgeXml(new File("src/test/resources/pp-simple-data-error.xml"))),
// 				"Expected doThing() to throw, but it didn't");
// 		assertTrue(thrown.getMessage().contains("тут больше элементов Дата, чем нужно"));
// 	}

// 	/**
// 	 * А тут проверяем, когда мы знаем в какое "знание" положить "новое" "знание"
// 	 * 
// 	 * Подход, такой же, у меня есть 2 знания, и одно из них является родителем для
// 	 * второго. И так же ложим в Хранилище, и там уже магия, было не было, оно там
// 	 * должно сохраниться или обновиться.
// 	 * 
// 	 * @throws ExceptionNotPersisted
// 	 */
// 	@Disabled
// 	@Test
// 	void testCreateKnowledgeInKnowlage() throws ExceptionNotPersisted {
// 		Knowledge parent = new Knowledge(0l, "Parent Test");
// 		Knowledge item = new Knowledge(1l, "Child Test");
// 		service.set(parent, item);
// 	}

// 	@Disabled
// 	@Test
// 	void testGetParent() {
// 		fail("Not yet implemented");
// 	}

// 	@Disabled
// 	@Test
// 	void testGetChildren() {
// 		fail("Not yet implemented");
// 	}

// }
