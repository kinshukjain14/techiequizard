package com.yash.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocationSQLQueries {
	@Value(value = "${selectcountriesquery}")
	private String selectcountresQuery;;
	@Value(value = "${selectstatessquery}")
	private String selectstatesQuery;
	@Value(value = "${selectcitiesquery}")
	private String selectcitiesQuiery;
	public String getSelectcountresQuery() {
		return selectcountresQuery;
	}
	public void setSelectcountresQuery(String selectcountresQuery) {
		this.selectcountresQuery = selectcountresQuery;
	}
	public String getSelectstatesQuery() {
		return selectstatesQuery;
	}
	public void setSelectstatesQuery(String selectstatesQuery) {
		this.selectstatesQuery = selectstatesQuery;
	}
	public String getSelectcitiesQuiery() {
		return selectcitiesQuiery;
	}
	public void setSelectcitiesQuiery(String selectcitiesQuiery) {
		this.selectcitiesQuiery = selectcitiesQuiery;
	}
	@Override
	public String toString() {
		return "LocationDB [selectcountresQuery=" + selectcountresQuery + ", selectstatesQuery=" + selectstatesQuery
				+ ", selectcitiesQuiery=" + selectcitiesQuiery + "]";
	}
	
	
}
