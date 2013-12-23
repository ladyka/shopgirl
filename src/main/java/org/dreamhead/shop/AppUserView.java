package org.dreamhead.shop;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.Oder;
import org.dreamhead.shop.entity.Repotr;
import org.dreamhead.shop.entity.Shop;
import org.dreamhead.shop.entity.SystemRole;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AppUserView  implements Serializable {
	
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
		try {
			for (SystemRole systemRole : appUser.getSystemRoles()) {
				if (systemRole.getId() == 1) {
					setStatus("Пользователь");
					break;
				}
			}
			if (status == null) {
				setStatus("Забанен");
			}
		} catch (Exception ex) {
			setStatus("Забанен");
		}
	}

	private static final long serialVersionUID = 81673389645L;

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
