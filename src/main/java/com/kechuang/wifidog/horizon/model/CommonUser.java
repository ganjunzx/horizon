package com.kechuang.wifidog.horizon.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CommonUser extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String userName;
	private String password;
	private long nodeID;//-2表示默认是所有节点
	private long userID;
	private byte userType;
	private byte multiTerminalLogin;
	private Date validTime;
	private long usableTime;//0表示默认没有可用时间
	private byte userStatus;//0表示正常1表示限制
	private String description;
	private Date createTime;
	private Date updateTime;
	private byte isLogined;

	private String nodeName;
	public CommonUser() {
		
		this.userName = null;
		this.password = null;
		this.nodeID = -1;
		this.userID = -1;
		this.userType = -1;
		this.multiTerminalLogin = -1;
		this.validTime = null;
		this.usableTime = -1;
		this.userStatus = -1;
		this.description = null;
		this.createTime = null;
		this.updateTime = null;
		this.isLogined = -1;
	}

	public CommonUser(String userName, String password, long nodeID,
			long businessID, byte userType, byte multiTerminalLogin,
			Date validTime, long usableTime, byte userStatus, String description) {
		
		this.userName = userName;
		this.password = password;
		this.nodeID = nodeID;
		this.userID = businessID;
		this.userType = userType;
		this.multiTerminalLogin = multiTerminalLogin;
		this.validTime = validTime;
		this.usableTime = usableTime;
		this.userStatus = userStatus;
		this.description = description;
		this.createTime = Calendar.getInstance(TimeZone.getDefault())
				.getTime();
		this.updateTime = createTime;
		this.isLogined = 0;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

	public byte getMultiTerminalLogin() {
		return multiTerminalLogin;
	}

	public void setMultiTerminalLogin(byte multiTerminalLogin) {
		this.multiTerminalLogin = multiTerminalLogin;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public long getUsableTime() {
		return usableTime;
	}

	public void setUsableTime(long usableTime) {
		this.usableTime = usableTime;
	}

	public byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(byte userStatus) {
		this.userStatus = userStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public byte getIsLogined() {
		return isLogined;
	}

	public void setIsLogined(byte isLogined) {
		this.isLogined = isLogined;
	}

}
