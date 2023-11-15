package domfm4z3b1115;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQuery {

	public static void main(String[] args) {
		File xml = new File("FM4Z3B_kurzusfelvetel.xml");
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xml);
			
			NodeList vlista = doc.getElementsByTagName("kurzusnev");
			
			for (int p=0; p<vlista.getLength();p++) {
				 NodeList vgye = vlista.item(p).getChildNodes();
				 //System.out.println(vgye);
			}
		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		} catch (SAXException sxe) {
			System.out.println(sxe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		

	}

}
