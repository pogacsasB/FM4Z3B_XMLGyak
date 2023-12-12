package hu.domparse.fm4z3b;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String url = "XMLFM4Z3B.xml";
		
		int input;
		
		System.out.println("Welcome!");
		
		Scanner scanner = new Scanner(System.in);
		input = 0;
		System.out.println("All the available commands:");
		System.out.println("  1. Read its content.");
		System.out.println("  2. Read its content in a structured manner.");
		System.out.println("  3. Modify an already existing element.");
		System.out.println("  4. Write the file in a tree-like structure.");
		System.out.println("\n  Queries:");
		System.out.println("  5. Specific ID search.");
		System.out.println("  6. Which ship can carry the most weight?");
		System.out.println("  7. Which road is the shortest?");
		System.out.println("  8. Info about each roads Docs.");
		System.out.println("  9. Can the ship carry the cargo?");
		System.out.println("\nSelect what you want to do in XMLFM4Z3B.xml");
		input = scanner.nextInt();
		
		switch(input) {
			case 1: DomReadFM4Z3B.Read(url); break;
			case 2: DomReadFM4Z3B.StructuredRead(url); break;
			case 3: DomModifyFM4Z3B.Modify(url); DomReadFM4Z3B.StructuredRead(url); break;
			case 4:	DomWriteFM4Z3B.Write(); break;
			case 5: DomQueryFM4Z3B.SpecificIdInfo(url); break;
			case 6:	DomQueryFM4Z3B.ShipCargoWeightOrdered(url); break;
			case 7:	DomQueryFM4Z3B.FindShortestRoad(url); break;
			case 8: DomQueryFM4Z3B.RoadDocsInfo(url); break;
			case 9: DomQueryFM4Z3B.CanItCarry(url); break;
			default: System.out.println("No such command.");
		}
		scanner.close();
	}
}
