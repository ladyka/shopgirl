package org.dreamhead.shop;

import java.io.Serializable;

import org.dreamhead.shop.entity.Price;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MyPrice implements Serializable{
	private static final long serialVersionUID = 816341736889645L;
	
	private int id;
	private int priceInt;
	private String nameshop;
	private String description;

	public MyPrice(Price price) {
		id = price.getId();
		priceInt = price.getPrice();
		nameshop = price.getShop().getNameShop();
		description = price.getShop().getDescription();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPriceInt() {
		return priceInt;
	}

	public void setPriceInt(int priceInt) {
		this.priceInt = priceInt;
	}

	public String getNameshop() {
		return nameshop;
	}

	public void setNameshop(String nameshop) {
		this.nameshop = nameshop;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MyPrice [id=" + id + ", priceInt=" + priceInt + ", nameshop="
				+ nameshop + ", description=" + description + "]";
	}
	
	
}