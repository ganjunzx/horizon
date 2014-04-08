package com.kechuang.wifidog.horizon.model;



public class MobileLocation extends BaseModel {
	private static final long serialVersionUID = 7575153899753577945L;

	private String tel;
	private String supplier;
	private String province;
	private String city;
	private boolean success = false;
	private String errer;
	
	public String getErrer() {
		return errer;
	}
	public void setErrer(String errer) {
		this.errer = errer;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSupplier() {
		if (supplier != null) {
			return "中国" + supplier;
		} else {
			return supplier;
		}
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
