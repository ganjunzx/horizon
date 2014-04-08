package com.kechuang.wifidog.horizon.model;


public class Tokens extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum STATUS {
		TOKEN_UNUSED, TOKEN_INUSE, TOKEN_USED
	};

	private long id;
	private long nodeID;
	private long userID;
	private byte status;
	private String token;

	public Tokens() {
		this.nodeID = -1;
		this.userID = -1;
		this.status = -1;
		this.token = null;
	}

	public long getNodeID() {
		return nodeID;
	}

	public void setNodeID(long nodeID) {
		this.nodeID = nodeID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
