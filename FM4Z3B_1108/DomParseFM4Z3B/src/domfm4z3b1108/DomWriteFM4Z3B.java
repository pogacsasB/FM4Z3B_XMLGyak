import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

public class DomWriteFM4Z3B {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("kurzusfelvetelFM4Z3B.xml"));

            document.getDocumentElement().normalize();

            Node root = document.getDocumentElement();

            System.out.println("Root Element: " + root.getNodeName());

            printNodes(root.getChildNodes(), 1);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("kurzusfelvetel1FM4Z3B.xml"));
            transformer.transform(source, result);

            System.out.println("File 'kurzusfelvetel1FM4Z3B.xml' created successfully.");

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
}
