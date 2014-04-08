package com.kechuang.wifidog.horizon.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RouteRecord extends BaseModel {
	/**
	 * 记录路由运行过程中的负载情况
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long nodeID;
	private long sysUptime;
	private long sysMem;
	private float sysLoad;
	private long wifidogUptime;
	private Date createTime;

	public RouteRecord() {
		this.nodeID = -1;
		this.sysUptime = -1;
		this.sysMem = -1;
		this.sysLoad = -1;
		this.wifidogUptime = -1;
		this.createTime = null;
	}

	public RouteRecord(long nodeID, long sysUptime, long sysMem,
			float sysLoad, long wifidogUptime) {
		this.nodeID = nodeID;
		this.sysUptime = sysUptime;
		this.sysMem = sysMem;
		this.sysLoad = sysLoad;
		this.wifidogUptime = wifidogUptime;
		this.createTime = Calendar.getInstance(TimeZone.getDefault())
				.getTime();
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

	public long getSysUptime() {
		return sysUptime;
	}

	public void setSysUptime(long sysUptime) {
		this.sysUptime = sysUptime;
	}

	public long getSysMem() {
		return sysMem;
	}

	public void setSysMem(long sysMem) {
		this.sysMem = sysMem;
	}

	public float getSysLoad() {
		return sysLoad;
	}

	public void setSysLoad(float sysLoad) {
		this.sysLoad = sysLoad;
	}

	public long getWifidogUptime() {
		return wifidogUptime;
	}

	public void setWifidogUptime(long wifidogUptime) {
		this.wifidogUptime = wifidogUptime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
