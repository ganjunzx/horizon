package com.kechuang.wifidog.horizon.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class User {
	public static enum USER_STATUS {
		NORMAL, LIMIT
	};

	public static enum USER_TYPE {
		SUPERUSER, VENDERUSER, BUSINESSUSER
	}
	
	public static enum VIP_LEVEL {
		VIP1, VIP2, VIP3
	}
	
	public static int[] LIMIT_ONLINE_MAX_NUM = {20, 40, 60};

	private long id;
	private String userName;
	private String password;
	private String email;
	private Date registerdTime;
	private Date updatedTime;
	private byte userStatus;
	private byte userType;
	private String country;
	private String province;
	private String city;
	private String streeName;
	private String companyName;
	private String description;
	private Date lastLogin;
	private byte vipLevel;
	private String cellPhoneNum;
	private String qqNum;
	private double acount;

	public User() {
		this.userName = null;
		this.password = null;
		this.email = null;
		this.registerdTime = null;
		this.updatedTime = null;
		this.userStatus = -1;
		this.userType = -1;
		this.country = null;
		this.province = null;
		this.city = null;
		this.streeName = null;
		this.companyName = null;
		this.description = null;
		this.lastLogin = null;
		this.updatedTime = null;
		this.vipLevel = -1;
		this.cellPhoneNum = null;
		this.qqNum = null;
		this.acount = -1;
	}

	public User(String userName, String password, String email,
			byte userStatus, byte userType, String country,
			String province, String city, String streeName, String companyName,
			String description, Date lastLogin, byte vipLevel, String cellPhoneNum, String qqNum, double acount) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.registerdTime = Calendar.getInstance(TimeZone.getDefault())
				.getTime();
		this.updatedTime = registerdTime;
		this.userStatus = userStatus;
		this.userType = userType;
		this.country = country;
		this.province = province;
		this.city = city;
		this.streeName = streeName;
		this.companyName = companyName;
		this.description = description;
		this.lastLogin = lastLogin;
		this.vipLevel = vipLevel;
		this.cellPhoneNum = cellPhoneNum;
		this.qqNum = qqNum;
		this.acount = acount;
	}

	public String getCellPhoneNum() {
		return cellPhoneNum;
	}

	public void setCellPhoneNum(String cellPhoneNum) {
		this.cellPhoneNum = cellPhoneNum;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisterdTime() {
		return registerdTime;
	}

	public void setRegisterdTime(Date registerdTime) {
		this.registerdTime = registerdTime;
	}

	public byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(byte userStatus) {
		this.userStatus = userStatus;
	}

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreeName() {
		return streeName;
	}

	public void setStreeName(String streeName) {
		this.streeName = streeName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String toString() {
		return "[ id : " + this.getId() + " , userName : " + this.getUserName()
				+ " , password : " + this.getPassword() + " , email : "
				+ this.getEmail() + " , registerdTime : "
				+ this.getRegisterdTime() + " , updatedTime : "
				+ this.getUpdatedTime() + " , userStatus : "
				+ this.getUserStatus() + " , userType : " + this.getUserType()
				+ " , country : " + this.getCountry() + " , province : "
				+ this.getProvince() + " , city : " + this.getCity()
				+ " , streeName : " + this.getStreeName() + " , companyName : "
				+ this.getCompanyName() + " , description : "
				+ this.getDescription() + " , lastLogin : "
				+ this.getLastLogin() + "]";
	}

	public byte getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(byte vipLevel) {
		this.vipLevel = vipLevel;
	}

	public double getAcount() {
		return acount;
	}

	public void setAcount(double acount) {
		this.acount = acount;
	}

}
