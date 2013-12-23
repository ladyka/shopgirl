package org.dreamhead.shop;

import java.io.Serializable;
import java.sql.Timestamp;

public class MyODer implements Serializable {
	private static final long serialVersionUID = 816736889645L; 

	private int id;
	private String name;
	private String status;
	private Timestamp timestamp;
	private int oderid;
	
	public MyODer(int id,String name, int status,Timestamp timestamp,int oder) {
		setId(id);
		setName(name);
		switch (status) {
		case 1:
			setName("Продано");
			break;

		default:
			setName("На складе");
			break;
		}
		setTimestamp(timestamp);
		setOderid(oder);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "MyODer [id=" + id + ", "
				+ (name != null ? "name=" + name + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (timestamp != null ? "timestamp=" + timestamp : "") + "]";
	}

	public int getOderid() {
		return oderid;
	}

	public void setOderid(int oderid) {
		this.oderid = oderid;
	}
	
	

}