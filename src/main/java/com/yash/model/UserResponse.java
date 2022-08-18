package com.yash.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.format.annotation.DateTimeFormat;

import com.yash.helper.LocalDateAdapter;
import com.yash.helper.LocalDateTimeAdapter;

@XmlRootElement
public class UserResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Long contactNo;
	private LocalDateTime registeredOn;
	private LocalDateTime lastLogin;
	private String gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	private String address;
	private String country;
	private String state;
	private String city;
	private UserDataErrors errors;
	
	
	public UserDataErrors getErrors() {
		return errors;
	}
	public void setErrors(UserDataErrors errors) {
		this.errors = errors;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getContactNo() {
		return contactNo;
	}
	public void setContactNo(Long long1) {
		this.contactNo = long1;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
	public LocalDateTime getRegisteredOn() {
		return registeredOn;
	}
	public void setRegisteredOn(LocalDateTime registeredOn) {
		this.registeredOn = registeredOn;
	}
	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
//	@Override
//	public String toString() {
//		return "UserModel [userId=" + userId + ", userName=" + userName + ", password=" + password + ", firstName="
//				+ firstName + ", lastName=" + lastName + ", email=" + email + ", contactNo=" + contactNo
//				+ ", registeredOn=" + registeredOn + ", lastLogin=" + lastLogin + ", gender=" + gender
//				+ ", dateOfBirth=" + dateOfBirth + ", address=" + address + ", country=" + country + ", state=" + state
//				+ ", city=" + city + "]";
//	}
	
}
