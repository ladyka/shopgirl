package org.dreamhead.shop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SystemRole database table.
 * 
 */
@Entity
@NamedQuery(name="SystemRole.findAll", query="SELECT s FROM SystemRole s")
public class SystemRole implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int ADMIN = 1;
	public static final int MANAGER = 2;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nameRole;

	//bi-directional many-to-many association to AppUser
	@ManyToMany
	@JoinTable(
		name="AppUser_has_SystemRole"
		, joinColumns={
			@JoinColumn(name="SystemRole_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="AppUser_id")
			}
		)
	private List<AppUser> appUsers;

	public SystemRole() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameRole() {
		return this.nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public List<AppUser> getAppUsers() {
		return this.appUsers;
	}

	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

}