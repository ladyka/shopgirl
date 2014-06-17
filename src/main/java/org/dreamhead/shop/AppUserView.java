package org.dreamhead.shop;

import java.io.Serializable;

import org.dreamhead.shop.entity.AppUser;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AppUserView  implements Serializable {
	private static final long serialVersionUID = 81673389645L;
	private int id;

	private String email;
	
	private String nick;

	private String phone;

	private String status;
	
	public AppUserView(AppUser appUser) {
		setId(appUser.getId());
		setEmail(appUser.getEmail());
		setNick(appUser.getNick());
		setPhone(appUser.getPhone());
		setStatus(appUser.getNameRole());
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	} 
	
	
}
