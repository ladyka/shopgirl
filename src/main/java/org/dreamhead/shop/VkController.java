package org.dreamhead.shop;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
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
	
	public static final String appID = "3590204";
	public static final String appSecret = "TvtfW9grTj1hEou3rZMK";
	//public static final String uriWebApp = "http://dreamhead.ru/store/";
	public static final String uriWebApp = "http://promo.quadrate64.com:14024/store/vk";
	
    @RequestMapping(value = "vk")
    public String vk(Model model, String code,Principal principal) {
    	try {
    		VkAPI vkAPI = new VkAPI(appID, appSecret, uriWebApp);
        	String string = vkAPI.autorizeWebSite(code);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(string);
			String access_token = String.valueOf(jsonObject.get("access_token"));
			//jsonObject.get("expires_in");
			String user_id = String.valueOf(jsonObject.get("user_id"));
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			appUser.setVktocken(access_token);
			appUser.setVkid(user_id);
			baseRequest.saveOrUpdate(appUser);
			model.addAttribute("rezult", true);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		model.addAttribute("rezult", false);
    	}
    	return "rezult";
    }

}
