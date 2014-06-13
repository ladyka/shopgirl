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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		model.addAttribute("allowToAddNewShipment", shipmentService.canEditShipment(principal.getName()));
		try {
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			model.addAttribute("user", true);
			model.addAttribute("appUserName", appUser.getNick());
		} catch (NullPointerException exception) {
			model.addAttribute("guest", true);
		}
		return "addnewShipment";
	}
	
	@RequestMapping(value = "shipmentadd", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String shipmentadd(
			Model model,
			int id,

			String description,

			String imageURI,

			String name,
			int catalogid
		) {
		Shipment shipment = null;
		if (id == 0) {
			shipment = new Shipment(); 
		} else {
			shipment = baseRequest.getEntity(Shipment.class, id);
		}
		shipment.setCategory(new Category(catalogid));
		shipment.setImageURI(imageURI);
		shipment.setName(name);
		
		baseRequest.saveOrUpdate(shipment);
		model.addAttribute("rezult", "Выполнено успешно");
		return "rezult";
	}
}
