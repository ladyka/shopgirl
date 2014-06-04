package org.dreamhead.shop.security;

import java.util.ArrayList;
import java.util.Collection;

import org.dreamhead.shop.db.BaseManager;
import org.dreamhead.shop.db.BaseRequest;
import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.SystemRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class UserDetails implements UserDetailsService {
	
	@Autowired
	BaseManager baseManager;
	
	@Autowired
	BaseRequest baseRequest;
	
	Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		AppUser qdocUser = baseRequest.getAppUserFromEmail(email);
        logger.info(email);

		if (qdocUser == null || qdocUser.getActive() != 2) throw new UsernameNotFoundException("Юзер не найден или доступ запрещён ему.");
		
		Collection<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
		
		for (SystemRole systemRole : qdocUser.getSystemRoles()) {
			logger.info("ROLE :  " + systemRole.getNameRole());
			authority.add(new SimpleGrantedAuthority(systemRole.getNameRole()));
		}

		User user = new User(qdocUser.getEmail(), qdocUser.getPassword(), true, true, true, true, authority);	
		return user;
	}

}
