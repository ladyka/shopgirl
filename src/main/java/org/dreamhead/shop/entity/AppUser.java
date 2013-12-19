package org.dreamhead.shop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the AppUser database table.
 * 
 */
@Entity
@NamedQuery(name="AppUser.findAll", query="SELECT a FROM AppUser a")
public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int active;

	private String email;

	@Column(name="Nick")
	private String nick;

	private String password;

	private String phone;

	//bi-directional many-to-many association to Shop
	@ManyToMany(mappedBy="appUsers")
	private List<Shop> shops;

	//bi-directional many-to-many association to SystemRole
	@ManyToMany(mappedBy="appUsers")
	private List<SystemRole> systemRoles;

	//bi-directional many-to-one association to Oder
	@OneToMany(mappedBy="appUser")
	private List<Oder> oders;

	//bi-directional many-to-one association to Repotr
	@OneToMany(mappedBy="appUser")
	private List<Repotr> repotrs;

	public AppUser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNick() {
		return this.nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Shop> getShops() {
		return this.shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

	public List<SystemRole> getSystemRoles() {
		return this.systemRoles;
	}

	public void setSystemRoles(List<SystemRole> systemRoles) {
		this.systemRoles = systemRoles;
	}

	public List<Oder> getOders() {
		return this.oders;
	}

	public void setOders(List<Oder> oders) {
		this.oders = oders;
	}

	public Oder addOder(Oder oder) {
		getOders().add(oder);
		oder.setAppUser(this);

		return oder;
	}

	public Oder removeOder(Oder oder) {
		getOders().remove(oder);
		oder.setAppUser(null);

		return oder;
	}

	public List<Repotr> getRepotrs() {
		return this.repotrs;
	}

	public void setRepotrs(List<Repotr> repotrs) {
		this.repotrs = repotrs;
	}

	public Repotr addRepotr(Repotr repotr) {
		getRepotrs().add(repotr);
		repotr.setAppUser(this);

		return repotr;
	}

	public Repotr removeRepotr(Repotr repotr) {
		getRepotrs().remove(repotr);
		repotr.setAppUser(null);

		return repotr;
	}

}