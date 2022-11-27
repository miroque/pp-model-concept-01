package ru.miroque.play;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlPlay {

    private static final String FILENAME = "standard.xml";
    private static final String FILENAME_MINI = "standard-minified.xml";

    public static void main(String[] args) {
        try {

        Document document =  DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILENAME));
        // Document document =  DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILENAME_MINI));

        
        System.out.println("Root Element :" + document.getDocumentElement().getNodeName());
        System.out.println("------");
        document.normalize();

        if (document.hasChildNodes()) {
            printNote(document.getChildNodes());
        }



        } catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }

    }
    
    private static void printNote(NodeList nodeList) {

        for (int count = 0; count < nodeList.getLength(); count++) {
  
            Node tempNode = nodeList.item(count);
  
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
  
                // get node name and value
                System.out.println("\nNode Name = " + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node (TextContent) Value = " + tempNode.getTextContent());
  
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
  
                System.out.println("Node Name = " + tempNode.getNodeName() + " [CLOSE]");
  
            }
  
        }
  
    }
}