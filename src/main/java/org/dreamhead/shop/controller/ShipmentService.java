package org.dreamhead.shop.controller;


import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ShipmentService {

	@Autowired
	BaseRequest baseRequest;
	
	public boolean canEditShipment(String email) {
		try {
			AppUser appUser = baseRequest.getAppUserFromEmail(email);
			if ( (appUser.getRole() == AppUser.ADMIN) || (appUser.getRole() == AppUser.MANAGER) ) {
				return true;
			} else {
				return false;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return false;
	}

}
