package org.dreamhead.shop;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.vk.VkAPI;
import org.dreamhead.vk.VkApiFactory;
import org.dreamhead.vk.VkApiSettings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VkController {
	
	@Autowired
	BaseRequest baseRequest;
	
//	public static final String appID = "3590204";
//	public static final String appSecret = "TvtfW9grTj1hEou3rZMK";
//	//public static final String uriWebApp = "http://dreamhead.ru/store/";
//	public static final String uriWebApp = "http://promo.quadrate64.com:14024/store/vk";
//	
    @RequestMapping(value = "vk")
    public String vk(Model model, String code,Principal principal) {
    	try {
    		
    		VkAPI vkAPI = VkApiFactory.standaloneAppDreamHead;
    		vkAPI.autorizeApp(code);
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			VkApiSettings vkApiSettings = vkAPI.getVkApiSettings();
			appUser.setVktocken(vkApiSettings.getAccess_token());
			appUser.setVkid(vkApiSettings.getUser_id());
			baseRequest.saveOrUpdate(appUser);
			model.addAttribute("rezult", true);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		model.addAttribute("rezult", false);
    	}
    	return "rezult";
    }
    
    

}
