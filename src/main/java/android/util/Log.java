package android.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	
	static Logger logger = LoggerFactory.getLogger("");

	public static void i(String tag, String string) {
		logger.info(tag + " " + string); 
	}

}
