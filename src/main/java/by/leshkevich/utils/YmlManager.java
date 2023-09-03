package by.leshkevich.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class works with yaml files
 */
public class YmlManager {
    Yaml yaml = new Yaml();

    /**
     * this method gets value from file .yml by key
     */
    public String getValue(String filename, String key) {
        String s = null;
        try (FileInputStream input = new FileInputStream(filename)) {

            Map<String, Object> data = yaml.load(input);
            s = (String) data.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
