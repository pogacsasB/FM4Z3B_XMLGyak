package FM4Z3B;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

public class ListFM4Z3B {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		
		list.add("Pogacsas Benedek");
		list.add("FM4Z3B");
		list.add("GEIK");
		
		String jsonStr = JSONArray.toJSONString(list);
		System.out.println(jsonStr);
		
	}

}
