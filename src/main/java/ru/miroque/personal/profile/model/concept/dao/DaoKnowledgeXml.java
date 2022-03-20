package ru.miroque.personal.profile.model.concept.dao;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



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

//	String usr = document.getElementsByTagName("user").item(0).getTextContent();
//	String pwd = document.getElementsByTagName("password").item(0).getTextContent();

}