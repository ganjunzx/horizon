package com.kechuang.wifidog.horizon.model;

import java.util.Date;


public class NodeLever extends BaseModel {
	private static final long serialVersionUID = 1L;
	private long id;
	private long nodeID;
	private long vipID;
	private Date endTime;
	private int validTime;
	private Lever lever;
	
	public NodeLever () {
		this.nodeID = -1;
		this.vipID = -1;
		this.endTime = null;
		this.validTime = -1;
	}
	
	public NodeLever (long nodeID, long vipID, Date endTime, int validTime) {
		this.nodeID = nodeID;
		this.vipID = vipID;
		this.endTime = endTime;
		this.validTime = validTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getValidTime() {
		return validTime;
	}

	public void setValidTime(int validTime) {
		this.validTime = validTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getNodeID() {
		return nodeID;
	}

	public void setNodeID(long nodeID) {
		this.nodeID = nodeID;
	}

	public long getVipID() {
		return vipID;
	}

	public void setVipID(long vipID) {
		this.vipID = vipID;
	}

	public Lever getLever() {
		return lever;
	}

	public void setLever(Lever lever) {
		this.lever = lever;
	}

}
