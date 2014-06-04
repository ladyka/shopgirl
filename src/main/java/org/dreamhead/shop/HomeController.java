package org.dreamhead.shop;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.dreamhead.shop.db.BaseManager;
import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.Category;
import org.dreamhead.shop.entity.SystemRole;

/**
 * Handles requests for the application home page.
 */
@Controller
@Transactional
public class HomeController {
	
	@Autowired
    BaseManager baseManager;
	
	@Autowired
	BaseRequest baseRequest;
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String home(Model model,Principal principal) {
		List<Category> categories = baseRequest.getCategories();
		model.addAttribute("categories", categories );
		try {
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			model.addAttribute("user", true);
			model.addAttribute("appUserName", appUser.getNick());
		} catch (NullPointerException exception) {
			model.addAttribute("guest", true);
		}
		return "home";
	}
	

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLoginPage(HttpServletRequest request) {
        return "login";
    }
    
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String getRegister(HttpServletRequest request) {
        return "register";
    }
    
    @RequestMapping(value = "registerpage")
    public String Register(
    		String email,
    		String nick,
    		String phone,
    		String password,
    		Model model
    		) {
    	AppUser appUser = new AppUser();
    	appUser.setActive(2);
    	appUser.setEmail(email);
    	appUser.setPassword(password);
    	appUser.setPhone(phone);
    	appUser.setNick(nick);
    	baseManager.save(appUser);
    	
    	SystemRole systemRole = baseManager.getEntity(SystemRole.class, 1);
    	List<AppUser> appUsers = systemRole.getAppUsers();
    	appUsers.add(appUser);
    	systemRole.setAppUsers(appUsers);
    	
//    	List<SystemRole> systemRoles = appUser.getSystemRoles();
//    	systemRoles.add(systemRole);
    	
    	baseManager.save(appUser);
    	baseManager.save(systemRole);
    	model.addAttribute("rezult", "Зарегестрировался");
        return "rezult";
    }
    
	
}
