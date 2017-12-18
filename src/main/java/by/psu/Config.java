package by.psu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties property = new Properties();

    private static Config config = null;

    public static Config getInstance(){
        if(config == null)
            config = new Config();
        return config;
    }

    private Config(){
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String titleProperty){
        return property.getProperty(titleProperty);
    }

    public void setProperty(String key, String value){
        property.setProperty(key, value);
        try (FileOutputStream fos = new FileOutputStream("src/main/resources/config.properties")){
            property.store(fos, "new values");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
