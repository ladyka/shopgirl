package org.dreamhead.shop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the Oder database table.
 * 
 */
@Entity
@NamedQuery(name="Oder.findAll", query="SELECT o FROM Oder o")
public class Oder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Timestamp dateodr;

	private int status;

	//bi-directional many-to-one association to AppUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="AppUser_id")
	private AppUser appUser;

	//bi-directional many-to-one association to Price
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Price_id")
	private Price price;

	public Oder() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDateodr() {
		return this.dateodr;
	}

	public void setDateodr(Timestamp dateodr) {
		this.dateodr = dateodr;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

}