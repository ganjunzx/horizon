package com.kechuang.wifidog.horizon.model;

import java.sql.Time;
import java.util.Date;


public class BusinessNode extends BaseModel {
	private static final long serialVersionUID = 7575153899753577945L;

	private long id;
	private String ndName;
	private byte nodeStatus;// 0:normal 1:limit
	private Date createTime;
	private String aliasName;
	private String mcode;
	
	private byte industry;
	private long turnOffTime;
	private long turnOffFreeTime;
	private byte multiTerminalLogin;
	private byte limitSpeed;
	private long totalLimitIncoming;
	private long totalLimitOutgoing;
	private long eachLimitIncoming;
	private long eachLimitOutgoing;
	private Time startTime;
	private Time endTime;
	private String portalUrl;
	private Date updateTime;
	private byte loginType;// 0:直接访问 1：用户密码登录 2：手机验证登录
	private long remainSms;
	
	private long smsCodeValidTime;
	private byte smsCodeValidTimeType;
	private byte smsType;
	private int smsCodeLength;
	private int smsCodeDayNum;
	private long smsCodeObtainInterval;
	private long smsContentID;
	private NodeLever nodeLever;

	public long getSmsCodeValidTime() {
		return smsCodeValidTime;
	}

	public void setSmsCodeValidTime(long smsCodeValidTime) {
		this.smsCodeValidTime = smsCodeValidTime;
	}

	public byte getSmsCodeValidTimeType() {
		return smsCodeValidTimeType;
	}

	public void setSmsCodeValidTimeType(byte smsCodeValidTimeType) {
		this.smsCodeValidTimeType = smsCodeValidTimeType;
	}

	public byte getSmsType() {
		return smsType;
	}

	public void setSmsType(byte smsType) {
		this.smsType = smsType;
	}

	public int getSmsCodeLength() {
		return smsCodeLength;
	}

	public void setSmsCodeLength(int smsCodeLength) {
		this.smsCodeLength = smsCodeLength;
	}

	public int getSmsCodeDayNum() {
		return smsCodeDayNum;
	}

	public void setSmsCodeDayNum(int smsCodeDayNum) {
		this.smsCodeDayNum = smsCodeDayNum;
	}

	public long getSmsCodeObtainInterval() {
		return smsCodeObtainInterval;
	}

	public void setSmsCodeObtainInterval(long smsCodeObtainInterval) {
		this.smsCodeObtainInterval = smsCodeObtainInterval;
	}

	public long getSmsContentID() {
		return smsContentID;
	}

	public void setSmsContentID(long smsContentID) {
		this.smsContentID = smsContentID;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public byte getIndustry() {
		return industry;
	}

	public void setIndustry(byte industry) {
		this.industry = industry;
	}

	public long getTurnOffTime() {
		return turnOffTime;
	}

	public void setTurnOffTime(long turnOffTime) {
		this.turnOffTime = turnOffTime;
	}

	public long getTurnOffFreeTime() {
		return turnOffFreeTime;
	}

	public void setTurnOffFreeTime(long turnOffFreeTime) {
		this.turnOffFreeTime = turnOffFreeTime;
	}

	public byte getMultiTerminalLogin() {
		return multiTerminalLogin;
	}

	public void setMultiTerminalLogin(byte multiTerminalLogin) {
		this.multiTerminalLogin = multiTerminalLogin;
	}

	public byte getLimitSpeed() {
		return limitSpeed;
	}

	public void setLimitSpeed(byte limitSpeed) {
		this.limitSpeed = limitSpeed;
	}

	public long getTotalLimitIncoming() {
		return totalLimitIncoming;
	}

	public void setTotalLimitIncoming(long totalLimitIncoming) {
		this.totalLimitIncoming = totalLimitIncoming;
	}

	public long getTotalLimitOutgoing() {
		return totalLimitOutgoing;
	}

	public void setTotalLimitOutgoing(long totalLimitOutgoing) {
		this.totalLimitOutgoing = totalLimitOutgoing;
	}

	public long getEachLimitIncoming() {
		return eachLimitIncoming;
	}

	public void setEachLimitIncoming(long eachLimitIncoming) {
		this.eachLimitIncoming = eachLimitIncoming;
	}

	public long getEachLimitOutgoing() {
		return eachLimitOutgoing;
	}

	public void setEachLimitOutgoing(long eachLimitOutgoing) {
		this.eachLimitOutgoing = eachLimitOutgoing;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public String getPortalUrl() {
		return portalUrl;
	}

	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public byte getLoginType() {
		return loginType;
	}

	public void setLoginType(byte loginType) {
		this.loginType = loginType;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNdName() {
		return ndName;
	}

	public void setNdName(String ndName) {
		this.ndName = ndName;
	}

	public byte getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(byte nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public long getRemainSms() {
		return remainSms;
	}

	public void setRemainSms(long remainSms) {
		this.remainSms = remainSms;
	}

	public NodeLever getNodeLever() {
		return nodeLever;
	}

	public void setNodeLever(NodeLever nodeLever) {
		this.nodeLever = nodeLever;
	}

}
