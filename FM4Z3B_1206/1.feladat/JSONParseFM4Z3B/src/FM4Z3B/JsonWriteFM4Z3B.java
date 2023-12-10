package FM4Z3B;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonWriteFM4Z3B {

	public static void main(String[] args) throws IOException {
		JSONObject employeeDetails = new JSONObject();
		
        employeeDetails.put("nev", "Pogacsas Benedek");
        employeeDetails.put("neptunkod", "FM4Z3B");
        employeeDetails.put("szak", "GEIK");
        
        JSONArray employeeList = new JSONArray();
        employeeList.add(employeeDetails);
        
        System.out.println(employeeList);
        
        FileWriter file = new FileWriter("JSONfm4z3b_out.json");
        
        file.write(employeeList.toJSONString()); 
        file.flush();
	}

}
