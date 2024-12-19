package utils;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FileConstant {

	private static Properties prop;
	
	public static final String ROOT_PATH = System.getProperty("user.dir");

	public static final Path APPCONFIG_PROPERTY_FILE = Paths.get(ROOT_PATH, "src", "main",
			"resources", "propertyfiles", "appconfig.properties");
	
	

	public static String getAppConfigFilePath() {
		return APPCONFIG_PROPERTY_FILE.toString();
	}

	public static Properties loadPropertyFile(String filePath) {
		prop = new Properties();
		try (FileInputStream fi = new FileInputStream(filePath)) {
			prop.load(fi);
			return prop;
		} catch (Exception e) {

		}
		return prop;
	}

	public static String getPropertyValue(String filePath, String propertykey) {
		if (filePath != null) {
			prop = loadPropertyFile(filePath);
		}
		if (propertykey != null) {
			return prop.getProperty(propertykey);
		}
		return null;
	}

}
