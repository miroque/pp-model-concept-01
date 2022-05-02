package ru.miroque.personal.profile.model.concept.dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.miroque.personal.profile.model.concept.entity.Check;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DaoCheckXml implements DaoCheck {
	private final Document storage;
	private final File storagePath;
	private final Element data;
	private final ResourceBundle bundle;

	public DaoCheckXml(File file) throws ExceptionBadWorkWithXml, SAXException, IOException, ParserConfigurationException {
		storagePath = file;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		storage = documentBuilder.parse(storagePath);
		data = extractDataNode(storage);
		bundle = ResourceBundle.getBundle("messages", new Locale("ru_ru"));
	}


	/**
	 * @param item Проверка. Обновляем существующий.
	 *             Создавать не получится. Отдельно от Знания смысла не имеет.
	 * @throws ExceptionNotPersisted
	 */
	@Override
	public void createOrUpdate(Check item) throws ExceptionNotPersisted {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xPath.evaluate("/personal-profile/data/descendant-or-self::*/check[@id=" + item.getId() + "]", data, XPathConstants.NODE);
			if (node != null) {
				node.getFirstChild().getNextSibling().setTextContent(item.getName());
				saveXmlFile();
			} else {
				throw new ExceptionNotPersisted(String.format(bundle.getString("error.check.not-found.persist"), item.getId()));
			}
		} catch (XPathExpressionException e) {
			throw new ExceptionNotPersisted(e.getMessage());
		}
	}

	@Override
	public void createOrUpdate(Knowledge box, Check item) throws ExceptionNotPersisted {
		try {
			XPath boxPath = XPathFactory.newInstance().newXPath();
			Node boxNode = (Node) boxPath.evaluate("/personal-profile/data/descendant-or-self::*/knowledge[@id=" + box.getId() + "]", data, XPathConstants.NODE);
			if (boxNode != null) {
				XPath itemPath = XPathFactory.newInstance().newXPath();
				Node itemNode = (Node) itemPath.evaluate("self::*/check[@id=" + item.getId() + "]", boxNode, XPathConstants.NODE);
				if (itemNode != null) {
					// тут надо обновить Проверку
					itemNode.getFirstChild().getNextSibling().setTextContent(item.getName());
				} else {
					// а тту надо создать эту Проверку
//					generateNewKnoledgeNode(item, boxNode);
				}
				saveXmlFile();
			} else if (boxNode == null) {
				throw new ExceptionNotPersisted("Не нашлось такого элемента в \"Хранилище\"");
			}
		} catch (XPathExpressionException e) {
			throw new ExceptionNotPersisted(e.getMessage());
		}
	}

	@Override
	public Check findById(long id) throws ExceptionBadWorkWithXml {
		XPath xPath = XPathFactory.newInstance().newXPath();
		try {
			Node node = (Node) xPath.evaluate("/personal-profile/data/descendant-or-self::*/ check[@id=" + id + "]", data, XPathConstants.NODE);
			return new Check(id, node.getFirstChild().getNextSibling().getTextContent());
		} catch (Exception e) {
			throw new ExceptionBadWorkWithXml(String.format(bundle.getString("error.check.not-found"), id));
		}
	}

	private Element extractDataNode(Document document) throws ExceptionBadWorkWithXml {
		Element root = storage.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("data");

		if (nodes.getLength() == 1) {
			return (Element) nodes.item(0);
		} else {
			throw new ExceptionBadWorkWithXml("тут больше элементов Дата, чем нужно");
		}
	}

	private void generateNewCheckNode(Check item, Node parentNode) {
		Element childKnowledge = storage.createElement("check");
		childKnowledge.setAttribute("id", item.getId().toString());

		Element name = storage.createElement("name");
		name.appendChild(storage.createTextNode(item.getName()));

		childKnowledge.appendChild(name);

		parentNode.appendChild(childKnowledge);
	}

	/**
	 * Сохраняет полностью весь файл.
	 *
	 * @throws ExceptionNotPersisted
	 */
	private void saveXmlFile() throws ExceptionNotPersisted {
		DOMSource source = new DOMSource(storage);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(storagePath);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw new ExceptionNotPersisted(e.getMessage());
		}
	}
}
