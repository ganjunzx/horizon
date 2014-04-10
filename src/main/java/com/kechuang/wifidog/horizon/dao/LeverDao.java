package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.Lever;
import com.kechuang.wifidog.horizon.model.SmsContent;

public interface LeverDao {
	int deleteByPrimaryKey(long id);
	int insert(Lever lever);
	int insertSelective(Lever lever);
	Lever selectByMap(Map<String, Object> map);
	int updateByPrimaryKeySelective(Lever lever);
	int updateByPrimaryKey(Lever lever);
	List<Lever> listAllLever(Map<String, Object> map);
	long getTotalCount();
}
