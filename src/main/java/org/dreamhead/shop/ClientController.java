package org.dreamhead.shop;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.vk.VkApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Transactional
@Controller
public class ClientController {

	@Autowired
	BaseRequest baseRequest;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @RequestMapping(value = "clientmain")
    public String clientmain(Model model) {
    	List<AppUserView> appUserViews = new ArrayList<AppUserView>();
    	List<AppUser> appUsers = baseRequest.getListEntity(AppUser.class);
    	for (AppUser appUser : appUsers) {
    		appUserViews.add(new AppUserView(appUser));
		}
    	model.addAttribute("elements", appUserViews);
        return "clientmain";
    }
    
    
    @RequestMapping(value = "client/ban/{id}")
    public String clientban(Model model,@PathVariable(value = "id") int id,Principal principal) {
    	AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
    	if ( (appUser.getRole() == AppUser.ADMIN) || (appUser.getRole() == AppUser.MANAGER) ) {
    		AppUser banUser = baseRequest.getEntity(AppUser.class, id);
    		banUser.setRole(AppUser.BAN);
    		baseRequest.saveOrUpdate(banUser);
    		logger.warn("USER " + banUser.toString() + " was banned " + appUser.getNameRole() + " " + appUser.toString());
    		model.addAttribute("rezult", "Забанен");
    	} else {
    		model.addAttribute("rezult", "Нет прав для выполнения данного действия");	
    	}
        return "rezult";
    }
    
    
    @RequestMapping(value = "cabinet")
    public String cabinet(Model model,Principal principal) {
    	try {
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			model.addAttribute("user", true);
			model.addAttribute("guest", false);
			model.addAttribute("appUserName", appUser.getNick());
			model.addAttribute("redirect_uri", VkApiFactory.standaloneAppDreamHead.getVkApiSettings().getUriWebApp());
			model.addAttribute("client_id", VkApiFactory.standaloneAppDreamHead.getVkApiSettings().getAppID());
		} catch (NullPointerException exception) {
			model.addAttribute("guest", true);
			model.addAttribute("user", false);
		}
        return "cabinet";
    }
//    
//    @RequestMapping(value = "setadmin")
//    public @ResponseBody String cabinet(Model model,Principal principal) {
//    	AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
//    	appUser.setRole(AppUser.ADMIN);
//    	baseRequest.saveOrUpdate(appUser);
//        return appUser.toString();
//    }
}
