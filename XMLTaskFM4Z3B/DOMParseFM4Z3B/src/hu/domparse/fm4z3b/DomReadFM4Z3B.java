package hu.domparse.fm4z3b;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

public class DomReadFM4Z3B {
	
	//Sima kiíratás
	public static void Read(String url) {
		try {
	        File inputFile = new File(url);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();

	        traverseNodes(doc.getDocumentElement(), "");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//Struktúrált kiíratás
	public static void StructuredRead(String url) {
		try {
            File inputFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            String xmlContent = docToString(doc);
            System.out.println(xmlContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	//Documentből Stringre konvertelő függvény
	private static String docToString(Document doc) {
        try {
            javax.xml.transform.TransformerFactory tf = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
            java.io.StringWriter writer = new java.io.StringWriter();
            transformer.transform(new javax.xml.transform.dom.DOMSource(doc), new javax.xml.transform.stream.StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	//Rekurzív metódus, ami képes kiírni egy elem gyerek elemét
	private static void traverseNodes(Node node, String indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String elementName = node.getNodeName();
            System.out.print(indent + elementName + ":");
            
            if (node.hasAttributes()) {
                NamedNodeMap attributes = node.getAttributes();
                System.out.print(" (");
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    if (i == 0) {
                    	System.out.print(attribute.getNodeName() + " = " + attribute.getNodeValue());
                    } else {
                    	System.out.print(", " + attribute.getNodeName() + " = " + attribute.getNodeValue());
                    }
                }
                System.out.print(")");
            }
            
            if (node.hasChildNodes()) {
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.print("\n");
                    	traverseNodes(childNode, indent + "\t");
                    } else if (childNode.getNodeType() == Node.TEXT_NODE && !childNode.getNodeValue().trim().isEmpty()) {
                        System.out.print(" " + childNode.getNodeValue().trim());
                    }
                }
            }
        }
    }
}
