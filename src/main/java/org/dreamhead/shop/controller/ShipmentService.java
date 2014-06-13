package org.dreamhead.shop.controller;

import java.util.List;

import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.SystemRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ShipmentService {

	@Autowired
	BaseRequest baseRequest;
	
	public boolean canEditShipment(String email) {
		AppUser appUser = baseRequest.getAppUserFromEmail(email);
		List<SystemRole> systemRoles = appUser.getSystemRoles();
		for (SystemRole systemRole : systemRoles) {
			if ( 
					(systemRole.getId() == SystemRole.MANAGER)  
					|| (systemRole.getId() == SystemRole.ADMIN)
				) {
				return true;
			}
		}
		return false;
	}

}
