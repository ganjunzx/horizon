package com.kechuang.wifidog.horizon.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SmsTrade extends BaseModel {
	private static final long serialVersionUID = 1L;
	private long id;
	private long userID;
	private long nodeID;
	private long applySms;
	private double totalPrice;
	private byte status;
	private Date createTime;
	private Date updateTime;
	
	private String ndName;
	
	private String userName;

	public SmsTrade () {
		this.userID = -1;
		this.nodeID = -1;
		this.applySms = -1;
		this.totalPrice = -1;
		this.status = -1;
		this.createTime = null;
		this.updateTime = null;
	}
	
	public SmsTrade (long userID, long nodeID, long applySms, double totalPrice, byte status) {
		this.userID = userID;
		this.nodeID = nodeID;
		this.applySms = applySms;
		this.totalPrice = totalPrice;
		this.status = status;
		this.createTime = Calendar.getInstance(TimeZone.getDefault())
				.getTime();
		this.updateTime = createTime;
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

	public long getApplySms() {
		return applySms;
	}

	public void setApplySms(long applySms) {
		this.applySms = applySms;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
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

	public long getNodeID() {
		return nodeID;
	}

	public void setNodeID(long nodeID) {
		this.nodeID = nodeID;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getNdName() {
		return ndName;
	}

	public void setNdName(String ndName) {
		this.ndName = ndName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}
