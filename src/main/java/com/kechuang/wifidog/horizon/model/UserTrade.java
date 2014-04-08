package com.kechuang.wifidog.horizon.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UserTrade extends BaseModel {
	private static final long serialVersionUID = 1L;
	private long id;
	private long userID;
	private double totalPrice;
	private byte status;
	private byte tradeType;
	private Date createTime;
	private Date updateTime;
	private String description;
	private byte tradeWay;
	
	private String userName;

	public UserTrade () {
		this.userID = -1;
		this.totalPrice = -1;
		this.status = -1;
		this.tradeType = -1;
		this.createTime = null;
		this.updateTime = null;
		this.description = null;
		this.tradeWay = -1;
	}
	
	public UserTrade (long userID, double totalPrice, byte status, byte tradeType, String description, byte tradeWay) {
		this.userID = userID;
		this.totalPrice = totalPrice;
		this.status = status;
		this.tradeType = tradeType;
		this.createTime = Calendar.getInstance(TimeZone.getDefault())
				.getTime();
		this.updateTime = createTime;
		this.description = description;
		this.tradeWay = tradeWay;
	}
	
	public byte getTradeType() {
		return tradeType;
	}

	public void setTradeType(byte tradeType) {
		this.tradeType = tradeType;
	}

	public byte getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(byte tradeWay) {
		this.tradeWay = tradeWay;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}
