package me.automate.utilities;
import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ConfigurationReader {

    private final static Properties properties = new Properties();

    static{

        try {
            FileInputStream file = new FileInputStream("Configuration.properties");
            properties.load(file);
            file.close();
            log.info("Configuration file loaded successfully.");
        } catch (IOException e) {
            log.error("Configuration file not found", e);
        }
    }

    public static String getProperty(String keyword){
        return properties.getProperty(keyword);
    }

}
