package ru.miroque.personal.profile.model.concept.dao;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

public class DaoKnowledgeXml implements DaoKnowledge {
	private Document storage;
	private File storagePath;
	private Element data;

	public DaoKnowledgeXml(File file) throws Exception {
		storagePath = file;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			storage = documentBuilder.parse(storagePath);
			data = extractDataNode(storage);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("dao");
//		printDocument(storage, System.out);
//		NodeList nodeList = storage.getChildNodes();
//		System.out.println(nodeList.getLength());
//		System.out.println(storage.getFirstChild().getFirstChild().getNodeName());
//		System.out.println(storage.getFirstChild().getNodeValue());

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

	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}

	/**
	 * Поиск элемента по Имени (на самом деле по текстовому значению). Три
	 * результата поиска может получиться: 1. ни чего не нашло 2. нашло одно
	 * совпадение 3. нашло больше одного совпадения
	 */
	@Override
	public Collection<Knowledge> findByName(String name) throws XPathExpressionException {
		// Evaluate XPath against Document itself
		XPath xPath = XPathFactory.newInstance().newXPath();
//		NodeList nodes = (NodeList)xPath.evaluate("/personal-profile/data/ancestor-or-self::knowlage/name/", storage, XPathConstants.NODESET);
		NodeList nodes = (NodeList) xPath.evaluate("//*[text()='" + name + "']", data, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); ++i) {
			Element e = (Element) nodes.item(i);
			System.out.println("e = " + e);
			System.out.println("e.getTagName() = " + e.getTagName());
			System.out.println("e.getTextContent() = " + e.getTextContent());
			System.out.println("e.getParentNode().getTextContent() = " + e.getParentNode().getTextContent());
			System.out.println("e.getParentNode().getNodeName() = " + e.getParentNode().getNodeName());

		}
		return null;
	}

	/**
	 * А тут мне надо найти Один Конкретынй узел, и его обновить. А если его нету,
	 * то создать в корневом элементе.
	 */
	@Override
	public void createOrUpdate(Knowledge item) throws ExceptionNotPersisted {
		// boolean isExists = isItemExistsInStorage(storage, item);
		// Давай пока без поиска, чисто, новый узел.
		// В тег ДАТА
		Element knowledge = storage.createElement("knowledge");
		Element name = storage.createElement("name");
		name.appendChild(storage.createTextNode(item.getName()));
		knowledge.appendChild(name);
		data.appendChild(knowledge);
		// Эта штука должна сохранять файл.
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

	private boolean isItemExistsInStorage(Document document, Knowledge item) {
		// TODO Auto-generated method stub
		return false;
	}

//	String usr = document.getElementsByTagName("user").item(0).getTextContent();
//	String pwd = document.getElementsByTagName("password").item(0).getTextContent();

}
