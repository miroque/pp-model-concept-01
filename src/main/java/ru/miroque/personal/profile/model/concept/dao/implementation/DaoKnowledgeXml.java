package ru.miroque.personal.profile.model.concept.dao.implementation;

import org.jboss.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledge;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionBadWorkWithXml;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

@Default
@ApplicationScoped
public class DaoKnowledgeXml implements DaoKnowledge {
	@Inject
	private Logger log;
	private final Document storage;
	private final File storagePath;
	private final Element data;

	@Inject
	public DaoKnowledgeXml(File file) throws ExceptionBadWorkWithXml, SAXException, IOException, ParserConfigurationException {
		storagePath = file;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		// or prohibit the use of all protocols by external entities:
		documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		DocumentBuilder documentBuilder;
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		storage = documentBuilder.parse(storagePath);
		storage.getDocumentElement().normalize();
		data = extractDataNode(storage);
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

	private static void printNote(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {
  
			Node tempNode = nodeList.item(count);
  
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
  
				// get node name and value
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());
  
				if (tempNode.hasAttributes()) {
  
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
					}
  
				}
  
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNote(tempNode.getChildNodes());
				}
  
				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
  
			}
  
		}
	}

	private Element extractDataNode(Document document) throws ExceptionBadWorkWithXml {
		Element root = storage.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("data");

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
		// Evaluate XPath against Document itself
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xPath.evaluate("//*[text()='" + name + "']", data, XPathConstants.NODESET);
/*		for (int i = 0; i < nodes.getLength(); ++i) {
			Element e = (Element) nodes.item(i);
			System.out.println("e = " + e);
			System.out.println("e.getTagName() = " + e.getTagName());
			System.out.println("e.getTextContent() = " + e.getTextContent());
			System.out.println("e.getParentNode().getTextContent() = " + e.getParentNode().getTextContent());
			System.out.println("e.getParentNode().getNodeName() = " + e.getParentNode().getNodeName());

		}*/
		// TODO: Здесь необходимо сделать конрвертатор из Нодов В Знания
		return Collections.emptyList();
	}

	/**
	 * А тут мне надо найти Один Конкретынй узел, и его обновить. А если его нету,
	 * то создать в корневом элементе.
	 */
	@Override
	public void createOrUpdate(Knowledge item) throws ExceptionNotPersisted {
		log.infov("Item: {0}", item);
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
	public void createOrUpdate(Knowledge parent, Knowledge item) throws ExceptionNotPersisted {
		log.infov("Parent: {0}, Item: {1}", parent, item);
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node parentNode = (Node) xPath.evaluate("/personal-profile/data/descendant-or-self::knowledge[@id=" + parent.getId() + "]", data, XPathConstants.NODE);
			if (parentNode != null) {
				xPath = XPathFactory.newInstance().newXPath();
				Node childNode = (Node) xPath.evaluate("self::*/knowledge[@id=" + item.getId() + "]", parentNode, XPathConstants.NODE);
				if (childNode != null) {
//					childNode.getFirstChild().getNextSibling().setTextContent(item.getName());
					// childNode.getFirstChild()/*.getNextSibling()*/.setTextContent(item.getName()); // <<<--- вот это рабочий вариант.. он обновляет значение тега НЕЙМ
					NodeList nodeList = childNode.getChildNodes();
					log.tracev("reap all ancestors.lenght::{0}", nodeList.getLength());
					for (int i=0; i<nodeList.getLength(); i++){
						log.trace(">>..> ::");
						Node n =  nodeList.item(i);
						log.tracev(">>..> :: n.NodeName [{0}]", n.getNodeName());
						log.tracev(">>..> :: n.NodeValue [{0}]", n.getNodeValue());
						log.tracev(">>..> :: n.TextContent [{0}]", n.getTextContent());
						log.tracev(">>..> :: n.NodeType [{0}]", n.getNodeType());
					}
					if (nodeList.getLength() == 1){
						Node node = nodeList.item(0);;
						if(node.getNodeName().equals("name")){
							log.tracev("text from::{0}", node.getTextContent());
							node.setTextContent(item.getName());
							log.tracev("text into::{0}", node.getTextContent());
						}
					} else {
						//TODO: replace i18n
						throw new ExceptionNotPersisted("Что-то чудное однако! Должен был быть только один элемент!");
					}
				} else {
					generateNewKnowledgeNode(item, parentNode);
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

	@Override
	public Collection<Knowledge> findAllAtRoot() {
		NodeList itemsRaw = storage.getElementsByTagName("knowledge");
		
		// Что-то так себе это вышло у меня
		// for (int i = 0; i < itemsRaw.getLength(); i++) {
		// 	Node itemRaw = itemsRaw.item(i);
		// 	if(itemRaw.getNodeType()==Node.ELEMENT_NODE){
		// 		Element e = (Element)itemRaw;
		// 		NodeList names = e.getElementsByTagName("name");
		// 		for (int j = 0; j < names.getLength(); j++) {
		// 			Node name = names.item(j);
		// 			if (name.getNodeType()==Node.ELEMENT_NODE) {
		// 				Element n = (Element) name;
		// 				log.infov("<>>sample-data-test>name-node-value::{0}", n.getTextContent());
		// 			}
		// 		}
		// 	}
		// }

		// Evaluate XPath against Document itself
		XPath xPath = XPathFactory.newInstance().newXPath();
		// NodeList nodes = (NodeList) xPath.evaluate("//*[text()='" + name + "']", data, XPathConstants.NODESET);
		NodeList items;
		try {
			items = (NodeList) xPath.evaluate("/personal-profile/data/descendant-or-self::*/knowledge", data, XPathConstants.NODESET);
			printNote(items);
			// for (int i = 0; i < items.getLength(); ++i) {
			// 	Element e = (Element) items.item(i);
			// 	log.infov("e::{0}", e);
			// 	log.infov("e.getTagName()::{0}", e.getTagName());
			// 	log.infov("e.getTextContent()::{0}", e.getTextContent());
			// 	// System.out.println("e.getParentNode().getTextContent() = " + e.getParentNode().getTextContent());
			// 	// System.out.println("e.getParentNode().getNodeName() = " + e.getParentNode().getNodeName());
	
			// }
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO: Здесь необходимо сделать конрвертатор из Нодов В Знания
		return Collections.emptyList();
	}

	/**
	 * Сохраняет полностью весь файл.
	 *
	 * @throws ExceptionNotPersisted
	 */
	private void saveXmlFile() throws ExceptionNotPersisted {
		DOMSource source = new DOMSource(storage);
		TransformerFactory factory = TransformerFactory.newInstance();
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		try {
			Transformer transformer = factory.newTransformer();
			StreamResult result = new StreamResult(storagePath);
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);
		} catch (TransformerException e) {
			//TODO: replace i18n
			throw new ExceptionNotPersisted(e.getMessage());
		}
	}

}
