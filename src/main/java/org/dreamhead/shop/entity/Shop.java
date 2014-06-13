package org.dreamhead.shop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Shop database table.
 * 
 */
@Entity
@NamedQuery(name="Shop.findAll", query="SELECT s FROM Shop s")
public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String description;

	private String nameShop;

	//bi-directional many-to-many association to AppUser
	@ManyToMany
	@JoinTable(
		name="AppUser_has_Shop"
		, joinColumns={
			@JoinColumn(name="Shop_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="AppUser_id")
			}
		)
	private List<AppUser> appUsers;

	//bi-directional many-to-one association to Price
	@OneToMany(mappedBy="shop")
	private List<Price> prices;

	public Shop() {
	}

	public int getId() {
		return this.id;
	}

	public Shop setId(int id) {
		this.id = id;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNameShop() {
		return this.nameShop;
	}

	public void setNameShop(String nameShop) {
		this.nameShop = nameShop;
	}

	public List<AppUser> getAppUsers() {
		return this.appUsers;
	}

	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	public List<Price> getPrices() {
		return this.prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public Price addPrice(Price price) {
		getPrices().add(price);
		price.setShop(this);

		return price;
	}

	public Price removePrice(Price price) {
		getPrices().remove(price);
		price.setShop(null);

		return price;
	}

}