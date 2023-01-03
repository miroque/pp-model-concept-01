package ru.miroque.personal.profile.model.concept.dao.implementation;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jboss.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledge;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

@Default
@ApplicationScoped
public class DaoKnowledgeXml implements DaoKnowledge {
	@Inject
	Logger log;
	private final Document storage;
	private final File storagePath;
	private final Element data;

	@Inject
	public DaoKnowledgeXml(File file) throws ExceptionBadWorkWithXml, SAXException, IOException, ParserConfigurationException {
		storagePath = file;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		// Отключил момент с ВАЛИДАЦИЕЙ.
		// И сама схема, не очень нужная мне, и валидатор, так себе в этой конфигурации работает.
		// Не разобрался ещё до конца.

		// StreamSource streamSource  = new StreamSource (DaoKnowledgeXml.class.getClassLoader().getResourceAsStream("personal-profile.xsd"));
		
         // create schema
		// SchemaFactory xsdFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// Schema schema = xsdFactory.newSchema(streamSource);

		// or prohibit the use of all protocols by external entities:
		// documentBuilderFactory.setValidating(true);
		// documentBuilderFactory.setSchema(schema);
		// documentBuilderFactory.setIgnoringElementContentWhitespace(true);
		// documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		// documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		// documentBuilderFactory.setIgnoringComments(true);
		DocumentBuilder documentBuilder;
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		storage = documentBuilder.parse(storagePath);
		storage.getDocumentElement().normalize();
		data = extractNode(storage, "data");
	}

	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, StandardCharsets.UTF_8)));
	}

	/**
	 * Метод для вычленения Element(Node).
	 * Ну типа <em>-=Оптимизация=-</em>
	 * 
	 * @param document из которого нужно достать требуемый мне узел
	 * @param nameOfNode <em>Имя</em> узла который хочу вернуть
	 * @return Element(Node) с указанным <em>Именем</em>
	 * @throws ExceptionBadWorkWithXml
	 */
	private Element extractNode(Document document, String nameOfNode) throws ExceptionBadWorkWithXml {
		Element root = storage.getDocumentElement();
		NodeList nodes = root.getElementsByTagName(nameOfNode);

		if (nodes.getLength() == 1) {
			return (Element) nodes.item(0);
		} else {
			//TODO: replace i18n
			throw new ExceptionBadWorkWithXml("тут больше элементов Дата, чем нужно");
		}
	}

	/**
	 * Поиск элемента по Имени (на самом деле по текстовому значению). Три
	 * результата поиска может получиться:
	 * <ol>
	 *     <li>ни чего не нашло</li>
	 *     <li>нашло одно совпадение</li>
	 *     <li>нашло больше одного совпадения</li>
	 * </ol>
	 */
	@Override
	public Collection<Knowledge> findByName(String name) throws XPathExpressionException {
		log.tracev("🔰[name]▓{0}", name);
		Collection<Knowledge> result = new ArrayList<Knowledge>();

		// Evaluate XPath against Document itself
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xPath.evaluate("/personal-profile/data/descendant-or-self::knowledge/descendant-or-self::name[contains(text(), '"+name+"')]", data, XPathConstants.NODESET);
		log.tracev("▍nodes.getLength() ▓ {0}", nodes.getLength());
		for (int i = 0; i < nodes.getLength(); ++i) {
			Element e = (Element) nodes.item(i);
			// log.tracev("▍e ▓ {0}", e);
			log.tracev("▍e.getTagName() ▓ {0}", e.getTagName());
			log.tracev("▍e.getTextContent() ▓ {0}", e.getTextContent());
			// log.tracev("▍e.getParentNode().getTextContent() ▓ {0}", e.getParentNode().getTextContent());
			log.tracev("▍e.getParentNode().getNodeName() ▓ {0}", e.getParentNode().getNodeName());
			// log.tracev("▍e.getParentNode().getAttributes().getNamedItem(\"id\") {0}", Long.valueOf(e.getParentNode().getAttributes().getNamedItem("id").getNodeValue()));
			if (e.getParentNode().getNodeName().equals("knowledge")){
				Knowledge item = new Knowledge(
					Long.valueOf(e.getParentNode().getAttributes().getNamedItem("id").getNodeValue()),
					e.getTextContent()
				);
				result.add(item);
			}

		}
		return result;
	}

	/**
	 * А тут мне надо найти Один Конкретынй узел, и его обновить. А если его нету,
	 * то создать в корневом элементе.
	 */
	@Override
	public void createOrUpdate(Knowledge item) throws ExceptionNotPersisted {
		log.infov("🔰[item]::{0}", item);
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate("//name[text()='" + item.getName() + "']", data, XPathConstants.NODESET);
			if (nodes.getLength() == 0) {
				generateNewKnowledgeNode(item, data);
				saveXmlFile();
				log.infov("saved new Knowledge::{0}", item);

			} else if (nodes.getLength() == 1) {
				//TODO: replace i18n
				throw new ExceptionNotPersisted("Уже есть такой элемент в \"Хранилище\"");
			} else {
				//TODO: replace i18n
				throw new ExceptionNotPersisted("Нашлось несколько элементов в \"Хранилище\", вообще-то это невозможный вариант");
			}
		} catch (XPathExpressionException e) {
			//TODO: replace i18n
			throw new ExceptionNotPersisted(e.getMessage());
		}
	}

	/**
	 * Сохраняем Знание, в указанный "Родитель"
	 */
	@Override
	public void createOrUpdate(Knowledge parent, Knowledge descendant) throws ExceptionNotPersisted {
		log.infov("🔰[parent]::{0}", parent);
		log.infov("🔰[descendant]::{0}", descendant);
		try {
			XPath xPath_Parent = XPathFactory.newInstance().newXPath();
			Node node_Parent = (Node) xPath_Parent.evaluate("/personal-profile/data/descendant-or-self::knowledge[@id=" + parent.getId() + "]", data, XPathConstants.NODE);
			// Родительский элемент -- да
			log.tracev("🔸 [node_Parent]::{0}", node_Parent);
			if (node_Parent != null) {
				XPath  xPath_Descendant = XPathFactory.newInstance().newXPath();
				Node node_Descendant = (Node) xPath_Descendant.evaluate("self::*/knowledge[@id=" + descendant.getId() + "]", node_Parent, XPathConstants.NODE);
				// элемент Потомок -- да
				log.tracev("🔸 [node_Descendant]::{0}", node_Descendant);
				if (node_Descendant != null) {
					log.trace("🏳️ descendant is NOT null");
					XPath  xPath_Descendant_Name = XPathFactory.newInstance().newXPath();
					Node node_Descendant_Name = (Node) xPath_Descendant_Name.evaluate("self::*/name", node_Descendant, XPathConstants.NODE);
					log.tracev("🔸 text from::{0}", node_Descendant_Name.getTextContent());
					node_Descendant_Name.setTextContent(descendant.getName());
					log.tracev("🔸 text into::{0}", node_Descendant_Name.getTextContent());
				} else {
					log.trace("🏴 descendant is null");
					generateNewKnowledgeNode(descendant, node_Parent);
				}
				saveXmlFile();
//			} else if (parentNode == null) {
//				//TODO: replace i18n
//				throw new ExceptionNotPersisted("Не нашлось такого элемента в \"Хранилище\"");
			} else {
				//TODO: replace i18n
				throw new ExceptionNotPersisted("Нашлось несколько элемент в \"Хранилище\"");
			}
			//TODO: replace i18n
		} catch (XPathExpressionException e) {
			//TODO: replace i18n
			throw new ExceptionNotPersisted(e.getMessage());
		}
	}

	private void generateNewKnowledgeNode(Knowledge item, Node parentNode) {
		Element childKnowledge = storage.createElement("knowledge");
		childKnowledge.setAttribute("id", item.getId().toString());

		Element name = storage.createElement("name");
		name.appendChild(storage.createTextNode(item.getName()));

		childKnowledge.appendChild(name);

		parentNode.appendChild(childKnowledge);
	}

	/**
	 * <p>Возвращает, все <em>Знания</em>, которые есть в <em>Хранилище</em></p>
	 * <p>Без структуры, чисто Список</p>
	 */
	@Override
	public Collection<Knowledge> finAllInStorage() {
		log.trace("🚩");
		NodeList itemsRaw = storage.getElementsByTagName("knowledge");
		log.tracev("🔸 [itemsRaw.size]::{0}", itemsRaw.getLength());
		List<Knowledge> items = new ArrayList<Knowledge>();
		for (int i = 0; i < itemsRaw.getLength(); i++) {
			Knowledge item = new Knowledge();
			Element node = (Element) itemsRaw.item(i);
			item.setId(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
			item.setName(node.getElementsByTagName("name").item(0).getTextContent());
			log.tracev("♻ 🔸 [item]::{0}", item);
			items.add(item);
		}
		log.trace("🏁");
		return items;
	}

	@Override
	public Collection<Knowledge> findAllAtRoot() {
		log.trace("🚩");
		List<Knowledge> items = new ArrayList<Knowledge>();
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList itemsRaw = (NodeList) xPath.evaluate("/personal-profile/data/child::knowledge", data, XPathConstants.NODESET);
			log.tracev("🔸 [itemsRaw.size]::{0}", itemsRaw.getLength());
			for (int i = 0; i < itemsRaw.getLength(); i++) {
				Knowledge item = new Knowledge();
				Element node = (Element) itemsRaw.item(i);
				item.setId(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
				item.setName(node.getElementsByTagName("name").item(0).getTextContent());
				log.tracev("♻ 🔸 [item]::{0}", item);
				items.add(item);
			}
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO: Здесь необходимо сделать конрвертатор из Нодов В Знания
		log.trace("🏁");
		return items;
	}

	/**
	 * <p>Сохраняет рабочий Документ в файл</p>
	 * <p>Но сам Документ не закрывает, и не перечитывает потом из файла</p>
	 * <p>Только сохранение</p>
	 *
	 * @throws ExceptionNotPersisted
	 */
	private void saveXmlFile() throws ExceptionNotPersisted {
		DOMSource source = new DOMSource(storage);
		TransformerFactory factory = TransformerFactory.newInstance();
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		try {
			Transformer transformer = factory.newTransformer(new StreamSource(getClass().getClassLoader().getResourceAsStream("pretty-print.xslt")));
			StreamResult result = new StreamResult(storagePath);
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);
		} catch (TransformerException e) {
			//TODO: replace i18n
			throw new ExceptionNotPersisted(e.getMessage());
		}
	}

	@Override
	public Collection<Knowledge> findAllAtBranch(Long id) {
		log.trace("🚩");
		List<Knowledge> items = new ArrayList<Knowledge>();
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList itemsRaw = (NodeList) xPath.evaluate("/personal-profile/data/descendant-or-self::knowledge[@id=" + id + "]/descendant::knowledge", data, XPathConstants.NODESET);
			log.tracev("🔸 [itemsRaw.size]::{0}", itemsRaw.getLength());
			for (int i = 0; i < itemsRaw.getLength(); i++) {
				Knowledge item = new Knowledge();
				Element node = (Element) itemsRaw.item(i);
				item.setId(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
				item.setName(node.getElementsByTagName("name").item(0).getTextContent());
				log.tracev("♻ 🔸 [item]::{0}", item);
				items.add(item);
			}
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO: Здесь необходимо сделать конрвертатор из Нодов В Знания
		log.trace("🏁");
		return items;
	}

}
