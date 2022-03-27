package ru.miroque.personal.profile.model.concept.dao;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.util.Collection;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;


public class DaoKnowledgeXml implements DaoKnowledge {
	private Document storage;

	public DaoKnowledgeXml(File file) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			storage = documentBuilder.parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("dao");
		printDocument(storage, System.out);
//		NodeList nodeList = storage.getChildNodes();
//		System.out.println(nodeList.getLength());
//		System.out.println(storage.getFirstChild().getFirstChild().getNodeName());
//		System.out.println(storage.getFirstChild().getNodeValue());
		
	}
	
	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), 
	         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}

	@Override
	public Collection<Knowledge> findByName(String name) throws XPathExpressionException {
		//Evaluate XPath against Document itself
		XPath xPath = XPathFactory.newInstance().newXPath();
//		NodeList nodes = (NodeList)xPath.evaluate("/personal-profile/data/ancestor-or-self::knowlage/name/", storage, XPathConstants.NODESET);
		NodeList nodes = (NodeList)xPath.evaluate("//*[text()='"+name+"']", storage, XPathConstants.NODESET);
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

	@Override
	public void persist(Knowledge item) {

	}

//	String usr = document.getElementsByTagName("user").item(0).getTextContent();
//	String pwd = document.getElementsByTagName("password").item(0).getTextContent();


}
