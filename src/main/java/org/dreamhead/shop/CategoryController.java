package org.dreamhead.shop;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.Category;
import org.dreamhead.shop.entity.Shipment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Transactional
public class CategoryController {
	
	private Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "category/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String home(Model model,
			@PathVariable(value = "id") int id) {
		Category category = new BaseRequest().getCategory(id);
		model.addAttribute("categoryName", category.getNameCategory() );
		List<Shipment> shipments = category.getShipments();
		for (Shipment shipment : shipments) {
			logger.info(shipment);
		}
		model.addAttribute("shipments", shipments );
		return "shipments";
	}
	

}
