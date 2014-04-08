package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.SmsSecurityCode;

public interface SmsSecurityCodeDao {
	int deleteByPrimaryKey(Long id);

	int insert(SmsSecurityCode smsSecurityCode);

	int insertSelective(SmsSecurityCode smsSecurityCode);
	
	SmsSecurityCode selectByMap(Map<String, Object> map);

	int updateByPrimaryKeySelective(SmsSecurityCode smsSecurityCode);

	int updateByPrimaryKey(SmsSecurityCode smsSecurityCode);

	List<SmsSecurityCode> listAllSmsSecurityCode(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
}
