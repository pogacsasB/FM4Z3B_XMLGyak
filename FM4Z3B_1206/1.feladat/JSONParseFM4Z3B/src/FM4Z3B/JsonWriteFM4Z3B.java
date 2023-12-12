package FM4Z3B;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

public class JsonWriteFM4Z3B {

    public static void main(String[] args) {

        try (Reader reader = new FileReader("orarendFM4Z3B.json")) {

            JSONObject JSONObject = new JSONObject();

            JSONArray orak = new JSONArray();
            orak.add(newOra(new String[]{"Adatkezelés XML-ben", "5", "A1 XXXII magasfsz.","Kedd 12","Dr. Bednarik László"}));
            orak.add(newOra(new String[]{"Web technológiák 1", "5", "A1 XXX. 3. em.","Hétfő 14","Agárdi Anita"}));
            orak.add(newOra(new String[]{"WEB alkalmazások(Java)", "5", "In I. em. 102","Kedd 14","Semleci Viktor"}));
            orak.add(newOra(new String[]{"Fizika II.", "4", "A1 XXX. 3. em.","Hétfő 8","Pszota Gábor"}));
            orak.add(newOra(new String[]{"Szoftvertesztelés", "5", "In I. em. 103","Hétfő 10","Dr. Hornyák Olivér"}));
            orak.add(newOra(new String[]{"Mesterséges intelligencia", "5", "A1 XXXII magasfsz.","Kedd 10","Kunné Dr Tamás Judit"}));
            
            for (int i = 0; i < orak.size() ;i++) {
                JSONObject a = (JSONObject) orak.get(i);
                System.out.println("\n"+ (i + 1) + ". Óra:");
                System.out.println("Kurzusnév: " + a.get("kurzusnev"));
		System.out.println("Kredit: " + a.get("kredit"));
		System.out.println("Hely: " + a.get("hely"));
		System.out.println("Időpont: " + a.get("idopont"));
		System.out.println("Oktató: " + a.get("oktato"));
            }

            JSONObject ora = new JSONObject();
            ora.put("ora", orak);
            JSONObject.put("FM4Z3B_orarend", ora);

            FileWriter url = new FileWriter("orarendFM4Z3B1.json");
            url.write(JSONObject.toString());
            url.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static JSONObject newOra(String[] s){

    	//kurzusnev, kredit, hely, idopont, oktato
        JSONObject a = new JSONObject();

        a.put("kurzusnev", s[0]);
        a.put("kredit", s[1]);
        a.put("hely", s[2]);
        a.put("idopont", s[3]);
        a.put("oktato", s[4]);

        return a;
    }
}
