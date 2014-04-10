package com.kechuang.wifidog.horizon.model;


public class Lever extends BaseModel {
	private static final long serialVersionUID = 1L;
	private long id;
	private String vip;
	private int maxOnlineNum;
	private double totalPriceOneMonth;
	
	public Lever () {
		this.vip = null;
		this.maxOnlineNum = -1;
		this.totalPriceOneMonth = -1;
	}
	
	public Lever (String vip, int maxOnlineNum, double totalPriceOneMonth) {
		this.vip = vip;
		this.maxOnlineNum = maxOnlineNum;
		this.totalPriceOneMonth = totalPriceOneMonth;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public int getMaxOnlineNum() {
		return maxOnlineNum;
	}

	public void setMaxOnlineNum(int maxOnlineNum) {
		this.maxOnlineNum = maxOnlineNum;
	}

	public double getTotalPriceOneMonth() {
		return totalPriceOneMonth;
	}

	public void setTotalPriceOneMonth(double totalPriceOneMonth) {
		this.totalPriceOneMonth = totalPriceOneMonth;
	}
}
