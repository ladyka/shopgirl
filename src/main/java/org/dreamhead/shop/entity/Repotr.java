package org.dreamhead.shop.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Repotr database table.
 * 
 */
@Entity
@NamedQuery(name="Repotr.findAll", query="SELECT r FROM Repotr r")
public class Repotr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String reportText;

	private int reportType;

	//bi-directional many-to-one association to AppUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="AppUser_id")
	private AppUser appUser;

	public Repotr() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReportText() {
		return this.reportText;
	}

	public void setReportText(String reportText) {
		this.reportText = reportText;
	}

	public int getReportType() {
		return this.reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

}