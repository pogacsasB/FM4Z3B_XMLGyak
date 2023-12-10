package domfm4z3b1108;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class DomReadFM4Z3B {
	
	public static void main(String[] args) {
        	try {
            		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            		DocumentBuilder builder = factory.newDocumentBuilder();
            		Document document = builder.parse("kurzusfelvetelFM4Z3B.xml");

            		document.getDocumentElement().normalize();

            		Node root = document.getDocumentElement();
            
            		System.out.println("Root Element: " + root.getNodeName());

            		printNodes(root.getChildNodes(), 1);

        	} catch (Exception e) {
            		e.printStackTrace();
        	}
    	}
	
	private static void printNodes(NodeList nodeList, int level) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                String nodeValue = node.getNodeValue();

                StringBuilder indent = new StringBuilder();
                for (int j = 0; j < level; j++) {
                    indent.append("  ");
                }

                System.out.println(indent + nodeName + ": " + nodeValue);

                if (node.hasChildNodes()) {
                    printNodes(node.getChildNodes(), level + 1);
                }
            }
        }
}
