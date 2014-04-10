package com.kechuang.wifidog.horizon.model;


public class BusinessNodeLever extends BaseModel {
	private static final long serialVersionUID = 1L;
	private long id;
	private String description;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
