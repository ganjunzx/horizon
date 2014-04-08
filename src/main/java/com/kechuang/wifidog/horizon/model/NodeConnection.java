package com.kechuang.wifidog.horizon.model;

import java.util.Date;

public class NodeConnection extends BaseModel {
	private static final long serialVersionUID = 1L;

	public static enum STATUS {
		ONLINE, OFFLINE, INIT
	};
	
	private long id;
	private long nodeID;
	private long tokensID;
	private byte status;
	private String mac;
	private String ip;
	private String identity;
	private long incoming;
	private long outgoing;
	private long totalIncoming;
	private long totalOutgoing;
	private Date connectStart;
	private Date connectEnd;
	private String cellPhoneNum;
	private String device;
	private String webIp;
	private long userID;
	private long businessID;
	
	private byte connectType;
	private byte interrupReason;
	private long freeTime;

	private String ndName;
	private String userName;
	private String connectTypeName;
	private String businessName;
	
	public NodeConnection() {
		this.nodeID = -1;
		this.tokensID = -1;
		this.status = -1;
		this.mac = null;
		this.ip = null;
		this.identity = null;
		this.incoming = -1;
		this.outgoing = -1;
		this.totalIncoming = -1;
		this.totalOutgoing = -1;
		this.connectStart = null;
		this.connectEnd = null;
		this.cellPhoneNum = null;
		this.device = null;
		this.webIp = null;
		this.userID = -1;
		this.businessID = -1;
		this.connectType = -1;
		this.interrupReason = -1;
		this.setFreeTime(-1);
	}
	
	
	public byte getInterrupReason() {
		return interrupReason;
	}

	public void setInterrupReason(byte interrupReason) {
		this.interrupReason = interrupReason;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte getConnectType() {
		return connectType;
	}

	public void setConnectType(byte connectType) {
		this.connectType = connectType;
	}

	public String getNdName() {
		return ndName;
	}

	public void setNdName(String ndName) {
		this.ndName = ndName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public long getIncoming() {
		return incoming;
	}

	public void setIncoming(long incoming) {
		this.incoming = incoming;
	}

	public long getOutgoing() {
		return outgoing;
	}

	public void setOutgoing(long outgoing) {
		this.outgoing = outgoing;
	}

	public long getTotalIncoming() {
		return totalIncoming;
	}

	public void setTotalIncoming(long totalIncoming) {
		this.totalIncoming = totalIncoming;
	}

	public long getTotalOutgoing() {
		return totalOutgoing;
	}

	public void setTotalOutgoing(long totalOutgoing) {
		this.totalOutgoing = totalOutgoing;
	}

	public Date getConnectStart() {
		return connectStart;
	}

	public void setConnectStart(Date connectStart) {
		this.connectStart = connectStart;
	}

	public Date getConnectEnd() {
		return connectEnd;
	}

	public void setConnectEnd(Date connectEnd) {
		this.connectEnd = connectEnd;
	}

	public String getCellPhoneNum() {
		return cellPhoneNum;
	}

	public void setCellPhoneNum(String cellPhoneNum) {
		this.cellPhoneNum = cellPhoneNum;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getWebIp() {
		return webIp;
	}

	public void setWebIp(String webIp) {
		this.webIp = webIp;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getTokensID() {
		return tokensID;
	}

	public void setTokensID(long tokensID) {
		this.tokensID = tokensID;
	}

	public long getBusinessID() {
		return businessID;
	}

	public void setBusinessID(long businessID) {
		this.businessID = businessID;
	}


	public long getFreeTime() {
		return freeTime;
	}


	public void setFreeTime(long freeTime) {
		this.freeTime = freeTime;
	}


	public String getConnectTypeName() {
		return connectTypeName;
	}


	public void setConnectTypeName(String connectTypeName) {
		this.connectTypeName = connectTypeName;
	}


	public String getBusinessName() {
		return businessName;
	}


	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
