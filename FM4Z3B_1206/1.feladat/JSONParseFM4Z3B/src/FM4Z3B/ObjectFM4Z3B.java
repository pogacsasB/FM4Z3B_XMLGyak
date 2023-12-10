package FM4Z3B;

import org.json.simple.JSONObject;

public class ObjectFM4Z3B {

	public static void main(String[] args) {


		JSONObject student = new JSONObject();
		
		student.put("nev", "Pogacsas Benedek");
		student.put("neptunkod", "FM4Z3B");
		student.put("szak", "GEIK");
		
		System.out.println(student.toString());
		
	}

}
