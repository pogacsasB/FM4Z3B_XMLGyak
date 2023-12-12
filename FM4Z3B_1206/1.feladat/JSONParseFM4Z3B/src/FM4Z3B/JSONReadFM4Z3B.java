package FM4Z3B;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONReadFM4Z3B {

	public static void main(String[] args) {

		JSONParser parser = new JSONParser();

		try {
			
			Object obj = parser.parse(new FileReader("orarendFM4Z3B.json"));

			JSONObject jsonObject = (JSONObject) obj;
			JSONObject o = (JSONObject) jsonObject.get("FM4Z3B_orarend");

			JSONArray kurzusok = (JSONArray) o.get("kurzusok");
			for (int i = 0; i < kurzusok.size(); i++) {

				System.out.println(i+1 + ". kurzus:\n");
				
				JSONObject a = (JSONObject) kurzusok.get(i);
				System.out.println("Kurzusnév: " + a.get("kurzusnev"));
				System.out.println("Kredit: " + a.get("kredit"));
				System.out.println("Hely: " + a.get("hely"));
				System.out.println("Időpont: " + a.get("idopont"));
				System.out.println("Oktató: " + a.get("oktato"));
				
				System.out.print("\n");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
