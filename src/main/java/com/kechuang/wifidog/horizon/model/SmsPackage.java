package com.kechuang.wifidog.horizon.model;



public class SmsPackage extends BaseModel {
	private static final long serialVersionUID = 7575153899753577945L;

	private int id;
	private String name;
	
	public SmsPackage(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
