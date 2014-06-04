package org.dreamhead.shop;


import java.util.ArrayList;
import java.util.List;

import org.dreamhead.shop.db.BaseManager;
import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.SystemRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
public class ClientController {

	@Autowired
	BaseManager baseRequest;   
	
    @RequestMapping(value = "clientmain")
    public String clientmain(Model model) {
    	SystemRole user = baseRequest.getEntity(SystemRole.class, 1);
    	List<AppUserView> appUserViews = new ArrayList<AppUserView>();
    	List<AppUser> appUsers = user.getAppUsers();
    	for (AppUser appUser : appUsers) {
    		appUserViews.add(new AppUserView(appUser));
		}
    	model.addAttribute("elements", appUserViews);
        return "clientmain";
    }
    
    
    @RequestMapping(value = "client/ban/{id}")
    public String clientban(Model model,@PathVariable(value = "id") int id) {
    	SystemRole role = baseRequest.getEntity(SystemRole.class, 1);
    	try {
    		for (AppUser iterable_element : role.getAppUsers()) {
    			if (iterable_element.getId() == id) {
    				iterable_element.setSystemRoles(new ArrayList<SystemRole>());
    				baseRequest.saveOrUpdate(iterable_element);
    				model.addAttribute("rezult", "Забанен");
    			}
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		model.addAttribute("rezult", "Пользователь уже в бане");
    	}
        return "rezult";
    }
}
