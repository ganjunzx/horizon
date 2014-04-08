package com.kechuang.wifidog.horizon.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SmsSecurityCode extends BaseModel {
	private static final long serialVersionUID = 1L;
	private long id;
	private long userID;
	private long nodeID;
	private String cellPhoneNum;
	private String securityCode;
	private Date createTime;
	private byte status;
	private byte mobileType;

	private String ndName;
	public SmsSecurityCode () {
		this.userID = -1;
		this.nodeID = -1;
		this.cellPhoneNum = null;
		this.securityCode = null;
		this.createTime = null;
		this.status = -1;
		this.mobileType = -1;
	}
	
	public SmsSecurityCode (long userID, long nodeID, String cellPhoneNum, String securityCode, byte status, byte mobileType) {
		this.userID = userID;
		this.nodeID = nodeID;
		this.cellPhoneNum = cellPhoneNum;
		this.securityCode = securityCode;
		this.status = status;
		this.createTime = Calendar.getInstance(TimeZone.getDefault())
				.getTime();
		this.mobileType = mobileType;
	}

	public long getNodeID() {
		return nodeID;
	}

	public void setNodeID(long nodeID) {
		this.nodeID = nodeID;
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

	public String getCellPhoneNum() {
		return cellPhoneNum;
	}

	public void setCellPhoneNum(String cellPhoneNum) {
		this.cellPhoneNum = cellPhoneNum;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getMobileType() {
		return mobileType;
	}

	public void setMobileType(byte mobileType) {
		this.mobileType = mobileType;
	}

	public String getNdName() {
		return ndName;
	}

	public void setNdName(String ndName) {
		this.ndName = ndName;
	}
	
}
