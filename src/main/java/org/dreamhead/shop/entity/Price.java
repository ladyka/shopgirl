package org.dreamhead.shop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Price database table.
 * 
 */
@Entity
@NamedQuery(name="Price.findAll", query="SELECT p FROM Price p")
public class Price implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int price;
	private int count;

	//bi-directional many-to-one association to Oder
	@OneToMany(mappedBy="price")
	private List<Oder> oders;

	//bi-directional many-to-one association to Shipment
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Shipment_id")
	private Shipment shipment;

	//bi-directional many-to-one association to Shop
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Shop_id")
	private Shop shop;

	public Price() {
	}

	public Price(int id2) {
		setId(id2);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Oder> getOders() {
		return this.oders;
	}

	public void setOders(List<Oder> oders) {
		this.oders = oders;
	}

	public Oder addOder(Oder oder) {
		getOders().add(oder);
		oder.setPrice(this);

		return oder;
	}

	public Oder removeOder(Oder oder) {
		getOders().remove(oder);
		oder.setPrice(null);

		return oder;
	}

	public Shipment getShipment() {
		return this.shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public String toString() {
		return "Price [id=" + id + ", price=" + price + "]";
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	

}