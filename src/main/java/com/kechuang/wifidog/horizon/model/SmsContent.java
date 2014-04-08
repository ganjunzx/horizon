package com.kechuang.wifidog.horizon.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SmsContent extends BaseModel {
	private static final long serialVersionUID = 1L;
	private long id;
	private long userID;
	private String smsContent;
	private byte status;
	private Date createTime;
	private Date updateTime;

	
	public SmsContent () {
		this.userID = -1;
		this.status = -1;
		this.updateTime = null;
		this.createTime = null;
		this.smsContent = null;
	}
	
	public SmsContent (long userID, String smsContent, byte status) {
		this.userID = userID;
		this.smsContent = smsContent;
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

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
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

}
