package org.vurtatoo.vk;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class VkApiFactory {
	
	

	public static VkAPIDEp standaloneAppDreamHead;
	
	static {
		VkApiSettings standaloneApp  = new VkApiSettings("3590186", "SblpGUbUx7BFSYkrqyPL", "http://shop.ladyka.tk:14024/store/vk",true);
		standaloneAppDreamHead = new VkAPIDEp(standaloneApp);
	}
}
