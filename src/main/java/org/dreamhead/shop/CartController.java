package org.dreamhead.shop;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dreamhead.shop.db.BQD;
import org.dreamhead.shop.db.ParamHQL;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.Oder;
import org.dreamhead.shop.entity.Price;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@Transactional
public class CartController {


	private Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "cart/add/{Priceid}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String home(
			Model model,
			@PathVariable(value = "Priceid") int Priceid,
			Principal principal
			
		) {
		Price price = BQD.M.getEntity(Price.class, Priceid);
		if (price.getCount() > 1) {
			price.setCount(price.getCount()-1);
			BQD.M.saveOrUpdate(price);
			
			Oder oder = new Oder();
			AppUser appUser = null;
			try {
				appUser = BQD.M.getListEntity(AppUser.class, 
						new ParamHQL("email", principal.getName())
						).get(0);
			} catch (NullPointerException ex) {
				logger.info("THIS is GUEST");
				return "login";
			}
			
			oder.setAppUser(appUser);
			oder.setDateodr(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			oder.setPrice(new Price(Priceid));
			oder.setStatus(0);
			BQD.M.saveOrUpdate(oder);
			
			model.addAttribute("rezult", "Добавлено");
		} else {
			model.addAttribute("rezult", "Товар закончился на складе, скоро будет.");
		}
		return "rezult";
	}
	
	@RequestMapping(value = "cart", produces = "text/plain;charset=UTF-8")
	public String cart(Model model,Principal principal) {
		List<MyODer> prices = new ArrayList<MyODer>();
		AppUser appUser = BQD.M.getListEntity(AppUser.class,
				new ParamHQL("email", principal.getName())).get(0);
		List<Oder> list = appUser.getOders();
		for (Oder oder : list) {
			MyODer myODer = new MyODer(
					oder.getPrice().getShipment().getId(),
					oder.getPrice().getShipment().getName(),
					oder.getStatus(),
					oder.getDateodr());
			prices.add(myODer);
			logger.info(myODer);
		}
		model.addAttribute("elements", prices);
		return "cart";
	}
}

