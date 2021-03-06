package org.dreamhead.shop.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dreamhead.shop.MyPrice;
import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.Category;
import org.dreamhead.shop.entity.Price;
import org.dreamhead.shop.entity.Shipment;
import org.dreamhead.shop.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.vurtatoo.vk.VkAPIDEp;

@Transactional
@Controller
public class ShipmentController {

	@Autowired
	BaseRequest baseRequest;
	
	@Autowired
	ShipmentService shipmentService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "shipment/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String home(
			Model model,
			@PathVariable(value = "id") int id
		) {
		Shipment shipment = baseRequest.getEntity(Shipment.class, id);
		model.addAttribute("shipment", shipment);
		
		model.addAttribute("shipmentName", shipment.getName());
		model.addAttribute("shipmentTitle", shipment.getName() + " " + shipment.getCategory().getNameCategory());
		
		List<Price> prices = shipment.getPrices();
		List<MyPrice> myPrices =  new ArrayList<MyPrice>();
		for (Price price : prices) {
			MyPrice myPrice = new MyPrice(price); 
			logger.info(myPrice);
			myPrices.add(myPrice);
		}
		model.addAttribute("prices", myPrices);
		return "shipment";
	}
	
	@RequestMapping(value = "shipmentaddpage", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String shipmentaddpage(
			Model model,
			Principal principal
		) {
		try {
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			model.addAttribute("user", true);
			model.addAttribute("appUserName", appUser.getNick());
			model.addAttribute("allowToAddNewShipment", shipmentService.canEditShipment(principal.getName()));

			List<Category> categories = baseRequest.getCategories();
			model.addAttribute("categories", categories );
			model.addAttribute("shipmentId",0);
			
		} catch (NullPointerException exception) {
			model.addAttribute("guest", true);
		}
		return "addnewShipment";
	}
	
	@RequestMapping(value = "shipmentEdit", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String shipmentadd(
			Model model,
			int id,

			String description,

			String imageURI,

			String name,
			int catalogid,
			int price,
			int count
		) {
		Shipment shipment = null;
		if (id == 0) {
			shipment = new Shipment(); 
		} else {
			shipment = baseRequest.getEntity(Shipment.class, id);
		}
		shipment.setDescription(description);
		shipment.setCategory(new Category(catalogid));
		shipment.setImageURI(imageURI);
		shipment.setName(name);
		
		baseRequest.saveOrUpdate(shipment);
		
		Price myPrice = new Price();
		
		myPrice.setCount(count);
		myPrice.setPrice(price);
		myPrice.setShipment(shipment);
		myPrice.setShop(new Shop().setId(1));
		
		baseRequest.saveOrUpdate(myPrice);
		
		logger.info("ADD : \n" + shipment.toString() + "\n" + myPrice.toString());
		
		model.addAttribute("rezult", "Выполнено успешно");
		return "rezult";
	}
	
	@RequestMapping(value = "shipment/vk/post/{id}")
	public String postShipmentWallVk(Principal principal,Model model,@PathVariable(value = "id") int id) {
		if (shipmentService.canEditShipment(principal.getName())) {
			Shipment shipment = baseRequest.getEntity(Shipment.class, id);
			shipment.getImageURI();
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			String url = VkAPIDEp.wallPost("-73028368", 
					shipment.getName() + " " + 
					shipment.getDescription() +
					" PRICE " + 
					shipment.getPrices().get(0).getPrice()
					, appUser.getVktocken(), appUser.getVkid());
			model.addAttribute("rezult", "Готово, <a href=\" " + url  + "\" >смотри</a>" );
		} {
			model.addAttribute("rezult", "Нет прав для того, что б запостить.");
		}
		return "rezult";
	}
}
