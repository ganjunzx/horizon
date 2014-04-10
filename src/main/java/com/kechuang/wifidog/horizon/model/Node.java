package com.kechuang.wifidog.horizon.model;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.kechuang.wifidog.horizon.utils.HorizonConfig;

public class Node extends BaseModel {
	private static final long serialVersionUID = 7575153899753577945L;

	public static enum INDUSTRY {
		COFFEE_SHOP, WESTERN_RESTAURANT, CHINESE_RESTAURANT, TEA_SHOP, CHAMBER, CONVENIENCE_STORE, DRINK_SHOP, HOUSINGESTATE, LET, SCHOOL, FACTORY, COMPANY, OTHER
	}

	public static enum MULTITERMINALLOGIN {
		NO, YES
	}

	public static enum LIMITSPEED {
		YES, NO
	}

	public static enum NODESTATUS {
		NORMAL, LIMIT
	}

	private long id;
	private String ndName;
	private String aliasName;
	private String mCode;
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
	private Date createTime;
	private byte loginType;// 0:直接访问 1：用户密码登录 2：手机验证登录
	private byte nodeStatus;// 0:normal 1:limit
	private long venderID;
	private long businessID;
	private byte running;
	private long remainSms;
	private long smsCodeValidTime;
	private byte smsCodeValidTimeType;
	private byte smsType;
	private int smsCodeLength;
	private int smsCodeDayNum;
	private long smsCodeObtainInterval;
	private long smsContentID;
	

	private String venderName;
	private String businessName;

	public Node() {
		this.ndName = null;
		this.aliasName = null;
		this.mCode = null;
		this.industry = -1;
		this.turnOffTime = -1;
		this.turnOffFreeTime = -1;
		this.multiTerminalLogin = -1;
		this.limitSpeed = -1;
		this.totalLimitIncoming = -1;
		this.totalLimitOutgoing = -1;
		this.eachLimitIncoming = -1;
		this.eachLimitOutgoing = -1;
		this.startTime = null;
		this.endTime = null;
		this.portalUrl = null;
		this.updateTime = null;
		this.createTime = null;
		this.loginType = -1;
		this.nodeStatus = -1;
		this.venderID = -1;
		this.businessID = -1;
		this.running = -1;
		this.remainSms = -1;
		this.smsCodeValidTime = -1;
		this.smsCodeLength = -1;
		this.smsType = -1;
		this.smsCodeDayNum = -1;
		this.smsCodeObtainInterval = -1;
		this.smsContentID = -1;
		this.smsCodeValidTimeType = -1;
	}

	public Node(String ndName, String mcode, long venderID) {
		this.ndName = ndName;
		this.aliasName = null;
		this.mCode = mcode;
		this.industry = -1;
		this.turnOffTime = -1;
		this.turnOffFreeTime = -1;
		this.multiTerminalLogin = -1;
		this.limitSpeed = -1;
		this.totalLimitIncoming = -1;
		this.totalLimitOutgoing = -1;
		this.eachLimitIncoming = -1;
		this.eachLimitOutgoing = -1;
		this.startTime = null;
		this.endTime = null;
		this.portalUrl = null;
		this.createTime = Calendar.getInstance(TimeZone.getDefault()).getTime();
		this.updateTime = createTime;
		this.loginType = -1;
		this.nodeStatus = -1;
		this.venderID = venderID;
		this.businessID = -1;
		this.running = (byte)HorizonConfig.NODE_RUNNING.STOP.getIndex();
		this.remainSms = -1;
		this.smsCodeValidTime = -1;
		this.smsCodeLength = -1;
		this.smsType = -1;
		this.smsCodeDayNum = -1;
		this.smsCodeObtainInterval = -1;
		this.smsCodeValidTimeType = -1;
	}

	public Node(String ndName, String aliasName,
			String mCode, byte industry, long turnOffTime, long turnOffFreeTime,
			byte multiTerminalLogin, byte limitSpeed, long totalLimitIncoming,
			long totalLimitOutgoing, long eachLimitIncoming, long eachLimitOutgoing,
			Time startTime, Time endTime, String portalUrl, byte loginType, byte nodeStatus,
			long venderID, long businessID, byte running, long remainSms, long smsCodeValidTime, byte smsCodeValidTimeType, byte smsType, int smsCodeLength, int smsCodeDayNum, long smsCodeObtainInterval, long smsContentID) {
		this.ndName = ndName;
		this.aliasName = aliasName;
		this.mCode = mCode;
		this.industry = industry;
		this.turnOffTime = turnOffTime;
		this.turnOffFreeTime = turnOffFreeTime;
		this.multiTerminalLogin = multiTerminalLogin;
		this.limitSpeed = limitSpeed;
		this.totalLimitIncoming = totalLimitIncoming;
		this.totalLimitOutgoing = totalLimitOutgoing;
		this.eachLimitIncoming = eachLimitIncoming;
		this.eachLimitOutgoing = eachLimitOutgoing;
		this.startTime = startTime;
		this.endTime = endTime;
		this.portalUrl = portalUrl;
		this.createTime = Calendar.getInstance(TimeZone.getDefault()).getTime();
		this.updateTime = createTime;
		this.loginType = loginType;
		this.nodeStatus = nodeStatus;
		this.venderID = venderID;
		this.businessID = businessID;
		this.running = running;
		this.remainSms = remainSms;
		this.smsCodeValidTime = smsCodeValidTime;
		this.smsCodeLength = smsCodeLength;
		this.smsType = smsType;
		this.smsCodeDayNum = smsCodeDayNum;
		this.smsCodeObtainInterval = smsCodeObtainInterval;
		this.smsContentID = smsContentID;
		this.smsCodeValidTimeType = smsCodeValidTimeType;
	}
	
	public long getRemainSms() {
		return remainSms;
	}

	public void setRemainSms(long remainSms) {
		this.remainSms = remainSms;
	}

	public long getSmsCodeValidTime() {
		return smsCodeValidTime;
	}

	public void setSmsCodeValidTime(long smsCodeValidTime) {
		this.smsCodeValidTime = smsCodeValidTime;
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

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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

	public String getPortalUrl() {
		return portalUrl;
	}

	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
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

	public byte getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(byte nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public long getVenderID() {
		return venderID;
	}

	public void setVenderID(long venderID) {
		this.venderID = venderID;
	}

	public long getBusinessID() {
		return businessID;
	}

	public void setBusinessID(long businessID) {
		this.businessID = businessID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public byte getRunning() {
		return running;
	}

	public void setRunning(byte running) {
		this.running = running;
	}

	public long getSmsContentID() {
		return smsContentID;
	}

	public void setSmsContentID(long smsContentID) {
		this.smsContentID = smsContentID;
	}

	public byte getSmsCodeValidTimeType() {
		return smsCodeValidTimeType;
	}

	public void setSmsCodeValidTimeType(byte smsCodeValidTimeType) {
		this.smsCodeValidTimeType = smsCodeValidTimeType;
	}

	/*
	 * public String toString() { return "[ id : " + this.getId() +
	 * " , ndName : " + this.getNodeName() + " , aliasName : " +
	 * this.getAliasName() + " , uniqueName : " + this.getUniqueName() +
	 * " , mCode : " + this.getmCode() + " , industry : " + this.getIndustry() +
	 * " , turnOffTime : " + this.getTurnOffTime() + " , turnOffFreeTime : " +
	 * this.getTurnOffFreeTime() + " , multiTerminalLogin : " +
	 * this.getMultiTerminalLogin() + " , limitSpeed : " + this.getLimitSpeed()
	 * + " , totalLimitIncoming : " + this.getTotalLimitIncoming() +
	 * " , totalLimitOutgoing : " + this.getTotalLimitOutgoing() +
	 * " , eachLimitIncoming : " + this.getEachLimitIncoming() +
	 * " , eachLimitOutgoing : " + this.getEachLimitOutgoing() +
	 * " , startTime : " + this.getStartTime() + " , endTime : " +
	 * this.getEndTime() + " , updateTime : " + this.getUpdateTime() +
	 * " , gateWayID : " + this.getGateWayID() + " , portalUrl : " +
	 * this.getGateWayID() + "]"; }
	 */

}
