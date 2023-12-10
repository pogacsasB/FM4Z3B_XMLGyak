package FM4Z3B;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReadFM4Z3B {

	public static void main(String[] args) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();

		FileReader toParse = new FileReader("JSONFM4Z3B.json");
		
		 Object obj = jsonParser.parse(toParse);
		 
         System.out.println(obj);
	}

}
