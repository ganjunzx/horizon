package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.SmsContent;

public interface SmsContentDao {
	int deleteByPrimaryKey(Long id);

	int insert(SmsContent smsRecord);

	int insertSelective(SmsContent smsRecord);
	
	SmsContent selectByMap(Map<String, Object> map);

	int updateByPrimaryKeySelective(SmsContent smsRecord);

	int updateByPrimaryKey(SmsContent smsRecord);

	List<SmsContent> listAllSmsContent(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
}
