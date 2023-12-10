package hu.saxfm4z3b;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SaxFM4Z3B {

	public static void main(String[] args) {
		
		try {

			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

			SAXParser saxParser = saxParserFactory.newSAXParser();

			SaxHandler handler = new SaxHandler();

			saxParser.parse(new File("FM4Z3B_kurzusfelvetel.xml"), handler);

		} catch (ParserConfigurationException | SAXException | IOException e) {

			e.printStackTrace();
		}
	}

	static class SaxHandler extends DefaultHandler {

		private int indent = 0;

		private String formatAttributes(Attributes attributes) {
			int attrLength = attributes.getLength();

			if (attrLength == 0) {
				return "";
			}
			StringBuilder sb = new StringBuilder(", {");
			for (int i = 0; i < attrLength; i++) {
				sb.append(attributes.getLocalName(i) + "-" + attributes.getValue(i));
				if (i < attrLength - 1) {
					sb.append(", ");
				}
			}
			sb.append("}");
			return sb.toString();
		}

		private void indent() {
			for (int i = 0; i < indent; i++) {
				System.out.println(" ");
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			indent++;
			indent();
			System.out.println(qName + formatAttributes(attributes) + " start");
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			indent();
			indent--;
			System.out.println(qName + " end");
		}

		@Override
		public void characters(char ch[], int start, int length) {
			String chars = new String(ch, start, length).trim();
			if (!chars.isEmpty()) {
				indent++;
				indent();
				indent--;
				System.out.println(chars);
			}
		}
	}

}
