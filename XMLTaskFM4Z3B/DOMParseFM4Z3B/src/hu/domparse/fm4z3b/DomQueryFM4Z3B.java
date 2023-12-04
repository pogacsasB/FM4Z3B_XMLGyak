package hu.domparse.fm4z3b;

import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomQueryFM4Z3B {
	
	//Element és id megadása után kiírja az információt
	public static void SpecificIdInfo(String url) {
		boolean check = false;
		try {
            File inputFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            String elementName, attributeValue, attributeName = "";
            Scanner scanner = new Scanner(System.in);
            boolean echeck;
            
            do {
                echeck = true;
            	System.out.println("Which elements information do you want to see?");
                elementName = scanner.nextLine();
                
                switch(elementName) {
            		case "Hajo": attributeName = "hajo_id"; break;
            		case "Kapitany": attributeName = "kapitany_id"; break;
            		case "Rakomany": attributeName = "rakomany_id"; break;
            		case "Ut": attributeName = "ut_id"; break;
            		case "Kikoto": attributeName = "kikoto_id"; break;
            		default: System.out.println("No such element!"); echeck = false;
                }   
            } while(!echeck);
            
            System.out.println("Which "+ elementName + " information do you wanto see?");
            attributeValue = scanner.nextLine();
            
            scanner.close();

            NodeList elements = doc.getElementsByTagName("*");
            for (int i = 0; i < elements.getLength(); i++) {
                Element element = (Element) elements.item(i);
                if (element.hasAttribute(attributeName) && element.getAttribute(attributeName).equals(attributeValue)) {
                	check = true;
                	System.out.println(element.getNodeName() + ": ");
                    NodeList childNodes = element.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if (childNodes.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                            System.out.println("\t" + childNodes.item(j).getNodeName() + ": " + childNodes.item(j).getTextContent().trim());
                        }
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (!check) {
        	System.out.println("No element with this id.");
        }
	}
	
	//Hajók rendezése súlyterhelés alapján(csökkenő)
	public static void ShipCargoWeightOrdered(String url) {
        try {
            Map<String, Double> weightMap = new HashMap<>();

            File inputFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList shipList = doc.getElementsByTagName("Hajo");
            for (int i = 0; i < shipList.getLength(); i++) {
                Element ship = (Element) shipList.item(i);
                String shipId = ship.getAttribute("hajo_id");
                NodeList weightList = ship.getElementsByTagName("sulyterheles");
                if (weightList.getLength() > 0) {
                    Element weightElement = (Element) weightList.item(0);
                    double weight = Double.parseDouble(weightElement.getTextContent().trim());
                    weightMap.put(shipId, weight);
                }
            }

            List<Map.Entry<String, Double>> sortedWeights = new ArrayList<>(weightMap.entrySet());
            sortedWeights.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

            System.out.println("Weight - Ship ID");
            for (Map.Entry<String, Double> entry : sortedWeights) {
                System.out.println(entry.getValue() + " - " + entry.getKey());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//Legrövidebb út megkeresése
	public static void FindShortestRoad(String url) {
        try {
            int shortestRoadLength = Integer.MAX_VALUE;
            String shortestRoadId = "";

            File inputFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList roadList = doc.getElementsByTagName("Ut");
            for (int i = 0; i < roadList.getLength(); i++) {
                Element road = (Element) roadList.item(i);
                String roadId = road.getAttribute("ut_id");
                Element roadLengthElement = (Element) road.getElementsByTagName("uthossz").item(0);
                int roadLength = Integer.parseInt(roadLengthElement.getTextContent().trim());
                if (roadLength < shortestRoadLength) {
                    shortestRoadLength = roadLength;
                    shortestRoadId = roadId;
                }
            }

            if (!shortestRoadId.isEmpty()) {
                System.out.println("Shortest Road ID: " + shortestRoadId);
                System.out.println("Shortest Road Length: " + shortestRoadLength);
            } else {
                System.out.println("No road found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//Utak kezdő és vég kikötőinek információinak kiírása
	public static void RoadDocsInfo(String url) {
        try {
            File inputFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList roadDocsList = doc.getElementsByTagName("Ut_Kikotoi");
            for (int i = 0; i < roadDocsList.getLength(); i++) {
                Element roadDocs = (Element) roadDocsList.item(i);
                String roadAttribute = roadDocs.getAttribute("ut");

                Element startDoc = (Element) roadDocs.getElementsByTagName("indulasi_kikoto").item(0);
                Element endDoc = (Element) roadDocs.getElementsByTagName("erkezesi_kikoto").item(0);
                String startDocId = startDoc.getTextContent().trim();
                String endDocId = endDoc.getTextContent().trim();

                System.out.println("Road ID: " + roadAttribute);
                System.out.println("\tFrom: ");
                outputDocInfo(doc, startDocId);
                System.out.println("\tTo: ");
                outputDocInfo(doc, endDocId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//Segéd metódus a kikötők kiírásához
	private static void outputDocInfo(Document doc, String docId) {
        NodeList docList = doc.getElementsByTagName("Kikoto");
        for (int j = 0; j < docList.getLength(); j++) {
            Element docElement = (Element) docList.item(j);
            String docIdAttribute = docElement.getAttribute("kikoto_id");
            if (docId.equals(docIdAttribute)) {
                Element place = (Element) docElement.getElementsByTagName("helyseg").item(0);
                Element shipStorage = (Element) docElement.getElementsByTagName("hajo_ferohely").item(0);

                Element country = (Element) place.getElementsByTagName("orszag").item(0);
                Element city = (Element) place.getElementsByTagName("varos").item(0);
                String countryName = country.getTextContent().trim();
                String cityName = city.getTextContent().trim();

                String shipStorageValue = shipStorage.getTextContent().trim();

                System.out.println("\t\tCountry: " + countryName);
                System.out.println("\t\tCity: " + cityName);
                System.out.println("\t\tShip Storage: " + shipStorageValue);
                System.out.println();
            }
        }
    }
	
	//Megnézi hogy egy bekérd rakományt eltudna-e szállítani egy bekért hajó
	public static void CanItCarry(String url) {
		try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter rakomany_id: ");
            String cargoId = scanner.nextLine().trim();
            System.out.print("Enter hajo_id: ");
            String shipId = scanner.nextLine().trim();

            File inputFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            double cargoWeight = 0.0;
            double shipWeightLimit = 0.0;

            NodeList cargoList = doc.getElementsByTagName("Rakomany");
            for (int i = 0; i < cargoList.getLength(); i++) {
                Element cargo = (Element) cargoList.item(i);
                String cargoID = cargo.getAttribute("rakomany_id");
                if (cargoID.equals(cargoId)) {
                    Element weight = (Element) cargo.getElementsByTagName("suly").item(0);
                    cargoWeight = Double.parseDouble(weight.getTextContent().trim());
                    break;
                }
            }

            NodeList shipList = doc.getElementsByTagName("Hajo");
            for (int i = 0; i < shipList.getLength(); i++) {
                Element ship = (Element) shipList.item(i);
                String shipID = ship.getAttribute("hajo_id");
                if (shipID.equals(shipId)) {
                    Element weightLimit = (Element) ship.getElementsByTagName("sulyterheles").item(0);
                    shipWeightLimit = Double.parseDouble(weightLimit.getTextContent().trim());
                    break;
                }
            }

            if (cargoWeight > shipWeightLimit) {
                System.out.println("The ship WOULD NOT be able to carry this cargo.");
            } else {
                System.out.println("The ship WOULD be able to carry this cargo.");
            }
            
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
