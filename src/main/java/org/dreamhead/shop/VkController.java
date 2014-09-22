package org.dreamhead.shop;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vurtatoo.vk.VkAPIDEp;
import org.vurtatoo.vk.VkApiFactory;
import org.vurtatoo.vk.VkApiSettings;
import org.vurtatoo.vk.VkAudio;
import org.vurtatoo.vk.VkAudioImplementation;
import org.vurtatoo.vk.api.Audio;
import org.vurtatoo.vk.exception.KException;

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
    		
    		VkAPIDEp vkAPI = VkApiFactory.standaloneAppDreamHead;
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
    @RequestMapping(value="vkaudio")
    public @ResponseBody List<Audio> getAudios() throws JSONException, IOException, KException {
    	VkAudio vkAudio = new VkAudioImplementation(new  VkApiSettings("4557449", "kxyj3gaLkfqkCHDWoFNq", "https://oauth.vk.com/blank.html", true).setAuthData("19ce7b1c58598c5011f3347ef0ae7eae2739810895ef957ca18978a7f61b2ea22d932257b7aff38541344", "0", "6704769"));
    	
    	Long uid = 203249956L;
		Long gid = null; 
		Long album_id = null;
		Collection<Long> aids = new ArrayList<Long>();
		String captcha_key = "";
		String captcha_sid = "";
		return vkAudio.getAudio(uid, gid, album_id, aids, captcha_key, captcha_sid);
    }
    

}
