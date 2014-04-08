package com.kechuang.wifidog.horizon.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RouteStatus extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long nodeID;// -2表示默认是所有节点
	private String webIp;
	private Date createTime;

	public RouteStatus() {
		this.nodeID = -1;
		this.webIp = null;
		this.createTime = null;
	}

	public RouteStatus(long nodeID, String webIp) {
		this.nodeID = nodeID;
		this.webIp = webIp;
		this.createTime = Calendar.getInstance(TimeZone.getDefault()).getTime();
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

	public String getWebIp() {
		return webIp;
	}

	public void setWebIp(String webIp) {
		this.webIp = webIp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
