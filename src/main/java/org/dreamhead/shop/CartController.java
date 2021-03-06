package org.dreamhead.shop;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dreamhead.shop.db.BaseManager;
import org.dreamhead.shop.db.BaseRequest;
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

	@Autowired
    BaseManager baseManager;
	
	@Autowired
	BaseRequest baseRequest;

	private Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "cart/add/{Priceid}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String home(
			Model model,
			@PathVariable(value = "Priceid") int Priceid,
			Principal principal
			
		) {
		Price price = baseManager.getEntity(Price.class, Priceid);
		if (price.getCount() > 1) {
			price.setCount(price.getCount()-1);
			baseManager.saveOrUpdate(price);
			
			Oder oder = new Oder();
			AppUser appUser = null;
			try {
				appUser = baseRequest.getAppUserFromEmail(principal.getName());
			} catch (NullPointerException ex) {
				logger.info("THIS is GUEST");
				return "login";
			}
			
			oder.setAppUser(appUser);
			oder.setDateodr(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			oder.setPrice(new Price(Priceid));
			oder.setStatus(0);
			baseManager.saveOrUpdate(oder);
			
			model.addAttribute("rezult", "Добавлено");
		} else {
			model.addAttribute("rezult", "Товар закончился на складе, скоро будет.");
		}
		return "rezult";
	}
	
	@RequestMapping(value = "cart", produces = "text/plain;charset=UTF-8")
	public String cart(Model model,Principal principal) {
		List<MyODer> prices = new ArrayList<MyODer>();
		AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName()); 
		List<Oder> list = appUser.getOders();
		for (Oder oder : list) {
			MyODer myODer = new MyODer(
					oder.getPrice().getShipment().getId(),
					oder.getPrice().getShipment().getName(),
					oder.getStatus(),
					oder.getDateodr(),
					oder.getId());
			prices.add(myODer);
			logger.info(myODer);
		}
		model.addAttribute("elements", prices);
		return "cart";
	}
	

	@RequestMapping(value = "cart/del/{OderId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String delCart(
			Model model,
			@PathVariable(value = "OderId") int OderId,
			Principal principal		
		) {
		try {
			Oder oder = baseManager.getEntity(Oder.class, OderId);
			AppUser appUser = baseRequest.getAppUserFromEmail(principal.getName());
			if (oder.getAppUser().getId() == appUser.getId()) {
				baseManager.delete(oder);
				model.addAttribute("rezult", "Удалено");
			} else {
				model.addAttribute("rezult", "Не делайте глупостей, это не ваша покупка :) ");
			}
			
		} catch (Exception ex) {
			model.addAttribute("rezult", "Произошла ошибка, уже в курсе. Исправляем.");
		}
		return "rezult";
	}
	
}

