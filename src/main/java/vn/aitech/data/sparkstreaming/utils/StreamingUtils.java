/**
 * 
 */
package vn.aitech.data.sparkstreaming.utils;

import java.io.IOException;
import java.util.Properties;


/**
 * @author thanhdq
 *
 */
public class StreamingUtils {
	
	public static Properties loadConfig(String path) {
		Properties props = new Properties();
		try {
			// load a properties file from class path, inside static method
			props.load(StreamingUtils.class.getClassLoader().getResourceAsStream(path));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return props;
	}

}
