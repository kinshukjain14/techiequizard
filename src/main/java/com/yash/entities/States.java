package com.yash.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "states")
public class States {
	@Id
	@Column(name = "id")
	private int stateId;
	@Column(name = "name")
	private String stateName;
	@Column(name = "country_id")
	private int countryId;
	
	public States() {
	}
	
	public States(int stateId, String stateName, int countryId) {
		super();
		this.stateId = stateId;
		this.stateName = stateName;
		this.countryId = countryId;
	}
	public int getStateId() {
		return stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public int getCountryId() {
		return countryId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countryId;
		result = prime * result + stateId;
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
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
		States other = (States) obj;
		if (countryId != other.countryId)
			return false;
		if (stateId != other.stateId)
			return false;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "States [stateId=" + stateId + ", stateName=" + stateName + ", countryId=" + countryId + "]";
	}
		
	
}
