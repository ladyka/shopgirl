package org.dreamhead.shop;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.Shipment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
public class SearchController {

	private Log logger = LogFactory.getLog(getClass());
	
    @RequestMapping(value = "search")
    public String search() {
        return "search";
    }
    
    @RequestMapping(value = "searchrezult")
    public String searchrezult(Model model,String searchquery) {
    	StringBuilder builder = new StringBuilder();
    	List<Shipment> shipments = new BaseRequest().searchShipments(searchquery);
    	builder.append("<table>");
    	for (Shipment shipment : shipments) {
			builder.append(tabe(shipment));
			logger.info(shipment);
		}
    	builder.append("</table>");
    	model.addAttribute("rezult", builder.toString());
        return "rezult";
    }

	private StringBuilder tabe(Shipment shipment) {
		StringBuilder builder = new StringBuilder();
		builder.append("<tr>");
		builder.append(element(shipment.getName()));
		builder.append(element(shipment.getDescription()));
		builder.append(element("<a href=\"shipment/" + shipment.getId() + "\" > Go</a>"));
		builder.append("</tr>");
		return builder;
	}

	private StringBuilder element(Object name) {
		StringBuilder builder = new StringBuilder();
		builder.append("<td>");
		builder.append(String.valueOf(name));
		builder.append("</td>");
		return builder;
	}
}
