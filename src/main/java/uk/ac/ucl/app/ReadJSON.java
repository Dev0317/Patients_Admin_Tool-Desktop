package uk.ac.ucl.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.script.*;

public class ReadJSON {

    public static List<Patient> parse(File file) throws ScriptException, ParseException, FileNotFoundException, IllegalArgumentException {
        List<Patient> patients = new ArrayList<>();
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\\Z");
        String json = sc.next().replaceAll("'", "\\\\'");
        Map<String, Object> jsonObject = (Map<String, Object>) engine.eval(String.format("JSON.parse('%s')", json));
        Map<String, Object> jsonArray = (Map<String, Object>) jsonObject.get("patients");
        for (Map.Entry<String, Object> entry : jsonArray.entrySet()) {
            Map<String, String> jsonObject2 = (Map<String, String>) entry.getValue();
            String[] data = {jsonObject2.get("id"), jsonObject2.get("birthdate"), jsonObject2.get("deathdate"),
                    jsonObject2.get("ssn"), jsonObject2.get("drivers"), jsonObject2.get("passport"),
                    jsonObject2.get("prefix"), jsonObject2.get("first"), jsonObject2.get("last"),
                    jsonObject2.get("suffix"), jsonObject2.get("maiden"), jsonObject2.get("marital"),
                    jsonObject2.get("race"), jsonObject2.get("ethnicity"), jsonObject2.get("gender"),
                    jsonObject2.get("birthplace"), jsonObject2.get("address"), jsonObject2.get("city"),
                    jsonObject2.get("state"), jsonObject2.get("zip")};
            patients.add(new Patient(data));
        }
        return patients;
    }
}
