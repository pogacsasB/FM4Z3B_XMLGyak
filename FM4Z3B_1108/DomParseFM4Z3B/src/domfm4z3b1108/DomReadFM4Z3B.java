package domfm4z3b1108;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class DomReadFM4Z3B {
	public DomReadFM4Z3B() {
	}
	
	public static void main(String[] args) {
		DomReadFM4Z3B prog = new DomReadFM4Z3B();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		
	}
	
	public void query1(Document dom) {
		Node root = dom.getDocumentElement();
		System.out.println(root);
	}


}
