package ru.miroque.personal.profile.model.concept.dao;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DaoKnowledgeXml implements DaoKnowledge {
	private Document storage;

	public DaoKnowledgeXml(File file) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			storage = documentBuilder.parse(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("dao");
		NodeList nodeList = storage.getChildNodes();
		System.out.println(nodeList.getLength());
		System.out.println(storage.getFirstChild().getFirstChild().getNodeName());
//		System.out.println(storage.getFirstChild().getNodeValue());
		
	}

//	String usr = document.getElementsByTagName("user").item(0).getTextContent();
//	String pwd = document.getElementsByTagName("password").item(0).getTextContent();

}
