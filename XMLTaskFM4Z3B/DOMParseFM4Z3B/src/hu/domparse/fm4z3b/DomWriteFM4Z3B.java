package hu.domparse.fm4z3b;

import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomWriteFM4Z3B {
	
	//Fa struktúrába kiiratás 
    public static void Write(String url) {
        try {
            File inputFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();
            displayElement(rootElement, 0);

            File outputFile = new File("XMLFM4Z3B1.xml");
            FileWriter writer = new FileWriter(outputFile);
            writeElement(rootElement, writer, 0);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Segéd metódus a kiiratáshoz
    private static void displayElement(Element element, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + element.getTagName());

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element) {
                displayElement((Element) childNodes.item(i), depth + 1);
            }
        }
    }

    //segéd metódus a fájlba íráshoz
    private static void writeElement(Element element, FileWriter writer, int depth) throws Exception {
        String indent = "  ".repeat(depth);
        writer.write(indent + "<" + element.getTagName() + ">\n");
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element) {
                writeElement((Element) childNodes.item(i), writer, depth + 1);
            }
        }
        writer.write(indent + "</" + element.getTagName() + ">\n");
    }
}
