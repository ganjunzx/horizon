package com.kechuang.wifidog.horizon.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BaseModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
