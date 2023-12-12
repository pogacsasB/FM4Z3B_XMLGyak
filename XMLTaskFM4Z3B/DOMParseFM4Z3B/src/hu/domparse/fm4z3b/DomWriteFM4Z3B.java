package hu.domparse.fm4z3b;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import java.util.StringJoiner;

import org.w3c.dom.*;

public class DomWriteFM4Z3B {
	
	public static void Write() {
		try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.newDocument();
            
            //Gyökérelem
            Element rootElement = document.createElement("XMLFM4Z3B");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaFM4Z3B.xsd");
            document.appendChild(rootElement);
            
            //Hajok
            createHajo(document, rootElement, "1", "1", "4", "5400", "Ever Golden");
            createHajo(document, rootElement, "2", "2", "2", "3200", "Madrid Maersk");
            createHajo(document, rootElement, "3", "3", "6", "7600", "CMA CGM");
            
            //Kapitanyok
            createKapitany(document, rootElement, "1", "Katona Ferenc", "magyar", "1974-02-14");
            createKapitany(document, rootElement, "2", "Charles Thompson", "angol", "1982-11-24");
            createKapitany(document, rootElement, "3", "Fernando Oliveira", "portugál", "1969-07-21");
            
            //Rakomanyok
            String[] ea1 = {"gyümölcsök", "zöldségek"};
            createRakomany(document, rootElement, "1", "2", ea1, "nincs", "ruhák", "1400");
            String[] ea2 = {"tejtermékek", "gyümölcsök"};
            createRakomany(document, rootElement, "2", "1", ea2, "autók", "nincs", "3000");
            String[] ea3 = {"zöldségek", "tejtermékek"};
            createRakomany(document, rootElement, "3", "3", ea3, "motorok", "szőnyegek", "6400");
            String[] ea4 = {"nincs"};
            createRakomany(document, rootElement, "4", "3", ea4, "autók", "nincs", "3000");
            
            //Utak
            createUt(document, rootElement, "1", "9841");
            createUt(document, rootElement, "2", "3576");
            createUt(document, rootElement, "3", "10500");
            
            //Kikotok
            createKikoto(document, rootElement, "1", "Rio de Janeiro", "Brazília", "11");
            createKikoto(document, rootElement, "2", "Piraeus", "Görögország", "8");
            createKikoto(document, rootElement, "3", "Shanghai", "Kína", "15");
            
            //Megtett_Utak
            createMegtettUt(document, rootElement, "1", "1", "2020-11-12", "2020-11-15");
            createMegtettUt(document, rootElement, "2", "3", "2020-02-26", "2020-03-02");
            createMegtettUt(document, rootElement, "3", "2", "2021-04-01", "2021-04-04");
            
            //Utak_Kikotoik
            createUtKikotoi(document, rootElement, "1", "3", "2");
            createUtKikotoi(document, rootElement, "2", "2", "1");
            createUtKikotoi(document, rootElement, "3", "1", "3");
                       		
            //Dokumentum kiírása a koznolra és egy fájlba
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");

            printDoc(document);   
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	//Hajo hozzáadása
	private static void createHajo(Document doc, Element root, String hajoid, String kapitany, String mrsz, String st, String mk) {
        Element hajo = doc.createElement("Hajo");
        hajo.setAttribute("hajo_id", hajoid);
        hajo.setAttribute("kapitany", kapitany);

        Element Element_mrsz = createElement(doc, "max_rakomany_szam", mrsz);
        Element Element_st = createElement(doc, "sulyterheles", st);
        Element Element_mk = createElement(doc, "marka", mk);
        hajo.appendChild(Element_mrsz);
        hajo.appendChild(Element_st);
        hajo.appendChild(Element_mk);

        root.appendChild(hajo);
	}
	
	//Kapitany hozzáadása
	private static void createKapitany(Document doc, Element root, String kapitanyid, String nev, String nzt, String szuld) {
        Element kapitany = doc.createElement("Kapitany");
        kapitany.setAttribute("kapitany_id", kapitanyid);

        Element Element_nev = createElement(doc, "nev", nev);
        Element Element_nzt = createElement(doc, "nemzetiseg", nzt);
        Element Element_szuld = createElement(doc, "szuletesi_datum", szuld);
        kapitany.appendChild(Element_nev);
        kapitany.appendChild(Element_nzt);
        kapitany.appendChild(Element_szuld);

        root.appendChild(kapitany);
	}
	
	//Rakomany hozzáadása
	private static void createRakomany(Document doc, Element root, String rakomanyid, String hajoid,String[] ea, String jrm, String tt, String suly) {
        Element rakomany = doc.createElement("Rakomany");
        rakomany.setAttribute("rakomanyid", rakomanyid);
        rakomany.setAttribute("hajo", hajoid);
        Element tipus = doc.createElement("tipus");
        rakomany.appendChild(tipus);
        
        Element[] Element_ea = new Element[ea.length];
        if (ea.length >= 1 && ea[0].equals("nincs") == false) {
        	for (int i = 0; i < ea.length; i++) {
        		Element_ea[i] = createElement(doc, "etel_alapanyag", ea[i]);
        		tipus.appendChild(Element_ea[i]);
        	}
        }
        if (jrm.equals("nincs") == false) {
        	Element Element_jrm = createElement(doc, "jarmu", jrm);
        	tipus.appendChild(Element_jrm);
        }
        if (tt.equals("nincs") == false) {
        	Element Element_tt = createElement(doc, "textil_termek", tt);
        	tipus.appendChild(Element_tt);
        }

        Element Element_suly = createElement(doc, "suly", suly);
        rakomany.appendChild(Element_suly);

        root.appendChild(rakomany);
	}
	
	//Ut hozzáadása
	private static void createUt(Document doc, Element root, String utid, String uh) {
        Element ut = doc.createElement("Ut");
        ut.setAttribute("ut_id", utid);

        Element Element_uh = createElement(doc, "uthossz", uh);
        ut.appendChild(Element_uh);

        root.appendChild(ut);
	}
	
	//Kikoto hozzáadása
	private static void createKikoto(Document doc, Element root, String kikotoid, String og, String vs, String hf) {
        Element kikoto = doc.createElement("Kikoto");
        kikoto.setAttribute("kikoto_id", kikotoid);
        
        Element hely = doc.createElement("helyseg");

        Element Element_og = createElement(doc, "orszag", og);
        Element Element_vs = createElement(doc, "varos", vs);
        Element Element_hf = createElement(doc, "hajo_ferohely", hf);
        hely.appendChild(Element_og);
        hely.appendChild(Element_vs);
        kikoto.appendChild(hely);
        kikoto.appendChild(Element_hf);

        root.appendChild(kikoto);
	}
	
	//Megtett_Ut hozzáadása
	private static void createMegtettUt(Document doc, Element root, String utid, String hajoid, String ii, String ei) {
        Element mu = doc.createElement("Megtett_Ut");
        mu.setAttribute("ut_id", utid);
        mu.setAttribute("hajo", hajoid);

        Element Element_ii = createElement(doc, "indulasi_ido", ii);
        Element Element_ei = createElement(doc, "erkezesi_ido", ei);
        mu.appendChild(Element_ii);
        mu.appendChild(Element_ei);

        root.appendChild(mu);
	}
	
	//Ut_Kikotoi hozzáadása
	private static void createUtKikotoi(Document doc, Element root, String utid, String ik, String ek) {
		Element mu = doc.createElement("Ut_Kikotoi");
		mu.setAttribute("ut_id", utid);

		Element Element_ik = createElement(doc, "indulasi_kikoto", ik);
		Element Element_ek = createElement(doc, "erkezesi_kikoto", ek);
		mu.appendChild(Element_ik);
		mu.appendChild(Element_ek);
		
		root.appendChild(mu);
	}
	
	//Segédmetódus Element-ek készítéséhez
	private static Element createElement(Document document, String name, String value) {
		Element element = document.createElement(name);
		element.appendChild(document.createTextNode(value));
		 
		return element;
	}
	
	//Segédmetódus a NodeList-ek kezeléséhez
	private static void printNodes(NodeList nodeList, PrintWriter writer) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			printNode(node, 1, writer);
			System.out.println("");
			writer.println("");
		}
	}
	
	//Segédmetódus a Node-ok kezeléséhez
	private static void printNode(Node node, int indent, PrintWriter writer) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			String nodeName = element.getTagName();
			StringJoiner attributes = new StringJoiner(" ");
			NamedNodeMap attributeMap = element.getAttributes();
			//Elem neve és attribútumainak kiírása
			for (int i = 0; i < attributeMap.getLength(); i++) {
				Node attribute = attributeMap.item(i);
				attributes.add(attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
			}

			System.out.print(indentHandler(indent));
			System.out.print("<" + nodeName + " " + attributes.toString() + ">");

			writer.print(indentHandler(indent));
			writer.print("<" + nodeName + " " + attributes.toString() + ">");

			NodeList children = element.getChildNodes();
			if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
				System.out.print(children.item(0).getNodeValue());
				writer.print(children.item(0).getNodeValue());
			} else {
				System.out.println();
				writer.println();
				for (int i = 0; i < children.getLength(); i++) {
					printNode(children.item(i), indent + 1, writer);
				}
				System.out.print(indentHandler(indent));
				writer.print(indentHandler(indent));
			}
			System.out.println("</" + nodeName + ">");
			writer.println("</" + nodeName + ">");
		}
	}
	
	private static void printDoc(Document doc) {
		try {
			File url = new File("XMLFM4Z3B1.xml");
			PrintWriter writer = new PrintWriter(new FileWriter(url, true));
	            
			Element rootElement = doc.getDocumentElement();
			String rootName = rootElement.getTagName();
	            
			//Gyökérelem attribútumainak kiírása
			StringJoiner rootAttributes = new StringJoiner(" ");
	            
			//Gyökérelem attribútumainak lekérése
			NamedNodeMap rootAttributeMap = rootElement.getAttributes();

			for (int i = 0; i < rootAttributeMap.getLength(); i++) {
				Node attribute = rootAttributeMap.item(i);
				rootAttributes.add(attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
			}

			System.out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

			System.out.print("<" + rootName + " " + rootAttributes.toString() + ">\n");
			writer.print("<" + rootName + " " + rootAttributes.toString() + ">\n");

			NodeList list_of_Hajo = doc.getElementsByTagName("Hajo");
			NodeList list_of_Kapitany = doc.getElementsByTagName("Kapitany");
			NodeList list_of_Rakomany = doc.getElementsByTagName("Rakomany");
			NodeList list_of_Ut = doc.getElementsByTagName("Ut");
			NodeList list_of_Kikoto = doc.getElementsByTagName("Kikoto");
			NodeList list_of_Megtett_Ut = doc.getElementsByTagName("Megtett_Ut");
			NodeList list_of_Ut_Kikotoi = doc.getElementsByTagName("Ut_Kikotoi");

			printNodes(list_of_Hajo, writer);
			printNodes(list_of_Kapitany, writer);
			printNodes(list_of_Rakomany, writer);
			printNodes(list_of_Ut, writer);
			printNodes(list_of_Kikoto, writer);
			printNodes(list_of_Megtett_Ut, writer);  
			printNodes(list_of_Ut_Kikotoi, writer);
	            
			System.out.println("</" + rootName + ">");
			writer.append("</" + rootName + ">");

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	private static String indentHandler(int indent) {
		StringBuilder tab = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			tab.append("\t");
		}
		return tab.toString();
	}
}