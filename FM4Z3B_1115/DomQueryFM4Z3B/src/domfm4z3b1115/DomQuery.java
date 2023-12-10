package domfm4z3b1115;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;

public class DomQuery {
    public static void main(String[] args) {
        try {
            File inputFile = new File("kurzusfelvetelFM4Z3B.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);

            document.getDocumentElement().normalize();

            System.out.println("Kurzusnevek:");
            NodeList kurzusnevList = document.getElementsByTagName("kurzusnev");
            for (int i = 0; i < kurzusnevList.getLength(); i++) {
                Node node = kurzusnevList.item(i);
                System.out.println(node.getTextContent());
            }

            System.out.println("\nOktatok:");
            NodeList oktatoList = document.getElementsByTagName("oktato");
            for (int i = 0; i < oktatoList.getLength(); i++) {
                Node node = oktatoList.item(i);
                System.out.println(node.getTextContent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
