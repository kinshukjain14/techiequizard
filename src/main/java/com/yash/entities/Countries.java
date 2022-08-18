package com.yash.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class Countries {
	@Id
	@Column(name = "id")
	private int countryId;
	@Column(name = "shortname")
	private String shortName;
	@Column(name = "name")
	private String countryName;
	@Column(name = "phonecode")
	private int phoneCode;
	
	public Countries() {
	}
	
	public Countries(int countryId, String shortName, String countryName, int phoneCode) {
		super();
		this.countryId = countryId;
		this.shortName = shortName;
		this.countryName = countryName;
		this.phoneCode = phoneCode;
	}
	public int getCountryId() {
		return countryId;
	}
	public String getShortName() {
		return shortName;
	}
	public String getCountryName() {
		return countryName;
	}
	public int getPhoneCode() {
		return phoneCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + countryId;
		result = prime * result + phoneCode;
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Countries other = (Countries) obj;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (countryId != other.countryId)
			return false;
		if (phoneCode != other.phoneCode)
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Countries [country_id=" + countryId + ", shortName=" + shortName + ", countryName=" + countryName
				+ ", phoneCode=" + phoneCode + "]";
	}
	
}
