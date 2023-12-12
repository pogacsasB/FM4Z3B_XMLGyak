package hu.domparse.fm4z3b;

import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class DomModifyFM4Z3B {
	
	//Egy metódus, amely megadott információk után módosítja az XML fájl adatait
	public static void Modify(String url) {
		try {
            File inputFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Scanner scanner = new Scanner(System.in);
            String parentElementName;
            String parentAttributeName = "", parentAttributeName2 = "";
            String parentAttributeValue, parentAttributeValue2 = "";
            String targetElementName;
            NodeList list;
            boolean ok = false;
            
            do {
                System.out.print("Enter the parent element's name: ");
                parentElementName = scanner.nextLine();
                list = doc.getElementsByTagName(parentElementName);
                
                if(list.getLength() == 0) {
                	System.out.println("No such parent element found.");
                }   
            } while(list.getLength() == 0);
            
            switch(parentElementName) {
            	case "Hajo": parentAttributeName = "hajo_id"; break;
            	case "Kapitany": parentAttributeName = "kapitany_id"; break;
            	case "Rakomany": parentAttributeName = "rakomany_id"; break;
            	case "Ut": parentAttributeName = "ut_id"; break;
            	case "Kikoto": parentAttributeName = "kikoto_id"; break;
            }
            
            if (parentElementName.equals("Megtett_Ut")) {
            	System.out.print("Enter hajo's value: ");
                parentAttributeValue = scanner.nextLine();
                parentAttributeName = "hajo";
                
                System.out.print("Enter ut's value: ");
                parentAttributeValue2 = scanner.nextLine();
                parentAttributeName2 = "ut";
            } else {
            	System.out.print("Enter " + parentAttributeName + "'s value: ");
                parentAttributeValue = scanner.nextLine();
            }
            
            do {
            	System.out.print("Enter the target element's name: ");
                targetElementName = scanner.nextLine();
                list = doc.getElementsByTagName(targetElementName);
                
                if(list.getLength() == 0) {
                	System.out.println("No such target element found.");
                }   
            } while(list.getLength() == 0);
            
            System.out.print("Enter the target element's new value: ");
            String targetElementNewValue = scanner.nextLine();

            NodeList parentList = doc.getElementsByTagName(parentElementName);
            for (int i = 0; i < parentList.getLength(); i++) {
                Element parentElement = (Element) parentList.item(i);
                if (parentElementName.equals("Megtett_Ut")) {
                	if (parentElement.hasAttribute(parentAttributeName) && parentElement.getAttribute(parentAttributeName).equals(parentAttributeValue) &&
                			parentElement.hasAttribute(parentAttributeName2) && parentElement.getAttribute(parentAttributeName2).equals(parentAttributeValue2)) {
                		ok = true;
                		NodeList childList = parentElement.getElementsByTagName(targetElementName);
                        for (int j = 0; j < childList.getLength(); j++) {
                            Element targetElement = (Element) childList.item(j);
                            targetElement.setTextContent(targetElementNewValue);
                        }
                    }
                	
                } else {
                	if (parentElement.hasAttribute(parentAttributeName) && parentElement.getAttribute(parentAttributeName).equals(parentAttributeValue)) {
                        ok = true;
                		NodeList childList = parentElement.getElementsByTagName(targetElementName);
                        for (int j = 0; j < childList.getLength(); j++) {
                            Element targetElement = (Element) childList.item(j);
                            targetElement.setTextContent(targetElementNewValue);
                        }
                    }
                }
            }

            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(new File(url));
            transformer.transform(source, result);

            if (ok) {
            	System.out.println("XML file updated successfully.");
            } else {
            	System.out.println("Update failed.");
            }
            
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
