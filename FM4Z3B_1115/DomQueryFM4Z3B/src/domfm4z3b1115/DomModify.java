package domfm4z3b1115

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;

public class DomModify {
    public static void main(String[] args) {
        try {
            File inputFile = new File("kurzusfelvetelFM4Z3B.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);

            document.getDocumentElement().normalize();

            String targetElementName = "kurzusnev";
            String newValue = "New Course Name";

            NodeList nodeList = document.getElementsByTagName(targetElementName);
            if (nodeList.getLength() > 0) {
                Node node = nodeList.item(0);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    element.setTextContent(newValue);
                    System.out.println("Element '" + targetElementName + "' modified successfully.");
                }
            } else {
                System.out.println("Element '" + targetElementName + "' not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
