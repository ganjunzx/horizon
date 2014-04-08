package com.kechuang.wifidog.horizon.model;

import java.util.Date;


public class VenderNode extends BaseModel {
	private static final long serialVersionUID = 7575153899753577945L;

	private long id;
	private String ndName;
	private String mCode;
	private byte nodeStatus;// 0:normal 1:limit
	private Date createTime;

	private String venderName;

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
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

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
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

}
