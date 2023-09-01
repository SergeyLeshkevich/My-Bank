package by.leshkevich.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class YmlManager {
    Yaml yaml = new Yaml();

    public String getValue(String filename, String key) {
        String s = null;
        try (FileInputStream input = new FileInputStream(filename)) {

            Map<String, Object> data = yaml.load(input);
//            Map<String, String> data = new HashMap<>();
//                    data.put("fine_percent","3");
//                    data.put("url","jdbc:postgresql://localhost:5432/postgres");
//                    data.put("user","postgres");
//                    data.put("password","admin");
//                    data.put("driver","org.postgresql.Driver");
            s = (String) data.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
