package org.dreamhead.shop;


import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.Category;
import org.dreamhead.shop.entity.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.support.PrincipalMethodArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Transactional
public class CategoryController {
	
	@Autowired
	BaseRequest baseRequest;
	
	private Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "category/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String getCategory(Model model, Principal principal,
			@PathVariable(value = "id") int id) {
		
		try {
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			model.addAttribute("user", true);
			model.addAttribute("guest", false);
			model.addAttribute("appUserName", appUser.getNick());
		} catch (NullPointerException exception) {
			model.addAttribute("guest", true);
			model.addAttribute("user", false);
		}
		
		Category category = baseRequest.getEntity(Category.class, id);
		model.addAttribute("categoryName", category.getNameCategory() );
		model.addAttribute("description", category.getDescription() );
		List<Shipment> shipments = category.getShipments();
		for (Shipment shipment : shipments) {
			logger.info(shipment);
		}
		model.addAttribute("shipments", shipments );
		return "shipments";
	}
	
	@RequestMapping(value = "category/edit/{id}", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String editCategory(Model model, Principal principal,
			@PathVariable(value = "id") int id, String description, String nameCategory) {
		
		try {
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			model.addAttribute("user", true);
			model.addAttribute("guest", false);
			model.addAttribute("appUserName", appUser.getNick());
			
			if ( (appUser.getRole() == AppUser.ADMIN) || (appUser.getRole() == AppUser.MANAGER) ) {
				model.addAttribute("canEdit",true);
				Category category = null;
				if (id == 0) {
					category = new Category();
				} else {
					category = baseRequest.getEntity(Category.class, id);
				}
				category.setDescription(description);
				category.setNameCategory(nameCategory);
				baseRequest.saveOrUpdate(category);

				model.addAttribute("id", id);
				model.addAttribute("nameCategory", category.getNameCategory());
				model.addAttribute("description", category.getDescription());
			} else {
				model.addAttribute("canEdit",false);
			}
			
		} catch (NullPointerException exception) {
			model.addAttribute("guest", true);
			model.addAttribute("user", false);
		}
		
		
		return "category";
	}
	
	@RequestMapping(value = "category/edit/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String editPageCategory(Model model, Principal principal,
			@PathVariable(value = "id") int id, String description, String nameCategory) {
		
		try {
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			model.addAttribute("user", true);
			model.addAttribute("guest", false);
			model.addAttribute("appUserName", appUser.getNick());
			if ( (appUser.getRole() == AppUser.ADMIN) || (appUser.getRole() == AppUser.MANAGER) ) {
				model.addAttribute("canEdit",true); 
				Category category = null;
				if (id == 0) {
					category = new Category();
				} else {
					category = baseRequest.getEntity(Category.class, id);
				}
				model.addAttribute("id", id);
				model.addAttribute("nameCategory", category.getNameCategory());
				model.addAttribute("description", category.getDescription());
			}
			
		} catch (NullPointerException exception) {
			model.addAttribute("guest", true);
			model.addAttribute("user", false);
		}
		
		
		return "category";
	}
	
}
